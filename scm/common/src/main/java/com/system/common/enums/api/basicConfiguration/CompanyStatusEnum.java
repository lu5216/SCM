package com.system.common.enums.api.basicConfiguration;

/**
 * @author lutong
 * @data 2024-12-13 013 14:17
 */
public enum CompanyStatusEnum {
    CX(1, "存续", "CX"),
    ZY(2, "在业", "ZY"),
    DX(3, "吊销", "DX"),
    ZX(4, "注销", "ZX"),
    QR(5, "迁入", "QR"),
    QC(6, "迁出", "QC"),
    TY(7, "停业", "TY"),
    QS(8, "清算", "QS"),
    ;

    CompanyStatusEnum(int id, String name, String code) {
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
