package com.system.systembase.impl.permissionsManagement.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.permissionsManagement.domain.SysPermissionsRole;
import com.system.systembase.api.permissionsManagement.service.ISysPermissionsRoleService;
import com.system.systembase.api.permissionsManagement.vo.DataPermissionsRoleVo;
import com.system.systembase.api.permissionsManagement.vo.FunctionPermissionsRoleVo;
import com.system.systembase.impl.permissionsManagement.mapper.SysPermissionsRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lutong
 * @data 2024-12-11 011 10:28
 */

@Slf4j
@Service
public class SysPermissionsRoleServiceImpl extends ServiceImpl<SysPermissionsRoleMapper, SysPermissionsRole>
        implements ISysPermissionsRoleService {

    @Autowired
    private SysPermissionsRoleMapper sysPermissionsRoleMapper;

    @Override
    public List<DataPermissionsRoleVo> getDataPermissionsRoles(String roleUid, String menuCode) {
        List<DataPermissionsRoleVo> list = sysPermissionsRoleMapper.getDataPermissionsRoles(roleUid, menuCode);
        return list;
    }

    @Override
    public List<FunctionPermissionsRoleVo> getFunctionPermissionsRoles(String roleUid, String menuCode) {
        List<FunctionPermissionsRoleVo> list = sysPermissionsRoleMapper.getFunctionPermissionsRoles(roleUid, menuCode);
        return list;
    }

    @Override
    public String updateIsCheckedById(Integer id, Boolean isChecked) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "无法获取用户信息，请重新登入!";
        }
        SysPermissionsRole sysPermissionsRole = new SysPermissionsRole();
        sysPermissionsRole.setId(id);
        sysPermissionsRole.setIsChecked(isChecked);
        sysPermissionsRole.setUpdateUserName(user.getLoginId());
        sysPermissionsRole.setUpdateTime(new Date());
        this.baseMapper.updateById(sysPermissionsRole);
        return null;
    }


    @Override
    public List<SysPermissionsRole> getButtonPermission(Integer roleId) {
        LambdaQueryWrapper<SysPermissionsRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPermissionsRole::getRoleId, roleId);
        wrapper.eq(SysPermissionsRole::getIsChecked, true);
        wrapper.isNotNull(SysPermissionsRole::getFunctionId);
        return this.baseMapper.selectList(wrapper);
    }


    @Override
    public List<SysPermissionsRole> getDataPermission(Integer roleId) {
        LambdaQueryWrapper<SysPermissionsRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPermissionsRole::getRoleId, roleId);
        wrapper.eq(SysPermissionsRole::getIsChecked, true);
        wrapper.isNotNull(SysPermissionsRole::getDataId);
        return this.baseMapper.selectList(wrapper);
    }
}
