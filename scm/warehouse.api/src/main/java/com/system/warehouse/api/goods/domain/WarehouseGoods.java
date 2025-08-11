package com.system.warehouse.api.goods.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.common.annotation.EnumFiledConvert;
import com.system.common.converter.EasyExcelConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@TableName("scm_warehouse_goods")
@ApiModel(value = "WarehouseGoods", description = "商品库")
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    @ExcelIgnore
    private Integer id;

    /**
     * 所属企业uid
     */
    @ApiModelProperty(value = "所属企业uid")
    @ExcelIgnore
    private String companyUid;

    /**
     * 商品识别码
     */
    @ApiModelProperty(value = "商品识别码")
    @ExcelProperty(value = "商品识别码", order = 2)
    private String goodsCode;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    @ExcelProperty(value = "商品名称", order = 1)
    private String goodsName;

    /**
     * 商品型号
     */
    @ApiModelProperty(value = "商品型号")
    @ExcelProperty(value = "商品型号", order = 3)
    private String goodsModel;

    /**
     * 商品尺寸
     */
    @ApiModelProperty(value = "商品尺寸")
    @ExcelProperty(value = "商品尺寸", order = 4)
    private String goodsSpec;

    /**
     * 商品类型
     */
    @ApiModelProperty(value = "商品类型")
    @ExcelProperty(value = "商品类型", order = 5)
    private String goodsType;

    /**
     * 净重(KG)
     */
    @ApiModelProperty(value = "净重(KG)")
    @ExcelProperty(value = "净重(KG)", order = 10)
    private BigDecimal netWeight;

    /**
     * 毛重(KG)
     */
    @ApiModelProperty(value = "毛重(KG)")
    @ExcelProperty(value = "毛重(KG)", order = 11)
    private BigDecimal grossWeight;

    /**
     * 体积(CMB)
     */
    @ApiModelProperty(value = "体积(CMB)")
    @ExcelProperty(value = "体积(CMB)", order = 9)
    private BigDecimal volume;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    @ExcelProperty(value = "单价", order = 8)
    private BigDecimal unitPrice;

    /**
     * 第一数量单位
     */
    @ApiModelProperty(value = "第一数量单位")
    @ExcelProperty(value = "单位1", order = 12)
    private String firstQuantityUnit;

    /**
     * 第二数量单位
     */
    @ApiModelProperty(value = "第二数量单位")
    @ExcelProperty(value = "单位2", order = 13)
    private String secondQuantityUnit;

    /**
     * 第一数量单位代码
     */
    @ApiModelProperty(value = "第一数量单位代码")
    @ExcelIgnore
    private String firstQuantityUnitCode;

    /**
     * 第二数量单位代码
     */
    @ApiModelProperty(value = "第二数量单位代码")
    @ExcelIgnore
    private String secondQuantityUnitCode;

    /**
     * 重量单位
     */
    @ApiModelProperty(value = "重量单位")
    @ExcelProperty(value = "重量单位", order = 14)
    private String weightUnit;

    /**
     * 重量单位代码
     */
    @ApiModelProperty(value = "重量单位代码")
    @ExcelIgnore
    private String weightUnitCode;

    /**
     * 币制
     */
    @ApiModelProperty(value = "币制Id")
    @ExcelIgnore
    private Integer currencyId;

    /**
     * 币制
     */
    @ApiModelProperty(value = "币制名称")
    @ExcelProperty(value = "币制名称", order = 15)
    @TableField(exist = false)
    private String currencyName;

    /**
     * 预设仓库id
     */
    @ApiModelProperty(value = "预设仓库id")
    @ExcelIgnore
    private Integer preWarehouseId;

    /**
     * 预设仓库名称
     */
    @ApiModelProperty(value = "预设仓库名称")
    @ExcelProperty(value = "预设仓库名称", order = 16)
    @TableField(exist = false)
    private String preWarehouseName;

    /**
     * 预设库区id
     */
    @ApiModelProperty(value = "预设库区id")
    @ExcelIgnore
    private Integer preWarehouseAreaId;

    /**
     * 预设库区名称
     */
    @ApiModelProperty(value = "预设库区名称")
    @ExcelProperty(value = "预设库区名称", order = 17)
    @TableField(exist = false)
    private String preWarehouseAreaName;

    /**
     * 原产国
     */
    @ApiModelProperty(value = "原产国")
    @ExcelProperty(value = "原产国", order = 18)
    private String originCountry;

    /**
     * 状态  1：启用  2：停用
     */
    @ApiModelProperty(value = "状态  1：启用  2：停用")
    @ExcelProperty(value = "状态", converter = EasyExcelConvert.class, order = 19)
    @EnumFiledConvert(enumMap = "1|启用,2|停用")
    private Integer status;

    /**
     * 包装方式
     */
    @ApiModelProperty(value = "包装方式")
    @ExcelProperty(value = "包装方式", order = 20)
    private String packaging;

    /**
     * 保质期
     */
    @ApiModelProperty(value = "保质期")
    @ExcelProperty(value = "保质期", order = 21)
    private Integer shelfLifeNo;

    /**
     * 保质期单位(年|月|天)
     */
    @ApiModelProperty(value = "保质期单位(年|月|天)")
    @ExcelProperty(value = "保质期单位(年|月|天)", order = 22)
    private String shelfLifeUnit;

    /**
     * 商品描述
     */
    @ApiModelProperty(value = "商品描述")
    @ExcelProperty(value = "商品描述", order = 25)
    private String goodsDescribe;

    /**
     * 商品图片
     */
    @ApiModelProperty(value = "商品图片")
    @ExcelIgnore
    private String goodsPicture;

    /**
     * 成本计算方式
     */
    @ApiModelProperty(value = "成本计算方式")
    @ExcelProperty(value = "成本计算方式", order = 23)
    private String costCalculationWay;

    /**
     * 商品种类
     */
    @ApiModelProperty(value = "商品种类")
    @ExcelProperty(value = "商品种类", order = 6)
    private String goodsSpeciesName;

    /**
     * 供应商结算方式：1.单位1结算；2.单位2结算
     */
    @ApiModelProperty(value = "供应商结算方式：1.单位1结算；2.单位2结算")
    @ExcelProperty(value = "供应商结算方式", converter = EasyExcelConvert.class, order = 24)
    @EnumFiledConvert(enumMap = "1|单位1结算,2|单位2结算")
    private Integer supplierSettlement;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    @ExcelProperty(value = "创建者", order = 26)
    private String createUserName;

    /**
     * 修改者
     */
    @ApiModelProperty(value = "修改者")
    @ExcelIgnore
    private String updateUserName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ColumnWidth(20)
    @ExcelProperty(value = "创建时间", order = 27)
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ExcelIgnore
    private Date updateTime;

    /**
     * 商品编号
     */
    @ApiModelProperty(value = "商品编号")
    @TableField(exist = false)
    @ExcelProperty(value = "商品编号", order = 7)
    private String goodsNumber;
}