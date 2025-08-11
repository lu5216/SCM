package com.system.systembase.api.wx.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 *  公众号推送数据
 * @author lutong
 * @data 2024-12-3 003 11:58
 */

@Data
public class TemplateParam {
    /**
     * 用户openid
     */
    @ApiModelProperty(value = "用户openid")
    private String openId;

    /**
     * 生产日期
     */
    @ApiModelProperty(value = "生产日期")
    private Date productionDate;

    /**
     * 工位名称
     */
    @ApiModelProperty(value = "工位名称")
    private String stationName;

    /**
     * 工位编码
     */
    @ApiModelProperty(value = "工位编码")
    private String stationNum;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String goodsName;

    /**
     * 商品识别码
     */
    @ApiModelProperty("商品识别码")
    private String goodsCode;

}
