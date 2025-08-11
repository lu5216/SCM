package com.system.warehouse.impl.goodsWarehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.warehouse.api.goodsWarehouse.domain.WarehouseArea;
import com.system.warehouse.api.goodsWarehouse.vo.WarehouseAreaVo;
import com.system.warehouse.impl.goodsWarehouse.provider.WarehouseAreaProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author lutong
 * @data 2025-1-7 007 9:13
 */

@Mapper
public interface WarehouseAreaMapper extends BaseMapper<WarehouseArea> {

    /**
     *  库区-分页查询-count
     * @param warehouseCode
     * @return
     */
    @SelectProvider(type = WarehouseAreaProvider.class, method = "getPageCount")
    Integer getPageCount(String warehouseCode);

    /**
     *  库区-分页查询-list
     * @param warehouseCode
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @SelectProvider(type = WarehouseAreaProvider.class, method = "getPageList")
    List<WarehouseAreaVo> getPageList(String warehouseCode, Integer pageIndex, Integer pageSize);
}
