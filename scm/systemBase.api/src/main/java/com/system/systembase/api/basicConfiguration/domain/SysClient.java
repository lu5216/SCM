package com.system.systembase.api.basicConfiguration.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@TableName("scm_sys_client")
@ApiModel(value = "SysClient", description = "客户管理表")
@AllArgsConstructor
@NoArgsConstructor
public class SysClient implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer id;

    /**
     * 客户编码
     */
    @ApiModelProperty(value = "客户编码")
    private String clientCode;

    /**
     * 绑定公司Uid
     */
    @ApiModelProperty(value = "绑定公司Uid")
    private String bindCompany;

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
     * 是否合作：1-合作,0不合作
     */
    @ApiModelProperty(value = "是否合作：1-合作,0不合作")
    private Boolean isCooperation;

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