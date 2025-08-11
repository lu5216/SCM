package com.system.systembase.api.basicConfiguration.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@TableName("scm_sys_base_data")
@ApiModel(value = "SysBaseData", description = "数据字典表")
@AllArgsConstructor
@NoArgsConstructor
public class SysBaseData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer id;

    /**
     * 父节点编码
     */
    @ApiModelProperty(value = "父节点编码")
    private String parentCode;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 代码
     */
    @ApiModelProperty(value = "代码")
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
     * 公司Uid
     */
    @ApiModelProperty(value = "公司Uid")
    private String companyUid;
}