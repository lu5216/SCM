package com.system.order.api.warehouseOrder.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.order.api.warehouseOrder.domain.WarehouseItem;
import com.system.order.api.warehouseOrder.params.WarehouseItemParam;
import com.system.order.api.warehouseOrder.params.WarehouseItemSaveParam;
import com.system.order.api.warehouseOrder.vo.WarehouseItemDetail;
import com.system.order.api.warehouseOrder.vo.WarehouseItemVo;

import java.util.List;

/**
 * @author lutong
 * @data 2025-2-14 014 14:33
 */

public interface IWarehouseItemService extends IService<WarehouseItem> {

    /**
     *  查询条数
     * @param param
     * @return
     */
    Integer selectCount(WarehouseItemParam param);

    /**
     *  查询列表
     * @param param
     * @return
     */
    List<WarehouseItemVo> selectList(WarehouseItemParam param);

    /**
     *  分页查询
     * @param param
     * @return
     */
    Page<WarehouseItemVo> selectPage(WarehouseItemParam param);

    /**
     *  新增/修改接口
     * @param param
     * @return
     */
    String insertAndUpdate(WarehouseItemSaveParam param);

    /**
     * 开始出库/入库
     * @param id
     * @return
     */
    String begin(Integer id);

    /**
     * 确认出库/入库完成
     * @param id
     * @return
     */
    String confirmFinished(Integer id);

    /**
     *  根据Id进行审核
     * @param id
     * @param auditStatus
     * @param auditReason
     * @return
     */
    String auditById(Integer id, Integer auditStatus, String auditReason);

    /**
     *  根据Id进行取消单
     * @param id
     * @param cancelReason
     * @return
     */
    String cancelById(Integer id, String cancelReason);


    /**
     *  根据ID查询出入库订单明细
     * @param id
     * @return
     */
    WarehouseItemDetail getDetailById(Integer id);


    /**
     *  根据关联单号查询
     * @param associatedOrderNo
     * @param companyUid
     * @return
     */
    List<WarehouseItem> findByAssociatedOrderNo(String associatedOrderNo, String companyUid);


    /**
     * 根据出入库单号查询
     * @param itemNo
     * @param companyUid
     * @return
     */
    WarehouseItem findByItemNo(String itemNo, String companyUid);
}
