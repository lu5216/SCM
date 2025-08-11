package com.system.entrance;

import com.alibaba.fastjson.JSON;
import com.system.common.utils.JwtUtil;
import com.system.common.vo.MinIOProperties;
import com.system.common.vo.WxMpProperties;
import com.system.systembase.impl.permissionsManagement.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@SpringBootTest
class EntranceApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private WxMpProperties wxMpProperties;

    @Autowired
    private MinIOProperties minIOProperties;

    @Test
    public void nacosTest() {
        log.info("WX的配置：{}", JSON.toJSONString(wxMpProperties));
        log.info("minIO的配置：{}", JSON.toJSONString(minIOProperties));
    }

    @Test
    public void loginTest() {
//        String username = "test";
//        SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
//        log.info("username={}, password={}", sysUser.getUsername(), sysUser.getPassword());
//        boolean b1 = passwordEncoder.matches("123456", sysUser.getPassword());
//        log.info("b1 = {}", b1);
    }

    @Test
    public void pswTest() {
//        String encode = passwordEncoder.encode("123456");
//        log.info("encode = {}", encode);
    }

    @Test
    public void matchesTest() {
//        String s1 = "/scm-demo/email/sendEmail";
//        String s2 = "/scm-demo/email/.*";
//        if (s1.matches(s2)) {
//            log.info("true");
//        }
    }


    @Test
    public void jwtTest() {
//        // 生成JWT
//        String jwt1 = JwtUtil.createJWT("10086","test", null);
//        log.info("生成JWT：{}",jwt1);
//
//        // 解析JWT
//        String jwt2 = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMDA4NiIsInN1YiI6Imxpc2hpIiwiaXNzIjoiIiwiaWF0IjoxNzMyODQ5NjQ2LCJleHAiOjE3MzI4NTMyNDZ9.ETyzvXHHA7VuAKGOvMx42xJGJt0BTr-nys-WdNqa-U8";
//        Claims claims1 = JwtUtil.parseJWT(jwt1);
//        Claims claims2 = JwtUtil.parseJWT(jwt2);
//        log.info("解析JWT1:id = {}, subject = {}", claims1.getId(), claims1.getSubject());
//        log.info("解析JWT2:id = {}, subject = {}", claims2.getId(), claims2.getSubject());
    }

    @Test
    public void PasswordEncoderTest() {
        // 盐，随机的盐
//        String encode1 = passwordEncoder.encode("123456");
//        String encode2 = passwordEncoder.encode("123456");
//        log.info("encode1 = {}, encode2 = {}", encode1, encode2);
//        boolean b1 = passwordEncoder.matches("123456", "$2a$10$tUdyJjBNvUZ3PXlxykl8FOza04z/uN8fc7jG2eDg1WNmgso0xQ6H2");
//        boolean b2 = passwordEncoder.matches("123456", "$2a$10$p4XEuQ2UPrTgp3WRGmjV2u9Quentdm6IB2K8GB9hLgp8XtK9qtze");
//        log.info("b1 = {}, b2 = {}", b1, b2);
    }
}
