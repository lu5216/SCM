package com.system.systembase.impl.permissionsManagement.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.exception.CustomerAuthenticationException;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.permissionsManagement.domain.SysRole;
import com.system.systembase.api.permissionsManagement.domain.SysUser;
import com.system.systembase.api.permissionsManagement.domain.SysUserRole;
import com.system.systembase.api.permissionsManagement.param.SysUserRoleParam;
import com.system.systembase.api.permissionsManagement.service.ISysRoleService;
import com.system.systembase.api.permissionsManagement.service.ISysUserRoleService;
import com.system.systembase.api.permissionsManagement.service.ISysUserService;
import com.system.systembase.api.permissionsManagement.vo.SysUserRoleVo;
import com.system.systembase.impl.permissionsManagement.mapper.SysUserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author lutong
 * @data 2024-12-6 006 14:18
 */

@Slf4j
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     *  校验用户是否存在
     * @param loginId 账号Id
     * @return SysUser
     */
    public SysUser verifyUser(String loginId) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getLoginId, loginId);
        return sysUserService.getOne(wrapper);
    }

    /**
     *  校验角色是否存在
     * @param roleUid 角色Uid
     * @return SysRole
     */
    public SysRole verifyRole(String roleUid) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleUid, roleUid);
        return sysRoleService.getOne(wrapper);
    }

    @Override
    public String saveUserRole(String loginId, String roleUid) {
        SysUser sysUser = this.verifyUser(loginId);
        if (sysUser == null) {
            return "该用户账号不存在, 请维护！";
        }
        SysRole sysRole = verifyRole(roleUid);
        if (sysRole == null) {
            return "该角色Uid不存在, 请维护！";
        }
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "登录信息过期，请重新登入！";
        }
        SysUserRoleVo userRoleVo = this.sysUserRoleMapper.selectUserRoleByLoginId(loginId);
        if (userRoleVo == null) {
            // 新增
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUser.getId());
            sysUserRole.setRoleId(sysRole.getId());
            sysUserRole.setCreateUserName(user.getLoginId());
            sysUserRole.setCreateTime(new Date());
            this.baseMapper.insert(sysUserRole);
        } else {
            if (Objects.equals(userRoleVo.getRoleUid(), roleUid)) {
                // 直接结束，不修改
                return null;
            }
            // 修改
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setId(userRoleVo.getId());
            sysUserRole.setRoleId(sysRole.getId());
            sysUserRole.setUpdateUserName(user.getLoginId());
            sysUserRole.setUpdateTime(new Date());
            this.baseMapper.updateById(sysUserRole);
        }

        return null;
    }

    @Override
    public String deleteUserRole(String loginId, String roleUid) {
        SysUser sysUser = this.verifyUser(loginId);
        if (sysUser == null) {
            return "该用户账号不存在, 请维护！";
        }
        SysRole sysRole = verifyRole(roleUid);
        if (sysRole == null) {
            return "该角色Uid不存在, 请维护！";
        }
        SysUserRoleVo sysUserRoleVo = this.selectUserRoleByLoginIdAndRoleUid(loginId, roleUid);
        if (sysUserRoleVo == null) {
            return "该用户角色关联信息不存在, 请维护！";
        }
        this.baseMapper.deleteById(sysUserRoleVo.getId());
        return null;
    }


    @Override
    public Page<SysUserRoleVo> selectUserRoleList(SysUserRoleParam param) {
        if (param.getPageIndex() == null) {
            param.setPageIndex(1);
        }
        if (param.getPageSize() == null) {
            param.setPageSize(20);
        }
        Page<SysUserRoleVo> page = new Page<>(param.getPageIndex(), param.getPageSize());
        Integer count = this.sysUserRoleMapper.selectUserRoleCount(param);
        page.setCurrent(count);
        if (count > 0) {
            List<SysUserRoleVo> sysUserRoleVoList = this.sysUserRoleMapper.selectUserRoleList(param);
            page.setRecords(sysUserRoleVoList);
        }
        return count > 0 ? page : new Page<>();
    }

    @Override
    public SysUserRoleVo selectUserRoleByLoginId(String loginId) {
        SysUser sysUser = this.verifyUser(loginId);
        if (sysUser == null) {
            throw new CustomerAuthenticationException("该用户账号不存在, 请维护！");
        }
        return this.sysUserRoleMapper.selectUserRoleByLoginId(loginId);
    }

    @Override
    public List<SysUserRoleVo> selectUserRoleByRoleUid(String roleUid) {
        SysRole sysRole = this.verifyRole(roleUid);
        if (sysRole == null) {
            throw new CustomerAuthenticationException("该角色Uid不存在, 请维护！");
        }
        return this.sysUserRoleMapper.selectUserRoleByRoleUid(roleUid);
    }

    @Override
    public SysUserRoleVo selectUserRoleByLoginIdAndRoleUid(String loginId, String roleUid) {
        SysUser sysUser = this.verifyUser(loginId);
        if (sysUser == null) {
            throw new CustomerAuthenticationException("该用户账号不存在, 请维护！");
        }
        SysRole sysRole = this.verifyRole(roleUid);
        if (sysRole == null) {
            throw new CustomerAuthenticationException("该角色Uid不存在, 请维护！");
        }
        return this.sysUserRoleMapper.selectUserRoleByLoginIdAndRoleUid(loginId, roleUid);
    }
}
