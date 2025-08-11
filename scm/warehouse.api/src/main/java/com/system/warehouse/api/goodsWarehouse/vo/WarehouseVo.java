package com.system.warehouse.api.goodsWarehouse.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2025-1-6 006 15:40
 */

@Data
public class WarehouseVo {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 仓库名称
     */
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    /**
     * 仓库编码
     */
    @ApiModelProperty(value = "仓库编码")
    private String warehouseCode;
}
