package com.system.transport.api.address.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lutong
 * @data 2025-1-22 022 13:50
 */

@Data
public class AddressAreaVo {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer id;

    /**
     * 序列
     */
    @ApiModelProperty(value = "序列")
    private Integer index;

    /**
     * 类型: 1-装车地址，2-卸货地址(多填用,分隔)
     */
    @ApiModelProperty(value = "类型: 1-装车地址，2-卸货地址(多填用,分隔)")
    private String type;

    /**
     * 类型: 1-装车地址，2-卸货地址(多填用,分隔)
     */
    @ApiModelProperty(value = "类型Str: 1-装车地址，2-卸货地址(多填用,分隔)")
    private String typeStr;

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
     * 详细地址：省+市+区
     */
    @ApiModelProperty(value = "详细地址")
    private String DetailedAddress;

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
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;
}
