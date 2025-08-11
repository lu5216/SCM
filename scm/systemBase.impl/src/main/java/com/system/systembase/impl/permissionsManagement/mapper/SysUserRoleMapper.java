package com.system.systembase.impl.permissionsManagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.systembase.api.permissionsManagement.domain.SysUserRole;
import com.system.systembase.api.permissionsManagement.param.SysUserRoleParam;
import com.system.systembase.api.permissionsManagement.vo.SysUserRoleVo;
import com.system.systembase.impl.permissionsManagement.provider.SysUserRoleProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author lutong
 * @data 2024-12-6 006 14:17
 */

@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {


    /**
     *  展示用户角色数据-count
     * @param param
     * @return Integer
     */
    @SelectProvider(type = SysUserRoleProvider.class, method = "selectUserRoleCount")
    Integer selectUserRoleCount(SysUserRoleParam param);

    /**
     *  展示用户角色数据-list
     * @param param
     * @return
     */
    @SelectProvider(type = SysUserRoleProvider.class, method = "selectUserRoleList")
    List<SysUserRoleVo> selectUserRoleList(SysUserRoleParam param);

    /**
     *  根据账号Id查询
     * @param loginId 账号Id
     * @return String
     */
    @SelectProvider(type = SysUserRoleProvider.class, method = "selectUserRoleByLoginId")
    SysUserRoleVo selectUserRoleByLoginId(String loginId);

    /**
     *  根据角色Uid查询
     * @param roleUid 角色Uid
     * @return String
     */
    @SelectProvider(type = SysUserRoleProvider.class, method = "selectUserRoleByRoleUid")
    List<SysUserRoleVo> selectUserRoleByRoleUid(String roleUid);

    /**
     *  根据账号Id和角色Uid查询
     * @param loginId 账号Id
     * @param roleUid 角色Uid
     * @return String
     */
    @SelectProvider(type = SysUserRoleProvider.class, method = "selectUserRoleByLoginIdAndRoleUid")
    SysUserRoleVo selectUserRoleByLoginIdAndRoleUid(String loginId, String roleUid);
}
