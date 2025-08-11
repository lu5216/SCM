package com.system.transport.impl.fleet.provider;

import com.system.transport.api.fleet.params.DriverManagementParam;
import org.apache.ibatis.annotations.Param;

/**
 * @author lutong
 * @data 2025-2-7 007 16:26
 */


public class DriverManagementProvider {

    public String getDriverCount(@Param("param") DriverManagementParam param) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT COUNT(dm.ID) ");
        sql.append(" FROM scm_driver_management dm ");
        sql.append(" WHERE dm.COMPANY_UID = '").append(param.getCompanyUid()).append("' ");
        sql.append(this.initDriver(param));

        return sql.toString();
    }


    public String getDriverList(@Param("param") DriverManagementParam param) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT dm.* ");
        sql.append(" FROM scm_driver_management dm ");
        sql.append(" WHERE dm.COMPANY_UID = '").append(param.getCompanyUid()).append("' ");
        sql.append(this.initDriver(param));
        sql.append(" ORDER BY CREATE_TIME DESC ");
        if (null != param.getPageIndex() && param.getPageSize() != null) {
            sql.append(" LIMIT ").append((param.getPageIndex() - 1) * param.getPageSize()).append(",").append(param.getPageSize());
        }
        return sql.toString();
    }


    public String initDriver(@Param("param") DriverManagementParam param) {
        StringBuffer sql = new StringBuffer();
        if (param.getName() != null && !param.getName().equals("")) {
            sql.append("AND dm.NAME like '%").append(param.getName()).append("%' ");
        }
        if (param.getPhone() != null && !param.getPhone().equals("")) {
            sql.append("AND dm.PHONE like '%").append(param.getPhone()).append("%' ");
        }
        if (param.getEmail() != null && !param.getEmail().equals("")) {
            sql.append("AND dm.EMAIL like '%").append(param.getEmail()).append("%' ");
        }
        return sql.toString();
    }
}
