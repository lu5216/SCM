package com.system.systembase.api.apiTest.vo;

import com.system.systembase.api.basicConfiguration.converter.annotation.DictConvert;
import com.system.systembase.api.basicConfiguration.converter.enums.DictEnum;
import lombok.Data;

/**
 * @author 123
 * @name TextDict
 * @data 2025-08-06
 */

@Data
public class TextDict {

    @DictConvert(parentCode = DictEnum.NATION, dictCode = "nationCode")
    private String nationName;

    private String nationCode = "FG";

    private String goodTypeName = "成品";

    @DictConvert(parentCode = DictEnum.GOODS_TYPE, dictName = "goodTypeName")
    private String goodTypeCode;

    private String test = "test";
}
