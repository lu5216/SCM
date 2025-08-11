package com.system.transport.api.fleet.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2025-2-12 012 11:49
 */

@Data
public class DriverVehicleVo {

    /**
     * 司机名称
     */
    @ApiModelProperty(value = "司机名称")
    private String driverName;

    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    private String licenseNumber;

    /**
     * 车型
     */
    @ApiModelProperty(value = "车型")
    private String vehicleType;

    /**
     * 载量
     */
    @ApiModelProperty(value = "载量")
    private String capacity;

    /**
     * 港澳车牌号
     */
    @ApiModelProperty(value = "港澳车牌号")
    private String specialLicenseNumber;
}
