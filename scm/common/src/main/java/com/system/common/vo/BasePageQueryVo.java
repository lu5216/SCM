package com.system.common.vo;

import com.system.common.exception.CustomerAuthenticationException;
import com.system.common.utils.SecurityUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lutong
 * @data 2025-2-26 026 10:39
 */

@Data
@ApiModel(value = "BasePageQueryVo", description = "基础分页查询参数")
public class BasePageQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页
     */
    @ApiModelProperty("当前页")
    private Integer pageIndex;

    /**
     * 页面大小
     */
    @ApiModelProperty("页面大小")
    private Integer pageSize;

    /**
     * 创建时间起始
     */
    @ApiModelProperty("创建时间起始，格式：yyyy-MM-dd")
    private String createTimeStart;

    /**
     * 创建时间结束
     */
    @ApiModelProperty("创建时间结束，格式：yyyy-MM-dd")
    private String createTimeEnd;

    /**
     * 企业uid
     */
    @ApiModelProperty(value = "企业uid", hidden = true)
    private String companyUid;

    public String getCompanyUid() {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            throw new CustomerAuthenticationException("获取用户信息异常!");
        }
        return null != companyUid ? companyUid : user.getCompanyUid();
    }

    public Integer getPageIndex() {
        return null != pageIndex ? pageIndex : 1;
    }

    public Integer getPageSize() {
        return null != pageSize ? pageSize : 20;
    }
}
