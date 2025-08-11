package com.system.systembase.api.basicConfiguration.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.systembase.api.basicConfiguration.enums.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@TableName("scm_sys_company")
@ApiModel(value = "SysCompany", description = "公司表")
@AllArgsConstructor
@NoArgsConstructor
public class SysCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer id;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    private String companyCode;

    /**
     * 公司Uid
     */
    @ApiModelProperty(value = "公司Uid")
    private String companyUid;

    /**
     * 公司类型
     */
    @ApiModelProperty(value = "公司类型")
    private String companyType;

    /**
     * 公司业务
     */
    @ApiModelProperty(value = "公司业务")
    private String companyBusiness;

    /**
     * 经营状态
     */
    @ApiModelProperty(value = "经营状态")
    private String status;

    /**
     * 公司地址
     */
    @ApiModelProperty(value = "公司地址")
    private String address;

    /**
     * 公司邮箱
     */
    @ApiModelProperty(value = "公司邮箱")
    private String email;

    /**
     * 公司电话
     */
    @ApiModelProperty(value = "公司电话")
    private String phone;

    /**
     * 子公司名称
     */
    @ApiModelProperty(value = "子公司名称")
    private String subsidiaryName;

    /**
     * 子公司编码
     */
    @ApiModelProperty(value = "子公司编码")
    private String subsidiaryCode;

    /**
     * 子公司Uid
     */
    @ApiModelProperty(value = "子公司Uid")
    private String subsidiaryUid;

    /**
     * 是否生效：1-生效,0-禁用
     */
    @ApiModelProperty(value = "是否生效：1-生效,0-禁用")
    private Boolean isValidated;

    /**
     * 是否为供应商：1-是,0-不是
     */
    @ApiModelProperty(value = "是否为供应商：1-是,0-不是")
    private Boolean isVendor;

    /**
     * 供应商类型
     */
    @ApiModelProperty(value = "供应商类型")
    private String vendorType;

    /**
     * 是否为客户：1-是,0-不是
     */
    @ApiModelProperty(value = "是否为客户：1-是,0-不是")
    private Boolean isClient;

    /**
     * 客户类型
     */
    @ApiModelProperty(value = "客户类型")
    private String clientType;

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