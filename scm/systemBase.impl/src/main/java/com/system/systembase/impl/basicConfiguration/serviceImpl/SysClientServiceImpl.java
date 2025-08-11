package com.system.systembase.impl.basicConfiguration.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.basicConfiguration.domain.SysClient;
import com.system.systembase.api.basicConfiguration.domain.SysCompany;
import com.system.systembase.api.basicConfiguration.domain.SysCompanyDetail;
import com.system.systembase.api.basicConfiguration.param.SysClientParam;
import com.system.systembase.api.basicConfiguration.service.ISysClientService;
import com.system.systembase.api.basicConfiguration.service.ISysCompanyDetailService;
import com.system.systembase.api.basicConfiguration.service.ISysCompanyService;
import com.system.systembase.api.basicConfiguration.vo.SysCVListVo;
import com.system.systembase.api.basicConfiguration.vo.SysClientDetail;
import com.system.systembase.api.basicConfiguration.vo.SysClientVo;
import com.system.systembase.api.redisTemplate.service.IRedisService;
import com.system.systembase.impl.basicConfiguration.mapper.SysClientMapper;
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
public class SysClientServiceImpl extends ServiceImpl<SysClientMapper, SysClient> implements ISysClientService {

    @Autowired
    private SysClientMapper sysClientMapper;

    @Autowired
    private ISysCompanyService sysCompanyService;

    @Autowired
    private ISysCompanyDetailService sysCompanyDetailService;

    @Autowired
    private IRedisService redisService;

    @Override
    public Page<SysClientVo> selectClientPage(SysClientParam param) {
        if (param.getPageIndex() == null) param.setPageIndex(1);
        if (param.getPageSize() == null) param.setPageSize(20);
        Page<SysClientVo> page = new Page<>(param.getPageIndex(), param.getPageSize());
        LambdaQueryWrapper<SysClient> wrapper = new LambdaQueryWrapper<>();
        if (param.getRegisterName() != null && !param.getRegisterName().equals("")) {
            wrapper.like(SysClient::getRegisterName, param.getRegisterName());
        }
        if (param.getStartDate() != null && !param.getStartDate().equals("")) {
            wrapper.ge(SysClient::getCreateTime, param.getStartDate() + " 00:00:00");
        }
        if (param.getEndDate() != null && !param.getEndDate().equals("")) {
            wrapper.le(SysClient::getCreateTime, param.getEndDate() + " 23:59:59");
        }
        List<SysClient> sysClientList = this.baseMapper.selectList(wrapper);
        page.setTotal(sysClientList.size());

        List<SysClientVo> list = new ArrayList<>();
        if (sysClientList.size() > 0) {
            for (SysClient client : sysClientList) {
                SysClientVo vo = new SysClientVo();
                BeanUtils.copyProperties(client, vo);
                SysCompany company = sysCompanyService.selectByCompanyUID(client.getBindCompany());
                if (company != null) {
                    vo.setCompanyUid(company.getCompanyUid());
                    vo.setCompanyName(company.getCompanyName());
                    vo.setCompanyCode(company.getCompanyCode());
                    vo.setClientType(company.getClientType());
                    vo.setIsValidated(company.getIsValidated());
                }
                list.add(vo);
            }
            // 过滤数据
            if (param.getClientType() != null && !param.getClientType().equals("")) {
                list = list.stream().filter(t -> Objects.equals(t.getClientType(), param.getClientType())).collect(Collectors.toList());
            }
            list = list.stream().skip((long) (param.getPageIndex() - 1) * param.getPageSize()).limit(param.getPageSize()).collect(Collectors.toList());
            page.setRecords(list);
        }
        return page;
    }


    @Override
    public SysClientDetail selectClientDetail(String clientCode) {
        return sysClientMapper.selectClientDetail(clientCode);
    }


    @Override
    @Transactional
    public String updateClientDetail(SysClientDetail detail) {
        // 查询三表
        LambdaQueryWrapper<SysClient> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysClient::getClientCode, detail.getClientCode());
        SysClient client = this.baseMapper.selectOne(wrapper);
        if (client == null) {
            return "不存在供应商编码为【" + detail.getClientCode() + "】的数据,请维护！";
        }
        SysCompany company = sysCompanyService.selectByCompanyUID(client.getBindCompany());
        SysCompanyDetail companyDetail = sysCompanyDetailService.selectDetailByCompanyUID(client.getBindCompany());
        if (company == null || companyDetail == null) {
            return "不存在公司UID为【" + client.getBindCompany() + "】的数据,请维护！";
        }

