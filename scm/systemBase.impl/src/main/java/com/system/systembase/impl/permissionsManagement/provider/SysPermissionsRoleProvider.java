package com.system.systembase.impl.permissionsManagement.provider;

import org.apache.ibatis.annotations.Param;

/**
 * @author lutong
 * @data 2024-12-11 011 10:48
 */
public class SysPermissionsRoleProvider {

    public String getDataPermissionsRoles(@Param("roleUid") String roleUid, @Param("menuCode") String menuCode) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT pr.ID, pr.ROLE_ID, r.ROLE, r.ROLE_UID, pr.DATA_ID, dp.MENU_CODE, dp.FIELD_CODE, dp.FIELD_NAME, dp.REMARK, pr.IS_CHECKED ");
        sql.append(" FROM scm_sys_permissions_role pr ");
        sql.append("       LEFT JOIN scm_sys_role r on r.ID = pr.ROLE_ID ");
        sql.append("       LEFT JOIN scm_sys_data_permissions dp on dp.ID = pr.DATA_ID ");
        sql.append(" WHERE 1=1 ");
        sql.append(" AND pr.DATA_ID is not null ");
        sql.append(" AND r.ROLE_UID = '").append(roleUid).append("' ");
        sql.append(" AND dp.MENU_CODE = '").append(menuCode).append("' ");
        return sql.toString();
    }

    public String getFunctionPermissionsRoles(@Param("roleUid") String roleUid, @Param("menuCode") String menuCode) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT pr.ID, pr.ROLE_ID, r.ROLE, r.ROLE_UID, pr.FUNCTION_ID, fp.MENU_CODE, fp.NAME, fp.CODE, fp.REMARK, pr.IS_CHECKED ");
        sql.append(" FROM scm_sys_permissions_role pr ");
        sql.append("       LEFT JOIN scm_sys_role r on r.ID = pr.ROLE_ID ");
        sql.append("       LEFT JOIN scm_sys_function_permissions fp on fp.ID = pr.FUNCTION_ID ");
        sql.append(" WHERE 1=1 ");
        sql.append(" AND pr.ROLE_ID is not null ");
        sql.append(" AND r.ROLE_UID = '").append(roleUid).append("' ");
        sql.append(" AND fp.MENU_CODE = '").append(menuCode).append("' ");
        return sql.toString();
    }
}
