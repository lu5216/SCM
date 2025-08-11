package com.system.systembase.impl.permissionsManagement.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.exception.CustomerAuthenticationException;
import com.system.common.utils.JwtUtil;
import com.system.common.utils.SecurityUtils;
import com.system.common.utils.SpringContextUtil;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.permissionsManagement.domain.SysDataPermissions;
import com.system.systembase.api.permissionsManagement.domain.SysFunctionPermissions;
import com.system.systembase.api.permissionsManagement.domain.SysPermissionsRole;
import com.system.systembase.api.permissionsManagement.domain.SysUser;
import com.system.systembase.api.permissionsManagement.param.RegistrationParam;
import com.system.systembase.api.permissionsManagement.param.SysUserListParam;
import com.system.systembase.api.permissionsManagement.param.SysUserParam;
import com.system.systembase.api.permissionsManagement.service.*;
import com.system.systembase.api.permissionsManagement.vo.SysUserDetails;
import com.system.systembase.api.permissionsManagement.vo.SysUserListVo;
import com.system.systembase.api.permissionsManagement.vo.SysUserRoleVo;
import com.system.systembase.api.redisTemplate.service.IRedisService;
import com.system.systembase.impl.permissionsManagement.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author lutong
 * @data 2024-11-28 028 15:19
 */

