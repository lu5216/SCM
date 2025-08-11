package com.system.systembase.api.basicConfiguration.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.system.systembase.api.basicConfiguration.domain.SysCompanyDetail;

/**
 * @author lutong
 * @data 2024-12-16 016 14:55
 */

public interface ISysCompanyDetailService extends IService<SysCompanyDetail> {


    /**
     * 添加公司明细
     * @author lutong
     * @data 2025-3-17 017
     * @param companyUid
     * @return java.lang.String
     **/
    String addCompanyDetail(String companyUid);

    /**
     *  根据CompanyUid查询企业明细
     * @param companyUid
     * @return SysCompanyDetail
     */
    SysCompanyDetail selectDetailByCompanyUID(String companyUid);

}
