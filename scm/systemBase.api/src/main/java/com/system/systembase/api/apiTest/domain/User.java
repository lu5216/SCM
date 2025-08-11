package com.system.systembase.api.apiTest.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@ApiModel(value = "User", description = "用户表")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 登入账号
     */
    @ApiModelProperty(value = "登入账号")
    @TableField(value = "LOGIN_ID")
    @ExcelProperty("登入账号")
    private String loginId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @TableField(value = "USERNAME")
    @ExcelProperty("用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @TableField(value = "PASSWORD")
    @ExcelProperty("密码")
    private String password;

    /**
     * 数据校验
     */
    @ApiModelProperty(value = "数据校验")
    @TableField(value = "VALIDATED")
    @ExcelProperty("数据校验")
    private Boolean validated;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码")
    @TableField(value = "VERIFY_CODE")
    @ExcelProperty("验证码")
    private String verifyCode;

    /**
     * 失效时间
     */
    @ApiModelProperty(value = "失效时间")
    @TableField(value = "EXPIRES_TIME")
    @ExcelProperty("失效时间")
    private Long expiresTime;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @TableField(value = "EMAIL")
    @ExcelProperty("邮箱")
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    @TableField(value = "MOBILE")
    @ExcelProperty("手机号")
    private String mobile;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    @TableField(value = "PICTURE")
    @ExcelProperty("图片")
    private String picture;

    /**
     * 账号是否删除:0-删除,1-没删
     */
    @ApiModelProperty(value = "账号是否删除:0-删除,1-没删")
    @TableField(value = "IS_DEL")
    @ExcelProperty("账号是否删除:0-删除,1-没删")
    private Boolean isDel;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    @TableField(value = "CREATE_USER_NAME")
    @ExcelProperty("创建者")
    private String createUserName;

    /**
     * 修改者
     */
    @ApiModelProperty(value = "修改者")
    @TableField(value = "UPDATE_USER_NAME")
    @ExcelProperty("修改者")
    private String updateUserName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME")
    @ExcelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @TableField(value = "UPDATE_TIME")
    @ExcelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    public User(Integer id, String loginId, String username) {
        this.id = id;
        this.loginId = loginId;
        this.username = username;
    }
}