package com.system.warehouse.impl.goodsWarehouse.provider;

import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author lutong
 * @data 2025-1-7 007 9:13
 */

public class WarehouseAreaProvider {
    public String getPageCount(@Param("warehouseCode") String warehouseCode) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT COUNT(wa.ID)");
        sql.append(" FROM scm_warehouse w ");
        sql.append("          LEFT JOIN scm_warehouse_area wa ON w.ID = wa.WAREHOUSE_ID ");
        sql.append(" WHERE wa.COMPANY_UID = '").append(user.getCompanyUid()).append("' ");
        sql.append(" AND wa.IS_DELETE = 0 ");
        if (warehouseCode != null && !warehouseCode.equals("")) {
            sql.append(" AND w.WAREHOUSE_CODE = '").append(warehouseCode).append("' ");
        }
        return sql.toString();
    }

    public String getPageList(@Param("warehouseCode") String warehouseCode,
                              @Param("pageIndex") Integer pageIndex,
                              @Param("pageSize") Integer pageSize) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT w.WAREHOUSE_NAME, w.WAREHOUSE_CODE,  wa.* ");
        sql.append(" FROM scm_warehouse w ");
        sql.append("          LEFT JOIN scm_warehouse_area wa ON w.ID = wa.WAREHOUSE_ID ");
        sql.append(" WHERE wa.COMPANY_UID = '").append(user.getCompanyUid()).append("' ");
        sql.append(" AND wa.IS_DELETE = 0 ");
        if (warehouseCode != null && !warehouseCode.equals("")) {
            sql.append(" AND w.WAREHOUSE_CODE = '").append(warehouseCode).append("' ");
        }
        sql.append(" ORDER BY CREATE_TIME DESC ");
        sql.append(" limit ").append((pageIndex - 1) * pageSize).append(",").append(pageSize);
        return sql.toString();
    }
}
