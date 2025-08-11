package com.system.warehouse.api.goods.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2025-1-10 010 11:14
 */

@Data
public class WarehouseGoodGetPageParam {

    /**
     * 商品识别码-精准
     */
    @ApiModelProperty(value = "商品识别码-精准")
    private String goodsCode;

    /**
     * 商品名称-精准
     */
    @ApiModelProperty(value = "商品名称-精准")
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
     * 商品类型-数据字典
     */
    @ApiModelProperty(value = "商品类型-数据字典")
    private String goodsType;

    /**
     * 预设仓库id
     */
    @ApiModelProperty(value = "预设仓库id")
    private Integer preWarehouseId;

    /**
     * 包装方式
     */
    @ApiModelProperty(value = "包装方式-数据字典")
    private String packaging;

    /**
     * 创建时间-开始
     */
    @ApiModelProperty(value = "创建时间-开始")
    private String startCreateDate;

    /**
     * 创建时间-结束
     */
    @ApiModelProperty(value = "创建时间-结束")
    private String endCreateDate;

    /**
     * 所属企业uid
     */
    @ApiModelProperty(value = "所属企业uid")
    private String companyUid;

    @ApiModelProperty(value = "pageIndex")
    private Integer pageIndex;

    @ApiModelProperty(value = "pageSize")
    private Integer pageSize;

    @ApiModelProperty(value = "导出-excel格式")
    private String suffix;
}
