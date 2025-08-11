package com.system.systembase.api.basicConfiguration.enums;

import java.util.Objects;

/**
 * @author lutong
 * @data 2025-2-12 012 16:56
 */
public enum CommonFileTypeEnum {

    IMG_FILE(0, "图片文件", "Img"),
    QC_FILE(1, "二维码图片文件", "QC"),
    BC_FILE(2, "条形码图片文件", "BC"),
    ;

    private Integer id;
    private String name;
    private String code;

    CommonFileTypeEnum(Integer id, String name, String code) {
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

    public static CommonFileTypeEnum getById(Integer id) {
        for (CommonFileTypeEnum e : CommonFileTypeEnum.values()) {
            if (Objects.equals(e.getId(), id)) {
                return e;
            }
        }
        return null;
    }

    public static CommonFileTypeEnum getByName(String name) {
        for (CommonFileTypeEnum e : CommonFileTypeEnum.values()) {
            if (Objects.equals(e.getName(), name)) {
                return e;
            }
        }
        return null;
    }
}
