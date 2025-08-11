package com.system.systembase.api.permissionsManagement.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2024-12-10 010 9:30
 */

@Data
public class SysMenuParam {

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
    private String remark;
}
