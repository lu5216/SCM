package com.system.systembase.api.basicConfiguration.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2025-1-10 010 16:01
 */

@Data
public class SysCVListVo {

    /**
     * 客户/供应商ID
     */
    @ApiModelProperty(value = "客户/供应商ID")
    private Integer id;

    /**
     * 客户/供应商编码
     */
    @ApiModelProperty(value = "客户/供应商编码")
    private String code;

    /**
     * 客户/供应商注册名称
     */
    @ApiModelProperty(value = "客户/供应商注册名称")
    private String name;
}
