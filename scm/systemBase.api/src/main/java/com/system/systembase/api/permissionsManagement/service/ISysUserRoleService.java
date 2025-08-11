package com.system.systembase.api.permissionsManagement.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.systembase.api.permissionsManagement.domain.SysUserRole;
import com.system.systembase.api.permissionsManagement.param.SysUserRoleParam;
import com.system.systembase.api.permissionsManagement.vo.SysUserRoleVo;

import java.util.List;

/**
 * @author lutong
 * @data 2024-12-6 006 14:16
 */

public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     *  给账号添加角色
     * @param loginId 账号id
     * @param roleUid 角色Uid
     * @return String
     */
    String saveUserRole(String loginId, String roleUid);

    /**
     *  删除账号的角色
     * @param loginId 账号id
     * @param roleUid 角色Uid
     * @return String
     */
    String deleteUserRole(String loginId, String roleUid);

    /**
     *  展示用户角色数据
     * @param param param
     * @return Page
     */
    Page<SysUserRoleVo> selectUserRoleList(SysUserRoleParam param);

    /**
     *  根据账号Id查询
     * @param loginId 账号Id
     * @return String
     */
    SysUserRoleVo selectUserRoleByLoginId(String loginId);

    /**
     *  根据角色Uid查询
     * @param roleUid 角色Uid
     * @return String
     */
    List<SysUserRoleVo> selectUserRoleByRoleUid(String roleUid);

    /**
     *  根据账号Id和角色Uid查询
     * @param loginId 账号Id
     * @param roleUid 角色Uid
     * @return String
     */
    SysUserRoleVo selectUserRoleByLoginIdAndRoleUid(String loginId, String roleUid);
}
