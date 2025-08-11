package com.system.warehouse.impl.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.warehouse.api.goods.domain.WarehouseGoodsNumber;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lutong
 * @data 2025-1-10 010 17:21
 */

@Mapper
public interface WarehouseGoodsNumberMapper extends BaseMapper<WarehouseGoodsNumber> {

    /**
     *  根据商品识别码查询
     * @param goodsCode
     * @return
     */
    @Select(" SELECT * \n" +
            " FROM scm_warehouse_goods_number \n" +
            " WHERE GOODS_CODE = #{goodsCode} \n" +
            " AND IS_DEL = 0 \n" +
            " ORDER BY CREATE_TIME DESC")
    List<WarehouseGoodsNumber> getByGoodsCode(String goodsCode);

    /**
     *  根据条码查询
     * @param goodsNumber
     * @return
     */
    @Select(" SELECT * \n" +
            " FROM scm_warehouse_goods_number \n" +
            " WHERE GOODS_NUMBER = #{goodsNumber} ")
    WarehouseGoodsNumber getByGoodsNumber(String goodsNumber);


    /**
     *  根据商品识别码删除
     * @param goodsCode
     * @return
     */
    @Delete(" DELETE \n" +
            " FROM scm_warehouse_goods_number \n" +
            " WHERE GOODS_CODE = #{goodsCode} ")
    WarehouseGoodsNumber deleteByGoodsCode(String goodsCode);

    /**
     *  根据条码删除
     * @param goodsNumber
     * @return
     */
    @Delete(" DELETE \n" +
            " FROM scm_warehouse_goods_number \n" +
            " WHERE GOODS_NUMBER = #{goodsNumber} ")
    WarehouseGoodsNumber deleteByGoodsNumber(String goodsNumber);
}
