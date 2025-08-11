package com.system.entrance.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.system.common.exception.CustomerAuthenticationException;
import com.system.common.vo.Result;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 *  用户认证校验失败处理器
 * @author lutong
 * @data 2024-12-2 002 16:16
 */

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    // 用户认证校验失败
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        String msg = null;
        if (exception instanceof AccountExpiredException) {
            msg = "账户过期，登入失败";
        } else if (exception instanceof BadCredentialsException) {
            msg = "用户名或密码错误，登入失败";
        } else if (exception instanceof CredentialsExpiredException) {
            msg = "密码过期，登入失败";
        } else if (exception instanceof DisabledException) {
            msg = "账户被禁用，登入失败";
        } else if (exception instanceof LockedException) {
            msg = "账户被锁，登入失败";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            msg = "账户不存在，登入失败";
        } else if (exception instanceof CustomerAuthenticationException) {
            msg = exception.getMessage();
        } else {
            msg = "登入失败！";
        }
        String result = JSON.toJSONString(Result.failed(msg));
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
