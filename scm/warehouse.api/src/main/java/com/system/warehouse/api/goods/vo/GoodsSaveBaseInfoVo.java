package com.system.warehouse.api.goods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2025-1-10 010 13:44
 */

@Data
@ApiModel(value = "GoodsSaveBaseInfoVo", description = "商品库-保存-商品信息")
public class GoodsSaveBaseInfoVo {

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private Integer id;

    /**
     * 商品识别码
     */
    @ApiModelProperty(value = "商品识别码", required = true)
    private String goodsCode;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称", required = true)
    private String goodsName;

    /**
     * 商品型号
     */
    @ApiModelProperty(value = "商品型号")
    private String goodsModel;

    /**
     * 商品尺寸
     */
    @ApiModelProperty(value = "商品尺寸")
    private String goodsSpec;

    /**
     * 商品类型
     */
    @ApiModelProperty(value = "商品类型", required = true)
    private String goodsType;

    /**
     * 商品种类
     */
    @ApiModelProperty(value = "商品种类")
    private String goodsSpeciesName;

    /**
     * 预设仓库id
     */
    @ApiModelProperty(value = "预设仓库id", required = true)
    private Integer preWarehouseId;

    /**
     * 预设库区
     */
    @ApiModelProperty(value = "预设库区id", required = true)
    private Integer preWarehouseAreaId;

    /**
     * 预设仓库名称
     */
    @ApiModelProperty(value = "预设仓库名称")
    private String preWarehouseName;

    /**
     * 预设库区名称
     */
    @ApiModelProperty(value = "预设库区名称")
    private String preWarehouseAreaName;

    /**
     * 包装方式
     */
    @ApiModelProperty(value = "包装方式", required = true)
    private String packaging;

    /**
     * 预警库存
     */
    @ApiModelProperty(value = "预警库存")
    private Integer inventoryAlert;

    /**
     * 是否需要预警
     */
    @ApiModelProperty(value = "是否需要预警: 0-需要, 1-不需要")
    private Boolean alertAlready;

    /**
     * 商品描述
     */
    @ApiModelProperty(value = "商品描述")
    private String goodsDescribe;

    /**
     * 商品图片
     */
    @ApiModelProperty(value = "商品图片")
    private String goodsPicture;
}
