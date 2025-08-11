package com.system.warehouse.impl.inventoryInfo.scheduled;

import com.system.common.exception.CustomerAuthenticationException;
import com.system.common.utils.DateUtil;
import com.system.systembase.api.permissionsManagement.domain.SysUser;
import com.system.systembase.api.permissionsManagement.service.ISysUserService;
import com.system.systembase.api.wx.param.InventoryAlertTemplateParam;
import com.system.systembase.impl.rabbitMQ.listener.EmailRabbitListener;
import com.system.systembase.impl.rabbitMQ.listener.WxIARabbitListener;
import com.system.warehouse.api.goods.domain.WarehouseGoods;
import com.system.warehouse.api.goods.service.IWarehouseGoodsService;
import com.system.warehouse.api.inventoryInfo.domain.GoodsInventoryInfo;
import com.system.warehouse.api.inventoryInfo.service.IGoodsInventoryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author lutong
 * @data 2025-3-6 006 9:28
 */

@Component
public class GoodsInventoryInfoScheduled {

    @Autowired
    private IGoodsInventoryInfoService goodsInventoryInfoService;

    @Autowired
    private IWarehouseGoodsService warehouseGoodsService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private EmailRabbitListener emailRabbitListener;

    @Autowired
    private WxIARabbitListener wxIARabbitListener;


    @Async("MyThreadPool")
    @Scheduled(cron = "0 0/1 * * * ?")  // 每分钟执行一次
    public void captureGoodsInventoryInfo() {
        List<GoodsInventoryInfo> list = goodsInventoryInfoService.getByListAlert();
        if (list.size() > 0) {
            for (GoodsInventoryInfo info : list) {
                this.jobRun(info);
            }
        }
    }


    public void jobRun(GoodsInventoryInfo info) {
        WarehouseGoods warehouseGoods = warehouseGoodsService.getById(info.getGoodsId());
        if (warehouseGoods.getCreateUserName() != null && !warehouseGoods.getCreateUserName().equals("")) {
            SysUser sysUser = sysUserService.getOneByLoginId(warehouseGoods.getCreateUserName());
            if (sysUser == null) {
                throw new CustomerAuthenticationException("未查到建单人账号,邮件推送失败！");
            }
            // 发送邮件
            if (sysUser.getEmail() != null && !sysUser.getEmail().equals("")) {
                String content = "您创建的商品【" + warehouseGoods.getGoodsName() + "(" + warehouseGoods.getGoodsCode() + ")】";
                if (Objects.equals(info.getQuantity(), info.getInventoryAlert())) {
                    content = content + "库存已低于预警库存，请尽快处理！";
                } else {
                    content = content + "库存已到达预警，请尽快处理！";
                }
                // rabbitMQ 异步发送邮件
                emailRabbitListener.sendEmailMQ(sysUser.getEmail(), content);
            }
            // 推送微信服务号模板消息
            if (sysUser.getOpenId() != null && !sysUser.getOpenId().equals("")) {
                InventoryAlertTemplateParam param = new InventoryAlertTemplateParam();

                param.setOpenId(sysUser.getOpenId());
                param.setInventoryDate(DateUtil.getDateForFormat(new Date(), DateUtil.YMD_HMS));
                param.setGoodsName(warehouseGoods.getGoodsName());
                param.setGoodsCode(warehouseGoods.getGoodsCode());
                param.setQuantity(String.valueOf(info.getQuantity()));
                param.setInventoryAlert(String.valueOf(info.getInventoryAlert()));

                // rabbitMQ 异步推送微信模板消息
                wxIARabbitListener.wxPushInventoryAlertMQ(param);
            }

            // 推送完成，更改状态，避免重复推送
            goodsInventoryInfoService.updateAlertAlready(info.getId(), sysUser.getLoginId());
        }
    }
}
