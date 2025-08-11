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
@TableName("scm_sys_data_permissions")
@ApiModel(value = "SysDataPermissions", description = "数据权限表")
@AllArgsConstructor
@NoArgsConstructor
public class SysDataPermissions implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单编码
     */
    @ApiModelProperty(value = "菜单编码")
    @TableField(value = "MENU_CODE")
    private String menuCode;

    /**
     * 字段，多个用逗号隔开
     */
    @ApiModelProperty(value = "字段，多个用逗号隔开")
    @TableField(value = "FIELD_CODE")
    private String fieldCode;

    /**
     * 字段名称，多个用逗号隔开
     */
    @ApiModelProperty(value = "字段名称，多个用逗号隔开")
    @TableField(value = "FIELD_NAME")
    private String fieldName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(value = "UPDATE_TIME")
    private Date updateTime;
}