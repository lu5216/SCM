package com.system.order.api.salesOrder.enums;

import java.util.Objects;

/**
 * @author lutong
 * @data 2025-3-6 006 11:07
 */
public enum ItemStatusEnum {
    STATUS_01(1, "待接单", "STATUS_01"),
    STATUS_02(2, "进行中", "STATUS_02"),
    STATUS_03(3, "待审核", "STATUS_03"),
    STATUS_04(4, "已完成", "STATUS_04"),
    STATUS_05(5, "已取消", "STATUS_05"),
    ;

    private Integer id;
    private String name;
    private String code;

    ItemStatusEnum(Integer id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static ItemStatusEnum getById(Integer id) {
        for (ItemStatusEnum e : ItemStatusEnum.values()) {
            if (Objects.equals(e.getId(), id)) {
                return e;
            }
        }
        return null;
    }

    public static ItemStatusEnum getByName(String name) {
        for (ItemStatusEnum e : ItemStatusEnum.values()) {
            if (Objects.equals(e.getName(), name)) {
                return e;
            }
        }
        return null;
    }
}
