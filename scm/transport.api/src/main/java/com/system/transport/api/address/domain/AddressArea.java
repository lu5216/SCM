package com.system.transport.api.address.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@TableName("scm_address_area")
@ApiModel(value = "AddressArea", description = "地址库")
@AllArgsConstructor
@NoArgsConstructor
public class AddressArea implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer id;

    /**
     * 地址CODE
     */
    @ApiModelProperty(value = "地址CODE")
    private String addressCode;

    /**
     * 类型: 1-装车地址，2-卸货地址(多填用,分隔)
     */
    @ApiModelProperty(value = "类型: 1-装车地址，2-卸货地址(多填用,分隔)")
    private String type;

    /**
     * 简称地址
     */
    @ApiModelProperty(value = "简称地址")
    private String address;

    /**
     * 省
     */
    @ApiModelProperty(value = "省")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty(value = "市")
    private String city;

    /**
     * 区
     */
    @ApiModelProperty(value = "区")
    private String area;

    /**
     * 所在地区
     */
    @ApiModelProperty(value = "所在地区")
    private String region;

    /**
     * 地址库电话
     */
    @ApiModelProperty(value = "地址库电话")
    private String phone;

    /**
     * 地址库邮箱
     */
    @ApiModelProperty(value = "地址库邮箱")
    private String email;

    /**
     * 地址库联系人
     */
    @ApiModelProperty(value = "地址库联系人")
    private String contact;

    /**
     * 是否删除(0未删，1删除)
     */
    @ApiModelProperty(value = "是否删除(0未删，1删除)")
    private Boolean idDel;

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
}