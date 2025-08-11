package com.system.common.enums;

/**
 * @author lutong
 * @data 2024-12-23 023 15:01
 */
public enum IgnoreApiEnum {
    LOGIN(1,"auth/sysLogin","登入接口"),
    SWAGGER_UI(2,"swagger-ui.html","swagger 接口文档"),
    BOOTSTRAP_UI(3,"doc.html","bootstrap 接口文档"),
    VERIFY(4,"verify/getVerificationCodePhoto","验证码图片接口"),
    WX_PUSH(5,"wx/push","微信公众消息推送"),
    QR_CODE(6,"auth/registration","注册账户"),
    ;

    private final int id;
    private final String code;
    private final String name;

    IgnoreApiEnum(int id, String code, String name) {
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
