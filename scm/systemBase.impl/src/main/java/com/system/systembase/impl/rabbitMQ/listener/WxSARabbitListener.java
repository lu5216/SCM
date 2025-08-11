package com.system.systembase.impl.rabbitMQ.listener;

import com.alibaba.fastjson.JSON;
import com.system.common.exception.CustomerAuthenticationException;
import com.system.systembase.api.wx.param.SalesOrderDeliveryParam;
import com.system.systembase.impl.rabbitMQ.constants.RabbitQueueConstant;
import com.system.systembase.impl.rabbitMQ.enums.RabbitEnum;
import com.system.systembase.impl.wx.manager.WxPushManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lutong
 * @name WxPushRabbitListener
 * @data 2025-03-14
 */

@Slf4j
@Component
public class WxSARabbitListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private WxPushManager wxPushManager;

    /**
     *  RabbitMQ-微信服务号消息模板推送-生产者
     * @author lutong
     * @data 2025-3-14 014
     * @param param
     * @return void
     **/
    public void wxPushSalesOrderMQ(SalesOrderDeliveryParam param) {
        log.info("wxPushMQ-参数-param: {}", JSON.toJSONString(param));
        try {
            rabbitTemplate.convertAndSend(RabbitEnum.WX_SA_DIRECT_EXCHANGE.getCode(), RabbitEnum.WX_SA_ROUTING_KEY.getCode(), param);
        } catch (Exception e) {
            log.error("wxPushMQ-微信服务号消息模板推送失败!");
            throw new CustomerAuthenticationException("wxPushMQ-微信服务号消息模板推送失败!");
        }
    }


    /**
     *  销售订单交付通知-微信服务号消息模板推送监听事件
     * @author lutong
     * @data 2025-3-14 014
     * @param param
     * @return void
     **/
    @RabbitListener(queues = RabbitQueueConstant.WX_SA_QUEUE)
    public void wxPushSalesOrderListener(SalesOrderDeliveryParam param) {
        // 发送邮件业务
        log.info("wxPushListener-销售订单交付通知-监听数据param: {}", JSON.toJSONString(param));
        String result = wxPushManager.pushSalesOrderDelivery(param);
        if (result == null) {
            throw new CustomerAuthenticationException("微信服务号推送失败！");
        }
    }
}
