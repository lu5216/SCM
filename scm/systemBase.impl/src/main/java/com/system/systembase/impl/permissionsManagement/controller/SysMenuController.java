package com.system.systembase.impl.permissionsManagement.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.vo.Result;
import com.system.systembase.api.permissionsManagement.domain.SysMenu;
import com.system.systembase.api.permissionsManagement.domain.SysRole;
import com.system.systembase.api.permissionsManagement.param.SysMenuParam;
import com.system.systembase.api.permissionsManagement.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lutong
 * @data 2024-12-9 009 17:01
 */

@Slf4j
@RestController
@Api(tags = "权限管理-菜单配置-菜单")
@RequestMapping("/menu")
@Controller
public class SysMenuController {

    @Autowired
    private ISysMenuService sysMenuService;

    @ApiOperation(value = "查询菜单列表")
    @PostMapping(value = "/selectMenuList")
    public Result<List<SysMenu>> selectMenuList(@RequestBody SysMenuParam param) {
        List<SysMenu> sysMenuList = sysMenuService.selectMenuList(param);
        return Result.success("查询菜单列表成功！", sysMenuList);
    }

}
