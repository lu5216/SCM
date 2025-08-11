package com.system.order.api.warehouseOrder.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2025-2-13 013 13:49
 */

@Data
public class WarehouseItemParam {

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
     * 货物去向(客户编码)
     */
    @ApiModelProperty(value = "货物去向(客户编码)")
    private String goodsDestination;

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
     * 关联单号
     */
    @ApiModelProperty(value = "关联单号")
    private String associatedOrderNo;

    /**
     * 商品Id
     */
    @ApiModelProperty(value = "商品Id")
    private Integer goodsId;

    /**
     * 创建者(制单人)
     */
    @ApiModelProperty(value = "制单人")
    private String createUserName;

    /**
     * 创建时间-开始
     */
    @ApiModelProperty(value = "创建时间-开始")
    private String createDataStart;

    /**
     * 创建时间-结束
     */
    @ApiModelProperty(value = "创建时间-结束")
    private String createDataEnd;

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
