package com.system.systembase.api.permissionsManagement.param;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lutong
 * @data 2024-12-4 004 16:04
 */

@Data
@ApiModel(value = "RegistrationParam", description = "用户表-注册、修改param")
public class RegistrationParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登入账号,不能修改
     */
    @ApiModelProperty(value = "登入账号")
    private String loginId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @JSONField(serialize = false)
    private String password;

    /**
     * 失效时间
     */
    @ApiModelProperty(value = "失效时间")
    private Long expiresTime;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String picture;
}
