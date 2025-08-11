package com.system.systembase.impl.basicConfiguration.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.exception.CustomerAuthenticationException;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.basicConfiguration.domain.SysCurrent;
import com.system.systembase.api.basicConfiguration.service.ISysCurrentService;
import com.system.systembase.api.basicConfiguration.vo.SysCurrentVo;
import com.system.systembase.impl.basicConfiguration.mapper.SysCurrentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author lutong
 * @data 2025-1-8 008 9:46
 */

@Slf4j
@Service
public class SysCurrentServiceImpl extends ServiceImpl<SysCurrentMapper, SysCurrent> implements ISysCurrentService {

    @Autowired
    private SysCurrentMapper sysCurrentMapper;

    @Override
    public Page<SysCurrent> selectPage(String key, Integer pageIndex, Integer pageSize) {
        if (pageIndex == null) pageIndex = 1;
        if (pageSize == null) pageSize = 20;

        SysUserVo user = SecurityUtils.getUser();
        String verifyUser = this.getSecurityUser(user);
        if (verifyUser != null) throw new CustomerAuthenticationException("账号未登入，请重新登入!");

        Page<SysCurrent> page = new Page<>(pageIndex, pageSize);
        Integer count = sysCurrentMapper.getPageCount(key, user.getCompanyUid());
        if (count > 0) {
            List<SysCurrent> list = sysCurrentMapper.getPageList(key, user.getCompanyUid(), pageIndex, pageSize);
            page.setRecords(list);
        }
        return page;
    }

    @Override
    public List<SysCurrentVo> getCurrentList(String key) {
        SysUserVo user = SecurityUtils.getUser();
        String verifyUser = this.getSecurityUser(user);
        if (verifyUser != null) throw new CustomerAuthenticationException("账号未登入，请重新登入!");

        List<SysCurrentVo> list = sysCurrentMapper.selectCurrentList(key, user.getCompanyUid());
        log.info("获取币别列表:{}", JSON.toJSONString(list));
        return list;
    }

    @Override
    public String editCurrent(String currentCode, BigDecimal preExchangeRate, BigDecimal buyExchangeRate, BigDecimal sellExchangeRate, String remark) {
        // 校验
        LambdaQueryWrapper<SysCurrent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysCurrent::getCurrentCode, currentCode);
        SysCurrent current = this.baseMapper.selectOne(wrapper);
        if (current == null) {
            return "不存在编码为【" + currentCode + "】的币别编码";
        }
        SysUserVo user = SecurityUtils.getUser();
        String verifyUser = this.getSecurityUser(user);
        if (verifyUser != null) return verifyUser;

        current.setPreExchangeRate(!(preExchangeRate == null) ? preExchangeRate : null);
        current.setBuyExchangeRate(!(buyExchangeRate == null) ? buyExchangeRate : null);
        current.setSellExchangeRate(!(sellExchangeRate == null) ? sellExchangeRate : null);
        current.setRemark(remark);
        current.setUpdateUserName(user.getLoginId());
        current.setUpdateTime(new Date());
        this.updateById(current);
        return null;
    }

    @Override
    public String addCurrent(String currentName, String currentCode, BigDecimal preExchangeRate, BigDecimal buyExchangeRate, BigDecimal sellExchangeRate, String remark) {
        // 校验
        String verifiedCurrentCode = this.verifyCurrentCode(currentCode);
        if (verifiedCurrentCode != null) return verifiedCurrentCode;
        String verifyCurrentName = this.verifyCurrentName(currentName);
        if (verifyCurrentName != null) return verifyCurrentName;
        SysUserVo user = SecurityUtils.getUser();
        String verifyUser = this.getSecurityUser(user);
        if (verifyUser != null) return verifyUser;

        SysCurrent current = new SysCurrent();
        current.setCurrentCode(currentCode);
        current.setCurrentName(currentName);
        current.setPreExchangeRate(preExchangeRate);
        current.setBuyExchangeRate(buyExchangeRate);
        current.setSellExchangeRate(sellExchangeRate);
        current.setRemark(remark);
        current.setCompanyUid(user.getCompanyUid());
        current.setCreateUserName(user.getLoginId());
        current.setCreateTime(new Date());
        this.baseMapper.insert(current);
        return null;
    }

    @Override
    public String deleteCurrent(String currentCode) {
        LambdaQueryWrapper<SysCurrent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysCurrent::getCurrentCode, currentCode);
        SysCurrent one = this.baseMapper.selectOne(wrapper);
        if (one == null) {
            return "不存在编码为【" + currentCode + "】的币别编码";
        }
        this.baseMapper.delete(wrapper);
        return null;
    }


    /**
     *  校验CurrentCode
     * @return
     */
    public String verifyCurrentCode(String currentCode) {
        LambdaQueryWrapper<SysCurrent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysCurrent::getCurrentCode, currentCode);
        SysCurrent one = this.baseMapper.selectOne(wrapper);
        if (one != null) {
            return "已存在编码为【" + currentCode + "】的币别编码，请修改！";
        }
        return null;
    }

    /**
     *  校验CurrentName
     * @return
     */
    public String verifyCurrentName(String currentName) {
        LambdaQueryWrapper<SysCurrent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysCurrent::getCurrentName, currentName);
        SysCurrent one = this.baseMapper.selectOne(wrapper);
        if (one != null) {
            return "已存在名称为【" + currentName + "】的币别名称，请修改！";
        }
        return null;
    }

    /**
     *  校验用户
     * @param user
     * @return
     */
    public String getSecurityUser(SysUserVo user) {
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        return null;
    }
}
