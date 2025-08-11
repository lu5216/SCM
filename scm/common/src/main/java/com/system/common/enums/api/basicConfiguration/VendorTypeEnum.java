package com.system.common.enums.api.basicConfiguration;

/**
 * @author lutong
 * @data 2024-12-16 016 10:17
 */
public enum VendorTypeEnum {
    YL(1, "原料供应商", "YL"),
    WL(2, "物流供应商", "WL"),
    ;

    VendorTypeEnum(int id, String name, String code) {
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
