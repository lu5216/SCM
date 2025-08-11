package com.system.systembase.impl.basicConfiguration.serviceImpl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.component.MinIOComponent;
import com.system.common.exception.CustomerAuthenticationException;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.MinIOProperties;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.basicConfiguration.domain.CommonFile;
import com.system.systembase.api.basicConfiguration.enums.CommonFileTypeEnum;
import com.system.systembase.api.basicConfiguration.service.ICommonFileService;
import com.system.systembase.api.basicConfiguration.vo.CommonFileVo;
import com.system.systembase.api.redisTemplate.service.IRedisService;
import com.system.systembase.impl.basicConfiguration.mapper.CommonFileMapper;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author lutong
 * @data 2025-2-12 012 17:01
 */

@Slf4j
@Service
public class CommonFileServiceImpl extends ServiceImpl<CommonFileMapper, CommonFile> implements ICommonFileService {

    @Autowired
    private MinIOProperties minIOProperties;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinIOComponent minIOComponent;

    @Autowired
    private IRedisService redisService;

    @Override
    public List<CommonFileVo> listByTypeList(List<Integer> typeList, String companyUid) {
        if (ObjectUtil.isEmpty(typeList)){
            return null;
        }

        List<CommonFileVo> fileVos = new ArrayList<>();
        LambdaQueryWrapper<CommonFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(CommonFile::getType, typeList)
                .eq(CommonFile::getStatus, 1)
                .eq(CommonFile::getCompanyUid, companyUid);
        List<CommonFile> list = this.baseMapper.selectList(wrapper);
        for (CommonFile file : list) {
            CommonFileVo fileVo = new CommonFileVo();
            BeanUtils.copyProperties(file, fileVo);
            fileVos.add(fileVo);
        }

        return fileVos;
    }

