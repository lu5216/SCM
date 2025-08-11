package com.system.systembase.api.wx.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lutong
 * @data 2025-3-6 006 10:53
 */

@Data
public class SalesOrderDeliveryParam implements Serializable {

    @ApiModelProperty(value = "用户openid")
    private String openId;

    @ApiModelProperty(value = "销售订单号")
    private String itemNo;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品编码")
    private String goodsCode;

    @ApiModelProperty(value = "工单状态")
    private String itemStatus;

    @ApiModelProperty(value = "交付日期")
    private String deliveryData;

    @ApiModelProperty(value = "交付地点")
    private String deliveryAddress;
}
