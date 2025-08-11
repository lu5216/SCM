package com.system.systembase.impl.basicConfiguration.provider;

import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.basicConfiguration.param.SysClientParam;
import com.system.systembase.api.basicConfiguration.vo.SysClientDetail;
import com.system.systembase.api.basicConfiguration.vo.SysVendorDetail;
import org.apache.ibatis.annotations.Param;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lutong
 * @data 2024-12-24 024 14:38
 */
public class SysCompanyProvider {

    public String selectVendorDetail(@Param("vendorCode") String vendorCode) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT v.*, c.*, cd.* ");
        sql.append(" FROM scm_sys_vendor v ");
        sql.append(" LEFT JOIN scm_sys_company c ON c.COMPANY_UID = v.BIND_COMPANY ");
        sql.append(" LEFT JOIN scm_sys_company_detail cd ON cd.COMPANY_UID = c.COMPANY_UID ");
        sql.append(" where c.IS_VALIDATED = 1 ");
        if (null != vendorCode && !"".equals(vendorCode)) {
            sql.append(" AND v.VENDOR_CODE = '").append(vendorCode).append("' ");
        }
        return sql.toString();
    }

    public String selectClientDetail(@Param("clientCode") String clientCode) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT sc.*, c.*, cd.* ");
        sql.append(" FROM scm_sys_client sc ");
        sql.append(" LEFT JOIN scm_sys_company c ON c.COMPANY_UID = sc.BIND_COMPANY ");
        sql.append(" LEFT JOIN scm_sys_company_detail cd ON cd.COMPANY_UID = c.COMPANY_UID ");
        sql.append(" where c.IS_CLIENT = 1 ");
        if (null != clientCode && !"".equals(clientCode)) {
            sql.append(" AND sc.CLIENT_CODE = '").append(clientCode).append("' ");
        }
        return sql.toString();
    }
}
