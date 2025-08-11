package com.system.systembase.impl.permissionsManagement.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.exception.CustomerAuthenticationException;
import com.system.systembase.api.permissionsManagement.domain.SysMenuRole;
import com.system.systembase.api.permissionsManagement.param.EditMenuRoleParam;
import com.system.systembase.api.permissionsManagement.param.SysMenuRoleParam;
import com.system.systembase.api.permissionsManagement.service.ISysMenuRoleService;
import com.system.systembase.api.permissionsManagement.vo.SysMenuRoleVo;
import com.system.systembase.impl.permissionsManagement.mapper.SysMenuRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lutong
 * @data 2024-12-9 009 17:02
 */

@Slf4j
@Service
public class SysMenuRoleServiceImpl extends ServiceImpl<SysMenuRoleMapper, SysMenuRole> implements ISysMenuRoleService {

    @Autowired
    private SysMenuRoleMapper sysMenuRoleMapper;

    @Override
    public String editRoleMenu(EditMenuRoleParam param) {
        try {
            // 删除该用户全部菜单
            LambdaQueryWrapper<SysMenuRole> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysMenuRole::getRoleId, param.getRoleId());
            this.baseMapper.delete(wrapper);
            // 新增
            if (param.getMenuIDList() != null) {
                sysMenuRoleMapper.editRoleMenu(param);
            }
            return null;
        } catch (Exception e) {
            log.error("编辑角色菜单信息失败，失败原因：", e);
            return "编辑角色菜单信息失败!";
        }
    }

    @Override
    public List<SysMenuRoleVo> selectMenuRoleList(SysMenuRoleParam param) {
        List<SysMenuRoleVo> list = sysMenuRoleMapper.selectMenuRoleList(param);
        log.info("展示角色菜单数据：{}", JSON.toJSONString(list));
        return list;
    }

    @Override
    public List<SysMenuRoleVo> selectMenuRoleByMenuCode(String menuCode) {
        if (menuCode == null) {
            throw new CustomerAuthenticationException("菜单编码不能为空！");
        }
        List<SysMenuRoleVo> list = sysMenuRoleMapper.selectMenuRoleByMenuCode(menuCode);
        log.info("根据菜单编码查询出的数据：{}", JSON.toJSONString(list));
        return list;
    }

    @Override
    public List<SysMenuRoleVo> selectMenuRoleByRoleUid(String roleUid) {
        if (roleUid == null) {
            throw new CustomerAuthenticationException("角色UID不能为空！");
        }
        List<SysMenuRoleVo> list = sysMenuRoleMapper.selectMenuRoleByRoleUid(roleUid);
        log.info("根据角色Uid查询出的数据：{}", JSON.toJSONString(list));
        return list;
    }

    @Override
    public SysMenuRoleVo selectMenuRoleByMenuCodeAndRoleUid(String menuCode, String roleUid) {
        if (menuCode == null) {
            throw new CustomerAuthenticationException("菜单编码不能为空！");
        }
        if (roleUid == null) {
            throw new CustomerAuthenticationException("角色UID不能为空！");
        }
        SysMenuRoleVo vo = sysMenuRoleMapper.selectMenuRoleByMenuCodeAndRoleUid(menuCode, roleUid);
        log.info("根据菜单编码和角色Uid查询出的数据：{}", JSON.toJSONString(vo));
        return null;
    }
}
