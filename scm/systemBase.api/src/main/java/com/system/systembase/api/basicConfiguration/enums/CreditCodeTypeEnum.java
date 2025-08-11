package com.system.systembase.api.basicConfiguration.enums;

/**
 * @author lutong
 * @data 2024-12-16 016 14:47
 */
public enum CreditCodeTypeEnum {
    USCI(1, "统一信用代码", "USCI"),
    OC(2, "组织机构代码", "OC"),
    ;

    CreditCodeTypeEnum(int id, String name, String code) {
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
