package com.system.systembase.api.permissionsManagement.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lutong
 * @data 2024-12-6 006 11:40
 */

@Data
public class SysRoleParam {

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
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createUserName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

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
