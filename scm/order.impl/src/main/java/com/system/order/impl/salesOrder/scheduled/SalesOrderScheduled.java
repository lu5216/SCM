package com.system.order.impl.salesOrder.scheduled;

import com.system.common.exception.CustomerAuthenticationException;
import com.system.common.utils.DateUtil;
import com.system.order.api.salesOrder.domain.SalesOrder;
import com.system.order.api.salesOrder.enums.ItemStatusEnum;
import com.system.order.api.salesOrder.service.ISalesOrderService;
import com.system.systembase.api.permissionsManagement.domain.SysUser;
import com.system.systembase.api.permissionsManagement.service.ISysUserService;
import com.system.systembase.api.wx.param.SalesOrderDeliveryParam;
import com.system.systembase.impl.rabbitMQ.listener.EmailRabbitListener;
import com.system.systembase.impl.rabbitMQ.listener.WxSARabbitListener;
import com.system.warehouse.api.goods.domain.WarehouseGoods;
import com.system.warehouse.api.goods.service.IWarehouseGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author lutong
 * @data 2025-3-5 005 16:56
 */

@Component
public class SalesOrderScheduled {

    @Autowired
    private ISalesOrderService salesOrderService;

    @Autowired
    private IWarehouseGoodsService warehouseGoodsService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private EmailRabbitListener emailRabbitListener;

    @Autowired
    private WxSARabbitListener wxSARabbitListener;


    @Scheduled(cron = " 0 0 9 * * ?") // 每天9点执行一次
    public void captureSaleOrder() {
        String date = DateUtil.getDateForFormat(new Date(), DateUtil.YMD);
        List<SalesOrder> list = salesOrderService.getListByTime(date);
        if (list.size() > 0) {
            for (SalesOrder salesOrder : list) {
                this.jobRun(salesOrder);
            }
        }
    }


    /**
     *  执行定时任务
     * @param salesOrder
     */
    public void jobRun(SalesOrder salesOrder) {
        if (salesOrder.getCreateUserName() != null && !salesOrder.getCreateUserName().equals("")) {
            SysUser sysUser = sysUserService.getOneByLoginId(salesOrder.getCreateUserName());
            if (sysUser == null) {
                throw new CustomerAuthenticationException("未查到建单人账号,邮件推送失败！");
            }
            // 发送邮件
            if (sysUser.getEmail() != null && !sysUser.getEmail().equals("")) {
                String content = "您创建的销售订单【" + salesOrder.getItemNo() + "】还有1天就要到达交付时间，请尽快处理！";
                // rabbitMQ 异步发送邮件
                emailRabbitListener.sendEmailMQ(sysUser.getEmail(), content);
            }
            // 推送微信服务号模板消息
            if (sysUser.getOpenId() != null && !sysUser.getOpenId().equals("")) {
                SalesOrderDeliveryParam param = new SalesOrderDeliveryParam();
                WarehouseGoods warehouseGoods = warehouseGoodsService.getById(salesOrder.getGoodsId());
                ItemStatusEnum itemStatusEnum = ItemStatusEnum.getById(salesOrder.getItemStatus());

                param.setOpenId(sysUser.getOpenId());
                param.setItemNo(salesOrder.getItemNo());
                param.setGoodsName(warehouseGoods != null ? warehouseGoods.getGoodsName() : null);
                param.setGoodsCode(warehouseGoods != null ? warehouseGoods.getGoodsCode() : null);
                param.setItemStatus(itemStatusEnum != null ? itemStatusEnum.getName() : null);
                param.setDeliveryData(DateUtil.getDateForFormat(salesOrder.getDeliveryDate(), DateUtil.YMD_HMS));
                param.setDeliveryAddress(salesOrder.getDeliveryAddress());

                // rabbitMQ 异步推送微信模板消息
                wxSARabbitListener.wxPushSalesOrderMQ(param);
            }
        }
    }
}
