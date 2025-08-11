package com.system.warehouse.api.goodsWarehouse.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2025-1-7 007 10:18
 */

@Data
public class WarehouseAreaParam {

    /**
     * 仓库ID
     */
    @ApiModelProperty(value = "仓库ID")
    private Integer warehouseId;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer id;

    /**
     * 仓库区域编码
     */
    @ApiModelProperty(value = "仓库区域编码")
    private String warehouseAreaCode;

    /**
     * 仓库区域名称
     */
    @ApiModelProperty(value = "仓库区域名称")
    private String warehouseAreaName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
