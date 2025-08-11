package com.system.systembase.api.permissionsManagement.enums;

/**
 *  数据权限模块枚举
 * @author lutong
 * @data 2024-12-10 010 14:28
 */
public enum DataPermissionsEnum {

    PM_SUM("PM_SUM", "用户管理"),
    PM_SRM("PM_SRM", "角色管理"),
    PM_SMM("PM_SMM", "菜单管理");

    private final String menuCode;
    private final String name;

    public String getMenuCode() {
        return menuCode;
    }

    public String getName() {
        return name;
    }

    DataPermissionsEnum(String menuCode, String name) {
        this.menuCode = menuCode;
        this.name = name;
    }
}
