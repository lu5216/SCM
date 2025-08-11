package com.system.transport.api.fleet.enums;

/**
 * @author lutong
 * @data 2025-2-11 011 17:46
 */

public enum VehicleTypeEnum {
    VTT(1, "厢式吨车", "VTT"),
    SCT(2, "栏板市货车", "SCT"),
    XJ(3, "小轿车", "XJ"),
    MB(4, "面包车", "MB"),
    HG(5, "货柜车", "HG"),
    PB(6, "平板车", "PB"),
    ST(7, "仓栅式货车", "ST"),
    GS(8, "罐式车", "GS"),
    LD(9, "冷冻车", "LD"),
    HW(10, "恒温车", "HW"),
    FY(11, "飞翼车", "FY"),
    ZX(12, "自卸车", "ZX"),
    DJ(13, "短架车", "DJ"),
    ;

    VehicleTypeEnum(int id, String name, String code) {
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