    @Override
    public CommonFileVo getByType(Integer type, String companyUid) {
        if (ObjectUtil.isNull(type)){
            return null;
        }
        LambdaQueryWrapper<CommonFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommonFile::getType, type)
                .eq(CommonFile::getStatus, 1)
                .eq(CommonFile::getCompanyUid, companyUid);
        wrapper.orderByDesc(CommonFile::getId);
        CommonFile commonFile = this.baseMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(commonFile)){
            return null;
        }
        CommonFileVo commonFileVo = new CommonFileVo();
        BeanUtils.copyProperties(commonFile, commonFileVo);
        return commonFileVo;
    }

    @Override
    public CommonFileVo getByTypeAndFileName(Integer type, String fileName, String companyUid) {
        if (ObjectUtil.isNull(type)){
            throw new CustomerAuthenticationException("文件类型不能为空！");
        }
        if (ObjectUtil.isNull(fileName)){
            throw new CustomerAuthenticationException("文件名称不能为空！");
        }
        LambdaQueryWrapper<CommonFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommonFile::getType, type)
                .eq(CommonFile::getFileName, fileName)
                .eq(CommonFile::getStatus, 1)
                .eq(CommonFile::getCompanyUid, companyUid);
        CommonFile commonFile = this.getOne(wrapper);
        if (ObjectUtil.isNull(commonFile)) {
            return new CommonFileVo();
        }
        CommonFileVo commonFileVo = new CommonFileVo();
        BeanUtils.copyProperties(commonFile, commonFileVo);
        return commonFileVo;
    }

    @Override
    public void updateByTypeAndFileUrl(Integer type, String newFileUrl, String oldFileUrl, String companyUid) {
        if (ObjectUtil.isNull(newFileUrl)){
            throw new CustomerAuthenticationException("文件名称不能为空！");
        }
        if (ObjectUtil.isNull(oldFileUrl)){
            throw new CustomerAuthenticationException("文件名称不能为空！");
        }
        if (ObjectUtil.isNull(type)){
            throw new CustomerAuthenticationException("文件类型不能为空！");
        }
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            throw new CustomerAuthenticationException("账号未登入，请重新登入!");
        }
        LambdaQueryWrapper<CommonFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommonFile::getType, type)
                .eq(CommonFile::getFileUrl, oldFileUrl)
                .eq(CommonFile::getStatus, 1)
                .eq(CommonFile::getCompanyUid, companyUid);
        CommonFile commonFile = this.getOne(wrapper);
        if (commonFile != null) {
            commonFile.setFileUrl(newFileUrl);
            commonFile.setUpdateTime(new Date());
            commonFile.setUpdateUserName(user.getLoginId());
            this.baseMapper.updateById(commonFile);
            log.info("文件{}的存储路径由:{}, 修改为:{}", commonFile.getFileName(), oldFileUrl, newFileUrl);
        }
    }

    @Override
    public String saveFile(CommonFileVo vo) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }

        // 校验保存文件
        String errMSG = this.checkData(vo);
        if ( errMSG != null) {
            return errMSG;
        }

        // 根据ID设置文件业务类型名称
        if (vo.getTypeName() == null || vo.getTypeName().equals("")) {
            CommonFileTypeEnum fileTypeEnum = CommonFileTypeEnum.getById(vo.getType());
            if (fileTypeEnum != null) {
                vo.setTypeName(fileTypeEnum.getName());
            }
        }

        //保存
        CommonFile file = new CommonFile();
        BeanUtils.copyProperties(vo, file);
        file.setStatus(true);
        file.setCompanyUid(user.getCompanyUid());
        file.setCreateUserName(user.getLoginId());
        file.setCreateTime(new Date());
        this.baseMapper.insert(file);
        return null;
    }

    @Override
    @Transactional
    public String uploadMinIOImage(MultipartFile file, String oldFileUrl) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            throw new CustomerAuthenticationException("获取用户信息异常!");
        }
        try {
            // 文件真实名称
            String originalFilename = file.getOriginalFilename();
            // 文件拓展名
            // String extName = originalFilename.substring(originalFilename.lastIndexOf("."));

            // 查询桶是否存在
            boolean bucketExist = minIOComponent.checkBucketExist(minIOProperties.getBucketName());
            if (!bucketExist) {
                throw new CustomerAuthenticationException("桶【" + minIOProperties.getBucketName() + "】不存在");
            }

            // 删除文件夹
            if (oldFileUrl != null && !oldFileUrl.equals("")) {
                String deleteUrl = oldFileUrl.replace(minIOProperties.getUrl() + "/", "");
                String[] folder = deleteUrl.split("/");
                minIOComponent.deleteBucketFolder(minIOProperties.getBucketName(), folder[1] + "/");
            }

            // 创建文件夹
            String folderName = redisService.createNo(CommonFileTypeEnum.IMG_FILE.getCode());
            minIOComponent.createBucketFolder(minIOProperties.getBucketName(), folderName);

            // 构建文件上传相关信息
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minIOProperties.getBucketName())
                    .object(folderName + "/" + originalFilename)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(minIOComponent.getFileContentType(Objects.requireNonNull(originalFilename)))
                    .build();
            // 上传
            minioClient.putObject(args);

            // 返回链接
            String url = minIOProperties.getUrl() + "/" + minIOProperties.getBucketName() + "/" + folderName + "/" + originalFilename;

            // 保存图片到文件表
            if (oldFileUrl != null && !oldFileUrl.equals("")) {
                this.updateByTypeAndFileUrl(CommonFileTypeEnum.IMG_FILE.getId(), url, oldFileUrl, user.getCompanyUid());
            } else {
                CommonFileVo fileVo = new CommonFileVo();
                fileVo.setFileUrl(url);
                fileVo.setFileName(originalFilename);
                fileVo.setFileSize(file.getSize());
                fileVo.setType(CommonFileTypeEnum.IMG_FILE.getId());
                fileVo.setTypeName(CommonFileTypeEnum.IMG_FILE.getName());
                fileVo.setLocalUrl(minIOProperties.getUrl());
                this.saveFile(fileVo);
            }

            log.info(originalFilename + "文件上传成功，返回链接：" + url);
            return url;
        } catch (Exception e) {
            throw new CustomerAuthenticationException("文件上传异常，原因：" + e);
        }
    }

    /**
     *  校验保存文件
     * @param vo
     * @return
     */
    public String checkData(CommonFileVo vo) {
        if (vo == null) {
            return "参数不能为空";
        }
        if (vo.getFileName() == null) {
            return "文件名称不能为空";
        }
        if (vo.getFileUrl() == null) {
            return "文件URL不能为空";
        }
        if (vo.getType() == null) {
            return "文件业务类型不能为空";
        }
        return null;
    }

    /**
     * 递归删除文件夹及其内容
     * @param file
     * @return
     */
    public void deleteFile(File file){
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteFile(f);
                }
            }
        }
        if (file.delete()) {
            log.info("文件删除成功: " + file.getPath());
        } else {
            log.info("文件删除失败: " + file.getPath());
        }
    }
}
