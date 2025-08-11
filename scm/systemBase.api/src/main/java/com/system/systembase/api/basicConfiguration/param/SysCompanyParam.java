package com.system.systembase.api.basicConfiguration.param;

import com.system.common.enums.api.basicConfiguration.CompanyStatusEnum;
import com.system.common.utils.api.basicConfiguration.ClientApiUtil;
import com.system.common.utils.api.basicConfiguration.CompanyApiUtil;
import com.system.common.utils.api.basicConfiguration.VendorApiUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

/**
 * @author lutong
 * @data 2024-12-13 013 14:09
 */

@Data
public class SysCompanyParam {
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司")
    private String company;

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

    public String getStatus() {
        return CompanyApiUtil.getCompanyStatusReturnCode(status);
    }

    public String getCompanyType() {
        return CompanyApiUtil.getCompanyTypeReturnCode(companyType);
    }

    public String getCompanyBusiness() {
        return CompanyApiUtil.getCompanyBusinessReturnCode(companyBusiness);
    }

    public String getVendorType() {
        return VendorApiUtil.getVendorTypeReturnCode(vendorType);
    }

    public String getClientType() {
        return ClientApiUtil.getClientTypeReturnCode(clientType);
    }
}
