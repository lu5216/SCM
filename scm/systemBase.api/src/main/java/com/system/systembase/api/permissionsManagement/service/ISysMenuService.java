package com.system.systembase.api.permissionsManagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.system.systembase.api.permissionsManagement.domain.SysMenu;
import com.system.systembase.api.permissionsManagement.param.SysMenuParam;

import java.util.List;

/**
 * @author lutong
 * @data 2024-12-9 009 17:01
 */

public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 查询全部菜单
     */
    List<SysMenu> selectMenuList(SysMenuParam param);
}
