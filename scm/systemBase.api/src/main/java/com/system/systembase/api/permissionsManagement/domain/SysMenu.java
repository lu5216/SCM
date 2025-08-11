package com.system.systembase.api.permissionsManagement.domain;

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
@TableName("scm_sys_menu")
@ApiModel(value = "SysMenu", description = "菜单表")
@AllArgsConstructor
@NoArgsConstructor
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    @TableField(value = "MENU_NAME")
    private String menuName;

    /**
     * 菜单编码
     */
    @ApiModelProperty(value = "菜单编码")
    @TableField(value = "MENU_CODE")
    private String menuCode;

    /**
     * 父菜单编码
     */
    @ApiModelProperty(value = "父菜单编码")
    @TableField(value = "PARENT_MENU_CODE")
    private String parentMenuCode;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址")
    @TableField(value = "PATH")
    private String path;

    /**
     * 组件地址
     */
    @ApiModelProperty(value = "组件地址")
    @TableField(value = "COMPONENT")
    private String component;

    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标")
    @TableField(value = "ICON")
    private String icon;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    @TableField(value = "ORDER_NO")
    private Integer orderNo;

    /**
     * 是否显示菜单：1-显示,0隐藏
     */
    @ApiModelProperty(value = "是否显示菜单：1-显示,0隐藏")
    @TableField(value = "IS_SHOW")
    private Boolean isShow;

    /**
     * 是否使用菜单：1-使用,0停用
     */
    @ApiModelProperty(value = "是否使用菜单：1-使用,0停用")
    @TableField(value = "IS_USE")
    private Boolean isUse;

    /**
     * 菜单备注
     */
    @ApiModelProperty(value = "菜单备注")
    @TableField(value = "REMARK")
    private String remark;

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