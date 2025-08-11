package com.system.warehouse.impl.inventoryInfo.provider;

import com.system.warehouse.api.inventoryInfo.params.GoodsInventoryInfoParam;
import org.apache.ibatis.annotations.Param;

import java.util.Objects;

/**
 * @author lutong
 * @data 2025-2-19 019 16:53
 */
public class GoodsInventoryInfoProvider {

    public String getGoodsPageCount(@Param("param") GoodsInventoryInfoParam param) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT COUNT(gii.ID) ");
        sql.append(" FROM scm_goods_inventory_info gii ");
        sql.append("          LEFT JOIN scm_warehouse_goods wg on gii.GOODS_ID = wg.ID ");
        sql.append(" WHERE wg.COMPANY_UID = '").append(param.getCompanyUid()).append("' ");
        sql.append(" AND STATUS = 1 ");
        sql.append(this.initSQL(param));
        return sql.toString();
    }

    public String getGoodsPageList(@Param("param") GoodsInventoryInfoParam param) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT gii.*, wg.* ");
        sql.append(" FROM scm_goods_inventory_info gii ");
        sql.append("          LEFT JOIN scm_warehouse_goods wg on gii.GOODS_ID = wg.ID ");
        sql.append(" WHERE wg.COMPANY_UID = '").append(param.getCompanyUid()).append("' ");
        sql.append(" AND STATUS = 1 ");
        sql.append(this.initSQL(param));
        sql.append(" ORDER BY gii.CREATE_TIME DESC ");
        sql.append(" LIMIT ").append((param.getPageIndex() - 1) * param.getPageSize()).append(",").append(param.getPageSize());
        return sql.toString();
    }

    public String initSQL(@Param("param") GoodsInventoryInfoParam param) {
        StringBuffer sql = new StringBuffer();
        if (null != param.getGoodsName() && !Objects.equals(param.getGoodsName(),"")) {
            sql.append(" AND wg.GOODS_NAME = '").append(param.getGoodsName()).append("' ");
        }
        if (null != param.getGoodsCode() && !Objects.equals(param.getGoodsCode(),"")) {
            sql.append(" AND wg.GOODS_CODE = '").append(param.getGoodsCode()).append("' ");
        }
        if (null != param.getWarehouseId() ) {
            sql.append(" AND gii.WAREHOUSE_ID = ").append(param.getWarehouseId()).append(" ");
        }
        if (null != param.getWarehouseAreaId() ) {
            sql.append(" AND gii.WAREHOUSE_AREA_ID = ").append(param.getWarehouseAreaId()).append(" ");
        }
        if (null != param.getCreateUserName() && !Objects.equals(param.getCreateUserName(),"")) {
            sql.append(" AND gii.CREATE_USER_NAME LIKE '%").append(param.getCreateUserName()).append("%' ");
        }

        return sql.toString();
    }
}
