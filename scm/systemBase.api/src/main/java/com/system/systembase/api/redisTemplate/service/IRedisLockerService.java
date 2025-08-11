package com.system.systembase.api.redisTemplate.service;

/**
 * @author lutong
 * @data 2025-3-5 005 9:35
 */
public interface IRedisLockerService {

    /**
     *  加锁, timeout=0则默认5秒
     * @param lockKey
     * @param timeout
     * @return
     */
    boolean lock(String lockKey, long timeout);


    /**
     *  解锁
     * @param lockKey
     */
    void unlock(String lockKey);
}