@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IRedisService redisService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ISysPermissionsRoleService sysPermissionsRoleService;

    @Autowired
    private ISysDataPermissionsService dataPermissionsService;

    @Autowired
    private ISysFunctionPermissionsService functionPermissionsService;

    private final ISysUserRoleService userRoleService;
    public SysUserServiceImpl(@Lazy ISysUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    /**
     * 登入
     */
    @Override
    public  Map<String, Object> sysLogin(SysUserParam param) {
        // 判断是否需要登入
        SysUser sysUser = this.getOneByLoginId(param.getLoginId());
        if (sysUser == null) {
            throw new CustomerAuthenticationException("不存在账户【" + param.getLoginId() + "】");
        }
        // 根据账号查询角色
        SysUserRoleVo userRoleVo = userRoleService.selectUserRoleByLoginId(param.getLoginId());

        // 获取当前账号角色的按钮权限
        List<String> buttonRole = this.getButtonPermission(userRoleVo.getRoleId());
        // 获取当前账号角色的数据权限
        List<String> dataRole = this.getDataPermission(userRoleVo.getRoleId());

        String token = redisService.getValueByKey(param.getLoginId());
        if (token != null && !token.equals("")) {
            long ttlMillis = redisService.getExpire(param.getLoginId());
            if (ttlMillis > 1) {
                Map<String, Object> map = new HashMap<>();
                map.put("loginId", sysUser.getLoginId());
                map.put("userName", sysUser.getUsername());
                map.put("roleUid", userRoleVo.getRoleUid());
                map.put("token", token);
                map.put("ttlMillis", ttlMillis);
                map.put("buttonRole", buttonRole);
                map.put("dataRole", dataRole);
                return map;
            }
        }

        // 封装对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(param.getLoginId(), param.getPassword());

        // 校验
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (ObjectUtils.isEmpty(authentication)) {
            throw new RuntimeException("登入失败！");
        }

        // 获取用户信息
        Object principal = authentication.getPrincipal();
        SysUserDetails details = new SysUserDetails();
        BeanUtils.copyProperties(principal, details);

        // 生成JWT
        long ttlMillis = 365 * 24 * 60 * 60 * 1000L;  // 过期时间（设为1年）
        String sysUserString = JSON.toJSONString(details);
        String jwt = JwtUtil.createJWT(sysUserString, ttlMillis);   // 设置过期时间为1年

        // 存储redis白名单
        String tokenKey = "token:" + param.getLoginId();
        redisService.addRedis(tokenKey, jwt, ttlMillis/1000, TimeUnit.SECONDS);

        Map<String, Object> map = new HashMap<>();
        map.put("loginId", param.getLoginId());
        map.put("userName", details.getUsername());
        map.put("roleUid", userRoleVo.getRoleUid());
        map.put("token", jwt);
        map.put("ttlMillis", (int) (ttlMillis/1000));
        map.put("buttonRole", buttonRole);
        map.put("dataRole", dataRole);
        return map;
    }

    /**
     *  查询是否数据库是否存在该账号
     * @param loginId
     * @return SysUser
     */
    public SysUser selectUser(String loginId) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getLoginId, loginId);
        return this.baseMapper.selectOne(wrapper);
    }


    @Override
    @Transactional
    public String registration(RegistrationParam param) {
        // 校验
//        SysUserVo user = SecurityUtils.getUser();
//        if (user == null) {
//            return "账号未登入，请重新登入!";
//        }
        if (param.getLoginId() == null) {
            return "账号不能为空！";
        }
        if (param.getUsername() == null) {
            return "用户名不能为空！";
        }
        // 不填写,则使用默认密码
        if (param.getPassword() == null) {
            param.setPassword("123456");
        }

        // 查询是否存在相同登入ID
        SysUser selectUser = this.selectUser(param.getLoginId());
        if (selectUser != null) {
            return "系统已存在该账户Id,请重新填写！";
        }

        // 转换存储
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(param, sysUser);
        sysUser.setIsDel(false);
        sysUser.setCreateTime(new Date());
        sysUser.setCreateUserName(param.getLoginId());
        // 密码加密存储
        String password = passwordEncoder.encode(param.getPassword());
        sysUser.setPassword(password);
        this.baseMapper.insert(sysUser);

        // 默认角色-订单文员
        String errMsg = userRoleService.saveUserRole(param.getLoginId(), "1003");
        if (errMsg != null) {
            return errMsg;
        }
        return null;
    }

    @Override
    public String deleteUser(SysUserParam param) {
        // 校验
        if (param.getLoginId() == null) {
            return "账号不能为空！";
        }
        if (param.getUsername() == null) {
            return "用户名不能为空！";
        }
        // 查询是否存在该用户
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getLoginId, param.getLoginId());
        wrapper.eq(SysUser::getUsername, param.getUsername());
        SysUser sysUserOne = this.baseMapper.selectOne(wrapper);
        if (sysUserOne == null) {
            return "系统不存在该账户,请重新填写！";
        }
        // 删除关联了该用户的用户角色信息
        ISysUserRoleService bean = SpringContextUtil.getBean(ISysUserRoleService.class);
        SysUserRoleVo sysUserRoleVo = bean.selectUserRoleByLoginId(param.getLoginId());
        if (sysUserRoleVo != null) {
            bean.deleteUserRole(param.getLoginId(), sysUserRoleVo.getRoleUid());
        }
        // 删除用户
        this.baseMapper.delete(wrapper);
        return null;
    }

    @Override
    public String updateUser(RegistrationParam param) {
        // 校验
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        if (param.getLoginId() == null) {
            return "账号不能为空!";
        }

        // 查询是否存在该用户
        SysUser selectUser = this.selectUser(param.getLoginId());
        if (selectUser == null) {
            return "系统不存在该账户名,请重新填写！";
        }
        if (Objects.equals(param.getLoginId(), user.getLoginId())) {
            return "无法修改自己的账户名，请联系管理员修改！";
        }

        // 修改
        selectUser.setExpiresTime(param.getExpiresTime());
        selectUser.setUsername(param.getUsername());
        selectUser.setEmail(param.getEmail());
        selectUser.setMobile(param.getMobile());
        selectUser.setPicture(param.getPicture());
        // 密码加密存储
        if (param.getPassword() != null && !param.getPassword().equals("")) {
            String password = passwordEncoder.encode(param.getPassword());
            selectUser.setPassword(password);
        }
        selectUser.setUpdateUserName(user.getLoginId());
        selectUser.setUpdateTime(new Date());
        this.baseMapper.updateById(selectUser);
        return null;
    }

    @Override
    public String isDelUser(String loginId, Boolean isDel) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            throw new CustomerAuthenticationException("账号未登入，请重新登入!");
        }
        // 查询是否存在该用户
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getLoginId, loginId);
        SysUser selectUser = this.baseMapper.selectOne(wrapper);
        if (selectUser == null) {
            return "系统不存在该账户Id,请重新填写！";
        }
        if (Objects.equals(loginId, user.getLoginId())) {
            return "无法启用或弃用自己的账户，请联系管理员修改！";
        }

        // 修改
        selectUser.setUpdateUserName(user.getLoginId());
        selectUser.setUpdateTime(new Date());
        if (selectUser.getIsDel() && isDel) {
            return "账户已弃用，请输入其他账户！";
        } else if (!selectUser.getIsDel() && !isDel) {
            return "账户已启用，请输入其他账户！";
        } else {
            selectUser.setIsDel(isDel);
            this.baseMapper.updateById(selectUser);
        }
        return null;
    }

    @Override
    public Page<SysUserListVo> selectUserList(SysUserListParam param) {
        Page<SysUserListVo> page = new Page<>(param.getPageIndex(), param.getPageSize());
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        if (param.getLoginId() != null && !param.getLoginId().equals("")) {
            wrapper.like(SysUser::getLoginId, param.getLoginId());
        }
        if (param.getUsername() != null && !param.getUsername().equals("")) {
            wrapper.like(SysUser::getUsername, param.getUsername());
        }
        if (param.getMobile() != null && !param.getMobile().equals("")) {
            wrapper.like(SysUser::getMobile, param.getMobile());
        }
        if (param.getEmail() != null && !param.getEmail().equals("")) {
            wrapper.like(SysUser::getEmail, param.getEmail());
        }
        List<SysUser> sysUserList = this.baseMapper.selectList(wrapper);
        page.setTotal(sysUserList.size());

        // 分页
        sysUserList = sysUserList.stream().skip((long) (param.getPageIndex() - 1) * param.getPageSize()).limit(param.getPageSize()).collect(Collectors.toList());
        if (sysUserList.size() > 0) {
            List<SysUserListVo> list = new ArrayList<>();
            for (SysUser sysUser : sysUserList) {
                SysUserListVo vo = new SysUserListVo();
                BeanUtils.copyProperties(sysUser, vo);

                // 添加角色信息
                SysUserRoleVo roleVo = userRoleService.selectUserRoleByLoginId(sysUser.getLoginId());
                if (roleVo != null) {
                    vo.setRoleId(roleVo.getRoleId());
                    vo.setRole(roleVo.getRole());
                    vo.setRoleUid(roleVo.getRoleUid());
                }
                list.add(vo);
            }
            page.setRecords(list);
        }

        return page;
    }


    @Override
    public SysUser getOneByLoginId(String loginId) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getLoginId, loginId)
                .eq(SysUser::getIsDel, 0);
        return this.baseMapper.selectOne(wrapper);
    }

    /**
     *  获取当前账号角色的按钮权限
     * @param roleId
     * @return
     */
    public List<String> getButtonPermission(Integer roleId) {
        // 根据角色获取按钮ID
        List<SysPermissionsRole> buttonPermission = this.sysPermissionsRoleService.getButtonPermission(roleId);
        Set<Integer> set = new HashSet<>();
        if (buttonPermission.size() > 0) {
            for (SysPermissionsRole permissionsRole : buttonPermission) {
                set.add(permissionsRole.getFunctionId());
            }
        }
        // 获取按钮编码
        if (!set.isEmpty()) {
            List<SysFunctionPermissions> functionPermissions = functionPermissionsService.listByIds(new ArrayList<>(set));
            List<String> buttonRole = functionPermissions.stream().map(SysFunctionPermissions::getCode).collect(Collectors.toList());
            log.info("获取当前账号角色的按钮权限：{}", buttonRole);
            return buttonRole;
        }
        return new ArrayList<String>();
    }


    /**
     *  获取当前账号角色的数据权限
     * @param roleId
     * @return
     */
    public List<String> getDataPermission(Integer roleId) {
        // 根据角色获取按钮ID
        List<SysPermissionsRole> buttonPermission = this.sysPermissionsRoleService.getDataPermission(roleId);
        Set<Integer> set = new HashSet<>();
        if (buttonPermission.size() > 0) {
            for (SysPermissionsRole permissionsRole : buttonPermission) {
                set.add(permissionsRole.getDataId());
            }
        }
        // 获取按钮编码
        if (!set.isEmpty()) {
            List<SysDataPermissions> dataPermissions = dataPermissionsService.listByIds(new ArrayList<>(set));
            List<String> dataRole = dataPermissions.stream().map(SysDataPermissions::getFieldCode).collect(Collectors.toList());
            log.info("获取当前账号角色的数据权限：{}", dataRole);
            return dataRole;
        }
        return new ArrayList<String>();
    }
}
