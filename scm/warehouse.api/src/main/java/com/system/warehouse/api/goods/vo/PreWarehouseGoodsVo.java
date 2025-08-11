package com.system.warehouse.api.goods.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.system.warehouse.api.goods.domain.WarehouseGoods;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2025-1-15 015 11:22
 */

@Data
public class PreWarehouseGoodsVo {

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

    /**
     * 预设仓库id
     */
    @ApiModelProperty(value = "预设仓库id")
    private Integer preWarehouseId;

    /**
     * 预设仓库名称
     */
    @ApiModelProperty(value = "预设仓库名称")
    private String preWarehouseName;

    /**
     * 预设仓库编码
     */
    @ApiModelProperty(value = "预设仓库编码")
    private String preWarehouseCode;

    /**
     * 预设库区id
     */
    @ApiModelProperty(value = "预设库区id")
    private Integer preWarehouseAreaId;

    /**
     * 预设库区名称
     */
    @ApiModelProperty(value = "预设库区名称")
    private String preWarehouseAreaName;

    /**
     * 预设库区编码
     */
    @ApiModelProperty(value = "预设库区编码")
    private String preWarehouseAreaCode;
}
