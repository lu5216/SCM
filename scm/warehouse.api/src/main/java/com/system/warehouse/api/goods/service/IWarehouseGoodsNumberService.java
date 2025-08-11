package com.system.warehouse.api.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.system.warehouse.api.goods.domain.WarehouseGoodsNumber;

import java.util.List;

/**
 * @author lutong
 * @data 2025-1-10 010 17:15
 */

public interface IWarehouseGoodsNumberService extends IService<WarehouseGoodsNumber> {

    /**
     *  根据商品识别码查询
     * @param goodsCode
     * @return
     */
    List<WarehouseGoodsNumber> getByGoodsCode(String goodsCode);

    /**
     *  添加商品条码信息
     * @param goodsCode
     * @param goodsNumber
     * @return
     */
    String addGoodsNumber(String goodsCode, String goodsNumber);


    /**
     *  根据商品识别码批量删除条码
     * @param goodsCode
     * @return
     */
    String deleteByGoodsCode(String goodsCode);


    /**
     *  根据商品条码删除条码
     * @param goodsNumber
     * @return
     */
    String deleteByGoodsNumber(String goodsNumber);


    /**
     *  根据商品条码修改状态
     * @param goodsNumber
     * @return
     */
    String updateStatus(String goodsNumber);
}
