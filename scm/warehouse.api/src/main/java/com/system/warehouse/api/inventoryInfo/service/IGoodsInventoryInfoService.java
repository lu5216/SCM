package com.system.warehouse.api.inventoryInfo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.warehouse.api.inventoryInfo.domain.GoodsInventoryInfo;
import com.system.warehouse.api.inventoryInfo.params.GoodsInventoryInfoParam;
import com.system.warehouse.api.inventoryInfo.vo.GoodsInventoryInfoVo;

import java.util.List;

/**
 * @author lutong
 * @data 2025-2-19 019 16:53
 */
public interface IGoodsInventoryInfoService extends IService<GoodsInventoryInfo> {

    /**
     *  分页查询
     * @param param
     * @return
     */
    Page<GoodsInventoryInfoVo> getPage(GoodsInventoryInfoParam param);

    /**
     *  根据商品ID删除
     * @param goodsId
     * @return
     */
    void deleteByGoodsId(Integer goodsId);

    /**
     *  根据商品ID新增
     * @param goodsId
     * @param inventoryAlert
     * @return
     */
    void addByGoodsId(Integer goodsId, Integer inventoryAlert, boolean alertAlready);

    /**
     *  根据【商品ID】修改【数量】
     * @param goodsId
     * @param quantity
     * @return
     */
    void updateByGoodsId(Integer goodsId, Integer quantity);


    /**
     *  根据【商品ID】修改【预警库存】
     * @param goodsId
     * @param inventoryAlert
     * @return
     */
    void updateInventoryAlert(Integer goodsId, Integer inventoryAlert, boolean alertAlready);


    /**
     * 根据商品ID查询一条数据
     * @param goodsId
     * @return
     */
    GoodsInventoryInfo getOneByGoodsId(Integer goodsId);

    /**
     *  修改库存信息为已预警
     * @param id
     */
    void updateAlertAlready(Integer id, String updateUserName);

    /**
     *  查询需要预警库存信息
     * @return
     */
    List<GoodsInventoryInfo> getByListAlert();
}
