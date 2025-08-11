package com.system.systembase.api.wx.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lutong
 * @data 2025-3-6 006 10:38
 */

@Data
public class InventoryAlertTemplateParam implements Serializable {

    @ApiModelProperty(value = "用户openid")
    private String openId;

    @ApiModelProperty(value = "预警库存")
    private String inventoryDate;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品编码")
    private String goodsCode;

    @ApiModelProperty(value = "库存数量")
    private String quantity;

    @ApiModelProperty(value = "预警库存")
    private String inventoryAlert;
}
