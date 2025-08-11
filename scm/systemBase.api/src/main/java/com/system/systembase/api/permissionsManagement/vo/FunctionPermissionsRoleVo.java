package com.system.systembase.api.permissionsManagement.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2024-12-11 011 10:39
 */

@Data
public class FunctionPermissionsRoleVo {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer id;

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
     * 功能权限ID
     */
    @ApiModelProperty(value = "功能权限ID")
    private Integer functionId;

    /**
     * 菜单编码
     */
    @ApiModelProperty(value = "菜单编码")
    private String menuCode;

    /**
     * 权限名
     */
    @ApiModelProperty(value = "权限名")
    private String name;

    /**
     * 权限编码
     */
    @ApiModelProperty(value = "权限编码")
    private String code;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 是否勾选：1-勾选,0-未勾选
     */
    @ApiModelProperty(value = "是否勾选：1-勾选,0-未勾选")
    private Boolean isChecked;
}
