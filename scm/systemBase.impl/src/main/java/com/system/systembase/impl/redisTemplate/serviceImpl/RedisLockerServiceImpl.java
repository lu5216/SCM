package com.system.systembase.impl.redisTemplate.serviceImpl;

import com.system.systembase.api.redisTemplate.service.IRedisLockerService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author lutong
 * @data 2025-3-5 005 9:38
 */

@Slf4j
@Service
public class RedisLockerServiceImpl implements IRedisLockerService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String LOCK_PREFIX = "Lock:";

    @Override
    public boolean lock(String lockKey, long timeout) {
        // 判断key是否为空
        if (StringUtil.isNullOrEmpty(lockKey)){
            return false;
        }
        // 过期时间默认为5秒
        if (timeout == 0){
            timeout = 5;
        }
        // 加锁
        Boolean isLocked = stringRedisTemplate.opsForValue().setIfAbsent(LOCK_PREFIX + lockKey, "locked", timeout, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(isLocked);
    }

    @Override
    public void unlock(String lockKey) {
        stringRedisTemplate.delete(LOCK_PREFIX + lockKey);
    }
}
