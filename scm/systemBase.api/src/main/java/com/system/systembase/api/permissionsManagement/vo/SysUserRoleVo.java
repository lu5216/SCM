package com.system.systembase.api.permissionsManagement.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lutong
 * @data 2024-12-6 006 15:10
 */

@Data
@ApiModel(value = "SysUserRoleVo", description = "用户角色表Vo")
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRoleVo {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer id;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Integer userId;

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
     * 账号是否删除:1-删除,0-没删
     */
    @ApiModelProperty(value = "账号是否删除:1-删除,0-没删")
    private Boolean isDel;

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
     * 角色说明
     */
    @ApiModelProperty(value = "角色说明")
    private String roleRemark;
}
