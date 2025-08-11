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
@TableName("scm_sys_permissions_role")
@ApiModel(value = "SysPermissionsRole", description = "角色权限表")
@AllArgsConstructor
@NoArgsConstructor
public class SysPermissionsRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    @TableField(value = "ROLE_ID")
    private Integer roleId;

    /**
     * 数据权限ID
     */
    @ApiModelProperty(value = "数据权限ID")
    @TableField(value = "DATA_ID")
    private Integer dataId;

    /**
     * 功能权限ID
     */
    @ApiModelProperty(value = "功能权限ID")
    @TableField(value = "FUNCTION_ID")
    private Integer functionId;

    /**
     * 是否勾选：1-勾选,0-未勾选
     */
    @ApiModelProperty(value = "是否勾选：1-勾选,0-未勾选")
    @TableField(value = "IS_CHECKED")
    private Boolean isChecked;

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