package com.system.systembase.api.apiTest.enums;


/**
 * @author lutong
 * @data 2024-11-27 027 15:05
 */

public enum UserEnums {
    WOMAN(0, "女人"),
    MAN(1, "男人");

    UserEnums (int id, String name) {
        this.id = id;
        this.name = name;
    }

    private final int id;
    private final String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
