package com.system.systembase.impl.basicConfiguration.provider;

import org.apache.ibatis.annotations.Param;

import java.util.Objects;

/**
 * @author lutong
 * @data 2025-1-8 008 17:59
 */
public class SysCurrentProvider {
    public String getPageCount(@Param("key") String key, @Param("companyUid") String companyUid) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select COUNT(ID) ");
        sql.append(" from scm_sys_current ");
        sql.append(" WHERE COMPANY_UID = '").append(companyUid).append("' ");
        if (key != null && !key.equals("")) {
            sql.append(" and (CURRENT_CODE like '%").append(key).append("%' or CURRENT_NAME like '%").append(key).append("%') ");
        }
        return sql.toString();
    }

    public String getPageList(@Param("key") String key, @Param("companyUid") String companyUid,
                             @Param("pageIndex") Integer pageIndex, @Param("pageSize") Integer pageSize) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select * ");
        sql.append(" from scm_sys_current ");
        sql.append(" WHERE COMPANY_UID = '").append(companyUid).append("' ");
        if (key != null && !key.equals("")) {
            sql.append(" and (CURRENT_CODE like '%").append(key).append("%' or CURRENT_NAME like '%").append(key).append("%') ");
        }
        sql.append(" limit ").append((pageIndex - 1) * pageSize).append(",").append(pageSize);
        return sql.toString();
    }

    public String selectCurrentList(@Param("key") String key, @Param("companyUid") String companyUid) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select * ");
        sql.append(" from scm_sys_current ");
        sql.append(" WHERE COMPANY_UID = '").append(companyUid).append("' ");
        if (key != null && Objects.equals(key, "")) {
            sql.append(" and (CURRENT_CODE like '%").append(key).append("%' or CURRENT_NAME like '%").append(key).append("%') ");
        }
        return sql.toString();
    }
}
