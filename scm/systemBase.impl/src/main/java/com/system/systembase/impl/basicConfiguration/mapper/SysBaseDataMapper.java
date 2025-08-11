package com.system.systembase.impl.basicConfiguration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.basicConfiguration.domain.SysBaseData;
import com.system.systembase.api.basicConfiguration.vo.SysBaseDataVo;
import com.system.systembase.impl.basicConfiguration.provider.SysBaseDataProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author lutong
 * @data 2024-12-11 011 14:41
 */

@Mapper
public interface SysBaseDataMapper extends BaseMapper<SysBaseData> {

    /**
     *  父列表查询
     * @param name
     * @return
     */
    @SelectProvider(type = SysBaseDataProvider.class, method = "selectParentDict")
    List<SysBaseDataVo> selectParentDict(String name, String companyUid);

    /**
     *  子列表查询
     * @param name
     * @return
     */
    @SelectProvider(type = SysBaseDataProvider.class, method = "selectDict")
    List<SysBaseDataVo> selectDict(String name, String parentCode, String companyUid);
}
