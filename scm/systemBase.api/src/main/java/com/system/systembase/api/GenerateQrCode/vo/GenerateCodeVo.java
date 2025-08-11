package com.system.systembase.api.GenerateQrCode.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lutong
 * @data 2025-1-14 014 17:15
 */

@Data
public class GenerateCodeVo {

    @ApiModelProperty(value = "图片名称")
    public String pictureName;

    @ApiModelProperty(value = "图片路径")
    public String picturePath;
}
