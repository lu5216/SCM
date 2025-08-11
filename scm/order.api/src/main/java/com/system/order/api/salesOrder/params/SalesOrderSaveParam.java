package com.system.order.api.salesOrder.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lutong
 * @data 2025-2-20 020 17:19
 */

@Data
public class SalesOrderSaveParam {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 工单号
     */
    @ApiModelProperty(value = "工单号")
    private String itemNo;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private Integer goodsId;

    /**
     * 销售数量
     */
    @ApiModelProperty(value = "销售数量")
    private Integer number;

    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Integer clientId;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 交付地点
     */
    @ApiModelProperty(value = "交付地点")
    private String deliveryAddress;

    /**
     * 交付日期
     */
    @ApiModelProperty(value = "交付日期")
    private String deliveryData;

    /**
     * 是否审核: 1-是,0-否
     */
    @ApiModelProperty(value = "是否审核: 1-是,0-否")
    private Boolean isAudit;
}
