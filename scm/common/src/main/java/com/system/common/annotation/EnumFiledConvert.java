package com.system.common.annotation;

import com.system.common.enums.SymbolConstant;

import java.lang.annotation.*;

/**
 * @author lutong
 * @data 2025-1-14 014 15:39
 */


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)  // 保留
public @interface EnumFiledConvert {
    /**
     * 枚举映射map  key-value,key-value,key-value,key-value
     * <p>
     * key|value,key|value
     * <p>
     * 注意，这里的key和value都是英文字符串，所以实际上他可以是数字，也可以是英文，也可以是多个个字符串
     * 例如：0|满勤,0-1|特殊,Y|是,n|error
     *
     * @return
     */
    String enumMap() default "未匹配到映射字典";

    /**
     * 枚举类导入、导出在excel中的分隔符号
     *
     * @return
     */
    String spiteChar() default SymbolConstant.SPE1;

    /**
     * 枚举类导入导出字典分隔符
     */
    String dictChar() default SymbolConstant.SPE9;

    /**
     * 单选 or 多选
     * <p>
     * 即多个枚举映射，都会比对输出，例如
     *
     * @return
     * @EnumFiledConvert(enumMap = "1-篮球,2-足球,3-乒乓球,4-羽毛球",single = false)
     * <p>
     * 字段值是"1,2,3,4"
     * 则最终输出的是"篮球,足球,乒乓球,羽毛球"
     * <p>
     * 字典值是"1,2"
     * 则最终输出的是"篮球,足球"
     */
    boolean single() default true;

    /**
     * 条件样式，对满足条件的字符串，设置红色字体
     */
    String fontColorMap() default "";
}
