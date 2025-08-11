package com.system.transport.impl.fleet.provider;

import com.system.transport.api.fleet.params.VehicleManagementParam;
import org.apache.ibatis.annotations.Param;

/**
 * @author lutong
 * @data 2025-2-10 010 17:16
 */
public class VehicleManagementProvider {

    public String getVehicleCount(@Param("param") VehicleManagementParam param) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT COUNT(vm.ID) ");
        sql.append(" FROM scm_vehicle_management vm ");
        sql.append(" WHERE vm.COMPANY_UID = '").append(param.getCompanyUid()).append("' ");
        sql.append(this.initDriver(param));

        return sql.toString();
    }


    public String getVehicleList(@Param("param") VehicleManagementParam param) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT vm.* ");
        sql.append(" FROM scm_vehicle_management vm ");
        sql.append(" WHERE vm.COMPANY_UID = '").append(param.getCompanyUid()).append("' ");
        sql.append(this.initDriver(param));
        sql.append(" ORDER BY CREATE_TIME DESC ");
        if (null != param.getPageIndex() && param.getPageSize() != null) {
            sql.append(" LIMIT ").append((param.getPageIndex() - 1) * param.getPageSize()).append(",").append(param.getPageSize());
        }
        return sql.toString();
    }


    public String initDriver(@Param("param") VehicleManagementParam param) {
        StringBuffer sql = new StringBuffer();
        if (param.getLicenseNumber() != null && !param.getLicenseNumber().equals("")) {
            sql.append("AND vm.LICENSE_NUMBER like '%").append(param.getLicenseNumber()).append("%' ");
        }
        if (param.getVehicleType() != null && !param.getVehicleType().equals("")) {
            sql.append("AND vm.VEHICLE_TYPE like '%").append(param.getVehicleType()).append("%' ");
        }
        if (param.getCapacity() != null && !param.getCapacity().equals("")) {
            sql.append("AND vm.CAPACITY like '%").append(param.getCapacity()).append("%' ");
        }
        if (param.getSpecialLicenseNumber() != null && !param.getSpecialLicenseNumber().equals("")) {
            sql.append("AND vm.SPECIAL_LICENSE_NUMBER like '%").append(param.getSpecialLicenseNumber()).append("%' ");
        }
        return sql.toString();
    }
}
