package com.system.entrance.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;


/**
 *  线程池
 * @author lutong
 * @name AsyncConfig
 * @data 2025-03-14
 */

@Configuration
@EnableAsync
@Slf4j
public class AsyncConfig {

    @Bean(name = "MyThreadPool")
    public ThreadPoolTaskExecutor asyncExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(30);
        // 队列大小
        executor.setQueueCapacity(500);
        // 最大线程数
        executor.setMaxPoolSize(50);
        // 线程池维护线程所允许的空闲时间
        executor.setKeepAliveSeconds(60);
        // 线程前缀
        executor.setThreadNamePrefix("MyThreadPool-");
        //用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 配置拒绝策略
        executor.setRejectedExecutionHandler((Runnable r, ThreadPoolExecutor exe) -> {
            log.warn("MyThreadPool-当前任务线程池队列已满!");
        });
        executor.initialize();
        //初始化线程池。
        executor.initialize();
        return executor;
    }
}
