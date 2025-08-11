package com.system.systembase.api.basicConfiguration.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.common.utils.api.basicConfiguration.VendorApiUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lutong
 * @data 2024-12-30 030 17:23
 */

@Data
public class SysVendorVo {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer id;

    /**
     * 供应商编码
     */
    @ApiModelProperty(value = "供应商编码")
    private String vendorCode;

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
     * 注册名称
     */
    @ApiModelProperty(value = "注册名称")
    private String registerName;

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

    /**
     * 供应商类型
     */
    @ApiModelProperty(value = "供应商类型")
    private String vendorType;

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
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date updateTime;

    /**
     * 是否合作：1-合作,0不合作
     */
    @ApiModelProperty(value = "是否合作：1-合作,0不合作")
    private Boolean isCooperation;

    /**
     * 是否启用：1-启用,0弃用
     */
    @ApiModelProperty(value = "是否启用：1-启用,0弃用")
    private Boolean isEnable;

    /**
     * 是否生效：1-生效,0-禁用
     */
    @ApiModelProperty(value = "是否生效：1-生效,0-禁用")
    private Boolean isValidated;

    public String getVendorType() {
        return VendorApiUtil.getVendorTypeReturnName(vendorType);
    }
}
