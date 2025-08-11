package com.system.warehouse.api.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2025-1-17 017 11:02
 */

@Data
public class WarehouseGoodsListVo {

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    public Integer goodsId;

    /**
     * 商品识别码
     */
    @ApiModelProperty(value = "商品识别码")
    public String goodsCode;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    public String goodsName;

}
