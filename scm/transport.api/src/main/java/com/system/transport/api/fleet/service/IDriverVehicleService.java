package com.system.transport.api.fleet.service;

import java.util.List;

/**
 * @author lutong
 * @data 2025-2-12 012 14:07
 */

public interface IDriverVehicleService {


    /**
     *  车辆关联多个司机
     * @param vehicleId
     * @param driverIdList
     * @return
     */
    String vehicleAssociateDriver(Integer vehicleId, List<Integer> driverIdList);


    /**
     *  司机关联车辆
     * @param driverId
     * @param vehicleId
     * @return
     */
    String driverAssociateVehicle(Integer driverId, Integer vehicleId);
}
