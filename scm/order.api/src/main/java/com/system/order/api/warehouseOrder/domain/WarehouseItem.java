package com.system.order.api.warehouseOrder.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@TableName("scm_item_warehouse")
@ApiModel(value = "WarehouseItem", description = "出入库订单")
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
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
     * 货物去向(客户编码)
     */
    @ApiModelProperty(value = "货物去向(客户编码)")
    private String goodsDestination;

    /**
     * 预计出入仓时间
     */
    @ApiModelProperty(value = "预计出入仓时间")
    private Date expectedInOutDate;

    /**
     * 实际出入仓时间
     */
    @ApiModelProperty(value = "实际出入仓时间")
    private Date confirmationTime;

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
     * 是否审核: 1-是 0-否
     */
    @ApiModelProperty(value = "是否审核: 1-是 0-否")
    private Boolean isAudit;

    /**
     * 审核确认人
     */
    @ApiModelProperty(value = "审核确认人")
    private String auditUserName;

    /**
     * 审核确认时间
     */
    @ApiModelProperty(value = "审核确认时间")
    private Date auditDate;

    /**
     * 审核状态 0 待审核 1 审核通过 2 审核不通过
     */
    @ApiModelProperty(value = "审核状态 0 待审核 1 审核通过 2 审核不通过")
    private Integer auditStatus;

    /**
     * 审核备注
     */
    @ApiModelProperty(value = "审核备注")
    private String auditReason;

    /**
     * 取消人
     */
    @ApiModelProperty(value = "取消人")
    private String cancelUserName;

    /**
     * 取消单时间
     */
    @ApiModelProperty(value = "取消单时间")
    private Date cancelDate;

    /**
     * 取消原因
     */
    @ApiModelProperty(value = "取消原因")
    private String cancelReason;

    /**
     * 企业UID
     */
    @ApiModelProperty(value = "企业UID")
    private String companyUid;

    /**
     * 创建者(制单人)
     */
    @ApiModelProperty(value = "创建者(制单人)")
    private String createUserName;

    /**
     * 修改者
     */
    @ApiModelProperty(value = "修改者")
    private String updateUserName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
}