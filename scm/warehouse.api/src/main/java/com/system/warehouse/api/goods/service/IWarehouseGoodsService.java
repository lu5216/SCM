package com.system.warehouse.api.goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.warehouse.api.goods.domain.WarehouseGoods;
import com.system.warehouse.api.goods.param.WarehouseGoodGetPageParam;
import com.system.warehouse.api.goods.vo.*;

import java.util.List;

/**
 * @author lutong
 * @data 2025-1-10 010 10:10
 */

public interface IWarehouseGoodsService extends IService<WarehouseGoods> {

    /**
     *  分页查询商品库
     * @param param
     * @return
     */
    Page<WarehouseGoods> getGoodsPage(WarehouseGoodGetPageParam param);

    /**
     *  查询商品库数据列表
     * @param param
     * @return
     */
    List<WarehouseGoods> getGoodsPageList(WarehouseGoodGetPageParam param);

    /**
     *  保存基础信息
     * @param vo
     * @return
     */
    String saveBaseInfo(GoodsSaveBaseInfoVo vo);

    /**
     *  保存价格信息
     * @param vo
     * @return
     */
    String savePriceInfo(GoodsSavePriceInfoVo vo);

    /**
     *  保存维护信息
     * @param vo
     * @return
     */
    String saveKeepInfo(GoodsSaveKeepInfoVo vo);

    /**
     *  根据商品识别码获取推荐仓库信息
     * @param goodsCode
     * @return
     */
    PreWarehouseGoodsVo getPreWarehouseByGoodsCode(String goodsCode);


    /**
     *  获取全部商品列表
     * @param key
     * @return
     */
    List<WarehouseGoodsListVo> getWarehouseGoodsList(String key);


    /**
     *  删除商品
     * @param goodsId
     * @return
     */
    String deleteWarehouseGoods(Integer goodsId);

    /**
     *  变更状态
     * @param goodsId
     * @return
     */
    String editStatus(Integer goodsId);

    /**
     *  查询明细
     * @param goodsId
     * @return
     */
    GoodsDetailsVo findDetailById(Integer goodsId);


    /**
     *  根据识别码获取商品信息
     * @param goodsCode
     * @param companyUid
     * @return
     */
    WarehouseGoods findOneByGoodsCode(String goodsCode, String companyUid);
}
