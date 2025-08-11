package com.system.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@ApiModel(value = "SysUserVo", description = "SysUserVo用户表")
@AllArgsConstructor
@NoArgsConstructor
public class SysUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登入账号
     */
    @ApiModelProperty(value = "登入账号")
    private String loginId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 数据校验
     */
    @ApiModelProperty(value = "数据校验")
    private Boolean validated;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码")
    private String verifyCode;

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
     * 微信服务号OPEN_ID
     */
    @ApiModelProperty(value = "微信服务号OPEN_ID")
    private String openId;

    /**
     * 公司Uid
     */
    @ApiModelProperty(value = "公司Uid")
    private String companyUid;

    /**
     * 账号是否删除:0-删除,1-没删
     */
    @ApiModelProperty(value = "账号是否删除:0-删除,1-没删")
    private Boolean isDel;
}
