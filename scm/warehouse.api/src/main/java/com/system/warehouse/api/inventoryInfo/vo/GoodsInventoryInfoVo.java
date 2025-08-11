package com.system.warehouse.api.inventoryInfo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lutong
 * @data 2025-2-19 019 17:03
 */

@Data
public class GoodsInventoryInfoVo {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer id;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private Integer goodsId;

    /**
     * 商品识别码
     */
    @ApiModelProperty(value = "商品识别码")
    private String goodsCode;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
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
    @ApiModelProperty(value = "商品类型")
    private String goodsType;

    /**
     * 净重(KG)
     */
    @ApiModelProperty(value = "净重(KG)")
    private BigDecimal netWeight;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    /**
     * 结算方式：1.单位1结算；2.单位2结算
     */
    @ApiModelProperty(value = "结算方式：1.单位1结算；2.单位2结算")
    private Integer supplierSettlement;

    /**
     * 仓库Id
     */
    @ApiModelProperty(value = "仓库ID")
    private String warehouseId;

    /**
     * 仓库名称
     */
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    /**
     * 库区Id
     */
    @ApiModelProperty(value = "库区Id")
    private String warehouseAreaId;

    /**
     * 库区名称
     */
    @ApiModelProperty(value = "库区名称")
    private String warehouseAreaName;

    /**
     * 库存数量
     */
    @ApiModelProperty(value = "库存数量")
    private Integer quantity;

    /**
     * 企业UID
     */
    @ApiModelProperty(value = "企业UID")
    private String companyUid;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createUserName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
}
