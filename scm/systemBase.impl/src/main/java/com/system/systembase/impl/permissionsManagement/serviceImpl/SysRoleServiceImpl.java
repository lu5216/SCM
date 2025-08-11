package com.system.systembase.impl.permissionsManagement.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.utils.SecurityUtils;
import com.system.common.utils.SpringContextUtil;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.permissionsManagement.domain.SysRole;
import com.system.systembase.api.permissionsManagement.param.SysRoleParam;
import com.system.systembase.api.permissionsManagement.service.ISysRoleService;
import com.system.systembase.api.permissionsManagement.service.ISysUserRoleService;
import com.system.systembase.api.permissionsManagement.vo.SysUserRoleVo;
import com.system.systembase.impl.permissionsManagement.mapper.SysRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lutong
 * @data 2024-12-5 005 17:47
 */

@Slf4j
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {


    /**
     *  根据角色查询一条数据
     * @param role
     * @return SysRole
     */
    public SysRole selectRole(String role) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRole, role);
        return this.baseMapper.selectOne(wrapper);
    }

    /**
     *  根据角色Uid查询一条数据
     * @param roleUid
     * @return SysRole
     */
    public SysRole selectRoleUid(String roleUid) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleUid, roleUid);
        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    public String addRole(SysRoleParam param) {
        // 数据校验
        if (param.getRole() == null) {
            return "角色不能为空！";
        }
        if (param.getRoleUid() == null) {
            return "角色UID不能为空！";
        }
        SysRole role = this.selectRole(param.getRole());
        if (role != null) {
            return "角色已存在，请修改！";
        }
        SysRole roleUid = this.selectRoleUid(param.getRoleUid());
        if (roleUid != null) {
            return "角色Uid已存在，请修改！";
        }
        // 新增
        SysUserVo user = SecurityUtils.getUser();
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(param, sysRole);
        sysRole.setCreateUserName(user.getLoginId());
        sysRole.setCreateTime(new Date());
        this.baseMapper.insert(sysRole);
        return null;
    }

    @Override
    @Transactional
    public String deleteRole(String roleUid) {
        // 校验
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleUid, roleUid);
        SysRole sysRole = this.baseMapper.selectOne(wrapper);
        if (sysRole == null) {
            return "不存在该角色Uid!";
        }
        // 删除关联了该角色的用户角色信息
        ISysUserRoleService bean = SpringContextUtil.getBean(ISysUserRoleService.class);
        List<SysUserRoleVo> sysUserRoleVoList = bean.selectUserRoleByRoleUid(roleUid);
        if (sysUserRoleVoList != null) {
            for (SysUserRoleVo vo : sysUserRoleVoList) {
                bean.deleteUserRole(vo.getLoginId(), roleUid);
            }
        }

        // 删除角色
        this.baseMapper.delete(wrapper);
        return null;
    }

    @Override
    public String updateRole(SysRoleParam param) {
        // 校验
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleUid, param.getRoleUid());
        SysRole roleUid = this.baseMapper.selectOne(wrapper);
        if (roleUid == null) {
            return "不存在该角色Uid!";
        }
        // 新增
        SysUserVo user = SecurityUtils.getUser();
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(param, sysRole);
        sysRole.setUpdateUserName(user.getLoginId());
        sysRole.setUpdateTime(new Date());
        this.baseMapper.update(sysRole, wrapper);
        return null;
    }

    @Override
    public Page<SysRole> selectRolePage(SysRoleParam param) {
        if (param.getPageIndex() == null) {
            param.setPageIndex(1);
        }
        if (param.getPageSize() == null) {
            param.setPageSize(20);
        }
        Page<SysRole> page = new Page<>(param.getPageIndex(), param.getPageSize());
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        if (param.getRole() != null && !param.getRole().equals("")) {
            wrapper.like(SysRole::getRole, param.getRole());
        }
        if (param.getRoleUid() != null && !param.getRoleUid().equals("")) {
            wrapper.like(SysRole::getRoleUid, param.getRoleUid());
        }
        if (param.getRoleRemark() != null && !param.getRoleRemark().equals("")) {
            wrapper.like(SysRole::getRoleRemark, param.getRoleRemark());
        }
        if (param.getCreateUserName() != null && !param.getCreateUserName().equals("")) {
            wrapper.like(SysRole::getCreateUserName, param.getCreateUserName());
        }

        List<SysRole> sysRoleList = this.baseMapper.selectList(wrapper);
        page.setTotal(sysRoleList.size());

        // 分页
        sysRoleList = sysRoleList.stream().skip((long) (param.getPageIndex() - 1) * param.getPageSize()).limit(param.getPageSize()).collect(Collectors.toList());
        page.setRecords(sysRoleList);
        return page;
    }


    @Override
    public List<SysRole> selectRoleList(String role) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        if (role != null && !role.equals("")) {
            wrapper.like(SysRole::getRole, role);
        }

        return this.baseMapper.selectList(wrapper);
    }

    @Override
    public SysRole getOneByRoleUid(String roleUid) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleUid, roleUid);
        return this.baseMapper.selectOne(wrapper);
    }
}
