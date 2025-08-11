package com.system.common.utils;

import com.system.common.vo.SysUserVo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lutong
 * @data 2024-12-4 004 16:47
 */
public class SecurityUtils {

    private static final ThreadLocal<SysUserVo> userLocal = new ThreadLocal<>();

    private static final ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<>();

    public static void add(SysUserVo user) {
        userLocal.set(user);
    }

    public static void add(HttpServletRequest request){
        requestLocal.set(request);
    }

    public static SysUserVo getUser () {
        return userLocal.get();
    }

    public static HttpServletRequest getUserRequest(){
        return requestLocal.get();
    }

    public static void remove(){
        userLocal.remove();
        requestLocal.remove();
    }
}
