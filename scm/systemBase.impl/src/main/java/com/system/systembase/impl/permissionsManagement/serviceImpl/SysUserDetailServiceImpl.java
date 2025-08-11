package com.system.systembase.impl.permissionsManagement.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.system.systembase.api.permissionsManagement.domain.SysUser;
import com.system.systembase.api.permissionsManagement.vo.SysUserDetails;
import com.system.systembase.impl.permissionsManagement.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lutong
 * @data 2024-11-28 028 15:36
 * 用户登录实现接口
 */

@Slf4j
@Service
public class SysUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 从数据库中查询用户
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        if (loginId == null || loginId.equals("")) {
            throw new InternalAuthenticationServiceException("");
        }
        SysUser sysUser = sysUserMapper.selectByLoginId(loginId);
        log.info("sysUser:{}", JSON.toJSONString(sysUser));
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new UsernameNotFoundException("");
        }
        // 赋权操作
        List<String> permissionsList  = new ArrayList<>();
        permissionsList.add("add");
        permissionsList.add("delete");
        permissionsList.add("update");
        permissionsList.add("select");
        return new SysUserDetails(sysUser, permissionsList);
    }
}
