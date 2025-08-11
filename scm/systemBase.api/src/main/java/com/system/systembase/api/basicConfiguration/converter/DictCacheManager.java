package com.system.systembase.api.basicConfiguration.converter;

import com.system.common.vo.SysUserVo;
import com.system.systembase.api.basicConfiguration.service.ISysBaseDataService;
import com.system.systembase.api.basicConfiguration.vo.SysBaseDataVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 123
 * @name DictCacheManager
 * @data 2025-08-06
 */

@Slf4j
@Component
public class DictCacheManager {

    @Autowired
    private ISysBaseDataService baseDataService;

    // 使用ConcurrentHashMap保证线程安全
    private static final Map<String, Map<String, String>> DICT_CACHE = new ConcurrentHashMap<>();

    // 使用锁保证更新操作的原子性
    private final ReentrantLock updateLock = new ReentrantLock();

    // 定时刷新任务
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();


    /**
     * 应用启动时加载字典数据
     */
    public void initDictData(SysUserVo user) {
        log.info("user: " + user);
        loadDictData();
    }

    /**
     * 加载字典数据
     */
    public void loadDictData() {
        updateLock.lock();
        try {
            log.info("开始加载字典数据...");
            long startTime = System.currentTimeMillis();

            Map<String, Map<String, String>> newCache = new HashMap<>();

            // 获取所有父字典
            List<SysBaseDataVo> parentDictList = baseDataService.selectParentDict(null);
            if (parentDictList != null && !parentDictList.isEmpty()) {
                for (SysBaseDataVo parentVo : parentDictList) {
                    // 获取子字典项
                    List<SysBaseDataVo> dataVoList = baseDataService.selectDict(null, parentVo.getCode());
                    Map<String, String> dictMap = new HashMap<>();

                    if (dataVoList != null && !dataVoList.isEmpty()) {
                        for (SysBaseDataVo vo : dataVoList) {
                            dictMap.put(vo.getCode(), vo.getName());
                        }
                    }

                    newCache.put(parentVo.getCode(), Collections.unmodifiableMap(dictMap));
                    log.debug("加载字典: {} - {}项", parentVo.getName(), dictMap.size());
                }
            }

            // 原子更新缓存
            DICT_CACHE.clear();
            DICT_CACHE.putAll(newCache);

            long duration = System.currentTimeMillis() - startTime;
            log.info("字典数据加载完成, 共加载 {} 个字典, 耗时 {} ms",
                    newCache.size(), duration);
        } catch (Exception e) {
            log.error("加载字典数据失败", e);
        } finally {
            updateLock.unlock();
        }
    }


    /**
     * 手动刷新字典缓存
     */
    public void refreshDictCache() {
        new Thread(this::loadDictData).start();
    }

    /**
     * 获取字典映射
     * @param dictType 字典类型
     * @return 不可修改的字典映射
     */
    public static Map<String, String> getDictMap(String dictType) {
        Map<String, String> dictMap = DICT_CACHE.get(dictType);
        return dictMap != null ?
                Collections.unmodifiableMap(dictMap) :
                Collections.emptyMap();
    }

    /**
     * 根据字典类型和键获取值
     * @param dictType 字典类型
     * @param key 键
     * @return 值，如果不存在返回null
     */
    public static String getDictValue(String dictType, String key) {
        Map<String, String> dictMap = DICT_CACHE.get(dictType);
        return dictMap != null ? dictMap.get(key) : null;
    }

    /**
     * 根据字典类型和值获取键
     * @param dictType 字典类型
     * @param value 值
     * @return 键，如果不存在返回null
     */
    public static String getDictKey(String dictType, String value) {
        Map<String, String> dictMap = DICT_CACHE.get(dictType);
        if (dictMap == null) return null;

        for (Map.Entry<String, String> entry : dictMap.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * 关闭资源
     */
    public void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
