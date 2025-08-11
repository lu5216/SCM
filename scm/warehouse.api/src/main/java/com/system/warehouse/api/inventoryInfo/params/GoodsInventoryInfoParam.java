package com.system.warehouse.api.inventoryInfo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2025-2-19 019 17:10
 */

@Data
public class GoodsInventoryInfoParam {

    /**
     * 商品识别码
     */
    @ApiModelProperty(value = "商品识别码")
    private String goodsCode;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    /**
     * 仓库ID
     */
    @ApiModelProperty(value = "仓库Id")
    private Integer warehouseId;

    /**
     * 库区ID
     */
    @ApiModelProperty(value = "库区Id")
    private Integer warehouseAreaId;

    /**
     * 企业UID
     */
    @ApiModelProperty(value = "企业UID")
    private String companyUid;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createUserName;

    /**
     * 页
     */
    @ApiModelProperty(value = "页")
    private Integer pageIndex;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Integer pageSize;
}
