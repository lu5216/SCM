package com.system.systembase.api.permissionsManagement.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@TableName("scm_sys_user")
@ApiModel(value = "SysUser", description = "用户表")
@AllArgsConstructor
@NoArgsConstructor
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;


    /**
     * 登入账号
     */
    @ApiModelProperty(value = "登入账号")
    @TableField(value = "LOGIN_ID")
    private String loginId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @TableField(value = "USERNAME")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @TableField(value = "PASSWORD")
    @JSONField(serialize = false)
    private String password;

    /**
     * 数据校验
     */
    @ApiModelProperty(value = "数据校验")
    @TableField(value = "VALIDATED")
    private Boolean validated;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码")
    @TableField(value = "VERIFY_CODE")
    private String verifyCode;

    /**
     * 失效时间
     */
    @ApiModelProperty(value = "失效时间")
    @TableField(value = "EXPIRES_TIME")
    private Long expiresTime;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @TableField(value = "EMAIL")
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    @TableField(value = "MOBILE")
    private String mobile;

    /**
     * 微信服务号OPEN_ID
     */
    @ApiModelProperty(value = "微信服务号OPEN_ID")
    @TableField(value = "OPEN_ID")
    private String openId;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    @TableField(value = "PICTURE")
    private String picture;

    /**
     * 账号是否删除:1-删除,0-没删
     */
    @ApiModelProperty(value = "账号是否删除:1-删除,0-没删")
    @TableField(value = "IS_DEL")
    private Boolean isDel;

    /**
     * 公司Uid
     */
    @ApiModelProperty(value = "公司Uid")
    @TableField(value = "COMPANY_UID")
    private String companyUid;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    @TableField(value = "CREATE_USER_NAME")
    private String createUserName;

    /**
     * 修改者
     */
    @ApiModelProperty(value = "修改者")
    @TableField(value = "UPDATE_USER_NAME")
    private String updateUserName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @TableField(value = "UPDATE_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
}