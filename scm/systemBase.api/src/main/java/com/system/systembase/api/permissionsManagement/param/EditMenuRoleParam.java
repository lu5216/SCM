package com.system.systembase.api.permissionsManagement.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lutong
 * @data 2024-12-10 010 10:26
 */

@Data
public class EditMenuRoleParam {
    /**
     * 菜单ID列表
     */
    @ApiModelProperty(value = "菜单ID列表")
    List<String> menuIDList;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;
}
