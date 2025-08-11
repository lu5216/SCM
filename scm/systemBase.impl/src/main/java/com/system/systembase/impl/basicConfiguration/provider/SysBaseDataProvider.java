package com.system.systembase.impl.basicConfiguration.provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;

/**
 * @author lutong
 * @data 2024-12-11 011 14:42
 */
public class SysBaseDataProvider {
    public String selectParentDict(@Param("name") String name, @Param("companyUid") String companyUid) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select bd.ID, bd.NAME, bd.CODE, bd.REMARK, bd.MIN_VALUE, bd.MAX_VALUE, bd.COMPANY_UID, ");
        sql.append("        bd2.NAME as PARENT_NAME, bd2.CODE as PARENT_CODE, bd2.REMARK as PARENT_REMARK ");
        sql.append(" from scm_sys_base_data bd ");
        sql.append("     LEFT JOIN scm_sys_base_data bd2 on bd.PARENT_CODE = bd2.CODE ");
        sql.append("     LEFT JOIN scm_sys_base_data bd3 on bd2.PARENT_CODE = bd3.CODE ");
        sql.append(" WHERE bd.COMPANY_UID = '").append(companyUid).append("' ");
        sql.append(" AND bd.PARENT_CODE is not null ");
        sql.append(" AND bd2.CODE = 'DICT' ");
        if (StringUtils.isNotBlank(name)) {
            sql.append(" AND bd.NAME like '%").append(name).append("%' ");
        }
        return sql.toString();
    }

    public String selectDict(@Param("name") String name, @Param("parentCode") String parentCode, @Param("companyUid") String companyUid) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select bd.ID, bd.NAME, bd.CODE, bd.REMARK, bd.MIN_VALUE, bd.MAX_VALUE, bd.COMPANY_UID, ");
        sql.append("        bd2.NAME as PARENT_NAME, bd2.CODE as PARENT_CODE, bd2.REMARK as PARENT_REMARK ");
        sql.append(" from scm_sys_base_data bd ");
        sql.append("     LEFT JOIN scm_sys_base_data bd2 on bd.PARENT_CODE = bd2.CODE ");
        sql.append("     LEFT JOIN scm_sys_base_data bd3 on bd2.PARENT_CODE = bd3.CODE ");
        sql.append(" WHERE bd.COMPANY_UID = '").append(companyUid).append("' ");
        sql.append(" AND bd.PARENT_CODE is not null ");
        sql.append(" AND bd2.CODE = '").append(parentCode).append("' ");
        if (StringUtils.isNotBlank(name)) {
            sql.append(" AND bd.NAME like '%").append(name).append("%' ");
        }
        return sql.toString();
    }
}
