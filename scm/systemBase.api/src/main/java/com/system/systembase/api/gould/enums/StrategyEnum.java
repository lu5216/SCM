package com.system.systembase.api.gould.enums;

import java.util.Objects;

/**
 *  驾车算路策略
 * @author lutong
 * @data 2025-3-5 005 14:56
 */
public enum StrategyEnum {

    STRATEGY_00(0, "速度优先（只返回一条路线），此路线不一定距离最短"),
    STRATEGY_01(1, "费用优先（只返回一条路线），不走收费路段，且耗时最少的路线"),
    STRATEGY_02(2, "常规最快（只返回一条路线）综合距离/耗时规划结果"),
    STRATEGY_32(32, "默认，高德推荐，同高德地图APP默认"),
    STRATEGY_33(33, "躲避拥堵"),
    STRATEGY_34(34, "高速优先"),
    STRATEGY_35(35, "不走高速"),
    STRATEGY_36(36, "少收费"),
    STRATEGY_37(37, "大路优先"),
    STRATEGY_38(38, "速度最快"),
    STRATEGY_39(39, "躲避拥堵＋高速优先"),
    STRATEGY_40(40, "躲避拥堵＋不走高速"),
    STRATEGY_41(41, "躲避拥堵＋少收费"),
    STRATEGY_42(42, "少收费＋不走高速"),
    STRATEGY_43(43, "躲避拥堵＋少收费＋不走高速"),
    STRATEGY_44(44, "躲避拥堵＋大路优先"),
    STRATEGY_45(45, "躲避拥堵＋速度最快"),
    ;

    private Integer id;
    private String name;

    StrategyEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public static StrategyEnum getById(Integer id) {
        for (StrategyEnum e : StrategyEnum.values()) {
            if (Objects.equals(e.getId(), id)) {
                return e;
            }
        }
        return null;
    }

    public static StrategyEnum getByName(String name) {
        for (StrategyEnum e : StrategyEnum.values()) {
            if (Objects.equals(e.getName(), name)) {
                return e;
            }
        }
        return null;
    }
}
