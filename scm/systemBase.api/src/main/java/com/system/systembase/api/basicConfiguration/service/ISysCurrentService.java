package com.system.systembase.api.basicConfiguration.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.systembase.api.basicConfiguration.domain.SysCurrent;
import com.system.systembase.api.basicConfiguration.vo.SysCurrentVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author lutong
 * @data 2025-1-8 008 9:47
 */


public interface ISysCurrentService extends IService<SysCurrent> {

    /**
     *  查询页面-分页查询
     * @param key
     * @return
     */
    Page<SysCurrent> selectPage(String key, Integer pageIndex, Integer pageSize);

    /**
     *  获取币别列表
     * @param key
     * @return
     */
    List<SysCurrentVo> getCurrentList(String key);

    /**
     *  编辑币别
     * @param currentCode
     * @param preExchangeRate
     * @param buyExchangeRate
     * @param sellExchangeRate
     * @param remark
     * @return
     */
    String editCurrent(String currentCode, BigDecimal preExchangeRate, BigDecimal buyExchangeRate, BigDecimal sellExchangeRate, String remark);

    /**
     *  增加币别
     * @param currentName
     * @param currentCode
     * @param preExchangeRate
     * @param buyExchangeRate
     * @param sellExchangeRate
     * @param remark
     * @return
     */
    String addCurrent(String currentName, String currentCode, BigDecimal preExchangeRate, BigDecimal buyExchangeRate, BigDecimal sellExchangeRate, String remark);

    /**
     *  删除币别
     * @param currentCode
     * @return
     */
    String deleteCurrent(String currentCode);
}
