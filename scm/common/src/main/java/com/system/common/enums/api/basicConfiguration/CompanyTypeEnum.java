package com.system.common.enums.api.basicConfiguration;

/**
 * @author lutong
 * @data 2024-12-13 013 11:39
 */

public enum CompanyTypeEnum {
    GYQY(1, "国有企业", "GYQY"),
    JTSYZ(2, "集体所有制", "JTSYZ"),
    SYQY(3, "私营企业", "SYQY"),
    YXZRGS(4, "有限责任公司", "YXZRGS"),
    GFYXGS(5, "股份有限公司", "GFYXGS"),
    YXHHQY(6, "有限合伙企业", "YXHHQY"),
    LYQY(7, "联营企业", "LYQY"),
    WSTZQY(8, "外商投资企业", "WSTZQY"),
    GRDZQY(9, "个人独资企业", "GRDZQY"),
    ;

    CompanyTypeEnum(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    private final int id;
    private final String name;
    private final String code;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
