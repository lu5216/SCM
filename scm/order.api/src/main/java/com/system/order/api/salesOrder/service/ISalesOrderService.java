package com.system.order.api.salesOrder.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.common.vo.SysUserVo;
import com.system.order.api.salesOrder.domain.SalesOrder;
import com.system.order.api.salesOrder.params.SalesOrderParam;
import com.system.order.api.salesOrder.params.SalesOrderSaveParam;
import com.system.order.api.salesOrder.vo.SalesOrderVo;

import java.text.ParseException;
import java.util.List;

/**
 * @author lutong
 * @data 2025-2-20 020 17:00
 */

public interface ISalesOrderService extends IService<SalesOrder> {

    /**
     *  查询条数-按工单
     * @param param
     * @return
     */
    Integer getItemCount(SalesOrderParam param);

    /**
     *  查询列表-按工单
     * @param param
     * @return
     */
    List<SalesOrderVo> getItemList(SalesOrderParam param);

    /**
     *  查询条数-按商品
     * @param param
     * @return
     */
    Integer getGoodsCount(SalesOrderParam param);

    /**
     *  查询列表-按商品
     * @param param
     * @return
     */
    List<SalesOrderVo> getGoodsList(SalesOrderParam param);

    /**
     *  分页查询
     * @param param
     * @return
     */
    Page<SalesOrderVo> selectPage(SalesOrderParam param);

    /**
     *  新增/修改接口
     * @param param
     * @return
     */
    String insertAndUpdate(SalesOrderSaveParam param) throws ParseException;


    /**
     *  接单
     * @param id
     * @return
     */
    String orderReceiving(Integer id);


    /**
     *  创建出库订单
     * @return
     */
    String createOutWarehouseItem(Integer id);

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
     *  完成出库单
     * @param itemNo
     */
    void finishOutWarehouseItem(String itemNo, SysUserVo user);

    /**
     * 根据销售单查询一条数据
     * @param salesOrderNo
     * @return
     */
    SalesOrder getOneBySalesOrderNo(String salesOrderNo);

    /**
     *  根据交付时间返回（当前时间未超过交付时间）（不查询已取消、已完成的订单）
     * @param deliveryData
     * @return
     */
    List<SalesOrder> getListByTime(String deliveryData);
}
