package com.system.systembase.api.permissionsManagement.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.systembase.api.permissionsManagement.domain.SysRole;
import com.system.systembase.api.permissionsManagement.param.SysRoleParam;

import java.util.List;


/**
 * @author lutong
 * @data 2024-12-5 005 17:50
 */

public interface ISysRoleService extends IService<SysRole> {

    /**
     *  增加角色
     * @param param
     * @return SysRole
     */
    String addRole(SysRoleParam param);

    /**
     *  根据角色UID删除角色
     * @param roleUid
     * @return String
     */
    String deleteRole(String roleUid);

    /**
     *  修改角色
     * @param param
     * @return String
     */
    String updateRole(SysRoleParam param);

    /**
     *  分页查询角色
     * @param param
     * @return
     */
    Page<SysRole> selectRolePage(SysRoleParam param);

    /**
     *  查询角色列表
     * @param role
     * @return
     */
    List<SysRole> selectRoleList(String role);

    /**
     *  查询角色列表
     * @param roleUid 角色UID
     * @return
     */
    SysRole getOneByRoleUid(String roleUid);
}
