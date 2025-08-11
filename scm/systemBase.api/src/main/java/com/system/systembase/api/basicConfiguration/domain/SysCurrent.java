package com.system.systembase.api.basicConfiguration.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@TableName("scm_sys_current")
@ApiModel(value = "SysCurrent", description = "币制设置")
@AllArgsConstructor
@NoArgsConstructor
public class SysCurrent implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /**
     * 预设汇率
     */
    @ApiModelProperty(value = "预设汇率")
    private BigDecimal preExchangeRate;

    /**
     * 买入汇率
     */
    @ApiModelProperty(value = "买入汇率")
    private BigDecimal buyExchangeRate;

    /**
     * 卖出汇率
     */
    @ApiModelProperty(value = "卖出汇率")
    private BigDecimal sellExchangeRate;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 企业UID
     */
    @ApiModelProperty(value = "企业UID")
    private String companyUid;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createUserName;

    /**
     * 修改者
     */
    @ApiModelProperty(value = "修改者")
    private String updateUserName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

}