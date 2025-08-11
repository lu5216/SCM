package com.system.common.enums;

/**
 *  统一输出格式
 * @author lutong
 * @data 2024-11-27 027 15:48
 */
public enum ResultEnum {
    SUCCESS(0, "成功"),
    FAILED(-1, "失败");

    private final int code;
    private final String name;

    private ResultEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
