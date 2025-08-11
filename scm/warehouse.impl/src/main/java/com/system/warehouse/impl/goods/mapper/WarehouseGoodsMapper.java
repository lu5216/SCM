package com.system.warehouse.impl.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.warehouse.api.goods.domain.WarehouseGoods;
import com.system.warehouse.api.goods.param.WarehouseGoodGetPageParam;
import com.system.warehouse.impl.goods.provider.WarehouseGoodsProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author lutong
 * @data 2025-1-9 009 15:25
 */

@Mapper
public interface WarehouseGoodsMapper extends BaseMapper<WarehouseGoods> {


    /**
     *  分页查询商品库
     * @param param
     * @return
     */
    @SelectProvider(type = WarehouseGoodsProvider.class, method = "getGoodsPageCount")
    Integer getGoodsPageCount(@Param("param") WarehouseGoodGetPageParam param);

    @SelectProvider(type = WarehouseGoodsProvider.class, method = "getGoodsPageList")
    List<WarehouseGoods> getGoodsPageList(@Param("param") WarehouseGoodGetPageParam param);

}
