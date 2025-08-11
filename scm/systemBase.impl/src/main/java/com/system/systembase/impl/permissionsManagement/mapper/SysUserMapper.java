package com.system.systembase.impl.permissionsManagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.systembase.api.permissionsManagement.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author lutong
 * @data 2024-11-28 028 15:19
 */

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     *  根据loginId查询用户信息
     * @param loginId
     * @return SysUser
     */
    @Select("SELECT * FROM scm_sys_user WHERE LOGIN_ID = #{loginId} and IS_DEL = 0")
    SysUser selectByLoginId(@Param("loginId") String loginId);
}
