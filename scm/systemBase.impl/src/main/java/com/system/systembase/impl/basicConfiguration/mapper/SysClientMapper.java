package com.system.systembase.impl.basicConfiguration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.systembase.api.basicConfiguration.domain.SysClient;
import com.system.systembase.api.basicConfiguration.vo.SysClientDetail;
import com.system.systembase.impl.basicConfiguration.provider.SysCompanyProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * @author lutong
 * @data 2024-12-24 024 14:09
 */

@Mapper
public interface SysClientMapper extends BaseMapper<SysClient> {

    /**
     *  查询客户详细数据
     * @param clientCode
     * @return
     */
    @SelectProvider(type = SysCompanyProvider.class, method = "selectClientDetail")
    SysClientDetail selectClientDetail(String clientCode);

}
