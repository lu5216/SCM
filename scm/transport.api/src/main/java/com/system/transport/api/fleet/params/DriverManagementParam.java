package com.system.transport.api.fleet.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2025-2-7 007 15:39
 */


@Data
public class DriverManagementParam {

    /**
     * 司机名称
     */
    @ApiModelProperty(value = "司机名称")
    public String name;

    /**
     * 司机电话
     */
    @ApiModelProperty(value = "司机电话")
    public String phone;

    /**
     * 司机邮件
     */
    @ApiModelProperty(value = "司机邮件")
    public String email;

    /**
     * 企业UID
     */
    @ApiModelProperty(value = "企业UID")
    private String companyUid;

    /**
     * 页
     */
    @ApiModelProperty(value = "页")
    private Integer pageIndex;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Integer pageSize;

    /**
     * 导出格式
     */
    @ApiModelProperty("导出格式")
    private String suffix;
}
