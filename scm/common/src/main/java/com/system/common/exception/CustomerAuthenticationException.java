package com.system.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 *  自定义验证异常类
 * @author lutong
 * @data 2024-12-2 002 14:10
 */
public class CustomerAuthenticationException extends AuthenticationException {

    public CustomerAuthenticationException(String msg) {
        super(msg);
    }
}
