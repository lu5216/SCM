package com.system.warehouse.api.goodsWarehouse.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@TableName("scm_warehouse")
@ApiModel(value = "Warehouse", description = "仓库表")
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 仓库名称
     */
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    /**
     * 仓库编码
     */
    @ApiModelProperty(value = "仓库编码")
    private String warehouseCode;

    /**
     * 仓库功能
     */
    @ApiModelProperty(value = "仓库功能")
    private String warehouseType;

    /**
     * 仓库类型
     */
    @ApiModelProperty(value = "仓库类型")
    private String warehouseStatus;

    /**
     * 仓库供应商
     */
    @ApiModelProperty(value = "仓库供应商")
    private String warehouseVendor;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String linkName;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String linePhone;

    /**
     * 仓库状态 1：正常，2：停用
     */
    @ApiModelProperty(value = "仓库状态 1：正常，2：停用")
    private Boolean status;

    /**
     * 企业UID
     */
    @ApiModelProperty(value = "企业UID")
    private String companyUid;

    /**
     * 省名称
     */
    @ApiModelProperty(value = "省名称")
    private String province;

    /**
     * 市名称
     */
    @ApiModelProperty(value = "市名称")
    private String city;

    /**
     * 区名称
     */
    @ApiModelProperty(value = "区名称")
    private String district;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String address;

    /**
     * 面积-长
     */
    @ApiModelProperty(value = "面积-长")
    private BigDecimal modelLength;

    /**
     * 面积-宽
     */
    @ApiModelProperty(value = "面积-宽")
    private BigDecimal modelWidth;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createUserName;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
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