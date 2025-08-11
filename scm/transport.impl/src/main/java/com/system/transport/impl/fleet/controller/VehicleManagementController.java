package com.system.transport.impl.fleet.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.utils.SecurityUtils;
import com.system.common.utils.UploadOrExportExcelUtil;
import com.system.common.vo.CompanyEnumVo;
import com.system.common.vo.Result;
import com.system.common.vo.SysUserVo;
import com.system.transport.api.fleet.domain.VehicleManagement;
import com.system.transport.api.fleet.enums.VehicleTypeEnum;
import com.system.transport.api.fleet.params.VehicleManagementParam;
import com.system.transport.api.fleet.service.IVehicleManagementService;
import com.system.transport.api.fleet.vo.DriverVehicleListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lutong
 * @data 2025-2-10 010 17:15
 */


@Slf4j
@RestController
@Api(tags = "自建车队-车辆管理")
@RequestMapping("/transport/fleet/vehicleManagement")
@Controller
public class VehicleManagementController {

    @Autowired
    private IVehicleManagementService vehicleManagementService;

    @ApiOperation(value = "分页查询自建车队-车辆管理")
    @PostMapping(value = "/getVehiclePage")
    public Result<Page<VehicleManagement>> getVehiclePage(@RequestBody VehicleManagementParam param) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return Result.failed("账号未登入，请重新登入!");
        }
        param.setCompanyUid(user.getCompanyUid());
        try {
            Page<VehicleManagement> page = vehicleManagementService.getVehiclePage(param);
            return Result.success("分页查询自建车队-车辆管理成功！", page);
        } catch (Exception e) {
            log.error("分页查询自建车队-车辆管理失败，失败原因：" + e);
            return Result.failed("分页查询自建车队-车辆管理失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "根据ID删除一条数据")
    @GetMapping(value = "/deleteVehicle")
    public Result<String> deleteVehicle(@RequestParam(value = "id") Integer id) {
        try {
            String result = vehicleManagementService.deleteVehicle(id);
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
    @PostMapping(value = "/saveOrUpdateVehicle")
    public Result<String> saveOrUpdateVehicle(@RequestBody VehicleManagement vehicleManagement) {
        try {
            String result = vehicleManagementService.saveOrUpdateVehicle(vehicleManagement);
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


    @ApiOperation(value = "查询车型列表数据")
    @GetMapping(value = "/selectVehicleType")
    public Result<List<CompanyEnumVo>> selectVehicleType(@RequestParam(value = "name", required = false) String name,
                                                             @RequestParam(value = "code", required = false) String code) {
        try {
            List<CompanyEnumVo> list = new ArrayList<>();
            for (VehicleTypeEnum vehicleTypeEnum : VehicleTypeEnum.values()) {
                CompanyEnumVo vo = new CompanyEnumVo();
                vo.setId(vehicleTypeEnum.getId());
                vo.setName(vehicleTypeEnum.getName());
                vo.setCode(vehicleTypeEnum.getCode());
                list.add(vo);
            }
            // filter进行模糊匹配
            if (name != null) {
                list = list.stream().filter(x -> x != null && x.getName().contains(name)).collect(Collectors.toList());
            }
            if (code != null) {
                list = list.stream().filter(x -> x != null && x.getCode().contains(code)).collect(Collectors.toList());
            }
            return Result.success("查询车型列表数据成功！", list);
        } catch (Exception e) {
            log.error("查询车型列表数据失败，失败原因：" + e);
            return Result.failed("查询车型列表数据失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "获取车辆列表")
    @GetMapping(value = "/getVehicleList")
    public Result<List<DriverVehicleListVo>> getVehicleList(@RequestParam(value = "name", required = false) String name) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return Result.failed("账号未登入，请重新登入!");
        }
        try {
            List<DriverVehicleListVo> list = vehicleManagementService.getVehicleList(name, user.getCompanyUid());
            return Result.success("获取车辆列表成功！", list);
        } catch (Exception e) {
            log.error("获取车辆列表失败，失败原因：" + e);
            return Result.failed("获取车辆列表失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "导出-按查询条件")
    @PostMapping(value = "/export")
    public Result<Object> export(HttpServletResponse response, @RequestBody VehicleManagementParam param) {
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
            List<VehicleManagement> list = vehicleManagementService.export(param);
            // 导出
            UploadOrExportExcelUtil<VehicleManagement> excel = new UploadOrExportExcelUtil<>();
            excel.exportExcel(response, list, VehicleManagement.class, "车队管理-车辆管理", param.getSuffix());
            return Result.success("导出成功！");
        } catch (Exception e) {
            log.error("导出失败,原因：" + e);
            return Result.failed("导出失败,原因：" + e);
        }
    }

}
