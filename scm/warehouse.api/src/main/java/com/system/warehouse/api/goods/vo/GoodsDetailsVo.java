package com.system.warehouse.api.goods.vo;

import com.system.warehouse.api.goods.domain.WarehouseGoodsNumber;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lutong
 * @data 2025-3-10 010 9:18
 */

@Data
public class GoodsDetailsVo {

    @ApiModelProperty("商品ID")
    private Integer goodsId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品编码")
    private String goodsCode;

    @ApiModelProperty("商品条码数据")
    private List<WarehouseGoodsNumber> warehouseGoodsNumberList;

    @ApiModelProperty("基础信息")
    private GoodsSaveBaseInfoVo baseInfoVo;

    @ApiModelProperty("价格信息")
    private GoodsSavePriceInfoVo priceInfoVo;

    @ApiModelProperty("维护信息")
    private GoodsSaveKeepInfoVo keepInfoVo;
}
