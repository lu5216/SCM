package com.system.systembase.impl.permissionsManagement.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.vo.Result;
import com.system.systembase.api.permissionsManagement.domain.SysRole;
import com.system.systembase.api.permissionsManagement.param.SysRoleParam;
import com.system.systembase.api.permissionsManagement.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author lutong
 * @data 2024-12-5 005 17:46
 */

@Slf4j
@RestController
@Api(tags = "权限管理-角色")
@RequestMapping("/role")
@Controller
public class SysRoleController {
    @Autowired
    private ISysRoleService sysRoleService;

    @ApiOperation(value = "增加角色")
    @PostMapping(value = "/addRole")
    public Result<String> addRole(@RequestBody SysRoleParam param) {
        String result = sysRoleService.addRole(param);
        if (result == null) {
            return Result.success("增加角色成功！");
        } else {
            log.error("增加角色失败, " + result);
            return Result.failed(result);
        }
    }

    @ApiOperation(value = "删除角色")
    @GetMapping(value = "/deleteRole")
    public Result<String> deleteRole(@RequestParam(value = "roleUid") String roleUid) {
        String result = sysRoleService.deleteRole(roleUid);
        if (result == null) {
            return Result.success("删除角色成功！");
        } else {
            log.error("删除角色失败, " + result);
            return Result.failed(result);
        }
    }

    @ApiOperation(value = "修改角色")
    @PostMapping(value = "/updateRole")
    public Result<String> updateRole(@RequestBody SysRoleParam param) {
        String result = sysRoleService.updateRole(param);
        if (result == null) {
            return Result.success("修改角色成功！");
        } else {
            log.error("修改角色失败, " + result);
            return Result.failed(result);
        }
    }

    @ApiOperation(value = "分页查询角色数据")
    @PostMapping(value = "/selectRolePage")
    public Result<Page<SysRole>> selectRolePage(@RequestBody SysRoleParam param) {
        Page<SysRole> page = sysRoleService.selectRolePage(param);
        return Result.success("分页查询角色数据成功！", page);
    }

    @ApiOperation(value = "查询角色列表")
    @GetMapping(value = "/selectRoleList")
    public Result<List<SysRole>> selectRoleList(@RequestParam(value = "role", required = false) String role) {
        List<SysRole> sysRoles = sysRoleService.selectRoleList(role);
        return Result.success("查询角色列表成功！", sysRoles);
    }

}
