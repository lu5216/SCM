package com.system.warehouse.api.goodsWarehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.warehouse.api.goodsWarehouse.domain.Warehouse;
import com.system.warehouse.api.goodsWarehouse.vo.WarehouseVo;

import java.util.List;

/**
 * @author lutong
 * @data 2025-1-6 006 15:30
 */

public interface IWarehouseService extends IService<Warehouse> {

    /**
     * 仓库设置-分页查询
     * @param warehouseCode
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Page<Warehouse> selectPage(String warehouseCode, Integer pageIndex, Integer pageSize);

    /**
     *  查询仓库列表-模糊查询
     * @param key
     * @return
     */
    List<WarehouseVo> selectWarehouseList(String key);

    /**
     *  保存仓库
     * @param warehouse
     * @return
     */
    String saveWarehouse(Warehouse warehouse);

    /**
     *  启用-停用仓库
     * @param warehouseCode
     * @return
     */
    String updateStatus(String warehouseCode);

    /**
     *  删除仓库
     * @param warehouseCode
     * @return
     */
    String deleteWarehouse(String warehouseCode);
}
