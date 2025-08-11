package com.system.systembase.api.basicConfiguration.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2025-1-8 008 17:26
 */

@Data
public class SysCurrentVo {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String currentName;

    /**
     * 编码
     */
    @ApiModelProperty(value = "编码")
    private String currentCode;
}
