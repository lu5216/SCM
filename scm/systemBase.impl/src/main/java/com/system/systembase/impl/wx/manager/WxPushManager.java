package com.system.systembase.impl.wx.manager;

import com.system.common.vo.WxMpProperties;
import com.system.systembase.api.wx.param.InventoryAlertTemplateParam;
import com.system.systembase.api.wx.param.SalesOrderDeliveryParam;
import com.system.systembase.api.wx.param.TemplateParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


import java.text.SimpleDateFormat;

/**
 * @author lutong
 * @data 2024-12-23 023 17:07
 */

@Slf4j
@Component
@PropertySource(value = "classpath:application-wx.yml")
public class WxPushManager {

    @Autowired
    private WxMpProperties wxMpProperties;

    /**
     * 微信公众号API的Service
     */
    private final WxMpService wxMpService;
    WxPushManager(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }


//    public String push(TemplateParam param) {
//        // 跳小程序所需数据
//        WxMpTemplateMessage.MiniProgram miniProgram = new WxMpTemplateMessage.MiniProgram();
//        miniProgram.setAppid(wxMpProperties.getAppletAppId());
//        miniProgram.setPagePath(wxMpProperties.getPagePath());
//
//        // 发送模板消息接口
//        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
//                .toUser(param.getOpenId())     // 接收者openid
//                .templateId(wxMpProperties.getTemplateId())
////                .miniProgram(miniProgram)
//                .build();
//
//        // 添加模板数据
//        SimpleDateFormat sdf = new SimpleDateFormat();
//        templateMessage.addData(new WxMpTemplateData("title", "生产任务通知", "#FF00FF"))
//                .addData(new WxMpTemplateData("productionDate", sdf.format(param.getProductionDate()), "#000000"))
//                .addData(new WxMpTemplateData("stationName", param.getStationName(), "#000000"))
//                .addData(new WxMpTemplateData("stationNum", param.getStationNum(), "#000000"))
//                .addData(new WxMpTemplateData("goodsName", param.getGoodsName(), "#000000"))
//                .addData(new WxMpTemplateData("goodsCode", param.getGoodsCode(), "#000000"));
//
//        String msgId = null;
//        try {
//            // 发送模板消息
//            msgId = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
//        } catch (WxErrorException e) {
//            e.printStackTrace();
//        }
//        log.warn("推送微信模板信息：{}", msgId != null ? "成功" : "失败");
//        return msgId;
//    }


    /**
     *  推送-预警库存
     * @param param
     * @return
     */
    public String pushInventoryAlert(InventoryAlertTemplateParam param) {
        // 跳小程序所需数据
        WxMpTemplateMessage.MiniProgram miniProgram = new WxMpTemplateMessage.MiniProgram();
        miniProgram.setAppid(wxMpProperties.getAppletAppId());
        miniProgram.setPagePath(wxMpProperties.getPagePath());

        // 发送模板消息接口
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(param.getOpenId())     // 接收者openid
                .templateId(wxMpProperties.getInventoryAlertTemplateId())
                .build();

        // 添加模板数据
        templateMessage.addData(new WxMpTemplateData("title", "预警库存通知", "#FF00FF"))
                .addData(new WxMpTemplateData("inventoryDate", param.getInventoryDate(), "#000000"))
                .addData(new WxMpTemplateData("goodsName", param.getGoodsName(), "#000000"))
                .addData(new WxMpTemplateData("goodsCode", param.getGoodsCode(), "#000000"))
                .addData(new WxMpTemplateData("quantity", param.getQuantity(), "#000000"))
                .addData(new WxMpTemplateData("inventoryAlert", param.getInventoryAlert(), "#000000"));

        String msgId = null;
        try {
            // 发送模板消息
            msgId = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        log.warn("推送微信模板信息：{}", msgId != null ? "成功" : "失败");
        return msgId;
    }



    /**
     *  推送-销售订单交付通知
     * @param param
     * @return
     */
    public String pushSalesOrderDelivery(SalesOrderDeliveryParam param) {
        // 跳小程序所需数据
        WxMpTemplateMessage.MiniProgram miniProgram = new WxMpTemplateMessage.MiniProgram();
        miniProgram.setAppid(wxMpProperties.getAppletAppId());
        miniProgram.setPagePath(wxMpProperties.getPagePath());

        // 发送模板消息接口
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(param.getOpenId())     // 接收者openid
                .templateId(wxMpProperties.getDeliveryTemplateId())
                .build();

        // 添加模板数据
        templateMessage.addData(new WxMpTemplateData("title", "销售订单交付通知", "#FF00FF"))
                .addData(new WxMpTemplateData("itemNo", param.getItemNo(), "#000000"))
                .addData(new WxMpTemplateData("goodsName", param.getGoodsName(), "#000000"))
                .addData(new WxMpTemplateData("goodsCode", param.getGoodsCode(), "#000000"))
                .addData(new WxMpTemplateData("itemStatus", param.getItemStatus(), "#000000"))
                .addData(new WxMpTemplateData("deliveryData", param.getDeliveryData(), "#000000"))
                .addData(new WxMpTemplateData("deliveryAddress", param.getDeliveryAddress(), "#000000"));

        String msgId = null;
        try {
            // 发送模板消息
            msgId = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        log.warn("推送微信模板信息：{}", msgId != null ? "成功" : "失败");
        return msgId;
    }
}
