package com.system.common.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
/**
 * @author lutong
 * @data 2024-12-23 023 17:21
 */

@Data
@Component
@RefreshScope
public class WxMpProperties {
    /**
     * 公众号appId
     */
    @Value("${wx.appId}")
    private String appId;

    /**
     * 公众号appSecret
     */
    @Value("${wx.secret}")
    private String secret;

    /**
     * 公众号token
     */
    @Value("${wx.token}")
    private String token;

    /**
     * 公众号aesKey
     */
    @Value("${wx.aesKey}")
    private String aesKey;
    /**
     * 模板名称
     */
    @Value("${wx.templateId}")
    private String templateId;

    /**
     * 模板名称-库存预警
     */
    @Value("${wx.inventoryAlertTemplateId}")
    private String inventoryAlertTemplateId;

    /**
     * 模板名称-销售订单交付通知
     */
    @Value("${wx.deliveryTemplateId}")
    private String deliveryTemplateId;

    /**
     * 小程序appletAppId
     */
    @Value("${wx.appletAppId}")
    private String appletAppId;

    /**
     * 小程序跳转路径
     */
    @Value("${wx.pagePath}")
    private String pagePath;
}
