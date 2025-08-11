package com.system.order.api.warehouseOrder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.warehouse.api.goods.domain.WarehouseGoods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lutong
 * @data 2025-2-13 013 11:50
 */

@Data
@ApiModel(value = "WarehouseItemDetail", description = "出入库订单详情")
public class WarehouseItemDetail {

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
     * 类型：1：入库，2：出库
     */
    @ApiModelProperty(value = "类型：1：入库，2：出库")
    private Integer type;

    /**
     * 工单状态：1.进行中 2.待验收 3.已完成 4.已取消
     */
    @ApiModelProperty(value = "工单状态：1.进行中 2.待验收 3.已完成 4.已取消")
    private Integer itemStatus;

    /**
     * 收货/出库状态 1-待收货/待出库 2-收货中/出库中 3-已收货/已出库
     */
    @ApiModelProperty(value = "收货/出库状态 1-待收货/待出库 2-收货中/出库中 3-已收货/已出库")
    private Integer inOutStatus;

    /**
     * 货物来源(供应商编码)
     */
    @ApiModelProperty(value = "货物来源(供应商编码)")
    private String goodsSource;

    /**
     * 货物来源(供应商名称)
     */
    @ApiModelProperty(value = "货物来源(供应商名称)")
    private String goodsSourceName;

    /**
     * 货物去向(客户编码)
     */
    @ApiModelProperty(value = "货物去向(客户编码)")
    private String goodsDestination;

    /**
     * 货物去向(客户名称)
     */
    @ApiModelProperty(value = "货物去向(客户名称)")
    private String goodsDestinationName;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    /**
     * 出入库商品数量
     */
    @ApiModelProperty(value = "出入库商品数量")
    private Integer number;

    /**
     * 预计出入仓时间
     */
    @ApiModelProperty(value = "预计出入仓时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date expectedInOutDate;

    /**
     * 关联单号
     */
    @ApiModelProperty(value = "关联单号")
    private String associatedOrderNo;

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
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 创建者(制单人)
     */
    @ApiModelProperty(value = "制单人")
    private String createUserName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;

    /**
     * 出入库订单-审核单VO
     */
    @ApiModelProperty("出入库订单-审核单VO")
    public WarehouseItemAuditVo auditVo;

    /**
     * 出入库订单-取消单Vo
     */
    @ApiModelProperty("出入库订单-取消单Vo")
    public WarehouseItemCancelVo cancelVo;

    /**
     * 商品详情
     */
    @ApiModelProperty("商品详情")
    public WarehouseGoods warehouseGoods;
}
