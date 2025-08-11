package com.system.systembase.api.basicConfiguration.converter.annotation;

import com.system.systembase.api.basicConfiguration.converter.enums.DictEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ZuiYing
 * @name BaseDataConvert
 * @data 2025-08-06
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DictConvert {

    /**
     * 父字典编码，不能为空
     */
    DictEnum parentCode();

    /**
     * 根据Name查询Code
     * dictName() 与 dictCode() 二选一
     */
    String dictName() default "";

    /**
     * 根据Code查询Name
     * dictName() 与 dictCode() 二选一
     */
    String dictCode() default "";
}
