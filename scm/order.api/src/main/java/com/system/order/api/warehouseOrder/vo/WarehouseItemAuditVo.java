package com.system.order.api.warehouseOrder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lutong
 * @data 2025-2-13 013 11:56
 */

@Data
@ApiModel(value = "WarehouseItemAuditVo", description = "出入库订单-审核单")
public class WarehouseItemAuditVo {

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
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
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
}
