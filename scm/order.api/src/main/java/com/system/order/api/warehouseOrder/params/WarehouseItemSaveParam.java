package com.system.order.api.warehouseOrder.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ZuiYing~
 * @version 1.0
 * @date 15/2/2025 下午5:09
 * @title
 */

@Data
public class WarehouseItemSaveParam {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer id;

    /**
     * 工单号,系统生成
     */
    @ApiModelProperty(value = "工单号,系统生成")
    private String itemNo;

    /**
     * 类型：1：入库，2：出库
     */
    @ApiModelProperty(value = "类型：1：入库，2：出库")
    private Integer type;

    /**
     * 货物来源(供应商编码)
     */
    @ApiModelProperty(value = "货物来源(供应商编码)")
    private String goodsSource;

    /**
     * 货物去向(客户编码)
     */
    @ApiModelProperty(value = "货物去向(客户编码)")
    private String goodsDestination;

    /**
     * 预计出入仓时间
     */
    @ApiModelProperty(value = "预计出入仓时间")
    private String expectedInOutDate;

    /**
     * 关联单号
     */
    @ApiModelProperty(value = "关联单号")
    private String associatedOrderNo;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 出入库商品ID
     */
    @ApiModelProperty(value = "出入库商品ID")
    private Integer goodsId;

    /**
     * 出入库商品数量
     */
    @ApiModelProperty(value = "出入库商品数量")
    private Integer number;

    /**
     * 是否审核: 1-是 0-否
     */
    @ApiModelProperty(value = "是否审核: 1-是 0-否")
    private Boolean isAudit;

}
