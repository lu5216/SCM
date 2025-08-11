package com.system.systembase.api.permissionsManagement.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lutong
 * @data 2024-11-28 028 15:28
 */

@Data
@ApiModel(value = "SysUserParam", description = "用户登陆、删除Param")
public class SysUserParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登入账号
     */
    @ApiModelProperty(value = "登入账号")
    private String loginId;

    /**
     * 用户
     */
    @ApiModelProperty(value = "用户")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码")
    private String verifyCode;
}
