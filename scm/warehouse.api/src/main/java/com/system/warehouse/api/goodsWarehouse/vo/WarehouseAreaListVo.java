package com.system.warehouse.api.goodsWarehouse.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @version 1.0
 * @date 10/3/2025 下午10:12
 * @title
 */

@Data
public class WarehouseAreaListVo {

    /**
     * 仓库区域ID
     */
    @ApiModelProperty(value = "仓库区域ID")
    private Long id;

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
}
