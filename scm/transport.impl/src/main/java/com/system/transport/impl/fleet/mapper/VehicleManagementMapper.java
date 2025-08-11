package com.system.transport.impl.fleet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.transport.api.fleet.domain.VehicleManagement;
import com.system.transport.api.fleet.params.VehicleManagementParam;
import com.system.transport.impl.fleet.provider.VehicleManagementProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author lutong
 * @data 2025-2-10 010 17:16
 */
public interface VehicleManagementMapper extends BaseMapper<VehicleManagement> {

    @SelectProvider(type = VehicleManagementProvider.class, method = "getVehicleCount")
    Integer getVehicleCount(VehicleManagementParam param);

    @SelectProvider(type = VehicleManagementProvider.class, method = "getVehicleList")
    List<VehicleManagement> getVehicleList(VehicleManagementParam param);
}
