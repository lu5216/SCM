package com.system.transport.impl.fleet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.transport.api.fleet.domain.DriverManagement;
import com.system.transport.api.fleet.params.DriverManagementParam;
import com.system.transport.impl.fleet.provider.DriverManagementProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author lutong
 * @data 2025-2-7 007 14:57
 */

public interface DriverManagementMapper extends BaseMapper<DriverManagement> {

    @SelectProvider(type = DriverManagementProvider.class, method = "getDriverCount")
    Integer getDriverCount(DriverManagementParam param);

    @SelectProvider(type = DriverManagementProvider.class, method = "getDriverList")
    List<DriverManagement> getDriverList(DriverManagementParam param);
}
