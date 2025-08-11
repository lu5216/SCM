package com.system.entrance.filter;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.system.common.enums.IgnoreApiEnum;
import com.system.common.enums.IgnoreApiRegularEnum;
import com.system.common.exception.CustomerAuthenticationException;
import com.system.common.utils.JwtUtil;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.CompanyEnumVo;
import com.system.common.vo.SysUserVo;
import com.system.entrance.handler.LoginFailureHandler;
import com.system.systembase.api.basicConfiguration.converter.DictCacheManager;
import com.system.systembase.api.permissionsManagement.domain.SysUser;
import com.system.systembase.api.permissionsManagement.service.ISysUserService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author lutong
 * @data 2024-11-29 029 14:38
 */

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private ISysUserService sysUserService;
    @Autowired
    public void ISysUserService (@Lazy ISysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Autowired
    private DictCacheManager cacheManager;

    /**
     *  获取忽略Api列表
     * @return
     */
    public List<CompanyEnumVo> getIgnoreApiEnumList() {
        List<CompanyEnumVo> list = new ArrayList<>();
        for (IgnoreApiEnum apiEnum : IgnoreApiEnum.values()) {
            CompanyEnumVo vo = new CompanyEnumVo();
            vo.setId(apiEnum.getId());
            vo.setName(apiEnum.getName());
            vo.setCode("/scm-demo/" + apiEnum.getCode());
            list.add(vo);
        }
        return list;
    }

    /**
     *  获取忽略Api列表
     * @return
     */
    public List<CompanyEnumVo> getIgnoreApiRegularEnumList() {
        List<CompanyEnumVo> list = new ArrayList<>();
        for (IgnoreApiRegularEnum apiEnum : IgnoreApiRegularEnum.values()) {
            CompanyEnumVo vo = new CompanyEnumVo();
            vo.setId(apiEnum.getId());
            vo.setName(apiEnum.getName());
            vo.setCode("/scm-demo/" + apiEnum.getCode());
            list.add(vo);
        }
        return list;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // 接口放行
            String uri = request.getRequestURI();
            List<CompanyEnumVo> ignoreApiEnumList = this.getIgnoreApiEnumList();
            List<CompanyEnumVo> ignoreApiRegularEnumList = this.getIgnoreApiRegularEnumList();
            boolean isRelease = false;
            if (ignoreApiEnumList.size() > 0) {
                for (CompanyEnumVo vo : ignoreApiEnumList) {
                    if (uri.equals(vo.getCode())) {
                        isRelease = true;
                        break;
                    }
                }
            }
            if (ignoreApiRegularEnumList.size() > 0) {
                for (CompanyEnumVo vo : ignoreApiRegularEnumList) {
                    if (uri.matches(vo.getCode())) {
                        isRelease = true;
                        break;
                    }
                }
            }
            // 不放行，需要校验token
            if (!isRelease) {
                this.addThreadLocal(request);
                this.validateToken(request);
                // 在用户加载后执行字典转换
                this.executeDictConversion(request);
            }
        } catch (AuthenticationException e) {
            loginFailureHandler.onAuthenticationFailure(request, response, e);
        }
        // 放行
        filterChain.doFilter(request, response);
    }

    /**
     *  ThreadLocal存储用户信息
     * @param request
     */
    public void addThreadLocal(HttpServletRequest request) {
        // 获取用户信息
        String token = request.getHeader("token");
        if (ObjectUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }
        Claims claims = JwtUtil.parseJWT(token);
        SysUser sysUser = JSON.parseObject(claims.getSubject(), SysUser.class);
        if (sysUser == null){
            throw new CustomerAuthenticationException("获取用户信息异常！");
        }
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, sysUser.getUsername())
                .eq(SysUser::getPassword, sysUser.getPassword())
                .eq(SysUser::getIsDel, 0);
        SysUser user = sysUserService.getOne(wrapper);
        if (user == null) {
            throw new CustomerAuthenticationException("获取用户信息异常！");
        }
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(user, sysUserVo);
        // ThreadLocal存储用户信息
        SecurityUtils.add(sysUserVo);
        SecurityUtils.add(request);
    }

    /**
     *  token校验
     * @param request
     */
    private void validateToken(HttpServletRequest request) {
        // 判断token令牌是否为空
        String token = request.getHeader("token");
        if (ObjectUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }
        if (ObjectUtils.isEmpty(token)) {
            throw new CustomerAuthenticationException("Token为空！");
        }
        // redis进行校验
        SysUserVo user = SecurityUtils.getUser();
        String tokenKey = "token:" + user.getLoginId();
        String redisToken = stringRedisTemplate.opsForValue().get(tokenKey);
        if (ObjectUtils.isEmpty(redisToken)) {
            throw new CustomerAuthenticationException("token已过期, 请重新登入！");
        }
        // 校验令牌
        SysUser sysUser = null;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            String sysUserString = claims.getSubject();
            sysUser = JSON.parseObject(sysUserString, SysUser.class);   // 字符串转为对象

        } catch (Exception e) {
            log.error("Token校验失败！");
            throw new CustomerAuthenticationException("Token校验失败! ");
        }
        // 把验证信息放到SpringSecurity上下文
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sysUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    }



    /**
     * 执行字典转换
     */
    private void executeDictConversion(HttpServletRequest request) {
        try {
            // 获取当前用户
            SysUserVo user = SecurityUtils.getUser();
            if (user == null) {
                log.warn("用户信息未加载，无法执行字典转换");
                return;
            }

            // 初始化字典转换器（如果尚未初始化）
            if (cacheManager != null) {
                // 执行字典转换
                cacheManager.initDictData(user);
            } else {
                log.error("字典转换器未初始化");
            }
        } catch (Exception e) {
            log.error("执行字典转换失败", e);
        }
    }
}
