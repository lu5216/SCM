package com.system.systembase.impl.apiTest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.systembase.api.apiTest.domain.User;
import com.system.systembase.api.apiTest.param.UserParam;
import com.system.systembase.impl.apiTest.provider.UserProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;


/**
 * @author lutong
 * @data 2024-11-27 027 15:05
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据Id查询
     * @param param
     */
    User selectByIdInXML(@Param("param") UserParam param);

    /**
     * 根据Id查询
     * @param param
     */
    @SelectProvider(type = UserProvider.class, method = "selectByIdInProvider")
    User selectByIdInProvider(@Param("param") UserParam param);

    /**
     * 根据Id查询
     * @param param
     */
    @Select("select * from scm_sys_user where id = #{param.id} ")
    User selectByIDInSQL(@Param("param") UserParam param);
}
