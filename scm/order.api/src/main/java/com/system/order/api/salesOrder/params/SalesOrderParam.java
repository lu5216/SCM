package com.system.order.api.salesOrder.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2025-2-20 020 17:00
 */

@Data
public class SalesOrderParam {

    /**
     * 工单号
     */
    @ApiModelProperty(value = "工单号")
    private String itemNo;

    /**
     * 维度：0-按工单，1-按商品
     */
    @ApiModelProperty(value = "维度：0-按工单，1-按商品")
    private Integer dimensionality;

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
     * 币制ID
     */
    @ApiModelProperty(value = "币制ID")
    private Integer currencyId;

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
     * 交付地点
     */
    @ApiModelProperty(value = "交付地点")
    private String deliveryAddress;

    /**
     * 交付日期-开始
     */
    @ApiModelProperty(value = "交付日期-开始")
    private String deliveryDataStart;

    /**
     * 交付日期-结束
     */
    @ApiModelProperty(value = "交付日期-结束")
    private String deliveryDataEnd;

    /**
     * 销售员
     */
    @ApiModelProperty(value = "销售员")
    private String createUserName;

    /**
     * 创建日期-开始
     */
    @ApiModelProperty(value = "创建日期-开始")
    private String createTimeStart;

    /**
     * 创建日期-结束
     */
    @ApiModelProperty(value = "创建日期-结束")
    private String createTimeEnd;

    /**
     * 当前页
     */
    @ApiModelProperty("当前页")
    private Integer pageIndex;

    /**
     * 页面大小
     */
    @ApiModelProperty("页面大小")
    private Integer pageSize;

    /**
     * 企业uid
     */
    @ApiModelProperty(value = "企业uid")
    private String companyUid;
}
