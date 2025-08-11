package com.system.order.impl.warehouseOrder.provider;

import com.system.order.api.warehouseOrder.params.WarehouseItemParam;
import org.apache.ibatis.annotations.Param;
import java.util.Objects;

/**
 * @author lutong
 * @data 2025-2-14 014 14:34
 */

public class WarehouseItemProvider {

    public String getPageCount(@Param("param") WarehouseItemParam param) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT COUNT(iw.ID) ");
        sql.append(" FROM scm_item_warehouse iw ");
        sql.append("          LEFT JOIN scm_warehouse_goods wg ON iw.GOODS_ID = wg.ID ");
        sql.append(" WHERE iw.COMPANY_UID = '").append(param.getCompanyUid()).append("' ");
        sql.append(this.initSQL(param));
        return sql.toString();
    }

    public String getPageList(@Param("param") WarehouseItemParam param) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT iw.ID, ");
        sql.append("        iw.ITEM_NO, ");
        sql.append("        iw.TYPE, ");
        sql.append("        iw.ITEM_STATUS, ");
        sql.append("        iw.IN_OUT_STATUS, ");
        sql.append("        iw.GOODS_SOURCE, ");
        sql.append("        iw.GOODS_DESTINATION, ");
        sql.append("        iw.EXPECTED_IN_OUT_DATE, ");
        sql.append("        iw.ASSOCIATED_ORDER_NO, ");
        sql.append("        iw.UNIT_PRICE, ");
        sql.append("        iw.AMOUNT, ");
        sql.append("        iw.is_AUDIT, ");
        sql.append("        iw.AUDIT_USER_NAME, ");
        sql.append("        iw.AUDIT_DATE, ");
        sql.append("        iw.AUDIT_STATUS, ");
        sql.append("        iw.GOODS_ID, ");
        sql.append("        wg.GOODS_NAME, ");
        sql.append("        wg.GOODS_CODE, ");
        sql.append("        iw.WAREHOUSE_ID, ");
        sql.append("        iw.WAREHOUSE_AREA_ID, ");
        sql.append("        iw.REMARK, ");
        sql.append("        iw.CREATE_USER_NAME, ");
        sql.append("        iw.CREATE_TIME ");
        sql.append(" FROM scm_item_warehouse iw ");
        sql.append("          LEFT JOIN scm_warehouse_goods wg ON iw.GOODS_ID = wg.ID ");
        sql.append(" WHERE iw.COMPANY_UID = '").append(param.getCompanyUid()).append("' ");
        sql.append(this.initSQL(param));
        sql.append(" ORDER BY CREATE_TIME DESC ");
        if (param.getPageIndex() != null && param.getPageSize() != null) {
            sql.append(" LIMIT ").append((param.getPageIndex() - 1) * param.getPageSize()).append(",").append(param.getPageSize());
        }

        return sql.toString();
    }

    public String initSQL(@Param("param") WarehouseItemParam param) {
        StringBuffer sql = new StringBuffer();
        if (null != param.getItemNo() && !Objects.equals(param.getItemNo(), "")) {
            sql.append(" AND iw.ITEM_NO LIKE '%").append(param.getItemNo()).append("%' ");
        }
        if (null != param.getType()) {
            sql.append(" AND iw.TYPE = ").append(param.getType()).append(" ");
        }
        if (null != param.getItemStatus()) {
            sql.append(" AND iw.ITEM_STATUS = ").append(param.getItemStatus()).append(" ");
        }
        if (null != param.getInOutStatus()) {
            sql.append(" AND iw.IN_OUT_STATUS = ").append(param.getInOutStatus()).append(" ");
        }
        if (null != param.getGoodsSource() && !Objects.equals(param.getGoodsSource(), "")) {
            sql.append(" AND iw.GOODS_SOURCE = '").append(param.getGoodsSource()).append("' ");
        }
        if (null != param.getGoodsDestination() && !Objects.equals(param.getGoodsDestination(), "")) {
            sql.append(" AND iw.GOODS_DESTINATION = '").append(param.getGoodsDestination()).append("' ");
        }
        // 仓库-库区
        if (null != param.getWarehouseId()) {
            sql.append(" AND iw.WAREHOUSE_ID = ").append(param.getWarehouseId()).append(" ");
        }
        if (null != param.getWarehouseAreaId()) {
            sql.append(" AND iw.WAREHOUSE_AREA_ID = ").append(param.getWarehouseAreaId()).append(" ");
        }
        if (null != param.getAssociatedOrderNo() && !Objects.equals(param.getAssociatedOrderNo(), "")) {
            sql.append(" AND iw.ASSOCIATED_ORDER_NO LIKE '%").append(param.getAssociatedOrderNo()).append("%' ");
        }
        if (null != param.getGoodsId()) {
            sql.append(" AND iw.GOODS_ID = ").append(param.getGoodsId()).append(" ");
        }
        if (null != param.getCreateUserName() && !Objects.equals(param.getCreateUserName(), "")) {
            sql.append(" AND iw.CREATE_USER_NAME like '%").append(param.getCreateUserName()).append("%' ");
        }
        // 时间
        if (null != param.getCreateDataStart() && !Objects.equals(param.getCreateDataStart(), "")) {
            sql.append(" AND iw.CREATE_TIME >= '").append(param.getCreateDataStart()).append(" 00:00:00' ");
        }
        if (null != param.getCreateDataEnd() && !Objects.equals(param.getCreateDataEnd(), "")) {
            sql.append(" AND iw.CREATE_TIME <= '").append(param.getCreateDataEnd()).append(" 23:59:59' ");
        }

        return sql.toString();
    }
}
