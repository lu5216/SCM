package com.system.systembase.impl.permissionsManagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.systembase.api.permissionsManagement.domain.SysPermissionsRole;
import com.system.systembase.api.permissionsManagement.vo.DataPermissionsRoleVo;
import com.system.systembase.api.permissionsManagement.vo.FunctionPermissionsRoleVo;
import com.system.systembase.impl.permissionsManagement.provider.SysPermissionsRoleProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author lutong
 * @data 2024-12-11 011 10:27
 */

@Mapper
public interface SysPermissionsRoleMapper extends BaseMapper<SysPermissionsRole> {

    /**
     *  根据角色UID查下数据权限
     * @param roleUid 角色UID
     * @return SysDataPermissionsRoleVo
     */
    @SelectProvider(type = SysPermissionsRoleProvider.class, method = "getDataPermissionsRoles")
    List<DataPermissionsRoleVo> getDataPermissionsRoles(String roleUid, String menuCode);

    /**
     *  根据角色UID查下功能权限
     * @param roleUid 角色UID
     * @return SysFunctionPermissionsRoleVo
     */
    @SelectProvider(type = SysPermissionsRoleProvider.class, method = "getFunctionPermissionsRoles")
    List<FunctionPermissionsRoleVo> getFunctionPermissionsRoles(String roleUid, String menuCode);

}
