package com.system.systembase.api.permissionsManagement.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.systembase.api.permissionsManagement.domain.SysUser;
import com.system.systembase.api.permissionsManagement.param.RegistrationParam;
import com.system.systembase.api.permissionsManagement.param.SysUserListParam;
import com.system.systembase.api.permissionsManagement.param.SysUserParam;
import com.system.systembase.api.permissionsManagement.vo.SysUserListVo;

import java.util.List;
import java.util.Map;

/**
 * @author lutong
 * @data 2024-11-28 028 15:18
 */


public interface ISysUserService extends IService<SysUser> {

    /**
     *  登入
     * @param param
     * @return Map<String, Object>
     */
    Map<String, Object> sysLogin(SysUserParam param);

    /**
     *  注册账户
     * @param param
     * @return String
     */
    String registration(RegistrationParam param);

    /**
     *  删除账户
     * @param param
     * @return String
     */
    String deleteUser(SysUserParam param);

    /**
     *  修改账户
     * @param param
     * @return SysUser
     */
    String updateUser(RegistrationParam param);

    /**
     *  启用-弃用账户
     * @param loginId
     * @param isDel
     * @return String
     */
    String isDelUser(String loginId, Boolean isDel);

    /**
     * 查询全部用户
     */
    Page<SysUserListVo> selectUserList(SysUserListParam param);

    /**
     *  根据loginId查询一条数据
     * @param loginId
     * @return
     */
    SysUser getOneByLoginId(String loginId);
}
