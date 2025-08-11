package com.system.systembase.api.basicConfiguration.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.system.systembase.api.basicConfiguration.domain.SysBaseData;
import com.system.systembase.api.basicConfiguration.vo.SysBaseDataVo;

import java.util.List;

/**
 * @author lutong
 * @data 2024-12-11 011 14:41
 */
public interface ISysBaseDataService extends IService<SysBaseData> {

    /**
     *  父列表查询
     * @param name
     * @return
     */
    List<SysBaseDataVo> selectParentDict(String name);

    /**
     *  子列表查询
     * @param name
     * @return
     */
    List<SysBaseDataVo> selectDict(String name, String parentCode);

    /**
     *  修改数据字典
     * @param param
     * @return
     */
    String updateDict(SysBaseData param);

    /**
     *  新增数据字典
     * @param param
     * @return
     */
    String addDict(SysBaseData param);

    /**
     *  删除数据字典
     * @param id
     * @return
     */
    String deleteDict(Integer id);
}
