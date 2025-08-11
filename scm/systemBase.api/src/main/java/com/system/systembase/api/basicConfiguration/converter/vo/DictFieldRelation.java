package com.system.systembase.api.basicConfiguration.converter.vo;

import com.system.systembase.api.basicConfiguration.converter.annotation.DictConvert;
import lombok.Data;

/**
 * @author 123
 * @name DictFieldRelation
 * @data 2025-08-06
 */


@Data
public class DictFieldRelation {
    /**
     * 待转换字段
     */
    private final String sourceField;

    /**
     * 目标字段
     */
    private final String targetField;

    /**
     * 父字典编码
     */
    private final String parentCode;

    public DictFieldRelation(DictConvert annotation, String targetField) {
        this.sourceField = !annotation.dictCode().isEmpty() ? annotation.dictCode() : annotation.dictName();
        this.targetField = targetField;
        this.parentCode = annotation.parentCode().getCode();
    }
}
