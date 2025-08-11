package com.system.systembase.api.permissionsManagement.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2024-12-11 011 10:35
 */

@Data
public class DataPermissionsRoleVo {

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
     * 数据权限ID
     */
    @ApiModelProperty(value = "数据权限ID")
    private Integer dataId;

    /**
     * 菜单编码
     */
    @ApiModelProperty(value = "菜单编码")
    private String menuCode;

    /**
     * 字段，多个用逗号隔开
     */
    @ApiModelProperty(value = "字段，多个用逗号隔开")
    private String fieldCode;

    /**
     * 字段名称，多个用逗号隔开
     */
    @ApiModelProperty(value = "字段名称，多个用逗号隔开")
    private String fieldName;

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
