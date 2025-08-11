package com.system.common.enums;

/**
 * @author lutong
 * @data 2024-12-23 023 15:07
 */
public enum IgnoreApiRegularEnum {
    SWAGGER_RESOURCES_1(1,"swagger-resources.*","swagger 接口文档-静态资源1"),
    SWAGGER_RESOURCES_2(2,"v2/.*","swagger 接口文档-静态资源2"),
    SWAGGER_RESOURCES_3(3,"webjars/.*","swagger 接口文档-静态资源3"),
    EMAIL(4,"email/.*","发送邮件接口"),
    STATIC_images(5,"images/.*","图片静态资源"),
    STATIC_email_TEMP(6,"emailTemplates/.*","发送邮件模板静态资源"),
    GENERATE_CODE(7,"generateCode/.*","生成二维码/条形码图片"),
    ;

    private final int id;
    private final String code;
    private final String name;

    IgnoreApiRegularEnum(int id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return this.name;
    }
}
