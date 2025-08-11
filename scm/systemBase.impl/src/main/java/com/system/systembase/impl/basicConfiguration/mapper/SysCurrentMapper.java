package com.system.systembase.impl.basicConfiguration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.systembase.api.basicConfiguration.domain.SysCurrent;
import com.system.systembase.api.basicConfiguration.vo.SysCurrentVo;
import com.system.systembase.impl.basicConfiguration.provider.SysCurrentProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author lutong
 * @data 2025-1-8 008 9:46
 */

@Mapper
public interface SysCurrentMapper extends BaseMapper<SysCurrent> {

    /**
     * 查询页面-count
     * @param Key
     * @return
     */
    @SelectProvider(type = SysCurrentProvider.class, method = "getPageCount")
    Integer getPageCount(@Param("key") String Key, @Param("companyUid") String companyUid);

    /**
     * 查询页面-list
     * @param Key
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @SelectProvider(type = SysCurrentProvider.class, method = "getPageList")
    List<SysCurrent> getPageList(@Param("key") String Key, @Param("companyUid") String companyUid,
                                 @Param("pageIndex") Integer pageIndex, @Param("pageSize") Integer pageSize);

    /**
     * 获取币别列表
     * @param Key
     * @return
     */
    @SelectProvider(type = SysCurrentProvider.class, method = "selectCurrentList")
    List<SysCurrentVo> selectCurrentList(@Param("key") String Key, @Param("companyUid") String companyUid);

}
