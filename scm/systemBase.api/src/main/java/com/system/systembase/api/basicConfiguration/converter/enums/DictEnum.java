package com.system.systembase.api.basicConfiguration.converter.enums;

import java.util.Objects;

public enum DictEnum {
    DICT(0, "数据字典", "DICT"),
    NATION(1, "国家", "NATION"),
    GOODS_TYPE(2, "商品类型", "GOODS_TYPE"),
    UNIT(3, "商品单位", "UNIT"),
    PACKING(4, "包装方式", "PACKING"),
    WEIGHT_UNIT(5, "重量单位", "WEIGHT_UNIT"),
    PURCHASE(6, "采购方式", "PURCHASE");

    private final Integer id;
    private final String name;
    private final String code;

    DictEnum(Integer id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public static DictEnum getById(Integer id) {
        for (DictEnum e : values()) {
            if (Objects.equals(e.getId(), id)) {
                return e;
            }
        }
        return null;
    }

    public static DictEnum getByName(String name) {
        for (DictEnum e : values()) {
            if (Objects.equals(e.getName(), name)) {
                return e;
            }
        }
        return null;
    }

    public static DictEnum getByCode(String code) {
        for (DictEnum e : values()) {
            if (Objects.equals(e.getCode(), code)) {
                return e;
            }
        }
        return null;
    }
}