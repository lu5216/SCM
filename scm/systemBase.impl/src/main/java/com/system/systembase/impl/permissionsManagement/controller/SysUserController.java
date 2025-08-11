package com.system.systembase.impl.permissionsManagement.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.exception.CustomerAuthenticationException;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.Result;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.permissionsManagement.domain.SysUser;
import com.system.systembase.api.permissionsManagement.param.RegistrationParam;
import com.system.systembase.api.permissionsManagement.param.SysUserListParam;
import com.system.systembase.api.permissionsManagement.param.SysUserParam;
import com.system.systembase.api.permissionsManagement.service.ISysUserService;
import com.system.systembase.api.permissionsManagement.vo.SysUserListVo;
import com.system.systembase.api.redisTemplate.service.IRedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author lutong
 * @data 2024-11-28 028 15:22
 */

@Slf4j
@RestController
@Api(tags = "权限管理-用户")
@RequestMapping("/auth")
@Controller
public class SysUserController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IRedisService redisService;


    @ApiOperation(value = "后端管理系统登入")
    @PostMapping(value = "/sysLogin")
    public Result<Map<String, Object>> sysLogin(HttpServletRequest request, @RequestBody SysUserParam param) {
        // 校验验证码
        String verifyCode = redisService.getValueByKey("verifyCode");
        if (verifyCode == null) {
            return Result.failed("验证码已过期！");
        }
        if (!Objects.equals(verifyCode, param.getVerifyCode())){
            return Result.failed("验证码错误！");
        }
        Map<String, Object> map = sysUserService.sysLogin(param);
        if (!(map.get("token") == null || map.get("token").equals("")) ||
                !(map.get("username") == null || map.get("username").equals(""))) {
            log.info(param.getLoginId() + "登入成功！");
            // 清除验证码redis
            redisService.deleteRedis("verifyCode");
            return Result.success("登入成功！", map);
        }
        return Result.failed("登入失败！");
    }

    @ApiOperation(value = "后端管理系统登出")
    @PostMapping(value = "/logout")
    public Result<Map<String, Object>> logout(HttpServletRequest request, HttpServletResponse response) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return Result.failed("账号未登入，请重新登入!");
        }

        // 获取token
        String token = request.getHeader("token");
        if (ObjectUtils.isEmpty(token)) {
            request.getParameter("token");
        }
        if (ObjectUtils.isEmpty(token)) {
            throw new CustomerAuthenticationException("token为空");
        }
        // 获取用户相关信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // 清空用户信息
            SecurityContextLogoutHandler handler = new SecurityContextLogoutHandler();
            handler.logout(request, response, authentication);
            // 清除redis
            String tokenKey = "token:" + user.getLoginId();
            stringRedisTemplate.delete(tokenKey);
        }
        return Result.success("用户登出系统成功！");
    }

    @ApiOperation(value = "注册账户")
    @PostMapping(value = "/registration")
    public Result<String> registration(@RequestBody RegistrationParam param) {
        String result = sysUserService.registration(param);
        if (result == null) {
            log.info(param.getUsername() + "注册成功！");
            return Result.success(param.getUsername() + "注册成功！");
        } else {
            log.info(param.getUsername() + "注册失败," + result);
            return Result.failed(param.getUsername() + "注册失败," + result);
        }
    }

    @ApiOperation(value = "删除账户")
    @PostMapping(value = "/deleteUser")
    public Result<String> deleteUser(@RequestBody SysUserParam param) {
        String result = sysUserService.deleteUser(param);
        if (result == null) {
            log.info("用户" + param.getUsername() + "删除成功！");
            return Result.success("用户" + param.getUsername() + "删除成功！");
        } else {
            log.info("用户" + param.getUsername() + "删除失败," + result);
            return Result.failed("用户" + param.getUsername() + "删除失败," + result);
        }
    }

    @ApiOperation(value = "修改账户")
    @PostMapping(value = "/updateUser")
    public Result<String> updateUser(@RequestBody RegistrationParam param) {
        String result = sysUserService.updateUser(param);
        if (result == null) {
            return Result.success("修改成功！");
        } else {
            return Result.failed(result);
        }
    }


    @ApiOperation(value = "启用-弃用账户")
    @GetMapping (value = "/isDelUser")
    public Result<String> isDelUser(@RequestParam(value = "loginId") String loginId,
                                    @RequestParam(value = "isDel") Boolean isDel) {
        try {
            String delUser = sysUserService.isDelUser(loginId, isDel);
            if (delUser == null) {
                if (isDel)  {
                    return Result.success("账户弃用成功！");
                } else {
                    return Result.success("账户启用成功！");
                }
            } else {
                return Result.failed(delUser);
            }
        } catch (Exception e) {
            log.error("启用-弃用账户接口异常,异常原因：" + e);
            throw new CustomerAuthenticationException("启用-弃用账户接口异常,异常原因：" + e);
        }
    }

    @ApiOperation(value = "分页查询用户列表")
    @PostMapping(value = "/selectUserList")
    public Result<Page<SysUserListVo>> selectUserList(@RequestBody SysUserListParam param) {
        Page<SysUserListVo> page = sysUserService.selectUserList(param);
        return Result.success("查询角色列表成功！", page);
    }
}
