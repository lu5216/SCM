package com.system.warehouse.impl.inventoryInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.warehouse.api.inventoryInfo.domain.GoodsInventoryInfo;
import com.system.warehouse.api.inventoryInfo.params.GoodsInventoryInfoParam;
import com.system.warehouse.api.inventoryInfo.vo.GoodsInventoryInfoVo;
import com.system.warehouse.impl.inventoryInfo.provider.GoodsInventoryInfoProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author lutong
 * @data 2025-2-19 019 16:53
 */

@Mapper
public interface GoodsInventoryInfoMapper extends BaseMapper<GoodsInventoryInfo> {

    /**
     *  分页查询商品库
     * @param param
     * @return
     */
    @SelectProvider(type = GoodsInventoryInfoProvider.class, method = "getGoodsPageCount")
    Integer getGoodsPageCount(@Param("param") GoodsInventoryInfoParam param);

    @SelectProvider(type = GoodsInventoryInfoProvider.class, method = "getGoodsPageList")
    List<GoodsInventoryInfoVo> getGoodsPageList(@Param("param") GoodsInventoryInfoParam param);
}
