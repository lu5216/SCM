package com.system.transport.api.fleet.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lutong
 * @data 2025-2-12 012 15:03
 */

@Data
public class DriverVehicleParam {

    /**
     * 车辆ID
     */
    @ApiModelProperty(value = "车辆ID")
    public Integer vehicleId;

    /**
     * 司机ID列表
     */
    @ApiModelProperty(value = "司机ID列表")
    public List<Integer> driverIdList;
}
