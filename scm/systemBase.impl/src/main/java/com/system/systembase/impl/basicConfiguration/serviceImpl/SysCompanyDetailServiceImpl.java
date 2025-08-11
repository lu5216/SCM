package com.system.systembase.impl.basicConfiguration.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.basicConfiguration.domain.SysCompanyDetail;
import com.system.systembase.api.basicConfiguration.service.ISysCompanyDetailService;
import com.system.systembase.impl.basicConfiguration.mapper.SysCompanyDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author lutong
 * @data 2024-12-16 016 14:56
 */

@Slf4j
@Service
public class SysCompanyDetailServiceImpl extends ServiceImpl<SysCompanyDetailMapper, SysCompanyDetail> implements ISysCompanyDetailService {

    @Override
    public String addCompanyDetail(String companyUid) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        SysCompanyDetail companyDetail = this.selectDetailByCompanyUID(companyUid);
        if (companyDetail != null) {
            return "公司明细已存在公司UID为【" + companyUid + "】的数据!";
        }

        SysCompanyDetail detail = new SysCompanyDetail();
        detail.setCompanyUid(companyUid);
        detail.setCreateTime(new Date());
        detail.setCreateUserName(user.getLoginId());
        this.baseMapper.insert(detail);
        return null;
    }

    @Override
    public SysCompanyDetail selectDetailByCompanyUID(String companyUid) {
        LambdaQueryWrapper<SysCompanyDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysCompanyDetail::getCompanyUid, companyUid);
        return this.baseMapper.selectOne(wrapper);
    }
}
