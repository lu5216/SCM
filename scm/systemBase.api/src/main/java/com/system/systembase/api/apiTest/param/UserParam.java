package com.system.systembase.api.apiTest.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lutong
 * @data 2024-11-27 027 15:04
 */

@Data
public class UserParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Integer id;

    /**
     * 导出格式
     */
    @ApiModelProperty("导出格式")
    private String suffix;
}
