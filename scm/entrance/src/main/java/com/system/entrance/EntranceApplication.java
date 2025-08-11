package com.system.entrance;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.system.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;

@Slf4j
@SpringBootApplication(scanBasePackages = "com.system.**", exclude = { DruidDataSourceAutoConfigure.class })
@ComponentScan(value = "com.system.**")
@MapperScan(basePackages = {"com.system.**.mapper", "com.system.**.domain"})
@EnableAsync
@EnableScheduling
@EnableDiscoveryClient
public class EntranceApplication {

    public static void main(String[] args) {
        try {
            long ttl = System.currentTimeMillis();
            ApplicationContext applicationContext = SpringApplication.run(EntranceApplication.class, args);
            String[] beanNames =  applicationContext.getBeanDefinitionNames();
            log.info(DateUtil.getDateForFormat(new Date(), DateUtil.YMD_HMS) + ": SCM项目启动成功!");
            log.info("加载bean的个数：" + beanNames.length);
            log.info("SCM项目启动时间：" + (System.currentTimeMillis() - ttl) + "ms");
        } catch (Exception e) {
            log.error("scm项目启动失败,失败原因：", e);
        }

    }

}
