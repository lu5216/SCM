package com.system.systembase.api.permissionsManagement.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2024-12-6 006 15:45
 */

@Data
@ApiModel(value = "SysUserRoleParam", description = "用户角色数据分页")
public class SysUserRoleParam {

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

    /**
     * 页
     */
    @ApiModelProperty(value = "页")
    private Integer pageIndex;

    /**
     * 数量
     */
    @ApiModelProperty(value = "每页数量")
    private Integer pageSize;
}
