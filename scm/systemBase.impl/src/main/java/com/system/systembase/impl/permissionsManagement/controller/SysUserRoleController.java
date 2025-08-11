package com.system.systembase.impl.permissionsManagement.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.vo.Result;
import com.system.systembase.api.permissionsManagement.param.SysUserRoleParam;
import com.system.systembase.api.permissionsManagement.service.ISysUserRoleService;
import com.system.systembase.api.permissionsManagement.vo.SysUserRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author lutong
 * @data 2024-12-6 006 14:17
 */

@Slf4j
@RestController
@Api(tags = "权限管理-角色配置-用户角色")
@RequestMapping("/userRole")
@Controller
public class SysUserRoleController {

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @ApiOperation(value = "账号添加/修改角色")
    @GetMapping(value = "/addUserRole")
    public Result<String> addUserRole(@RequestParam(value = "loginId") String loginId,
                                      @RequestParam(value = "roleUid") String roleUid) {
        String result = sysUserRoleService.saveUserRole(loginId, roleUid);
        if (result == null) {
            return Result.success("保存角色成功！");
        } else {
            log.error("保存角色失败, " + result);
            return Result.failed(result);
        }
    }

    @ApiOperation(value = "删除账号的角色")
    @GetMapping(value = "/deleteUserRole")
    public Result<String> deleteUserRole(@RequestParam(value = "loginId") String loginId,
                                         @RequestParam(value = "roleUid") String roleUid) {
        String result = sysUserRoleService.deleteUserRole(loginId, roleUid);
        if (result == null) {
            return Result.success("删除账号的角色成功！");
        } else {
            log.error("删除账号的角色失败, " + result);
            return Result.failed(result);
        }
    }

    @ApiOperation(value = "展示用户角色数据")
    @PostMapping(value = "/userRoleList")
    public Result<Page<SysUserRoleVo>> userRoleList(@RequestBody SysUserRoleParam param) {
        try {
            Page<SysUserRoleVo> page = sysUserRoleService.selectUserRoleList(param);
            log.info("展示用户角色数据:{}", JSON.toJSONString("page"));
            return Result.success(page);
        } catch (Exception e) {
            log.info("展示用户角色数据报错,原因: " + e);
            return Result.failed("展示用户角色数据报错,原因: " + e);
        }
    }

}
