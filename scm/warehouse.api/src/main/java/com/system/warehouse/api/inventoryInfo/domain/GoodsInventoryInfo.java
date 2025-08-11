package com.system.warehouse.api.inventoryInfo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@TableName("scm_goods_inventory_info")
@ApiModel(value = "WarehouseArea", description = "库存信息表")
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInventoryInfo implements Serializable {

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
     * 仓库id
     */
    @ApiModelProperty(value = "仓库id")
    private Integer warehouseId;

    /**
     * 库区id
     */
    @ApiModelProperty(value = "库区id")
    private Integer warehouseAreaId;

    /**
     * 库存数量
     */
    @ApiModelProperty(value = "库存数量")
    private Integer quantity;

    /**
     * 预警库存
     */
    @ApiModelProperty(value = "预警库存")
    private Integer inventoryAlert;

    /**
     * 是否已预警
     */
    @ApiModelProperty(value = "是否已预警: 0-没有, 1-有")
    private Boolean alertAlready;

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
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}