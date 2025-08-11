package com.system.systembase.api.basicConfiguration.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2024-12-11 011 14:58
 */

@Data
public class SysBaseDataVo {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 代码
     */
    @ApiModelProperty(value = "编码")
    private String code;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 最小值
     */
    @ApiModelProperty(value = "最小值")
    private String minValue;

    /**
     * 最大值
     */
    @ApiModelProperty(value = "最大值")
    private String maxValue;

    /**
     * 父节点编码
     */
    @ApiModelProperty(value = "父节点编码")
    private String parentCode;

    /**
     * 父节点名称
     */
    @ApiModelProperty(value = "父节点名称")
    private String parentName;

    /**
     * 父节点备注
     */
    @ApiModelProperty(value = "父节点备注")
    private String parentRemark;

    /**
     * 公司Uid
     */
    @ApiModelProperty(value = "公司Uid")
    private String companyUid;

}
