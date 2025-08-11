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
@TableName("scm_sys_role")
@ApiModel(value = "SysRole", description = "角色表")
@AllArgsConstructor
@NoArgsConstructor
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色
     */
    @ApiModelProperty(value = "角色")
    @TableField(value = "ROLE")
    private String role;

    /**
     * 角色UID
     */
    @ApiModelProperty(value = "角色UID")
    @TableField(value = "ROLE_UID")
    private String roleUid;

    /**
     * 角色说明
     */
    @ApiModelProperty(value = "角色说明")
    @TableField(value = "ROLE_REMARK")
    private String roleRemark;

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