package com.system.systembase.impl.apiTest.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.systembase.api.basicConfiguration.converter.BaseDictConvert;
import com.system.systembase.api.apiTest.domain.User;
import com.system.systembase.api.apiTest.param.UserParam;
import com.system.systembase.api.apiTest.service.IUserService;
import com.system.systembase.api.apiTest.vo.TextDict;
import com.system.systembase.impl.apiTest.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



/**
 * @author lutong
 * @data 2024-11-27 027 15:06
 */

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User selectById(UserParam param) {
        User user1 = this.baseMapper.selectByIdInXML(param);
        User user2 = this.baseMapper.selectByIdInProvider(param);
        User user3 = this.baseMapper.selectByIDInSQL(param);
        User user4 = this.getById(param.getId());
        log.info("user1：{}",JSON.toJSONString(user1));
        log.info("user2：{}",JSON.toJSONString(user2));
        log.info("user3：{}",JSON.toJSONString(user3));
        log.info("user4：{}",JSON.toJSONString(user4));
        return user4;
    }


    @Override
    public TextDict testDict() {
        TextDict test = new TextDict();
        BaseDictConvert.convertDict(test);
        log.info("test: {}", JSON.toJSONString(test));
        return test;
    }

    public static void main(String[] args) {
        TextDict test = new TextDict();
        BaseDictConvert.convertDict(test);
        log.info("test: {}", JSON.toJSONString(test));
    }
}
