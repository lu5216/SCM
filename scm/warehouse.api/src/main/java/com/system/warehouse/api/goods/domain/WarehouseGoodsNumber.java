package com.system.warehouse.api.goods.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@TableName("scm_warehouse_goods_number")
@ApiModel(value = "WarehouseGoodsNumber", description = "商品条码表")
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseGoodsNumber implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 商品条码
     */
    @ApiModelProperty(value = "商品条码")
    private String goodsNumber;

    /**
     * 状态(0无效，1有效)
     */
    @ApiModelProperty(value = "状态(0无效，1有效)")
    private Boolean status;

    /**
     * 是否删除(0未删，1删除)
     */
    @ApiModelProperty(value = "是否删除(0未删，1删除)")
    private Boolean isDel;

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
     * 修改者
     */
    @ApiModelProperty(value = "修改者")
    private String updateUserName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date updateTime;
}