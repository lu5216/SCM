package com.system.warehouse.api.goodsWarehouse.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lutong
 * @data 2025-1-7 007 11:22
 */

@Data
public class WarehouseAreaModuleParam {

    /**
     * 仓库ID
     */
    @ApiModelProperty(value = "仓库ID")
    private Integer warehouseId;

    /**
     * 仓库区域编码
     */
    @ApiModelProperty(value = "仓库区域编码")
    private String warehouseAreaCode;

    /**
     * 库区-面积-长
     */
    @ApiModelProperty(value = "库区-面积-长")
    private BigDecimal modelLength;

    /**
     * 库区-面积-宽
     */
    @ApiModelProperty(value = "库区-面积-宽")
    private BigDecimal modelWidth;

    /**
     * 坐标-x
     */
    @ApiModelProperty(value = "坐标-x")
    private BigDecimal modelX;

    /**
     * 坐标-y
     */
    @ApiModelProperty(value = "坐标-y")
    private BigDecimal modelY;
}
