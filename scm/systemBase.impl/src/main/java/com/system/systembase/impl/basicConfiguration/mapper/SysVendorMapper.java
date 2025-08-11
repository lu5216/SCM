package com.system.systembase.impl.basicConfiguration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.systembase.api.basicConfiguration.domain.SysVendor;
import com.system.systembase.api.basicConfiguration.vo.SysVendorDetail;
import com.system.systembase.impl.basicConfiguration.provider.SysCompanyProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * @author lutong
 * @data 2024-12-24 024 14:09
 */

@Mapper
public interface SysVendorMapper extends BaseMapper<SysVendor> {

    /**
     *  查询供应商详细数据
     * @param vendorCode
     * @return
     */
    @SelectProvider(type = SysCompanyProvider.class, method = "selectVendorDetail")
    SysVendorDetail selectVendorDetail(String vendorCode);
}
