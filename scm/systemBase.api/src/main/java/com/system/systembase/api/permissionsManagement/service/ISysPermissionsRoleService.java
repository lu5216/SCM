package com.system.systembase.api.permissionsManagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.system.systembase.api.permissionsManagement.domain.SysPermissionsRole;
import com.system.systembase.api.permissionsManagement.vo.DataPermissionsRoleVo;
import com.system.systembase.api.permissionsManagement.vo.FunctionPermissionsRoleVo;

import java.util.List;


/**
 * @author lutong
 * @data 2024-12-11 011 10:26
 */
public interface ISysPermissionsRoleService extends IService<SysPermissionsRole> {

    /**
     *  根据角色UID查询数据权限
     * @param roleUid 角色UID
     * @return SysDataPermissionsRoleVo
     */
    List<DataPermissionsRoleVo> getDataPermissionsRoles(String roleUid, String menuCode);

    /**
     *  根据角色UID查询功能权限
     * @param roleUid 角色UID
     * @return SysFunctionPermissionsRoleVo
     */
    List<FunctionPermissionsRoleVo> getFunctionPermissionsRoles(String roleUid, String menuCode);

    /**
     *  变更勾选状态-根据ID
     * @param id id
     * @param isChecked 是否勾选：true勾选，false不勾选
     * @return String
     */
    String updateIsCheckedById(Integer id, Boolean isChecked);


    /**
     * 获取当前账号角色的按钮权限
     * @param roleId
     * @return
     */
    List<SysPermissionsRole> getButtonPermission(Integer roleId);


    /**
     * 获取当前账号角色的数据权限
     * @param roleId
     * @return
     */
    List<SysPermissionsRole> getDataPermission(Integer roleId);
}
