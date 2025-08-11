package com.system.systembase.api.sendEmail.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2024-12-17 017 10:17
 */

@Data
public class EmailVo<T> {

    /**
     * 发送人名称
     */
    @ApiModelProperty(value = "发送人名称")
    public String name;

    /**
     * 发送人邮箱
     */
    @ApiModelProperty(value = "发送人邮箱")
    public String form;

    /**
     * 发送对象邮箱
     */
    @ApiModelProperty(value = "发送对象邮箱")
    public String to;

    /**
     * 主题
     */
    @ApiModelProperty(value = "主题")
    public String subject;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    public T content;
}
