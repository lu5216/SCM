package com.system.warehouse.impl.goodsWarehouse.provider;

import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author lutong
 * @data 2025-1-6 006 15:30
 */


public class WarehouseProvider {

    public String getPageCount(@Param("warehouseCode") String warehouseCode) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT COUNT(ID)");
        sql.append(" FROM scm_warehouse ");
        sql.append(" WHERE COMPANY_UID = '").append(user.getCompanyUid()).append("' ");
        if (warehouseCode != null && !warehouseCode.equals("")) {
            sql.append(" AND (WAREHOUSE_CODE LIKE '%").append(warehouseCode).append("%' OR ").
                    append("WAREHOUSE_NAME LIKE '%").append(warehouseCode).append("%') ");
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
        sql.append(" SELECT * ");
        sql.append(" FROM scm_warehouse ");
        sql.append(" WHERE COMPANY_UID = '").append(user.getCompanyUid()).append("' ");
        if (warehouseCode != null && !warehouseCode.equals("")) {
            sql.append(" AND (WAREHOUSE_CODE LIKE '%").append(warehouseCode).append("%' OR ").
                    append("WAREHOUSE_NAME LIKE '%").append(warehouseCode).append("%') ");
        }
        sql.append(" ORDER BY CREATE_TIME DESC ");
        sql.append(" limit ").append((pageIndex - 1) * pageSize).append(",").append(pageSize);
        return sql.toString();
    }

}
