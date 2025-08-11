package com.system.order.api.warehouseOrder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lutong
 * @data 2025-2-13 013 11:57
 */

@Data
@ApiModel(value = "WarehouseItemAuditVo", description = "出入库订单-取消单")
public class WarehouseItemCancelVo {

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
     * 工单状态：1.进行中 2.待验收 3.已完成 4.已取消
     */
    @ApiModelProperty(value = "工单状态：1.进行中 2.待验收 3.已完成 4.已取消")
    private Integer itemStatus;

    /**
     * 取消人
     */
    @ApiModelProperty(value = "取消人")
    private String cancelUserName;

    /**
     * 取消单时间
     */
    @ApiModelProperty(value = "取消单时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date cancelDate;

    /**
     * 取消原因
     */
    @ApiModelProperty(value = "取消原因")
    private String cancelReason;

}
