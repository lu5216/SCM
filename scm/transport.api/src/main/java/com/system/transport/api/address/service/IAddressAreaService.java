package com.system.transport.api.address.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.transport.api.address.domain.AddressArea;
import com.system.transport.api.address.vo.AddressAreaVo;

import java.util.List;

/**
 * @author lutong
 * @data 2025-1-22 022 13:43
 */

public interface IAddressAreaService extends IService<AddressArea> {


    /**
     *  查询-page
     * @param key
     * @return
     */
    Page<AddressAreaVo> getAddressAreaVoPage(String key, Integer pageIndex, Integer pageSize);

    /**
     *  查询-list
     * @param key
     * @return
     */
    List<AddressAreaVo> getAddressAreaVoList(String key);

    /**
     *  查询-count
     * @param key
     * @return
     */
    Integer getAddressAreaCount(String key);

    // 增-删-改-查-导入-导出

    /**
     *  新增地址库
     * @param addressArea
     * @return
     */
    String addAddressArea(AddressArea addressArea);

    /**
     *  修改地址库
     * @param addressArea
     * @return
     */
    String updateAddressArea(AddressArea addressArea);

    /**
     *  根据ID删除
     * @param id
     * @return
     */
    String deleteAddressAreaById(Integer id);

}
