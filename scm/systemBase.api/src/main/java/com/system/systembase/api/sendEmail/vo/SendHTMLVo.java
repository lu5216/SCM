package com.system.systembase.api.sendEmail.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2024-12-17 017 10:13
 */
@Data
public class SendHTMLVo {

    /**
     * 收件人名称
     */
    @ApiModelProperty(value = "收件人名称")
    public String recipientName;

    /**
     * 跳转链接
     */
    @ApiModelProperty(value = "跳转链接")
    public String url;

    /**
     * 头像
     */
    @ApiModelProperty(value = "SCM系统-头像")
    public String avatar = "https://q1.qlogo.cn/headimg_dl?dst_uin=1032065316&spec=100";

    /**
     * 头像
     */
    @ApiModelProperty(value = "二维码图片")
    public String qrCode = "https://img.51miz.com/Element/00/86/19/08/225420b4_E861908_5a3143ca.png";
}
