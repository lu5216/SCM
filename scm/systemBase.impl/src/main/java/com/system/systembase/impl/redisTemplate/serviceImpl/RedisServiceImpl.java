package com.system.systembase.impl.redisTemplate.serviceImpl;

import com.system.common.utils.DateUtil;
import com.system.systembase.api.redisTemplate.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lutong
 * @data 2025-1-22 022 16:39
 */

@Slf4j
@Service
public class RedisServiceImpl implements IRedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String getValueByKey(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public List<String> getValuesList(Collection<String> keys) {
        List<String> list = stringRedisTemplate.opsForValue().multiGet(keys);
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public void addRedis(String key, String value, long expiresTime, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, expiresTime, unit);
    }

    @Override
    public void addRedis(String key, String value, long expiresTime) {
        stringRedisTemplate.opsForValue().set(key, value, expiresTime, TimeUnit.SECONDS);
    }

    @Override
    public void addRedis(String key, String value) {
        long expiresTime = DateUtil.getSecondsNextEarlyMorning();
        stringRedisTemplate.opsForValue().set(key, value, expiresTime, TimeUnit.SECONDS);
    }

    @Override
    public void setRedis(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void coverBatchAdd(Map<? extends String, ? extends String> map) {
        stringRedisTemplate.opsForValue().multiSet(map);
    }

    @Override
    public Boolean batchAdd(Map<? extends String, ? extends String> map) {
        return stringRedisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    @Override
    public void updateRedis(String oldKey, String newValue, long expiresTime, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(oldKey, newValue, expiresTime, unit);
    }

    @Override
    public void updateRedis(String oldKey, String newValue, long expiresTime) {
        stringRedisTemplate.opsForValue().set(oldKey, newValue, expiresTime, TimeUnit.SECONDS);
    }

    @Override
    public void updateRedis(String oldKey, String newValue) {
        long expiresTime = DateUtil.getSecondsNextEarlyMorning();
        stringRedisTemplate.opsForValue().set(oldKey, newValue, expiresTime, TimeUnit.SECONDS);
    }

    @Override
    public void deleteRedis(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public Long batchDeleteRedis(Collection<String> keys) {
        return stringRedisTemplate.opsForValue().getOperations().delete(keys);
    }

    @Override
    public Integer appendRedis(String key, String appendValue) {
        return stringRedisTemplate.opsForValue().append(key, appendValue);
    }


    @Override
    public long getExpire(String key) {
        Boolean hasKey = stringRedisTemplate.hasKey(key);
        if (Boolean.TRUE.equals(hasKey)) {
            return stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);

        }
        // 获取键的剩余生存时间，单位为秒
        return -2;
    }

    @Override
    public String createNo(String character) {
        StringBuilder sb = new StringBuilder();
        String dateStr = DateUtil.getDateForFormat(new Date(), "yyyyMMdd");
        sb.append(character).append(dateStr);
        // 设置key
        String key = sb.toString();

        // 校验是否存在
        String VerifyValue = this.getValueByKey(key);
        if (VerifyValue == null || VerifyValue.equals("")) {
            // 新增
            this.addRedis(key, "1");
            return key + "001";
        }

        // 递增单号
        String value = String.format("%03d", Integer.parseInt(VerifyValue)+1);
        if (Integer.parseInt(value) > 999) {
            log.error("生成单号失败，序号大于999, key：" + key);
            throw new RuntimeException("生成单号失败，序号大于999!");
        }

        // 更新
        this.updateRedis(key, value);
        return key + value;
    }
}
