package com.system.systembase.impl.basicConfiguration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.systembase.api.basicConfiguration.domain.SysCompany;
import com.system.systembase.api.basicConfiguration.param.SysCompanyParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lutong
 * @data 2024-12-13 013 14:04
 */

@Mapper
public interface SysCompanyMapper extends BaseMapper<SysCompany> {

    @Select("select * \n" +
            "from scm_sys_company")
    List<SysCompany> selectCompany(@Param("param") SysCompanyParam param);
}
