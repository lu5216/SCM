package com.system.transport.api.fleet.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2025-2-12 012 13:52
 */

@Data
public class DriverVehicleListVo {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer id;

    /**
     * 司机名称
     */
    @ApiModelProperty(value = "司机名称")
    private String name;
}
