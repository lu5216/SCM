package com.system.systembase.api.redisTemplate.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lutong
 * @data 2025-1-22 022 16:39
 */

public interface IRedisService {

    /**
     *  查询key对应的value，不存在返回null
     * @param key
     * @return value
     */
    String getValueByKey(String key);

    /**
     *  批量获取，key不存在返回null
     * @param keys
     * @return
     */
    List<String> getValuesList(Collection<String> keys);

    /**
     *  新增一条redis数据
     * @param key
     * @param value
     * @param expiresTime
     * @param unit
     */
    void addRedis(String key, String value, long expiresTime, TimeUnit unit);

    /**
     *  新增一条redis数据
     * @param key
     * @param value
     * @param expiresTime
     */
    void addRedis(String key, String value, long expiresTime);

    /**
     *  新增一条redis数据,过期时间默认为明天0点
     * @param key
     * @param value
     */
    void addRedis(String key, String value);

    /**
     *  新增一条redis数据,永久存在
     * @param key
     * @param value
     */
    void setRedis(String key, String value);

    /**
     * 批量插入，key值存在会覆盖原值
     * @param map
     */
    void coverBatchAdd(Map<? extends String, ? extends String> map);

    /**
     * 批量插入，如果里面的所有key都不存在，则全部插入，返回true，如果其中一个在redis中已存在，全不插入，返回false
     * @param map
     * @return
     */
    Boolean batchAdd(Map<? extends String, ? extends String> map);

    /**
     *  修改redis数据
     * @param oldKey
     * @param newValue
     * @param expiresTime
     * @param unit
     */
    void updateRedis(String oldKey, String newValue, long expiresTime, TimeUnit unit);

    /**
     *  修改redis数据
     * @param oldKey
     * @param newValue
     * @param expiresTime
     */
    void updateRedis(String oldKey, String newValue, long expiresTime);

    /**
     *  修改redis数据,过期时间默认为明天0点
     * @param oldKey
     * @param newValue
     */
    void updateRedis(String oldKey, String newValue);

    /**
     * 删除redis数据
     * @param key
     */
    void deleteRedis(String key);

    /**
     * 删除多个key，返回删除key的个数
     * @param keys
     * @return count
     */
    Long batchDeleteRedis(Collection<String> keys);

    /**
     * 在原有的值基础上新增字符串到末尾
     * @param key
     * @param appendValue
     * @return count
     */
    Integer appendRedis(String key, String appendValue);


    /**
     * 从redis中获取key对应的过期时间;
     * 如果该值有过期时间，就返回相应的过期时间;
     * 如果该值没有设置过期时间，就返回-1;
     * 如果没有该值，就返回-2;
     * @param key
     * @return long
     */
    long getExpire(String key);


        /**
         *  按单号开头字母和天为维度生成递增的单号
         * @param character
         * @return orderNo
         */
    String createNo(String character);
}
