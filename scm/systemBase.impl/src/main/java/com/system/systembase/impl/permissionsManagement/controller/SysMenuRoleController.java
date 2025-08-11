package com.system.systembase.impl.permissionsManagement.controller;

import com.system.common.vo.Result;
import com.system.systembase.api.permissionsManagement.param.EditMenuRoleParam;
import com.system.systembase.api.permissionsManagement.param.SysMenuRoleParam;
import com.system.systembase.api.permissionsManagement.service.ISysMenuRoleService;
import com.system.systembase.api.permissionsManagement.vo.SysMenuRoleVo;
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
@Api(tags = "权限管理-菜单配置-角色菜单")
@RequestMapping("/menuRole")
@Controller
public class SysMenuRoleController {
    @Autowired
    private ISysMenuRoleService sysMenuRoleService;

    @ApiOperation(value = "编辑角色菜单")
    @PostMapping(value = "/editRoleMenu")
    public Result<String> editRoleMenu(@RequestBody EditMenuRoleParam param) {
        String result = sysMenuRoleService.editRoleMenu(param);
        if (result == null) {
            return Result.success("编辑角色菜单信息成功！");
        } else {
            return Result.failed(result);
        }
    }

    @ApiOperation(value = "展示角色菜单数据")
    @PostMapping(value = "/selectMenuRoleList")
    public Result<List<SysMenuRoleVo>> selectMenuRoleList(@RequestBody SysMenuRoleParam param) {
        try {
            List<SysMenuRoleVo> list = sysMenuRoleService.selectMenuRoleList(param);
            return Result.success("展示角色菜单数据成功！", list);
        } catch (Exception e) {
            log.error("展示角色菜单数据失败,失败原因：" + e);
            return Result.failed("展示角色菜单数据失败,失败原因：" + e);
        }
    }
}
