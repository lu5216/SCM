package com.system.order.impl.warehouseOrder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.order.api.warehouseOrder.domain.WarehouseItem;
import com.system.order.api.warehouseOrder.params.WarehouseItemParam;
import com.system.order.api.warehouseOrder.vo.WarehouseItemVo;
import com.system.order.impl.warehouseOrder.provider.WarehouseItemProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author lutong
 * @data 2025-2-14 014 14:34
 */

@Mapper
public interface WarehouseItemMapper extends BaseMapper<WarehouseItem> {

    @SelectProvider(type = WarehouseItemProvider.class, method = "getPageCount")
    Integer getPageCount(WarehouseItemParam param);

    @SelectProvider(type = WarehouseItemProvider.class, method = "getPageList")
    List<WarehouseItemVo> getPageList(WarehouseItemParam param);
}
