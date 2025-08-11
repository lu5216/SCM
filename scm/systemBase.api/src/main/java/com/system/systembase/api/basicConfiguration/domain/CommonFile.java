package com.system.systembase.api.basicConfiguration.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@TableName("scm_common_file")
@ApiModel(value = "CommonFile", description = "通用文件表")
@AllArgsConstructor
@NoArgsConstructor
public class CommonFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer id;

    /**
     * 文件URL
     */
    @ApiModelProperty(value = "文件URL")
    private String fileUrl;

    /**
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    /**
     * 文件大小,单位字节
     */
    @ApiModelProperty(value = "文件大小,单位字节")
    private Long fileSize;

    /**
     * 文件业务状态,1正常,0删除
     */
    @ApiModelProperty(value = "文件业务状态,1正常,0删除")
    private Boolean status;

    /**
     * 文件业务类型,k-v
     */
    @ApiModelProperty(value = "文件业务类型,k")
    private Integer type;

    /**
     * 文件业务类型名称
     */
    @ApiModelProperty(value = "文件业务类型名称,v")
    private String typeName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

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
     * 企业UID
     */
    @ApiModelProperty(value = "企业UID")
    private String companyUid;

    /**
     * 文件服务器存储路径url
     */
    @ApiModelProperty(value = "文件服务器存储路径url")
    private String localUrl;
}