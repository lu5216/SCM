package com.system.systembase.impl.permissionsManagement.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.systembase.api.permissionsManagement.domain.SysMenu;
import com.system.systembase.api.permissionsManagement.param.SysMenuParam;
import com.system.systembase.api.permissionsManagement.service.ISysMenuService;
import com.system.systembase.impl.permissionsManagement.mapper.SysMenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lutong
 * @data 2024-12-9 009 17:02
 */
@Slf4j
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public List<SysMenu> selectMenuList(SysMenuParam param) {
        if (param.getIsUse() == null) {
            param.setIsUse(true);
        }
        if (param.getIsShow() == null) {
            param.setIsShow(true);
        }
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getIsShow, param.getIsShow())
               .eq(SysMenu::getIsUse, param.getIsUse())
                .orderByAsc(SysMenu::getOrderNo);
        if (param.getMenuName() != null) {
            wrapper.like(SysMenu::getMenuName, param.getMenuName());
        }
        if (param.getMenuCode() != null) {
            wrapper.like(SysMenu::getMenuCode, param.getMenuCode());
        }
        if (param.getParentMenuCode() != null) {
            wrapper.like(SysMenu::getParentMenuCode, param.getParentMenuCode());
        }
        if (param.getRemark() != null) {
            wrapper.like(SysMenu::getRemark, param.getRemark());
        }
        return this.baseMapper.selectList(wrapper);
    }
}
