package com.system.systembase.impl.permissionsManagement.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.system.common.exception.CustomerAuthenticationException;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.Result;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.permissionsManagement.domain.SysDataPermissions;
import com.system.systembase.api.permissionsManagement.domain.SysFunctionPermissions;
import com.system.systembase.api.permissionsManagement.domain.SysPermissionsRole;
import com.system.systembase.api.permissionsManagement.domain.SysRole;
import com.system.systembase.api.permissionsManagement.service.*;
import com.system.systembase.api.permissionsManagement.vo.DataPermissionsRoleVo;
import com.system.systembase.api.permissionsManagement.vo.FunctionPermissionsRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lutong
 * @data 2024-12-11 011 10:27
 */

@Slf4j
@RestController
@Api(tags = "权限管理-角色权限")
@RequestMapping("/permissionsRole")
@Controller
public class SysPermissionsRoleController {

    @Autowired
    private ISysPermissionsRoleService sysPermissionsRoleService;

    @Autowired
    private ISysDataPermissionsService dataPermissionsService;

    @Autowired
    private ISysFunctionPermissionsService functionPermissionsService;

    @Autowired
    private ISysRoleService sysRoleService;


    @ApiOperation(value = "查询数据权限")
    @GetMapping(value = "/getDataPermissionsRoles")
    public Result<List<DataPermissionsRoleVo>> getDataPermissionsRoles(@RequestParam(value = "roleUid") String roleUid,
                                                                       @RequestParam(value = "menuCode") String menuCode) {
        try {
            SysUserVo user = SecurityUtils.getUser();
            if (user == null) {
                throw new CustomerAuthenticationException("无法获取用户信息，请重新登入!");
            }
            List<DataPermissionsRoleVo> list = sysPermissionsRoleService.getDataPermissionsRoles(roleUid, menuCode);
            if (list.size() == 0) {
                // 查询数据权限表
                LambdaQueryWrapper<SysDataPermissions> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(SysDataPermissions::getMenuCode, menuCode);
                List<SysDataPermissions> dataPermissionsList = dataPermissionsService.list(wrapper);
                // 查询角色信息
                SysRole sysRole = sysRoleService.getOneByRoleUid(roleUid);
                // 批量新增
                List<SysPermissionsRole> permissionsRoleList = new ArrayList<>();
                if (dataPermissionsList.size() > 0) {
                    for (SysDataPermissions dataPermissions : dataPermissionsList) {
                        SysPermissionsRole sysPermissionsRole = new SysPermissionsRole();
                        sysPermissionsRole.setRoleId(sysRole.getId());
                        sysPermissionsRole.setDataId(dataPermissions.getId());
                        sysPermissionsRole.setIsChecked(false);
                        sysPermissionsRole.setCreateUserName(user.getLoginId());
                        sysPermissionsRole.setCreateTime(new Date());
                        permissionsRoleList.add(sysPermissionsRole);
                    }
                    log.info("角色数据权限新增数据：{}", JSON.toJSONString(permissionsRoleList));
                    sysPermissionsRoleService.saveBatch(permissionsRoleList);

                    // 再次查询返回数据
                    List<DataPermissionsRoleVo> resultList = sysPermissionsRoleService.getDataPermissionsRoles(roleUid, menuCode);
                    return Result.success("查询数据权限成功！", resultList);
                } else {
                    return Result.success("查询数据权限成功！", null);
                }
            } else {
                return Result.success("查询数据权限成功！", list);
            }
        } catch (Exception e) {
            log.error("根查询数据权限失败,失败原因：" + e);
            return Result.failed("查询数据权限失败,失败原因：" + e);
        }
    }



    @ApiOperation(value = "查询功能权限")
    @GetMapping(value = "/getFunctionPermissionsRoles")
    public Result<List<FunctionPermissionsRoleVo>> getFunctionPermissionsRoles(@RequestParam(value = "roleUid") String roleUid,
                                                                           @RequestParam(value = "menuCode") String menuCode) {
        try {
            SysUserVo user = SecurityUtils.getUser();
            if (user == null) {
                throw new CustomerAuthenticationException("无法获取用户信息，请重新登入!");
            }
            List<FunctionPermissionsRoleVo> list = sysPermissionsRoleService.getFunctionPermissionsRoles(roleUid, menuCode);
            if (list.size() == 0) {
                // 查询数据权限表
                LambdaQueryWrapper<SysFunctionPermissions> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(SysFunctionPermissions::getMenuCode, menuCode);
                List<SysFunctionPermissions> functionPermissionsList = functionPermissionsService.list(wrapper);
                // 查询角色信息
                SysRole sysRole = sysRoleService.getOneByRoleUid(roleUid);
                // 批量新增
                List<SysPermissionsRole> permissionsRoleList = new ArrayList<>();
                if (functionPermissionsList.size() > 0) {
                    for (SysFunctionPermissions functionPermissions : functionPermissionsList) {
                        SysPermissionsRole sysPermissionsRole = new SysPermissionsRole();
                        sysPermissionsRole.setRoleId(sysRole.getId());
                        sysPermissionsRole.setFunctionId(functionPermissions.getId());
                        sysPermissionsRole.setIsChecked(false);
                        sysPermissionsRole.setCreateUserName(user.getLoginId());
                        sysPermissionsRole.setCreateTime(new Date());
                        permissionsRoleList.add(sysPermissionsRole);
                    }
                    log.info("角色功能权限新增数据：{}", JSON.toJSONString(permissionsRoleList));
                    sysPermissionsRoleService.saveBatch(permissionsRoleList);

                    // 再次查询返回数据
                    List<FunctionPermissionsRoleVo> resultList = sysPermissionsRoleService.getFunctionPermissionsRoles(roleUid, menuCode);
                    return Result.success("查询功能权限成功！", resultList);
                } else {
                    return Result.success("查询功能权限成功！", null);
                }
            } else {
                return Result.success("查询功能权限成功！", list);
            }
        } catch (Exception e) {
            log.error("查询功能权限失败,失败原因：" + e);
            return Result.failed("查询功能权限失败,失败原因：" + e);
        }
    }


    @ApiOperation(value = "变更勾选状态")
    @GetMapping(value = "/updateIsCheckedById")
    public Result<String> updateIsCheckedById(@RequestParam(value = "id") Integer id,
                                              @RequestParam(value = "isChecked") Boolean isChecked) {
        // 校验是否存在该数据
        SysPermissionsRole permissionsRole = sysPermissionsRoleService.getById(id);
        if (permissionsRole == null) {
            return Result.failed("不存在id为【" + id + "】的数据");
        }
        if (permissionsRole.getIsChecked() == isChecked) {
            return Result.failed("未变更勾选状态！");
        }
        // 变更状态
        String result = sysPermissionsRoleService.updateIsCheckedById(id, isChecked);
        if (result == null) {
            return Result.success("变更状态成功！");
        } else {
            return Result.failed(result);
        }
    }
}
