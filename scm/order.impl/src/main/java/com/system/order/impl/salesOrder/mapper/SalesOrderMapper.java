package com.system.order.impl.salesOrder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.order.api.salesOrder.domain.SalesOrder;
import com.system.order.api.salesOrder.params.SalesOrderParam;
import com.system.order.api.salesOrder.vo.SalesOrderVo;
import com.system.order.impl.salesOrder.provider.SalesOrderProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author lutong
 * @data 2025-2-20 020 17:03
 */

@Mapper
public interface SalesOrderMapper extends BaseMapper<SalesOrder> {

    @SelectProvider(type = SalesOrderProvider.class, method = "getItemCount")
    Integer getItemCount(SalesOrderParam param);

    @SelectProvider(type = SalesOrderProvider.class, method = "getItemList")
    List<SalesOrderVo> getItemList(SalesOrderParam param);

    @SelectProvider(type = SalesOrderProvider.class, method = "getGoodsCount")
    Integer getGoodsCount(SalesOrderParam param);

    @SelectProvider(type = SalesOrderProvider.class, method = "getGoodsList")
    List<SalesOrderVo> getGoodsList(SalesOrderParam param);
}
