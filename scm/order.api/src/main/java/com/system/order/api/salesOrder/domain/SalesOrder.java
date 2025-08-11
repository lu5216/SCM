package com.system.order.api.salesOrder.domain;

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
@TableName("scm_sales_order")
@ApiModel(value = "SalesOrder", description = "销售订单")
@AllArgsConstructor
@NoArgsConstructor
public class SalesOrder implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 销售数量
     */
    @ApiModelProperty(value = "销售数量")
    private Integer number;

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
     * 币制ID
     */
    @ApiModelProperty(value = "币制ID")
    private Integer currencyId;

    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Integer clientId;

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
    private Date deliveryDate;

    /**
     * 是否审核: 1-是,0-否
     */
    @ApiModelProperty(value = "是否审核: 1-是,0-否")
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
     * 审核状态 0-待审核,1-审核通过,2-审核不通过
     */
    @ApiModelProperty(value = "审核状态 0-待审核,1-审核通过,2-审核不通过")
    private Integer auditStatus;

    /**
     * 审核原因
     */
    @ApiModelProperty(value = "审核原因")
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