package com.system.systembase.api.basicConfiguration.vo;

import com.system.common.utils.api.basicConfiguration.CompanyApiUtil;
import com.system.common.utils.api.basicConfiguration.VendorApiUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

/**
 * @author lutong
 * @data 2024-12-24 024 14:20
 */

@Data
public class SysVendorDetail {

    /**
     * 供应商编码
     */
    @ApiModelProperty(value = "供应商编码")
    private String vendorCode;

    // 供应商数据
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String linkman;

    /**
     * 注册邮箱
     */
    @ApiModelProperty(value = "注册邮箱")
    private String email;

    /**
     * 注册电话
     */
    @ApiModelProperty(value = "注册电话")
    private String phone;

    // 公司数据
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
     * 供应商类型
     */
    @ApiModelProperty(value = "供应商类型")
    private String vendorType;

    // 公司明细数据
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
    private Date companyCreateDate;

    /**
     * 营业期限
     */
    @ApiModelProperty(value = "营业期限")
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

    // 公用参数
    /**
     * 修改者
     */
    @ApiModelProperty(value = "修改者")
    private String updateUserName;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


    public String getCompanyType() {
        String type = CompanyApiUtil.getCompanyTypeReturnName(companyType);
        if (Objects.equals(type, companyType)) {
            type = CompanyApiUtil.getCompanyTypeReturnCode(companyType);
        }
        return type;
    }

    public String getCompanyBusiness() {
        String business =  CompanyApiUtil.getCompanyBusinessReturnName(companyBusiness);
        if (Objects.equals(business, companyBusiness)) {
            business = CompanyApiUtil.getCompanyBusinessReturnCode(companyBusiness);
        }
        return business;
    }

    public String getStatus() {
        String companyStatus = CompanyApiUtil.getCompanyStatusReturnName(status);
        if (Objects.equals(companyStatus, status)) {
            companyStatus = CompanyApiUtil.getCompanyStatusReturnCode(status);
        }
        return companyStatus;
    }

    public String getVendorType() {
        String type = VendorApiUtil.getVendorTypeReturnName(vendorType);
        if (Objects.equals(type, vendorType)) {
            type = VendorApiUtil.getVendorTypeReturnCode(vendorType);
        }
        return type;
    }
}
