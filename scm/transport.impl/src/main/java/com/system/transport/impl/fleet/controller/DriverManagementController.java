package com.system.transport.impl.fleet.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.utils.SecurityUtils;
import com.system.common.utils.UploadOrExportExcelUtil;
import com.system.common.vo.Result;
import com.system.common.vo.SysUserVo;
import com.system.transport.api.fleet.domain.DriverManagement;
import com.system.transport.api.fleet.params.DriverManagementParam;
import com.system.transport.api.fleet.service.IDriverManagementService;
import com.system.transport.api.fleet.vo.DriverVehicleListVo;
import com.system.transport.api.fleet.vo.DriverVehicleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author lutong
 * @data 2025-2-10 010 16:42
 */
@Slf4j
@RestController
@Api(tags = "自建车队-司机管理")
@RequestMapping("/transport/fleet/driverManagement")
@Controller
public class DriverManagementController {

    @Autowired
    private IDriverManagementService driverManagementService;


    @ApiOperation(value = "分页查询自建车队-司机管理")
    @PostMapping(value = "/getDriverPage")
    public Result<Page<DriverManagement>> getDriverPage(@RequestBody DriverManagementParam param) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return Result.failed("账号未登入，请重新登入!");
        }
        param.setCompanyUid(user.getCompanyUid());
        try {
            Page<DriverManagement> page = driverManagementService.getDriverPage(param);
            return Result.success("分页查询自建车队-司机管理成功！", page);
        } catch (Exception e) {
            log.error("分页查询自建车队-司机管理失败，失败原因：" + e);
            return Result.failed("分页查询自建车队-司机管理失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "根据ID删除一条数据")
    @GetMapping(value = "/deleteDriver")
    public Result<String> deleteDriver(@RequestParam(value = "id") Integer id) {
        try {
            String result = driverManagementService.deleteDriver(id);
            if (result == null) {
                return Result.success("删除成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("根据id查询一条数据失败，失败原因：" + e);
            return Result.failed("根据id查询一条数据失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "保存或修改")
    @PostMapping(value = "/saveOrUpdateDriver")
    public Result<String> saveOrUpdateDriver(@RequestBody DriverManagement driverManagement) {
        try {
            String result = driverManagementService.saveOrUpdateDriver(driverManagement);
            if (result == null) {
                return Result.success("保存成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("保存或修改失败，失败原因：" + e);
            return Result.failed("保存或修改失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "根据司机ID查询关联车辆")
    @GetMapping(value = "/getAssociatedVehicle")
    public Result<List<DriverVehicleVo>> getAssociatedVehicle(@RequestParam(value = "id") Integer id) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return Result.failed("账号未登入，请重新登入!");
        }
        try {
            List<DriverVehicleVo> list = driverManagementService.getAssociatedVehicle(id, user.getCompanyUid());
            return Result.success("查询关联车辆成功！", list);
        } catch (Exception e) {
            log.error("根据司机ID查询关联车辆失败，失败原因：" + e);
            return Result.failed("根据司机ID查询关联车辆失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "获取司机列表")
    @GetMapping(value = "/getDriverList")
    public Result<List<DriverVehicleListVo>> getDriverList(@RequestParam(value = "name", required = false) String name) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return Result.failed("账号未登入，请重新登入!");
        }
        try {
            List<DriverVehicleListVo> list = driverManagementService.getDriverList(name, user.getCompanyUid());
            return Result.success("获取司机列表成功！", list);
        } catch (Exception e) {
            log.error("获取司机列表失败，失败原因：" + e);
            return Result.failed("获取司机列表失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "导出-按查询条件")
    @PostMapping(value = "/export")
    public Result<Object> export(HttpServletResponse response, @RequestBody DriverManagementParam param) {
        log.info("导出-参数：" + (param != null ? JSON.toJSONString(param) : "NONE"));
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return Result.failed("账号未登入，请重新登入!");
        }
        if (param != null) {
            param.setCompanyUid(user.getCompanyUid());
        }
        try {
            //获取数据
            List<DriverManagement> list = driverManagementService.export(param);
            // 导出
            UploadOrExportExcelUtil<DriverManagement> excel = new UploadOrExportExcelUtil<>();
            excel.exportExcel(response, list, DriverManagement.class, "车队管理-司机管理", param.getSuffix());
            return Result.success("导出成功！");
        } catch (Exception e) {
            log.error("导出失败,原因：" + e);
            return Result.failed("导出失败,原因：" + e);
        }
    }
}
