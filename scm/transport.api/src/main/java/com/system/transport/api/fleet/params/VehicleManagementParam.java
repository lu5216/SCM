package com.system.transport.api.fleet.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2025-2-10 010 17:06
 */

@Data
public class VehicleManagementParam {

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

    /**
     * 企业UID
     */
    @ApiModelProperty(value = "企业UID")
    private String companyUid;

    /**
     * 页
     */
    @ApiModelProperty(value = "页")
    private Integer pageIndex;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Integer pageSize;

    /**
     * 导出格式
     */
    @ApiModelProperty("导出格式")
    private String suffix;
}