        // 修改供应商数据
        client.setRegisterName(detail.getCompanyName());
        client.setLinkman(detail.getLinkman());
        client.setEmail(detail.getEmail());
        client.setPhone(detail.getPhone());
        client.setUpdateTime(detail.getUpdateTime());
        client.setUpdateUserName(detail.getUpdateUserName());
        this.baseMapper.updateById(client);
        // 修改公司数据
        company.setCompanyName(detail.getCompanyName());
        company.setCompanyType(detail.getCompanyType());
        company.setCompanyBusiness(detail.getCompanyBusiness());
        company.setStatus(detail.getStatus());
        company.setEmail(detail.getEmail());
        company.setPhone(detail.getPhone());
        company.setAddress(detail.getAddress());
        company.setClientType(detail.getClientType());
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
    public String addClientData(SysClient client) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        if (client.getRegisterName() == null || client.getRegisterName().equals("")) {
            return "注册名称不能为空！";
        }

        // 添加客户编码
        String clientCode = redisService.createNo("C");
        client.setClientCode(clientCode);
        // 添加绑定公司UID
        String companyUid = redisService.createNo("CU");
        client.setBindCompany(companyUid);

        if (client.getIsCooperation() == null) client.setIsCooperation(true);
        client.setCreateUserName(user.getLoginId());
        client.setCreateTime(new Date());
        this.baseMapper.insert(client);

        // 新增公司数据
        String errMsg = sysCompanyService.addCompany(client);
        if (errMsg != null) return errMsg;
        return null;
    }


    @Override
    @Transactional
    public String deleteClientData(Integer id) {
        SysClient client = this.baseMapper.selectById(id);
        if (client == null) {
            return "找不到该客户数据，请维护！";
        }

        // 删除公司数据
        SysCompany company = sysCompanyService.selectByCompanyUID(client.getBindCompany());
        SysCompanyDetail companyDetail = sysCompanyDetailService.selectDetailByCompanyUID(client.getBindCompany());
        if (company == null || companyDetail == null) {
            return "不存在公司UID为【" + client.getBindCompany() + "】的数据,请维护！";
        }
        this.baseMapper.deleteById(id);
        sysCompanyService.removeById(company.getId());
        sysCompanyDetailService.removeById(companyDetail.getId());
        return null;
    }


    @Override
    public String updateIsCooperation(String clientCode) {
        LambdaQueryWrapper<SysClient> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysClient::getClientCode, clientCode);
        SysClient sysClient = this.baseMapper.selectOne(wrapper);
        if (sysClient == null) {
            return "客户编码【" + clientCode + "】不存在!";
        }
        sysClient.setIsCooperation(!sysClient.getIsCooperation());
        this.baseMapper.updateById(sysClient);
        return null;
    }


    @Override
    public String updateIsValidated(String clientCode) {
        LambdaQueryWrapper<SysClient> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysClient::getClientCode, clientCode);
        SysClient sysClient = this.baseMapper.selectOne(wrapper);
        if (sysClient == null) {
            return "客户编码【" + clientCode + "】不存在!";
        }
        if (sysClient.getBindCompany() != null) {
            String companyUid = sysClient.getBindCompany();
            SysCompany company = sysCompanyService.selectByCompanyUID(companyUid);
            if (company == null) {
                return "客户编码【" + clientCode + "】绑定的企业编码【" + companyUid + "】不存在!";
            }
            company.setIsValidated(!company.getIsValidated());
            sysCompanyService.updateById(company);
        } else {
            return "客户编码【" + clientCode + "】不存在绑定的企业编码!";
        }
        return null;
    }

    @Override
    public List<SysCVListVo> getClientList(String key) {
        List<SysCVListVo> cvList = new ArrayList<>();

        LambdaQueryWrapper<SysClient> wrapper = new LambdaQueryWrapper<>();
        if (null != key && !key.equals("")) {
            wrapper.like(SysClient::getClientCode, key).or().like(SysClient::getRegisterName, key);
        }
        List<SysClient> list = this.baseMapper.selectList(wrapper);
        for (SysClient client : list) {
            SysCVListVo vo = new SysCVListVo();
            vo.setId(client.getId());
            vo.setName(client.getRegisterName());
            vo.setCode(client.getClientCode());
            cvList.add(vo);
        }
        return cvList;
    }


    @Override
    public SysClient getByClientCode(String clientCode) {
        LambdaQueryWrapper<SysClient> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysClient::getClientCode, clientCode);
        return this.baseMapper.selectOne(wrapper);
    }
}
