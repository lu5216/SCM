package com.system.systembase.impl.basicConfiguration.controller;

import com.system.common.utils.SecurityUtils;
import com.system.common.vo.CompanyEnumVo;
import com.system.common.vo.Result;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.basicConfiguration.enums.CommonFileTypeEnum;
import com.system.systembase.api.basicConfiguration.service.ICommonFileService;
import com.system.systembase.api.basicConfiguration.vo.CommonFileVo;
import com.system.systembase.api.redisTemplate.service.IRedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lutong
 * @data 2025-2-12 012 17:24
 */

@Slf4j
@RestController
@Api(tags = "接口-系统通用文件")
@RequestMapping("/commonFile")
@Controller
public class CommonFileController {

    @Autowired
    private ICommonFileService commonFileService;

    @Autowired
    private IRedisService redisService;


    @ApiOperation("根据文件类型列表获取文件列表")
    @PostMapping("/listByTypeList")
    public Result<List<CommonFileVo>> listByTypeList(@RequestBody List<Integer> typeList) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return Result.failed("账号未登入，请重新登入!");
        }
        List<CommonFileVo> list = commonFileService.listByTypeList(typeList, user.getCompanyUid());
        return Result.success("获取文件地址成功！", list);
    }


    @ApiOperation("根据文件类型获取文件(如果存在多个,则取最新的)")
    @GetMapping("/getByType")
    public Result<CommonFileVo> getByType(@RequestParam(value = "type") Integer type) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return Result.failed("账号未登入，请重新登入!");
        }
        CommonFileVo commonFileVo = commonFileService.getByType(type, user.getCompanyUid());
        return Result.success("获取文件地址成功！", commonFileVo);
    }


    @ApiOperation("根据文件类型和名称获取文件")
    @GetMapping("/getByTypeAndFileName")
    public Result<CommonFileVo> getByTypeAndFileName(@RequestParam(value = "type") Integer type,
                                                @RequestParam(value = "fileName") String fileName) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return Result.failed("账号未登入，请重新登入!");
        }
        CommonFileVo commonFileVo = commonFileService.getByTypeAndFileName(type, fileName, user.getCompanyUid());
        return Result.success("获取文件地址成功！", commonFileVo);
    }


    @ApiOperation("存储文件")
    @PostMapping("/saveFile")
    public Result<String> saveFile(@RequestBody CommonFileVo vo) {
        try {
            String errMSG = commonFileService.saveFile(vo);
            if (errMSG == null) {
                return Result.success("存储文件成功！");
            } else {
                return Result.failed(errMSG);
            }
        } catch (Exception e) {
            log.error("存储文件失败，失败原因：", e);
            return Result.success("存储文件失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "查询通用文件列表数据")
    @GetMapping(value = "/getCommonFileTypeList")
    public Result<List<CompanyEnumVo>> getCommonFileTypeList(@RequestParam(value = "name", required = false) String name,
                                                             @RequestParam(value = "code", required = false) String code) {
        try {
            List<CompanyEnumVo> list = new ArrayList<>();
            for (CommonFileTypeEnum commonFileTypeEnum : CommonFileTypeEnum.values()) {
                CompanyEnumVo vo = new CompanyEnumVo();
                vo.setId(commonFileTypeEnum.getId());
                vo.setName(commonFileTypeEnum.getName());
                vo.setCode(commonFileTypeEnum.getCode());
                list.add(vo);
            }
            // filter进行模糊匹配
            if (name != null) {
                list = list.stream().filter(x -> x != null && x.getName().contains(name)).collect(Collectors.toList());
            }
            if (code != null) {
                list = list.stream().filter(x -> x != null && x.getCode().contains(code)).collect(Collectors.toList());
            }
            return Result.success("查询通用文件列表数据成功！", list);
        } catch (Exception e) {
            log.error("查询通用文件列表数据失败，失败原因：" + e);
            return Result.failed("查询通用文件列表数据失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "上传图片-本地")
    @PostMapping(value = "/upload")
    public Result<String> upload(HttpServletRequest request,
                                 @RequestParam(value = "oldFileUrl", required = false) String oldFileUrl,
                                 @RequestParam(value = "file")MultipartFile file) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return Result.failed("获取用户信息异常!");
        }
        try {
            //获取文件后缀
            String path = "D://image/";
            log.info("开始上传文件，存储路径：{}", path);
            String originalFilename = file.getOriginalFilename();
            if (StringUtils.isBlank(originalFilename)) {
                return Result.failed("文件名为空");
            }

            // 文件路径
            String subPath = redisService.createNo(CommonFileTypeEnum.IMG_FILE.getCode());
            String fullPath = Paths.get(path, subPath).toString();
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs(); //若不存在该目录，则创建目录
            }

            // 上传
            String filePath = Paths.get(fullPath, originalFilename).toString();
            File destFile = new File(filePath);
            file.transferTo(destFile);
            String url = path + subPath + "/" + originalFilename;
            log.info("返回的上传结果:{}", url);

            // 保存图片到文件表
            if (oldFileUrl != null && !oldFileUrl.equals("")) {
                commonFileService.updateByTypeAndFileUrl(CommonFileTypeEnum.IMG_FILE.getId(), url, oldFileUrl, user.getCompanyUid());
            } else {
                CommonFileVo fileVo = new CommonFileVo();
                fileVo.setFileUrl(url);
                fileVo.setFileName(originalFilename);
                fileVo.setFileSize(file.getSize());
                fileVo.setType(CommonFileTypeEnum.IMG_FILE.getId());
                fileVo.setTypeName(CommonFileTypeEnum.IMG_FILE.getName());
                commonFileService.saveFile(fileVo);
            }

            // 递归删除旧文件夹及其内容
            if (oldFileUrl != null && !oldFileUrl.equals("")) {
                String[] oldUrlList = oldFileUrl.split("/", -1);
                StringBuilder deleteUrl = new StringBuilder();
                for (int i = 0; i < oldUrlList.length - 1; i++) {
                    deleteUrl.append(oldUrlList[i]).append("/");
                }
                File deleteFile = new File(String.valueOf(deleteUrl));
                commonFileService.deleteFile(deleteFile);
            }
            return Result.success("上传成功", url);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("保存上传的附件失败, 失败原因:{}", e.getMessage());
            return Result.failed("上传失败, 失败原因:{}" + e.getMessage());
        }
    }


    @ApiOperation(value = "上传图片-minIO")
    @PostMapping(value = "/uploadImage")
    public Result<String> uploadImage(@RequestParam(value = "oldFileUrl", required = false) String oldFileUrl,
                                      @RequestParam(value = "file")MultipartFile file) {
        String url = commonFileService.uploadMinIOImage(file, oldFileUrl);
        return Result.success("上传成功", url);
    }

}
