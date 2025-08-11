package com.system.transport.impl.fleet.serviceImpl;

import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import com.system.transport.api.fleet.domain.DriverManagement;
import com.system.transport.api.fleet.domain.VehicleManagement;
import com.system.transport.api.fleet.service.IDriverManagementService;
import com.system.transport.api.fleet.service.IDriverVehicleService;
import com.system.transport.api.fleet.service.IVehicleManagementService;
import com.system.transport.api.fleet.vo.DriverVehicleListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author lutong
 * @data 2025-2-12 012 14:15
 */

@Slf4j
@Service
public class DriverVehicleServiceImpl implements IDriverVehicleService {

    @Autowired
    private IDriverManagementService driverManagementService;

    @Autowired
    private IVehicleManagementService vehicleManagementService;


    @Override
    public String vehicleAssociateDriver(Integer vehicleId, List<Integer> driverIdList) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }

        VehicleManagement vehicleManagement = vehicleManagementService.getById(vehicleId);
        if (vehicleManagement == null) {
            return "不存在id为【" + vehicleId + "】的车辆！";
        }

        StringBuilder driverIds = new StringBuilder();
        StringBuilder driverNames = new StringBuilder();
        Collections.sort(driverIdList);
        for (Integer id : driverIdList) {
            driverIds.append(id).append(",");
            DriverVehicleListVo driverVo = driverManagementService.getDriverNameById(id);
            if (driverVo != null) {
                driverNames.append(driverVo.getName()).append(",");
            } else {
                return "不存在id为【" + id + "】的司机！";
            }
        }
        driverIds.deleteCharAt(driverIds.length() - 1);
        driverNames.deleteCharAt(driverNames.length() - 1);

        // 关联
        vehicleManagement.setAssociatedDriverId(String.valueOf(driverIds));
        vehicleManagement.setAssociatedDriver(String.valueOf(driverNames));
        vehicleManagement.setUpdateUserName(user.getLoginId());
        vehicleManagement.setUpdateTime(new Date());
        vehicleManagementService.updateById(vehicleManagement);
        return null;
    }

    @Override
    public String driverAssociateVehicle(Integer driverId, Integer vehicleId) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }

        DriverManagement driverManagement = driverManagementService.getById(driverId);
        if (driverManagement == null) {
            return "不存在id为【" + driverId + "】的司机！";
        }
        VehicleManagement vehicleManagement = vehicleManagementService.getById(vehicleId);
        if (vehicleManagement == null) {
            return "不存在id为【" + vehicleId + "】的车辆！";
        }

        if (!Objects.equals(vehicleManagement.getAssociatedDriverId(), "") && vehicleManagement.getAssociatedDriverId() != null) {
            // 待关联的车辆已关联司机
            String[] splitId = vehicleManagement.getAssociatedDriverId().split(",");
            // 校验是否已关联
            for (String id : splitId) {
                if (id.equals(driverId.toString())) {
                    return "车辆ID【" + driverId + "】与司机ID【" + vehicleId + "】已关联！";
                }
            }

            vehicleManagement.setAssociatedDriverId(vehicleManagement.getAssociatedDriverId() + "," + driverId);
            vehicleManagement.setAssociatedDriver(vehicleManagement.getAssociatedDriver() + "," + driverManagement.getName());
        } else {
            // 待关联的车辆未关联司机
            vehicleManagement.setAssociatedDriverId(String.valueOf(driverId));
            vehicleManagement.setAssociatedDriver(driverManagement.getName());
        }

        // 关联
        vehicleManagement.setUpdateUserName(user.getLoginId());
        vehicleManagement.setUpdateTime(new Date());
        vehicleManagementService.updateById(vehicleManagement);
        return null;
    }
}
