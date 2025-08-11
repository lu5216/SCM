package com.system.systembase.impl.permissionsManagement.provider;

import com.system.systembase.api.permissionsManagement.param.SysUserRoleParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;

import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * @author lutong
 * @data 2024-12-6 006 14:22
 */

@Slf4j
public class SysUserRoleProvider {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String selectUserRoleCount(@Param("param") SysUserRoleParam param) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT COUNT(ur.ID) ");
        sql.append("FROM scm_sys_user_role ur ");
        sql.append("     LEFT JOIN scm_sys_user u ON u.ID = ur.USER_ID ");
        sql.append("     LEFT JOIN scm_sys_role r ON r.ID = ur.ROLE_ID ");
        sql.append(" WHERE 1=1 ");
        sql.append(this.initSQL(param));
        return sql.toString();
    }

    public String selectUserRoleList(@Param("param") SysUserRoleParam param) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ur.*, u.LOGIN_ID, u.USERNAME, u.IS_DEL, r.ROLE, r.ROLE_UID, r.ROLE_REMARK ");
        sql.append("FROM scm_sys_user_role ur ");
        sql.append("     LEFT JOIN scm_sys_user u ON u.ID = ur.USER_ID ");
        sql.append("     LEFT JOIN scm_sys_role r ON r.ID = ur.ROLE_ID ");
        sql.append(" WHERE 1=1 ");
        sql.append(this.initSQL(param));
        if (null != param.getPageIndex() && param.getPageSize() != null) {
            sql.append(" limit ").append((param.getPageIndex() - 1) * param.getPageSize()).append(",").append(param.getPageSize());
        }
        return sql.toString();
    }

    public String initSQL(@Param("param") SysUserRoleParam param) {
        StringBuffer sql = new StringBuffer();
        if (Objects.equals(param.getLoginId(), "") || param.getLoginId() != null) {
            sql.append(" AND u.LOGIN_ID = '").append(param.getLoginId()).append("' ");
        }
        if (Objects.equals(param.getUsername(), "") || param.getUsername() != null) {
            sql.append(" AND u.USERNAME = '").append(param.getUsername()).append("' ");
        }
        if (param.getIsDel() != null) {
            if (param.getIsDel()) {
                sql.append(" AND u.IS_DEL = ").append(param.getIsDel()).append(" ");
            } else {
                sql.append(" AND u.IS_DEL = ").append(false).append(" ");
            }
        }
        if (Objects.equals(param.getRole(), "") || param.getRole() != null) {
            sql.append(" AND r.ROLE = '").append(param.getRole()).append("' ");
        }
        if (Objects.equals(param.getRoleUid(), "") || param.getRoleUid() != null) {
            sql.append(" AND r.ROLE_UID = '").append(param.getRoleUid()).append("' ");
        }
        if (Objects.equals(param.getRoleRemark(), "") || param.getRoleRemark() != null) {
            sql.append(" AND r.ROLE_REMARK = '").append(param.getRoleRemark()).append("' ");
        }
        return sql.toString();
    }


    public String selectUserRoleByLoginId(@Param("loginId") String loginId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ur.*, u.LOGIN_ID, u.USERNAME, u.IS_DEL, r.ROLE, r.ROLE_UID, r.ROLE_REMARK ");
        sql.append("FROM scm_sys_user_role ur ");
        sql.append("     LEFT JOIN scm_sys_user u ON u.ID = ur.USER_ID ");
        sql.append("     LEFT JOIN scm_sys_role r ON r.ID = ur.ROLE_ID ");
        sql.append(" WHERE 1=1 ");
        sql.append(" AND u.LOGIN_ID = '").append(loginId).append("' ");
        return sql.toString();
    }

    public String selectUserRoleByRoleUid(@Param("roleUid") String roleUid) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ur.*, u.LOGIN_ID, u.USERNAME, u.IS_DEL, r.ROLE, r.ROLE_UID, r.ROLE_REMARK ");
        sql.append("FROM scm_sys_user_role ur ");
        sql.append("     LEFT JOIN scm_sys_user u ON u.ID = ur.USER_ID ");
        sql.append("     LEFT JOIN scm_sys_role r ON r.ID = ur.ROLE_ID ");
        sql.append(" WHERE 1=1 ");
        sql.append(" AND r.ROLE_UID = '").append(roleUid).append("' ");
        return sql.toString();
    }

    public String selectUserRoleByLoginIdAndRoleUid(@Param("loginId") String loginId,
                                                    @Param("roleUid") String roleUid) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ur.*, u.LOGIN_ID, u.USERNAME, u.IS_DEL, r.ROLE, r.ROLE_UID, r.ROLE_REMARK ");
        sql.append("FROM scm_sys_user_role ur ");
        sql.append("     LEFT JOIN scm_sys_user u ON u.ID = ur.USER_ID ");
        sql.append("     LEFT JOIN scm_sys_role r ON r.ID = ur.ROLE_ID ");
        sql.append(" WHERE 1=1 ");
        sql.append(" AND u.LOGIN_ID = '").append(loginId).append("' ");
        sql.append(" AND r.ROLE_UID = '").append(roleUid).append("' ");
        return sql.toString();
    }
}
