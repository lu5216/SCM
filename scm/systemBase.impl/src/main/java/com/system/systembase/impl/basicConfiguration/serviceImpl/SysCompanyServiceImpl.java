package com.system.systembase.impl.basicConfiguration.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.enums.api.basicConfiguration.CompanyStatusEnum;
import com.system.common.vo.Result;
import com.system.systembase.api.basicConfiguration.domain.SysClient;
import com.system.systembase.api.basicConfiguration.domain.SysCompany;
import com.system.systembase.api.basicConfiguration.domain.SysCompanyDetail;
import com.system.systembase.api.basicConfiguration.domain.SysVendor;
import com.system.systembase.api.basicConfiguration.param.SysCompanyParam;
import com.system.systembase.api.basicConfiguration.service.ISysCompanyDetailService;
import com.system.systembase.api.basicConfiguration.service.ISysCompanyService;
import com.system.systembase.api.redisTemplate.service.IRedisService;
import com.system.systembase.impl.basicConfiguration.mapper.SysCompanyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author lutong
 * @data 2024-12-13 013 14:04
 */

@Slf4j
@Service
public class SysCompanyServiceImpl extends ServiceImpl<SysCompanyMapper, SysCompany> implements ISysCompanyService {

    @Autowired
    private ISysCompanyDetailService sysCompanyDetailService;

    @Autowired
    private IRedisService redisService;

    @Override
    public List<SysCompany> selectCompany(SysCompanyParam param) {
        LambdaQueryWrapper<SysCompany> wrapper = new LambdaQueryWrapper<>();
        if (param.getCompany() != null) {
            wrapper.like(SysCompany::getCompanyName ,param.getCompany()).or().like(SysCompany::getCompanyName ,param.getCompany());
        }
        if (param.getCompanyUid() != null) {
            wrapper.like(SysCompany::getCompanyUid, param.getCompanyUid());
        }
        if (param.getCompanyType() != null) {
            wrapper.eq(SysCompany::getCompanyType, param.getCompanyType());
        }
        if (param.getCompanyBusiness() != null) {
            wrapper.eq(SysCompany::getCompanyBusiness, param.getCompanyBusiness());
        }
        if (param.getStatus() != null) {
            wrapper.eq(SysCompany::getStatus, param.getStatus());
        }
        if (param.getIsVendor() != null) {
            wrapper.eq(SysCompany::getIsVendor, param.getIsVendor());
            if (param.getVendorType() != null) {
                wrapper.eq(SysCompany::getVendorType, param.getVendorType());
            }
        } else if (param.getIsClient() != null) {
            wrapper.eq(SysCompany::getIsClient, param.getIsClient());
            if (param.getClientType() != null) {
                wrapper.eq(SysCompany::getClientType, param.getClientType());
            }
        }
        List<SysCompany> companyList = this.baseMapper.selectList(wrapper);
        log.info("查询出的数据：{}", JSON.toJSONString(companyList));
        return companyList;
    }


    @Override
    @Transactional
    public String addCompany(SysClient client) {
        SysCompany company = new SysCompany();
        String companyCode = redisService.createNo("CC");
        company.setCompanyCode(companyCode);
        company.setCompanyUid(client.getBindCompany());
        company.setCompanyName(client.getRegisterName());
        company.setEmail(client.getEmail());
        company.setPhone(client.getPhone());
        company.setStatus(CompanyStatusEnum.ZY.getCode());
        company.setIsValidated(true);
        company.setIsVendor(false);
        company.setIsClient(true);
        company.setCreateTime(new Date());
        company.setCreateUserName(client.getCreateUserName());
        this.baseMapper.insert(company);

        // 新增公司明细
        String errMsg = sysCompanyDetailService.addCompanyDetail(client.getBindCompany());
        if (errMsg != null) return errMsg;
        return null;
    }

    @Override
    @Transactional
    public String addCompany(SysVendor vendor) {
        SysCompany company = new SysCompany();
        String companyCode = redisService.createNo("CC");
        company.setCompanyCode(companyCode);
        company.setCompanyName(vendor.getRegisterName());
        company.setCompanyUid(vendor.getBindCompany());
        company.setEmail(vendor.getEmail());
        company.setPhone(vendor.getPhone());
        company.setStatus(CompanyStatusEnum.ZY.getCode());
        company.setIsValidated(true);
        company.setIsVendor(true);
        company.setIsClient(false);
        company.setCreateTime(new Date());
        company.setCreateUserName(vendor.getCreateUserName());
        this.baseMapper.insert(company);

        // 新增公司明细
        String errMsg = sysCompanyDetailService.addCompanyDetail(vendor.getBindCompany());
        if (errMsg != null) return errMsg;
        return null;
    }

    @Override
    public Result<String> isValidated(Integer id) {
        SysCompany company = this.baseMapper.selectById(id);
        if (company == null) {
            return Result.failed("不存在该企业，请维护！");
        }
        company.setIsValidated(!company.getIsValidated());
        this.baseMapper.updateById(company);
        return company.getIsValidated() ? Result.success("启用企业成功！") : Result.success("停用企业成功！");
    }

    @Override
    public SysCompany selectByCompanyUID(String companyUid) {
        LambdaQueryWrapper<SysCompany> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysCompany::getCompanyUid, companyUid);
        return this.baseMapper.selectOne(wrapper);
    }
}
