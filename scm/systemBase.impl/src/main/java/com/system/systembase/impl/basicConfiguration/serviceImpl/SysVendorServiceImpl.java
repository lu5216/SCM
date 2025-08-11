package com.system.systembase.impl.basicConfiguration.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.basicConfiguration.domain.SysClient;
import com.system.systembase.api.basicConfiguration.domain.SysCompany;
import com.system.systembase.api.basicConfiguration.domain.SysCompanyDetail;
import com.system.systembase.api.basicConfiguration.domain.SysVendor;
import com.system.systembase.api.basicConfiguration.param.SysVendorParam;
import com.system.systembase.api.basicConfiguration.service.ISysCompanyDetailService;
import com.system.systembase.api.basicConfiguration.service.ISysCompanyService;
import com.system.systembase.api.basicConfiguration.service.ISysVendorService;
import com.system.systembase.api.basicConfiguration.vo.SysCVListVo;
import com.system.systembase.api.basicConfiguration.vo.SysVendorDetail;
import com.system.systembase.api.basicConfiguration.vo.SysVendorVo;
import com.system.systembase.api.redisTemplate.service.IRedisService;
import com.system.systembase.impl.basicConfiguration.mapper.SysVendorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lutong
 * @data 2024-12-24 024 14:11
 */

@Slf4j
@Service
public class SysVendorServiceImpl extends ServiceImpl<SysVendorMapper, SysVendor> implements ISysVendorService {

    @Autowired
    private SysVendorMapper sysVendorMapper;

    @Autowired
    private ISysCompanyService sysCompanyService;

    @Autowired
    private ISysCompanyDetailService sysCompanyDetailService;

    @Autowired
    private IRedisService redisService;

    @Override
    public Page<SysVendorVo> selectVendorPage(SysVendorParam param) {
        if (param.getPageIndex() == null) param.setPageIndex(1);
        if (param.getPageSize() == null) param.setPageSize(20);

        Page<SysVendorVo> page = new Page<>(param.getPageIndex(), param.getPageSize());
        LambdaQueryWrapper<SysVendor> wrapper = new LambdaQueryWrapper<>();
        if (param.getRegisterName() != null && !param.getRegisterName().equals("")) {
            wrapper.like(SysVendor::getRegisterName, param.getRegisterName());
        }
        if (param.getStartDate() != null && !param.getStartDate().equals("")) {
            wrapper.ge(SysVendor::getCreateTime, param.getStartDate() + " 00:00:00");
        }
        if (param.getEndDate() != null && !param.getEndDate().equals("")) {
            wrapper.le(SysVendor::getCreateTime, param.getEndDate() + " 23:59:59");
        }
        List<SysVendor> sysVendorList = this.baseMapper.selectList(wrapper);
        page.setTotal(sysVendorList.size());

        List<SysVendorVo> list = new ArrayList<>();
        if (sysVendorList.size() > 0){
            for (SysVendor vendor : sysVendorList) {
                SysVendorVo vo = new SysVendorVo();
                BeanUtils.copyProperties(vendor, vo);
                SysCompany company = sysCompanyService.selectByCompanyUID(vendor.getBindCompany());
                if (company != null) {
                    vo.setCompanyUid(company.getCompanyUid());
                    vo.setCompanyName(company.getCompanyName());
                    vo.setCompanyCode(company.getCompanyCode());
                    vo.setVendorType(company.getVendorType());
                    vo.setIsValidated(company.getIsValidated());
                }
                list.add(vo);
            }
            // 过滤数据
            log.info(param.getVendorType());
            if (param.getVendorType() != null && !param.getVendorType().equals("")) {
                list = list.stream().filter(t -> Objects.equals(t.getVendorType(), param.getVendorType())).collect(Collectors.toList());
            }
            list = list.stream().skip((long) (param.getPageIndex() - 1) * param.getPageSize()).limit(param.getPageSize()).collect(Collectors.toList());
            page.setRecords(list);
        }
        return page;
    }

    @Override
    public SysVendorDetail selectVendorDetail(String vendorCode) {
        return sysVendorMapper.selectVendorDetail(vendorCode);
    }

    @Override
    @Transactional
    public String updateVendorDetail(SysVendorDetail detail) {
        // 查询三表
        LambdaQueryWrapper<SysVendor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysVendor::getVendorCode, detail.getVendorCode());
        SysVendor vendor = this.baseMapper.selectOne(wrapper);
        if (vendor == null) {
            return "不存在【" + detail.getVendorCode() + "】供应商编码,请维护！";
        }
        SysCompany company = sysCompanyService.selectByCompanyUID(vendor.getBindCompany());
        SysCompanyDetail companyDetail = sysCompanyDetailService.selectDetailByCompanyUID(vendor.getBindCompany());
        if (company == null || companyDetail == null) {
            return "不存在公司UID为【" + vendor.getBindCompany() + "】的数据,请维护！";
        }

