package com.system.warehouse.api.goods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lutong
 * @data 2025-1-10 010 13:59
 */

@Data
@ApiModel(value = "GoodsSavePriceInfoVo", description = "商品库-保存-价格信息")
public class GoodsSavePriceInfoVo {

    /**
     * 商品识别码
     */
    @ApiModelProperty(value = "商品识别码", required = true)
    private String goodsCode;

    /**
     * 净重(KG)
     */
    @ApiModelProperty(value = "净重(KG)", required = true)
    private BigDecimal netWeight;

    /**
     * 毛重(KG)
     */
    @ApiModelProperty(value = "毛重(KG)")
    private BigDecimal grossWeight;

    /**
     * 体积(CMB)
     */
    @ApiModelProperty(value = "体积(CMB)")
    private BigDecimal volume;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价", required = true)
    private BigDecimal unitPrice;

    /**
     * 第一数量单位
     */
    @ApiModelProperty(value = "第一数量单位", required = true)
    private String firstQuantityUnit;

    /**
     * 第二数量单位
     */
    @ApiModelProperty(value = "第二数量单位")
    private String secondQuantityUnit;

    /**
     * 第一数量单位代码
     */
    @ApiModelProperty(value = "第一数量单位代码", required = true)
    private String firstQuantityUnitCode;

    /**
     * 第二数量单位代码
     */
    @ApiModelProperty(value = "第二数量单位代码")
    private String secondQuantityUnitCode;

    /**
     * 重量单位
     */
    @ApiModelProperty(value = "重量单位", required = true)
    private String weightUnit;

    /**
     * 重量单位代码
     */
    @ApiModelProperty(value = "重量单位代码", required = true)
    private String weightUnitCode;

    /**
     * 币制
     */
    @ApiModelProperty(value = "币制Id", required = true)
    private Integer currencyId;

    /**
     * 币制名称
     */
    @ApiModelProperty(value = "币制名称")
    private String currencyName;

    /**
     * 成本计算方式
     */
    @ApiModelProperty(value = "成本计算方式，填写公式")
    private String costCalculationWay;

    /**
     * 供应商结算方式：1.单位1结算；2.单位2结算
     */
    @ApiModelProperty(value = "供应商结算方式：1.单位1结算；2.单位2结算", required = true)
    private Integer supplierSettlement;
}
