package com.system.systembase.api.basicConfiguration.domain;

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
@TableName("scm_sys_company_detail")
@ApiModel(value = "SysCompanyDetail", description = "公司详情表")
@AllArgsConstructor
@NoArgsConstructor
public class SysCompanyDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer id;

    /**
     * 公司Uid
     */
    @ApiModelProperty(value = "公司Uid")
    private String companyUid;

    /**
     * 公司简称
     */
    @ApiModelProperty(value = "公司简称")
    private String abbreviation;

    /**
     * 企业代码类型
     */
    @ApiModelProperty(value = "企业代码类型")
    private String creditCodeType;

    /**
     * 企业代码
     */
    @ApiModelProperty(value = "企业代码")
    private String creditCode;

    /**
     * 纳税人类别
     */
    @ApiModelProperty(value = "纳税人类别")
    private String ratepayerType;

    /**
     * 注册资金（万元）
     */
    @ApiModelProperty(value = "注册资金（万元）")
    private String registeredCapital;

    /**
     * 法定代表人
     */
    @ApiModelProperty(value = "法定代表人")
    private String legalRepresentative;

    /**
     * 企业成立日期
     */
    @ApiModelProperty(value = "企业成立日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date companyCreateDate;

    /**
     * 营业期限
     */
    @ApiModelProperty(value = "营业期限")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date periodBusiness;

    /**
     * 注册地址
     */
    @ApiModelProperty(value = "注册地址")
    private String enrollAddress;

    /**
     * 经营范围
     */
    @ApiModelProperty(value = "经营范围")
    private String scopeBusiness;

    /**
     * 企业营业执照
     */
    @ApiModelProperty(value = "企业营业执照")
    private String license;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
}