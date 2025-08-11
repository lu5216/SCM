package com.system.systembase.impl.basicConfiguration.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.Result;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.basicConfiguration.domain.SysClient;
import com.system.systembase.api.basicConfiguration.param.SysClientParam;
import com.system.systembase.api.basicConfiguration.service.ISysClientService;
import com.system.systembase.api.basicConfiguration.vo.SysCVListVo;
import com.system.systembase.api.basicConfiguration.vo.SysClientDetail;
import com.system.systembase.api.basicConfiguration.vo.SysClientVo;
import com.system.systembase.api.basicConfiguration.vo.SysVendorDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author lutong
 * @data 2024-12-24 024 14:06
 */

@Slf4j
@RestController
@Api(tags = "基础配置-客户管理")
@RequestMapping("/client")
@Controller
public class SysClientController {

    @Autowired
    private ISysClientService sysClientService;

    @ApiOperation(value = "查询客户分页数据")
    @PostMapping(value = "/selectClientPage")
    public Result<Page<SysClientVo>> selectDict(@RequestBody SysClientParam param) {
        try {
            Page<SysClientVo> page = sysClientService.selectClientPage(param);
            return Result.success("查询客户分页数据成功！", page);
        } catch (Exception e) {
            log.error("查询客户分页数据失败，失败原因：" + e);
            return Result.failed("查询客户分页数据失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "查询客户详情数据")
    @GetMapping(value = "/selectClientDetail")
    public Result<SysClientDetail> selectClientDetail(@RequestParam(value = "clientCode") String clientCode) {
        try {
            SysClientDetail detail = sysClientService.selectClientDetail(clientCode);
            return Result.success("查询客户详情数据成功！", detail);
        } catch (Exception e) {
            log.error("查询客户详情数据失败，失败原因：" + e);
            return Result.failed("查询客户详情数据失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "修改客户详情数据")
    @PostMapping(value = "/updateClientDetail")
    public Result<String> updateClientDetail(@RequestBody SysClientDetail detail) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return Result.failed("账号未登入，请重新登入!");
        }
        detail.setUpdateTime(new Date());
        detail.setUpdateUserName(user.getLoginId());
        try {
            String result = sysClientService.updateClientDetail(detail);
            if (result != null) {
                return Result.failed(result);
            }
            return Result.success("修改客户详情数据成功！");
        } catch (Exception e) {
            log.error("修改客户详情数据失败，失败原因：" + e);
            return Result.failed("修改客户详情数据失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "增加客户数据")
    @PostMapping(value = "/addClientData")
    public Result<String> addClientData(@RequestBody SysClient sysClient) {
        try {
            String result = sysClientService.addClientData(sysClient);
            if (result != null) {
                return Result.failed(result);
            }
            return Result.success("增加客户数据成功！");
        } catch (Exception e) {
            log.error("增加客户数据失败，失败原因：" + e);
            return Result.failed("增加客户数据失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "删除客户数据")
    @GetMapping(value = "/deleteClientData")
    public Result<String> deleteClientData(@RequestParam(value = "id") Integer id) {
        try {
            String result = sysClientService.deleteClientData(id);
            if (result != null) {
                return Result.failed(result);
            }
            return Result.success("删除客户数据成功！");
        } catch (Exception e) {
            log.error("删除客户数据失败，失败原因：" + e);
            return Result.failed("删除客户数据失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "更改合作状态")
    @GetMapping(value = "/updateIsCooperation")
    public Result<String> updateIsCooperation(@RequestParam(value = "clientCode") String clientCode) {
        try {
            String result = sysClientService.updateIsCooperation(clientCode);
            if (result != null) {
                return Result.failed(result);
            }
            return Result.success("修改成功！");
        } catch (Exception e) {
            log.error("修改失败，失败原因：" + e);
            return Result.failed("修改失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "明细-更改生效状态")
    @GetMapping(value = "/updateIsValidated")
    public Result<String> updateIsValidated(@RequestParam(value = "clientCode") String clientCode) {
        try {
            String result = sysClientService.updateIsValidated(clientCode);
            if (result != null) {
                return Result.failed(result);
            }
            return Result.success("修改成功！");
        } catch (Exception e) {
            log.error("修改失败，失败原因：" + e);
            return Result.failed("修改失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "获取客户列表")
    @GetMapping(value = "/getClientList")
    public Result<List<SysCVListVo>> getClientList(@RequestParam(value = "key", required = false) String key) {
        try {
            List<SysCVListVo> list = sysClientService.getClientList(key);
            return Result.success("获取客户列表成功！", list);
        } catch (Exception e) {
            log.error("获取客户列表失败，失败原因：" + e);
            return Result.failed("获取客户列表失败，失败原因：" + e);
        }
    }
}
