package com.system.order.api.salesOrder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lutong
 * @data 2025-2-20 020 17:00
 */

@Data
public class SalesOrderVo {

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
     * 工单状态：1.待接单 2.进行中 3.已完成 4.已取消
     */
    @ApiModelProperty(value = "工单状态：1.待接单 2.进行中 3.已完成 4.已取消")
    private Integer itemStatus;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private Integer goodsId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    /**
     * 商品编码
     */
    @ApiModelProperty(value = "商品编码")
    private String goodsCode;

    /**
     * 商品型号
     */
    @ApiModelProperty(value = "商品型号")
    private String goodsModel;

    /**
     * 销售数量
     */
    @ApiModelProperty(value = "销售数量")
    private Integer number;

    /**
     * 库存数量
     */
    @ApiModelProperty(value = "库存数量")
    private Integer quantity;

    /**
     * 货物存储仓库ID
     */
    @ApiModelProperty(value = "货物存储仓库ID")
    private Integer warehouseId;

    /**
     * 货物存储库区ID
     */
    @ApiModelProperty(value = "货物存储库区ID")
    private Integer warehouseAreaId;

    /**
     * 货物存储仓库-库区 返回：仓库名称(仓库编码)-库区名称(库区编码)
     */
    @ApiModelProperty(value = "货物存储仓库-库区")
    private String warehouse;

    /**
     * 币制ID
     */
    @ApiModelProperty(value = "币制ID")
    private Integer currencyId;

    /**
     * 币制名称
     */
    @ApiModelProperty(value = "币制名称")
    private String currencyName;

    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Integer clientId;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String clientName;

    /**
     * 商品金额
     */
    @ApiModelProperty(value = "商品金额")
    private BigDecimal amount;

    /**
     * 商品单价
     */
    @ApiModelProperty(value = "商品单价")
    private BigDecimal unitPrice;

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
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private String deliveryDate;

    /**
     * 是否审核: 1-是,0-否
     */
    @ApiModelProperty(value = "是否审核: 1-是,0-否")
    private Boolean isAudit;

    /**
     * 创建者(制单人)
     */
    @ApiModelProperty(value = "创建者(制单人)")
    private String createUserName;

    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;
}
