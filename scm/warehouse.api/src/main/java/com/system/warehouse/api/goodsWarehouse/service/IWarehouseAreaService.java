package com.system.warehouse.api.goodsWarehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.warehouse.api.goodsWarehouse.domain.WarehouseArea;
import com.system.warehouse.api.goodsWarehouse.param.WarehouseAreaModuleParam;
import com.system.warehouse.api.goodsWarehouse.param.WarehouseAreaParam;
import com.system.warehouse.api.goodsWarehouse.vo.WarehouseAreaListVo;
import com.system.warehouse.api.goodsWarehouse.vo.WarehouseAreaVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author lutong
 * @data 2025-1-7 007 9:14
 */

public interface IWarehouseAreaService extends IService<WarehouseArea> {

    /**
     *  库区-分页查询
     * @param warehouseCode
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Page<WarehouseAreaVo> selectPage(String warehouseCode, Integer pageIndex, Integer pageSize);

    /**
     *  查询库区列表
     * @param warehouseId
     * @return
     */
    List<WarehouseAreaListVo> selectByWarehouseId(Integer warehouseId);

    /**
     *  保存库区
     * @param param
     * @return
     */
    String saveWarehouseArea(WarehouseAreaParam param);

    /**
     *  启用-停用库区
     * @param warehouseAreaCode
     * @return
     */
    String updateStatus(String warehouseAreaCode);

    /**
     *  删除仓库
     * @param warehouseAreaCode
     * @return
     */
    String deleteWarehouseArea(String warehouseAreaCode);

    /**
     *  编辑库区面积
     * @param param
     * @return
     */
    String editModel(WarehouseAreaModuleParam param);

}
