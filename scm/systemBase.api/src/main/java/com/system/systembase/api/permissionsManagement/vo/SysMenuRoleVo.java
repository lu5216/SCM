package com.system.systembase.api.permissionsManagement.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2024-12-10 010 10:20
 */

@Data
public class SysMenuRoleVo {
    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID")
    private Integer menuId;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    /**
     * 菜单编码
     */
    @ApiModelProperty(value = "菜单编码")
    private String menuCode;

    /**
     * 父菜单编码
     */
    @ApiModelProperty(value = "父菜单编码")
    private String parentMenuCode;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址")
    private String path;

    /**
     * 组件地址
     */
    @ApiModelProperty(value = "组件地址")
    private String component;

    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标")
    private String icon;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer orderNo;

    /**
     * 是否显示菜单：1-显示,0隐藏
     */
    @ApiModelProperty(value = "是否显示菜单：1-显示,0隐藏")
    private Boolean isShow;

    /**
     * 是否使用菜单：1-使用,0停用
     */
    @ApiModelProperty(value = "是否使用菜单：1-使用,0停用")
    private Boolean isUse;

    /**
     * 菜单备注
     */
    @ApiModelProperty(value = "菜单备注")
    private String menuRemark;

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
     * 角色说明
     */
    @ApiModelProperty(value = "角色说明")
    private String roleRemark;
}
