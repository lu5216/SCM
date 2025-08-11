package com.system.systembase.api.basicConfiguration.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.systembase.api.basicConfiguration.domain.SysVendor;
import com.system.systembase.api.basicConfiguration.param.SysVendorParam;
import com.system.systembase.api.basicConfiguration.vo.SysCVListVo;
import com.system.systembase.api.basicConfiguration.vo.SysVendorDetail;
import com.system.systembase.api.basicConfiguration.vo.SysVendorVo;

import java.util.List;

/**
 * @author lutong
 * @data 2024-12-24 024 14:08
 */

public interface ISysVendorService extends IService<SysVendor> {

    /**
     *  分页查询供应商数据
     * @param param
     * @return
     */
    Page<SysVendorVo> selectVendorPage(SysVendorParam param);

    /**
     *  查询供应商详情数据
     * @param vendorCode
     * @return
     */
    SysVendorDetail selectVendorDetail(String vendorCode);

    /**
     *  修改供应商详细数据
     * @param detail
     * @return
     */
    String updateVendorDetail(SysVendorDetail detail);

    /**
     *  增加供应商数据
     * @param vendor
     * @return
     */
    String addVendorData(SysVendor vendor);

    /**
     *  删除供应商数据
     * @param id 供应商ID
     * @return
     */
    String deleteVendorData(Integer id);

    /**
     *  更改合作状态
     * @param vendorCode
     * @return
     */
    String updateIsCooperation(String vendorCode);

    /**
     *  更改启用状态
     * @param vendorCode
     * @return
     */
    String updateIsEnable(String vendorCode);

    /**
     *  更改生效状态
     * @param vendorCode
     * @return
     */
    String updateIsValidated(String vendorCode);

    /**
     *  获取供应商列表
     * @param key
     * @return
     */
    List<SysCVListVo> getVendorList(String key);


    /**
     * 根据供应商编码获取
     * @param vendorCode
     * @return
     */
    SysVendor getByVendorCode(String vendorCode);
}
