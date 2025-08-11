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
@TableName("scm_driver_management")
@ApiModel(value = "DriverManagement", description = "司机管理")
@AllArgsConstructor
@NoArgsConstructor
public class DriverManagement implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    @ExcelIgnore
    private Integer id;

    /**
     * 司机名称
     */
    @ApiModelProperty(value = "司机名称")
    @ExcelProperty(value = "司机名称")
    private String name;

    /**
     * 司机电话
     */
    @ApiModelProperty(value = "司机电话")
    @ExcelProperty(value = "司机电话")
    private String phone;

    /**
     * 司机邮件
     */
    @ApiModelProperty(value = "司机邮件")
    @ExcelProperty(value = "司机邮件")
    private String email;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    @ExcelProperty(value = "身份证号")
    private String idNo;

    /**
     * 危品从业资格(0无，1有)
     */
    @ApiModelProperty(value = "危品从业资格(0无，1有)")
    @ExcelProperty(value = "危品从业资格", converter = EasyExcelConvert.class)
    @EnumFiledConvert(enumMap = "false|无,true|有")
    private Boolean hgq;

    /**
     * 海关监管备案(0未备案，1已备案)
     */
    @ApiModelProperty(value = "海关监管备案(0未备案，1已备案)")
    @ExcelProperty(value = "海关监管备案", converter = EasyExcelConvert.class)
    @EnumFiledConvert(enumMap = "false|未备案,true|已备案")
    private Boolean csf;

    /**
     * 从业资格证
     */
    @ApiModelProperty(value = "从业资格证")
    @ExcelIgnore
    private String pqc;

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