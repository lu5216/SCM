package com.system.common.enums.api.basicConfiguration;

/**
 * @author lutong
 * @data 2024-12-13 013 11:52
 */
public enum CompanyBusinessEnum {
    NLMY(1, "农、林、牧、渔业", "NLMY"),
    CK(2, "采矿业", "CK"),
    ZZ(3, "制造业", "ZZ"),
    DRRS(4, "电力、热力、燃气及水的生产和供应业", "DRRS"),
    HG(5, "环境和公共设施管理业", "HG"),
    JZ(6, "建筑业", "JZ"),
    JCY(7, "交通运输、仓储和邮政业", "JCY"),
    IT(8, "信息传输、计算机服务和软件业", "IT"),
    PL(9, "批发和零售业", "PL"),
    ZC(10, "住宿、餐饮业", "ZC"),
    JB(11, "金融、保险业", "JB"),
    FDC(12, "房地产业", "FDC"),
    ZS(13, "租贷和商务服务业", "ZS"),
    KJD(14, "科学研究、技术服务和地质勘查业", "KJD"),
    SHG(15, "水利、环境和公共设施管理业", "SHG"),
    JQ(16, "居民服务和其他服务业", "JQ"),
    JY(17, "教育", "JY"),
    WSS(18, "卫生、社会保障和社会服务业", "WSS"),
    WTY(19, "文化、体育、娱乐业", "WTY"),
    ZH(20, "综合(含投资类、主业不明显)", "ZH"),
    QT(21, "其他", "QT"),
    ;

    CompanyBusinessEnum(int id, String name, String code) {
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
