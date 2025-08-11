package com.system.systembase.api.permissionsManagement.vo;

import com.alibaba.fastjson.JSON;
import com.system.systembase.api.permissionsManagement.domain.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author lutong
 * @data 2024-11-29 029 11:13
 */

@Data
@Slf4j
@NoArgsConstructor
public class SysUserDetails implements UserDetails {

    @ApiModelProperty(value = "sysUser")
    private SysUser sysUser;

    /**
     * 权限列表
     */
    @ApiModelProperty(value = "权限列表")
    private List<String> permissionsList;

    /**
     * 自定义权限列表集合
     */
    List<SimpleGrantedAuthority> authorityList;

    public SysUserDetails(SysUser sysUser, List<String> permissionsList) {
        this.sysUser = sysUser;
        this.permissionsList = permissionsList;
    }

    /**
     *  权限
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorityList != null) {
            return authorityList;
        }
        authorityList = new ArrayList<>();
        for (String item : permissionsList) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(item);
            authorityList.add(authority);
        }
        log.info("权限列表：{}", JSON.toJSONString(authorityList));
        return authorityList;
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    /**
     *  账户是否过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     *  是否不上锁
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     *  凭据是否过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     *  是否启动
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
