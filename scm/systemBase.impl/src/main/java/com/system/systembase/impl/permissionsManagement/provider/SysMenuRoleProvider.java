package com.system.systembase.impl.permissionsManagement.provider;

import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.permissionsManagement.param.EditMenuRoleParam;
import com.system.systembase.api.permissionsManagement.param.SysMenuRoleParam;
import org.apache.ibatis.annotations.Param;

import java.util.Objects;

/**
 * @author lutong
 * @data 2024-12-10 010 10:31
 */
public class SysMenuRoleProvider {
    public String editRoleMenu(@Param("param") EditMenuRoleParam param) {
        SysUserVo user = SecurityUtils.getUser();
        StringBuffer sql = new StringBuffer();
        sql.append(" INSERT INTO scm_sys_menu_role (role_id, menu_id, CREATE_USER_NAME) ");
        sql.append(" VALUES ");
        for (String menuId : param.getMenuIDList()) {
            sql.append(" ( ");
            sql.append(param.getRoleId()).append(", ");
            sql.append(menuId).append(", ");
            sql.append("'").append(user.getLoginId()).append("' ");
            sql.append(" ),");
        }
        sql.deleteCharAt(sql.length() - 1);
        return sql.toString();
    }

    public String initSQL() {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT mr.*, m.*, r.ROLE, r.ROLE_UID, r.ROLE_REMARK ");
        sql.append(" FROM scm_sys_menu_role mr ");
        sql.append("          LEFT JOIN scm_sys_menu m on m.ID = mr.MENU_ID ");
        sql.append("          LEFT JOIN scm_sys_role r on r.ID = mr.ROLE_ID ");
        sql.append(" WHERE 1=1 ");
        return sql.toString();
    }

    public String inquireSQL(@Param("param") SysMenuRoleParam param) {
        StringBuffer sql = new StringBuffer();
        if (Objects.equals(param.getMenuName(), "") || param.getMenuName() != null) {
            sql.append(" AND m.MENU_NAME = '").append(param.getMenuName()).append("' ");
        }
        if (Objects.equals(param.getMenuCode(), "") || param.getMenuCode() != null) {
            sql.append(" AND m.MENU_CODE = '").append(param.getMenuCode()).append("' ");
        }
        if (Objects.equals(param.getParentMenuCode(), "") || param.getParentMenuCode() != null) {
            sql.append(" AND m.MENU_CODE = '").append(param.getParentMenuCode()).append("' ");
        }
        if (Objects.equals(param.getMenuRemark(), "") || param.getMenuRemark() != null) {
            sql.append(" AND m.REMARK = '").append(param.getMenuRemark()).append("' ");
        }
        if (param.getIsUse() != null) {
            if (param.getIsUse()) {
                sql.append(" AND m.IS_USE = ").append(param.getIsUse()).append(" ");
            } else {
                sql.append(" AND m.IS_USE = ").append(true).append(" ");
            }
        }
        if (param.getIsShow() != null) {
            if (param.getIsShow()) {
                sql.append(" AND m.IS_SHOW = ").append(param.getIsShow()).append(" ");
            } else {
                sql.append(" AND m.IS_SHOW = ").append(true).append(" ");
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

    public String selectMenuRoleList(@Param("param") SysMenuRoleParam param) {
        StringBuffer sql = new StringBuffer();
        sql.append(this.initSQL());
        sql.append(this.inquireSQL(param));
        sql.append(" ORDER BY m.ORDER_NO ASC ");
        return sql.toString();
    }

    public String selectMenuRoleByMenuCode(@Param("menuCode") String menuCode) {
        StringBuffer sql = new StringBuffer();
        sql.append(this.initSQL());
        sql.append(" AND m.MENU_CODE = '").append(menuCode).append("' ");
        return sql.toString();
    }

    public String selectMenuRoleByRoleUid(@Param("roleUid") String roleUid) {
        StringBuffer sql = new StringBuffer();
        sql.append(this.initSQL());
        sql.append(" AND r.ROLE_UID = '").append(roleUid).append("' ");
        return sql.toString();
    }

    public String selectMenuRoleByMenuCodeAndRoleUid(@Param("menuCode") String menuCode,
                                                     @Param("roleUid") String roleUid) {
        StringBuffer sql = new StringBuffer();
        sql.append(this.initSQL());
        sql.append(" AND m.MENU_CODE = '").append(menuCode).append("' ");
        sql.append(" AND r.ROLE_UID = '").append(roleUid).append("' ");
        return sql.toString();
    }
}
