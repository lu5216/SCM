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
@TableName("scm_warehouse_area")
@ApiModel(value = "WarehouseArea", description = "库区表")
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseArea implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 仓库区域ID(主键、自增)
     */
    @ApiModelProperty(value = "仓库区域ID(主键、自增)")
    private Long id;

    /**
     * 仓库区域编码
     */
    @ApiModelProperty(value = "仓库区域编码")
    private String warehouseAreaCode;

    /**
     * 仓库区域名称
     */
    @ApiModelProperty(value = "仓库区域名称")
    private String warehouseAreaName;

    /**
     * 仓库ID
     */
    @ApiModelProperty(value = "仓库ID")
    private Integer warehouseId;

    /**
     * 状态(0无效，1有效)
     */
    @ApiModelProperty(value = "状态(0无效，1有效)")
    private Boolean status;

    /**
     * 逻辑删除（0、表示正常 1、表示删除）
     */
    @ApiModelProperty(value = "逻辑删除（0、表示正常 1、表示删除）")
    private Boolean isDelete;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

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
     * 坐标-x
     */
    @ApiModelProperty(value = "坐标-x")
    private BigDecimal modelX;

    /**
     * 坐标-y
     */
    @ApiModelProperty(value = "坐标-y")
    private BigDecimal modelY;

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