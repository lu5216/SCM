package com.system.systembase.api.basicConfiguration.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.system.common.vo.Result;
import com.system.systembase.api.basicConfiguration.domain.SysClient;
import com.system.systembase.api.basicConfiguration.domain.SysCompany;
import com.system.systembase.api.basicConfiguration.domain.SysVendor;
import com.system.systembase.api.basicConfiguration.param.SysCompanyParam;

import java.util.List;

/**
 * @author lutong
 * @data 2024-12-13 013 14:03
 */

public interface ISysCompanyService extends IService<SysCompany> {

    /**
     *  查询企业列表
     * @param param
     * @return
     */
    List<SysCompany> selectCompany(SysCompanyParam param);

    /**
     *  启用-停用企业
     * @param id
     * @return
     */
    Result<String> isValidated(Integer id);

    /**
     *  新增公司数据-从客户新增
     * @author lutong
     * @data 2025-3-17 017
     * @param client
     * @return java.lang.String
     **/
    String addCompany(SysClient client);

    /**
     * 新增公司数据-从供应商新增
     * @author lutong
     * @data 2025-3-17 017
     * @param vendor
     * @return java.lang.String
     **/
    String addCompany(SysVendor vendor);

    /**
     *  根据companyUid查询企业信息
     * @param companyUid
     * @return
     */
    SysCompany selectByCompanyUID(String companyUid);
}
