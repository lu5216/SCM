package com.system.systembase.impl.rabbitMQ.enums;

/**
 * @author lutong
 * @name RabbitEnum
 * @data 2025-03-14
 */
public enum RabbitEnum {

    // 交换机
    EMAIL_DIRECT_EXCHANGE("交换机-发送邮件", "scm_email_direct_exchange"),
    WX_IA_DIRECT_EXCHANGE("交换机-微信服务号消息模板推送-预警库存", "scm_wx_ia_direct_exchange"),
    WX_SA_DIRECT_EXCHANGE("交换机-微信服务号消息模板推送-销售订单交付通知", "scm_wx_sa_direct_exchange"),

    // 路由密钥
    EMAIL_ROUTING_KEY("路由密钥-发送邮件", "scm_email_routing_key"),
    WX_IA_ROUTING_KEY("路由密钥-微信服务号消息模板推送-预警库存", "scm_wx_ia_routing_key"),
    WX_SA_ROUTING_KEY("路由密钥-微信服务号消息模板推送-销售订单交付通知", "scm_wx_sa_routing_key"),
    ;

    private final String name;
    private final String code;

    private RabbitEnum(String name, String code) {
        this.name = name;
        this.code = code;

    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
