package com.system.systembase.api.basicConfiguration.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lutong
 * @data 2025-2-12 012 17:05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="通用文件Vo对象", description="通用文件表")
public class CommonFileVo implements Serializable {

    private static final long serialVersionUID=1L;


    @ApiModelProperty(value = "文件URL")
    private String fileUrl;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "文件大小,单位字节")
    private Long fileSize;

    @ApiModelProperty(value = "文件业务类型,k")
    private Integer type;

    @ApiModelProperty(value = "文件业务类型名称,v")
    private String typeName;

    @ApiModelProperty(value = "文件服务器存储路径url")
    private String localUrl;

}