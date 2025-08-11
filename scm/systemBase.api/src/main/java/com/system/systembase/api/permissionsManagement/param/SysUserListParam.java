package com.system.systembase.api.permissionsManagement.param;

import com.system.common.vo.BasePageQueryVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2024-12-10 010 9:41
 */

@Data
public class SysUserListParam extends BasePageQueryVo {
    /**
     * 登入账号
     */
    @ApiModelProperty(value = "登入账号")
    private String loginId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /**
     * 账号是否删除:1-删除,0-没删
     */
    @ApiModelProperty(value = "账号是否删除:1-删除,0-没删")
    private Boolean isDel;
}
