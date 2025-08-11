package com.system.systembase.api.basicConfiguration.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.system.systembase.api.basicConfiguration.domain.CommonFile;
import com.system.systembase.api.basicConfiguration.vo.CommonFileVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author lutong
 * @data 2025-2-12 012 17:00
 */

public interface ICommonFileService extends IService<CommonFile> {

    /**
     * 根据文件类型列表获取文件列表
     * @param typeList 文件业务类型列表
     * @return
     */
    List<CommonFileVo> listByTypeList(List<Integer> typeList, String companyUid);


    /**
     * 根据文件类型获取文件(如果存在多个,则取最新的)
     * @param type 文件业务类型
     * @return
     */
    CommonFileVo getByType(Integer type, String companyUid);


    /**
     *  根据文件类型和名称获取文件
     * @param type
     * @param fileName
     * @return
     */
    CommonFileVo getByTypeAndFileName(Integer type, String fileName, String companyUid);

    /**
     * 根据文件类型和名称修改文件
     * @author lutong
     * @data 2025-3-17 017
     * @param type
     * @param newFileUrl
     * @param oldFileUrl
     * @param companyUid
     * @return java.lang.String
     **/
    void updateByTypeAndFileUrl(Integer type, String newFileUrl, String oldFileUrl, String companyUid);

    /**
     *  存储文件地址-新增
     * @param vo
     * @return
     */
    String saveFile(CommonFileVo vo);

    /**
     * 存储图片到minIO
     * @author lutong
     * @data 2025-3-24 024
     * @param file
     * @param oldFileUrl
     * @return java.lang.String
     **/
    String uploadMinIOImage(MultipartFile file, String oldFileUrl);

    /**
     * 递归删除文件夹及其内容
     * @author lutong
     * @data 2025-3-17 017
     * @param file
     * @return void
     **/
    void deleteFile(File file);

}
