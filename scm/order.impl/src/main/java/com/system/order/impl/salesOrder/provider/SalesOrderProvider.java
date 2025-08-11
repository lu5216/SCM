package com.system.order.impl.salesOrder.provider;

import com.system.order.api.salesOrder.params.SalesOrderParam;
import org.apache.ibatis.annotations.Param;

import java.util.Objects;

/**
 * @author lutong
 * @data 2025-2-20 020 17:57
 */

public class SalesOrderProvider {
    public String getItemCount(@Param("param") SalesOrderParam param) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT COUNT(so.ID) ");
        sql.append(" FROM scm_sales_order so ");
        sql.append("          LEFT JOIN scm_warehouse_goods wg ON so.GOODS_ID = wg.ID ");
        sql.append(" WHERE so.COMPANY_UID = '").append(param.getCompanyUid()).append("' ");
        sql.append(this.initSQL(param));
        return sql.toString();
    }

    public String getItemList(@Param("param") SalesOrderParam param) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT so.*, wg.GOODS_NAME, wg.GOODS_CODE, wg.GOODS_MODEL ");
        sql.append(" FROM scm_sales_order so ");
        sql.append("          LEFT JOIN scm_warehouse_goods wg ON so.GOODS_ID = wg.ID ");
        sql.append(" WHERE so.COMPANY_UID = '").append(param.getCompanyUid()).append("' ");
        sql.append(this.initSQL(param));
        sql.append(" ORDER BY CREATE_TIME DESC ");
        if (param.getPageIndex() != null && param.getPageSize() != null) {
            sql.append(" LIMIT ").append((param.getPageIndex() - 1) * param.getPageSize()).append(",").append(param.getPageSize());
        }
        return sql.toString();
    }

    public String getGoodsCount(@Param("param") SalesOrderParam param) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT COUNT(wg.ID) ");
        sql.append(" FROM scm_warehouse_goods wg ");
        sql.append("          LEFT JOIN scm_sales_order so ON so.GOODS_ID = wg.ID ");
        sql.append(" WHERE so.COMPANY_UID = '").append(param.getCompanyUid()).append("' ");
        sql.append(this.initSQL(param));
        return sql.toString();
    }

    public String getGoodsList(@Param("param") SalesOrderParam param) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT so.*, wg.GOODS_NAME, wg.GOODS_CODE, wg.GOODS_MODEL ");
        sql.append(" FROM scm_warehouse_goods wg ");
        sql.append("          LEFT JOIN scm_sales_order so ON so.GOODS_ID = wg.ID ");
        sql.append(" WHERE so.COMPANY_UID = '").append(param.getCompanyUid()).append("' ");
        sql.append(this.initSQL(param));
        sql.append(" ORDER BY CREATE_TIME DESC ");
        if (param.getPageIndex() != null && param.getPageSize() != null) {
            sql.append(" LIMIT ").append((param.getPageIndex() - 1) * param.getPageSize()).append(",").append(param.getPageSize());
        }
        return sql.toString();
    }

    public String initSQL(@Param("param") SalesOrderParam param) {
        StringBuffer sql = new StringBuffer();
        if (null != param.getItemNo() && !Objects.equals(param.getItemNo(), "")) {
            sql.append(" AND so.ITEM_NO LIKE '%").append(param.getItemNo()).append("%' ");
        }
        if (null != param.getItemStatus()) {
            sql.append(" AND so.ITEM_STATUS = ").append(param.getItemStatus()).append(" ");
        }
        if (null != param.getCurrencyId()) {
            sql.append(" AND so.CURRENCY_ID = ").append(param.getCurrencyId()).append(" ");
        }
        // 仓库-库区
        if (null != param.getWarehouseId()) {
            sql.append(" AND so.WAREHOUSE_ID = ").append(param.getWarehouseId()).append(" ");
        }
        if (null != param.getWarehouseAreaId()) {
            sql.append(" AND so.WAREHOUSE_AREA_ID = ").append(param.getWarehouseAreaId()).append(" ");
        }
        if (null != param.getGoodsId()) {
            sql.append(" AND so.GOODS_ID = ").append(param.getGoodsId()).append(" ");
        }
        if (null != param.getCreateUserName() && !Objects.equals(param.getCreateUserName(), "")) {
            sql.append(" AND so.CREATE_USER_NAME like '%").append(param.getCreateUserName()).append("%' ");
        }
        if (null != param.getDeliveryAddress() && !Objects.equals(param.getDeliveryAddress(), "")) {
            sql.append(" AND so.DELIVERY_ADDRESS LIKE '%").append(param.getDeliveryAddress()).append("%' ");
        }
        // 时间
        if (null != param.getDeliveryDataStart() && !Objects.equals(param.getDeliveryDataStart(), "")) {
            sql.append(" AND so.DELIVERY_DATE >= '").append(param.getDeliveryDataStart()).append(" 00:00:00' ");
        }
        if (null != param.getDeliveryDataEnd() && !Objects.equals(param.getDeliveryDataEnd(), "")) {
            sql.append(" AND so.DELIVERY_DATE <= '").append(param.getDeliveryDataEnd()).append(" 23:59:59' ");
        }
        if (null != param.getCreateTimeStart() && !Objects.equals(param.getCreateTimeStart(), "")) {
            sql.append(" AND so.CREATE_TIME >= '").append(param.getCreateTimeStart()).append(" 00:00:00' ");
        }
        if (null != param.getCreateTimeEnd() && !Objects.equals(param.getCreateTimeEnd(), "")) {
            sql.append(" AND so.CREATE_TIME <= '").append(param.getCreateTimeEnd()).append(" 23:59:59' ");
        }

        return sql.toString();
    }
}
