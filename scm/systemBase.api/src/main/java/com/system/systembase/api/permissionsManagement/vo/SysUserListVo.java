package com.system.systembase.api.permissionsManagement.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2024-12-11 011 9:31
 */

@Data
public class SysUserListVo {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer id;


    /**
     * 登入账号
     */
    @ApiModelProperty(value = "登入账号")
    private String loginId;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    /**
     * 角色
     */
    @ApiModelProperty(value = "角色")
    private String role;

    /**
     * 角色UID
     */
    @ApiModelProperty(value = "角色UID")
    private String roleUid;

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
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String picture;

    /**
     * 账号是否删除:1-删除,0-没删
     */
    @ApiModelProperty(value = "账号是否删除:1-删除,0-没删")
    private Boolean isDel;
}
