package com.system.warehouse.impl.goodsWarehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.warehouse.api.goodsWarehouse.domain.Warehouse;
import com.system.warehouse.impl.goodsWarehouse.provider.WarehouseProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author lutong
 * @data 2025-1-6 006 15:29
 */

@Mapper
public interface WarehouseMapper extends BaseMapper<Warehouse> {

    /**
     * 仓库设置-分页查询-count
     * @param warehouseCode
     * @return
     */
    @SelectProvider(type = WarehouseProvider.class, method = "getPageCount")
    Integer getPageCount(String warehouseCode);


    /**
     * 仓库设置-分页查询-list
     * @param warehouseCode
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @SelectProvider(type = WarehouseProvider.class, method = "getPageList")
    List<Warehouse> getPageList(String warehouseCode, Integer pageIndex, Integer pageSize);
}