        // 修改客户数据
        vendor.setRegisterName(detail.getCompanyName());
        vendor.setLinkman(detail.getLinkman());
        vendor.setEmail(detail.getEmail());
        vendor.setPhone(detail.getPhone());
        vendor.setUpdateTime(detail.getUpdateTime());
        vendor.setUpdateUserName(detail.getUpdateUserName());
        this.baseMapper.updateById(vendor);
        // 修改公司数据
        company.setCompanyName(detail.getCompanyName());
        company.setCompanyType(detail.getCompanyType());
        company.setCompanyBusiness(detail.getCompanyBusiness());
        company.setStatus(detail.getStatus());
        company.setEmail(detail.getEmail());
        company.setPhone(detail.getPhone());
        company.setAddress(detail.getAddress());
        company.setVendorType(detail.getVendorType());
        company.setUpdateTime(detail.getUpdateTime());
        company.setUpdateUserName(detail.getUpdateUserName());
        sysCompanyService.updateById(company);
        // 修改公司明细数据
        companyDetail.setAbbreviation(detail.getAbbreviation());
        companyDetail.setCreditCodeType(detail.getCreditCodeType());
        companyDetail.setCreditCode(detail.getCreditCode());
        companyDetail.setRatepayerType(detail.getRatepayerType());
        companyDetail.setRegisteredCapital(detail.getRegisteredCapital());
        companyDetail.setLegalRepresentative(detail.getLegalRepresentative());
        companyDetail.setCompanyCreateDate(detail.getCompanyCreateDate());
        companyDetail.setPeriodBusiness(detail.getPeriodBusiness());
        companyDetail.setEnrollAddress(detail.getEnrollAddress());
        companyDetail.setScopeBusiness(detail.getScopeBusiness());
        companyDetail.setLicense(detail.getLicense());
        companyDetail.setUpdateTime(detail.getUpdateTime());
        companyDetail.setUpdateUserName(detail.getUpdateUserName());
        sysCompanyDetailService.updateById(companyDetail);
        return null;
    }

    @Override
    @Transactional
    public String addVendorData(SysVendor vendor) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        if (vendor.getRegisterName() == null || vendor.getRegisterName().equals("")) {
            return "注册名称不能为空！";
        }

        // 添加供应商编码
        String vendorCode = redisService.createNo("V");
        vendor.setVendorCode(vendorCode);
        // 添加绑定公司UID
        String companyUid = redisService.createNo("CU");
        vendor.setBindCompany(companyUid);


        if (vendor.getIsCooperation() == null) vendor.setIsCooperation(true);
        if (vendor.getIsEnable() == null) vendor.setIsEnable(true);
        vendor.setCreateUserName(user.getLoginId());
        vendor.setCreateTime(new Date());
        this.baseMapper.insert(vendor);

        // 新增公司数据
        String errMsg = sysCompanyService.addCompany(vendor);
        if (errMsg != null) return errMsg;
        return null;
    }

    @Override
    @Transactional
    public String deleteVendorData(Integer id) {
        SysVendor vendor = this.baseMapper.selectById(id);
        if (vendor == null) {
            return "找不到该供应商数据，请维护！";
        }
        this.baseMapper.deleteById(id);

        // 删除公司数据
        SysCompany company = sysCompanyService.selectByCompanyUID(vendor.getBindCompany());
        SysCompanyDetail companyDetail = sysCompanyDetailService.selectDetailByCompanyUID(vendor.getBindCompany());
        if (company == null || companyDetail == null) {
            return "不存在公司UID为【" + vendor.getBindCompany() + "】的数据,请维护！";
        }
        sysCompanyService.removeById(company.getId());
        sysCompanyDetailService.removeById(companyDetail.getId());
        return null;
    }


    @Override
    public String updateIsCooperation(String vendorCode) {
        LambdaQueryWrapper<SysVendor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysVendor::getVendorCode, vendorCode);
        SysVendor sysVendor = this.baseMapper.selectOne(wrapper);
        if (sysVendor == null) {
            return "供应商编码【" + vendorCode + "】不存在!";
        }
        sysVendor.setIsCooperation(!sysVendor.getIsCooperation());
        this.baseMapper.updateById(sysVendor);
        return null;
    }

    @Override
    public String updateIsEnable(String vendorCode) {
        LambdaQueryWrapper<SysVendor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysVendor::getVendorCode, vendorCode);
        SysVendor sysVendor = this.baseMapper.selectOne(wrapper);
        if (sysVendor == null) {
            return "供应商编码【" + vendorCode + "】不存在!";
        }
        sysVendor.setIsEnable(!sysVendor.getIsEnable());
        this.baseMapper.updateById(sysVendor);
        return null;
    }

    @Override
    public String updateIsValidated(String vendorCode) {
        LambdaQueryWrapper<SysVendor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysVendor::getVendorCode, vendorCode);
        SysVendor sysVendor = this.baseMapper.selectOne(wrapper);
        if (sysVendor == null) {
            return "供应商编码【" + vendorCode + "】不存在!";
        }
        if (sysVendor.getBindCompany() != null) {
            String companyUid = sysVendor.getBindCompany();
            SysCompany company = sysCompanyService.selectByCompanyUID(companyUid);
            if (company == null) {
                return "客户编码【" + vendorCode + "】绑定的企业编码【" + companyUid + "】不存在!";
            }
            company.setIsValidated(!company.getIsValidated());
            sysCompanyService.updateById(company);
        } else {
            return "供应商编码【" + vendorCode + "】不存在绑定的企业编码!";
        }
        return null;
    }


    @Override
    public List<SysCVListVo> getVendorList(String key) {
        List<SysCVListVo> cvList = new ArrayList<>();

        LambdaQueryWrapper<SysVendor> wrapper = new LambdaQueryWrapper<>();
        if (null != key && !key.equals("")) {
            wrapper.like(SysVendor::getVendorCode, key).or().like(SysVendor::getRegisterName, key);
        }
        List<SysVendor> list = this.baseMapper.selectList(wrapper);
        for (SysVendor vendor : list) {
            if (vendor.getIsEnable()) {
                SysCVListVo vo = new SysCVListVo();
                vo.setId(vendor.getId());
                vo.setName(vendor.getRegisterName());
                vo.setCode(vendor.getVendorCode());
                cvList.add(vo);
            }
        }
        return cvList;
    }


    @Override
    public SysVendor getByVendorCode(String vendorCode) {
        LambdaQueryWrapper<SysVendor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysVendor::getVendorCode, vendorCode);
        return this.baseMapper.selectOne(wrapper);
    }
}
