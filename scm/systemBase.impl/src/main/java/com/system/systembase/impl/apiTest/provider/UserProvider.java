package com.system.systembase.impl.apiTest.provider;

import com.system.systembase.api.apiTest.param.UserParam;
import org.apache.ibatis.annotations.Param;

import java.text.SimpleDateFormat;

/**
 * @author lutong
 * @data 2024-11-27 027 15:05
 */
public class UserProvider {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String selectByIdInProvider(@Param("param") UserParam param) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select * ");
        sql.append(" from scm_sys_user ");
        sql.append(" where id = ").append(param.getId()).append(" limit 1 ");
        return sql.toString();
    }

}
