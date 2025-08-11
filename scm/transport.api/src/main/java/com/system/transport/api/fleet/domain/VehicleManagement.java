package com.system.transport.api.fleet.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.common.annotation.EnumFiledConvert;
import com.system.common.converter.EasyExcelConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@TableName("scm_vehicle_management")
@ApiModel(value = "VehicleManagement", description = "车辆管理")
@AllArgsConstructor
@NoArgsConstructor
public class VehicleManagement implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    @ExcelIgnore
    private Integer id;

    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    @ExcelProperty(value = "车牌号")
    private String licenseNumber;

    /**
     * 车型
     */
    @ApiModelProperty(value = "车型")
    @ExcelProperty(value = "车型")
    private String vehicleType;

    /**
     * 载量
     */
    @ApiModelProperty(value = "载量")
    @ExcelProperty(value = "载量")
    private String capacity;

    /**
     * 港澳车牌号
     */
    @ApiModelProperty(value = "港澳车牌号")
    @ExcelProperty(value = "港澳车牌号")
    private String specialLicenseNumber;

    /**
     * 是否装载GPS(0否，1是)
     */
    @ApiModelProperty(value = "是否装载GPS(0否，1是)")
    @ExcelProperty(value = "是否装载GPS", converter = EasyExcelConvert.class)
    @EnumFiledConvert(enumMap = "false|否,true|是")
    private Boolean isGps;

    /**
     * 危品备案车辆(0未备案，1已备案)
     */
    @ApiModelProperty(value = "危品备案车辆(0未备案，1已备案)")
    @ExcelProperty(value = "危品备案车辆", converter = EasyExcelConvert.class)
    @EnumFiledConvert(enumMap = "false|未备案,true|已备案")
    private Boolean isHgq;

    /**
     * 海关监管备案(0未备案，1已备案)
     */
    @ApiModelProperty(value = "海关监管备案(0未备案，1已备案)")
    @ExcelProperty(value = "海关监管备案", converter = EasyExcelConvert.class)
    @EnumFiledConvert(enumMap = "false|未备案,true|已备案")
    private Boolean isCsf;

    /**
     * 运营证
     */
    @ApiModelProperty(value = "运营证")
    @ExcelIgnore
    private String oc;

    /**
     * 关联司机,多个司机用","分隔
     */
    @ApiModelProperty(value = "关联司机,多个司机用','分隔")
    @ExcelProperty(value = "关联司机")
    private String associatedDriver;

    /**
     * 关联司机ID,多个司机用","分隔
     */
    @ApiModelProperty(value = "关联司机,多个司机用','分隔")
    @ExcelIgnore
    private String associatedDriverId;

    /**
     * 企业UID
     */
    @ApiModelProperty(value = "企业UID")
    @ExcelIgnore
    private String companyUid;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    @ExcelProperty(value = "创建者")
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
    @ExcelProperty(value = "创建时间")
    @ColumnWidth(20)
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @ExcelIgnore
    private Date updateTime;
}