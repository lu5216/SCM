package com.system.systembase.api.basicConfiguration.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2024-12-24 024 14:28
 */

@Data
public class SysClientParam {

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String registerName;

    /**
     * 客户类型
     */
    @ApiModelProperty(value = "客户类型")
    private String clientType;

    /**
     * 创建时间-开始
     */
    @ApiModelProperty(value = "创建时间-开始")
    private String StartDate;

    /**
     * 创建时间-结束
     */
    @ApiModelProperty(value = "创建时间-结束")
    private String endDate;

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
}
