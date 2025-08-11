package com.system.common.enums.api.basicConfiguration;

/**
 * @author lutong
 * @data 2024-12-16 016 10:17
 */
public enum ClientTypeMenu {
    PT(1, "普通用户", "PT"),
    GJ(2, "高级用户", "GJ"),
    ;

    ClientTypeMenu(int id, String name, String code) {
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
