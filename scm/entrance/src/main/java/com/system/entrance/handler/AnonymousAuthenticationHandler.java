package com.system.entrance.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.system.common.vo.Result;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 *  匿名用户访问受限资源处理器
 * @author lutong
 * @data 2024-12-2 002 14:40
 */
@Component
public class AnonymousAuthenticationHandler implements AuthenticationEntryPoint {

    // 访问受限资源处理
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        String result = "";
        // 用户名或密码错误
        if (authException instanceof BadCredentialsException) {
            result = JSON.toJSONString(Result.failed(authException.getMessage()), SerializerFeature.DisableCircularReferenceDetect);
        }
        // 用户名为空
        else if (authException instanceof InternalAuthenticationServiceException){
            result = JSON.toJSONString(Result.failed("用户名为空！"), SerializerFeature.DisableCircularReferenceDetect);
        }
        // 无权限访问
        else {
            result = JSON.toJSONString(Result.failed("匿名用户无权限访问！"), SerializerFeature.DisableCircularReferenceDetect);
        }

        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();

    }
}
