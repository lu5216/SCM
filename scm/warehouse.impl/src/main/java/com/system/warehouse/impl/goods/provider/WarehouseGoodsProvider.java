package com.system.warehouse.impl.goods.provider;

import com.system.warehouse.api.goods.param.WarehouseGoodGetPageParam;
import org.apache.ibatis.annotations.Param;

import java.util.Objects;

/**
 * @author lutong
 * @data 2025-1-9 009 15:26
 */

public class WarehouseGoodsProvider {

    public String getGoodsPageCount(@Param("param") WarehouseGoodGetPageParam param) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT COUNT(wg.ID) ");
        sql.append(" FROM scm_warehouse_goods wg ");
        sql.append(" WHERE wg.COMPANY_UID = '").append(param.getCompanyUid()).append("' ");
        sql.append(this.getGoodsPageInit(param));
        return sql.toString();
    }

    public String getGoodsPageList(@Param("param") WarehouseGoodGetPageParam param) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT wg.*, ");
        sql.append("        (select GROUP_CONCAT(wgn.GOODS_NUMBER) " );
        sql.append("         FROM scm_warehouse_goods_number wgn " );
        sql.append("         WHERE wgn.GOODS_ID = wg.ID AND wgn.IS_DEL = 0) as GOODS_NUMBER " );
        sql.append(" FROM scm_warehouse_goods wg ");
        sql.append(" WHERE wg.COMPANY_UID = '").append(param.getCompanyUid()).append("' ");
        sql.append(this.getGoodsPageInit(param));
        sql.append(" ORDER BY wg.CREATE_TIME DESC ");
        sql.append(" LIMIT ").append((param.getPageIndex() - 1) * param.getPageSize()).append(",").append(param.getPageSize());
        return sql.toString();
    }

    public String getGoodsPageInit(@Param("param") WarehouseGoodGetPageParam param) {
        StringBuffer sql = new StringBuffer();
        if (null != param.getGoodsCode() && !Objects.equals(param.getGoodsCode(),"")) {
            sql.append(" AND wg.GOODS_CODE = '").append(param.getGoodsCode()).append("' ");
        }
        if (null != param.getGoodsName() && !Objects.equals(param.getGoodsName(),"")) {
            sql.append(" AND wg.GOODS_NAME = '").append(param.getGoodsName()).append("' ");
        }
        if (null != param.getGoodsModel() && !Objects.equals(param.getGoodsModel(),"")) {
            sql.append(" AND wg.GOODS_MODEL LIKE '%").append(param.getGoodsModel()).append("%' ");
        }
        if (null != param.getGoodsSpec() && !Objects.equals(param.getGoodsSpec(),"")) {
            sql.append(" AND wg.GOODS_SPEC LIKE '%").append(param.getGoodsSpec()).append("%' ");
        }
        if (null != param.getGoodsType() && !Objects.equals(param.getGoodsType(),"")) {
            sql.append(" AND wg.GOODS_TYPE = '").append(param.getGoodsType()).append("' ");
        }
        if (null != param.getPreWarehouseId()) {
            sql.append(" AND wg.PRE_WAREHOUSE_ID = ").append(param.getPreWarehouseId()).append(" ");
        }
        if (null != param.getPackaging() && !Objects.equals(param.getPackaging(),"")) {
            sql.append(" AND wg.PACKAGING = '").append(param.getPackaging()).append("' ");
        }
        if (null != param.getStartCreateDate() && !Objects.equals(param.getStartCreateDate(),"")) {
            sql.append(" AND wg.CREATE_TIME >= '").append(param.getStartCreateDate()).append(" 00:00:00' ");
        }
        if (null != param.getEndCreateDate() && !Objects.equals(param.getEndCreateDate(),"")) {
            sql.append(" AND wg.CREATE_TIME <= '").append(param.getEndCreateDate()).append(" 23:59:59' ");
        }
        return sql.toString();
    }

}
