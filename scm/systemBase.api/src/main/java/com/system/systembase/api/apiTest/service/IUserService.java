package com.system.systembase.api.apiTest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.system.systembase.api.apiTest.domain.User;
import com.system.systembase.api.apiTest.param.UserParam;
import com.system.systembase.api.apiTest.vo.TextDict;

/**
 * @author lutong
 * @data 2024-11-27 027 15:04
 */
public interface IUserService extends IService<User> {

    /**
     * 根据Id查询
     * @param param
     */
    User selectById(UserParam param);


    /**
     * 测试数据字典注解
     * @return
     */
    TextDict testDict();
}
