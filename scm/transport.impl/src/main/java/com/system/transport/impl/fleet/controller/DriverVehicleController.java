package com.system.transport.impl.fleet.controller;

import com.system.common.vo.Result;
import com.system.transport.api.fleet.params.DriverVehicleParam;
import com.system.transport.api.fleet.service.IDriverVehicleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author lutong
 * @data 2025-2-12 012 14:58
 */

@Slf4j
@RestController
@Api(tags = "自建车队-关联司机与车辆")
@RequestMapping("/transport/fleet/associate")
@Controller
public class DriverVehicleController {

    @Autowired
    private IDriverVehicleService driverVehicleService;


    @ApiOperation(value = "车辆管理-车辆关联多个司机")
    @PostMapping(value = "/vehicleAssociateDriver")
    public Result<String> vehicleAssociateDriver(@RequestBody DriverVehicleParam param) {
        try {
            String result = driverVehicleService.vehicleAssociateDriver(param.getVehicleId(), param.getDriverIdList());
            if (result == null) {
                return Result.success("关联成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("车辆关联多个司机失败，失败原因：" + e);
            return Result.failed("车辆关联多个司机失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "司机管理-司机关联车辆")
    @GetMapping(value = "/driverAssociateVehicle")
    public Result<String> driverAssociateVehicle(@RequestParam(value = "driverId") Integer driverId,
                                               @RequestParam(value = "vehicleId") Integer vehicleId) {
        try {
            String result = driverVehicleService.driverAssociateVehicle(driverId, vehicleId);
            if (result == null) {
                return Result.success("关联成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("司机关联车辆失败，失败原因：" + e);
            return Result.failed("司机关联车辆失败，失败原因：" + e);
        }
    }
}
