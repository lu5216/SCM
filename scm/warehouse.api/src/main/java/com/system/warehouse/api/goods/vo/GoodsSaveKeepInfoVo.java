package com.system.warehouse.api.goods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author lutong
 * @data 2025-1-10 010 14:05
 */

@Data
@ApiModel(value = "GoodsSaveKeepInfoVo", description = "商品库-保存-维护信息")
public class GoodsSaveKeepInfoVo {

    /**
     * 商品识别码
     */
    @ApiModelProperty(value = "商品识别码", required = true)
    private String goodsCode;

    /**
     * 原产国
     */
    @ApiModelProperty(value = "原产国")
    private String originCountry;

    /**
     * 保质期
     */
    @ApiModelProperty(value = "保质期")
    private Integer shelfLifeNo;

    /**
     * 保质期单位(年|月|天)
     */
    @ApiModelProperty(value = "保质期单位(年|月|天)")
    private String shelfLifeUnit;

}
