package com.system.entrance.config;

import com.system.entrance.handler.AnonymousAuthenticationHandler;
import com.system.entrance.handler.CustomerAccessDeniedHandler;
import com.system.entrance.filter.JwtAuthenticationTokenFilter;
import com.system.entrance.handler.LoginFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @author lutong
 * @data 2024-11-28 028 16:00
 * Security配置类
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private CustomerAccessDeniedHandler customerAccessDeniedHandler;

    @Autowired
    private AnonymousAuthenticationHandler anonymousAuthenticationHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    /**
     * 密码管理器, 将密码转换成密文
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 登入时进行一次校验
     * @param configuration config
     * @return configuration.getAuthenticationManager()
     * @throws Exception e
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer securityCustomizer() {
        // 设置忽略拦截
        return (web) -> web.ignoring().antMatchers(
                "/webjars/**", "/swagger-resources/**", "/v2/**", // 接口文档-静态资源
                "/swagger-ui.html",   // swagger 接口文档
                "/doc.html",  // bootstrap 接口文档
                "/images/**",   // 图片静态资源
                "/emailTemplates/**"   // 邮件模板静态资源
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 关闭csrf
        http.csrf().disable().formLogin().and().cors();

        // 用户认证校验失败处理器
        http.formLogin(configurer -> configurer.failureHandler(loginFailureHandler));

        http.headers().frameOptions().disable();  //解决 in a frame because it set 'X-Frame-Options' to 'DENY' 问题
        // 配置请求的拦截方式
        http.authorizeRequests()
                // 不拦截登录相关请求
                .antMatchers(
                        "/auth/sysLogin",                     // 登入接口
                        "/auth/registration",                 // 注册接口
                        "/verify/getVerificationCodePhoto",   // 获取验证码接口
                        "/email/*",                           // 发送邮件
                        "/wx/push",                           // 微信公众号推送
                        "/generateCode/**"                    // 生成二维码/条形码图片
                        )
                .permitAll()
			    .anyRequest().authenticated();   // 任何尚未匹配的URL只需要验证用户即可访问
        // 设置过滤器执行顺序
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);



        // 配置自定义异常处理类
        http.exceptionHandling(configurer -> {
            configurer.accessDeniedHandler(customerAccessDeniedHandler);
            configurer.authenticationEntryPoint(anonymousAuthenticationHandler);
        });
        return http.build();
    }
}
