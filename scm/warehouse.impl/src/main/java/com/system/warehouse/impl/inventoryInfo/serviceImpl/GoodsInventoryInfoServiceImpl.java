package com.system.warehouse.impl.inventoryInfo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.exception.CustomerAuthenticationException;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.redisTemplate.service.IRedisLockerService;
import com.system.warehouse.api.goods.domain.WarehouseGoods;
import com.system.warehouse.api.goods.service.IWarehouseGoodsService;
import com.system.warehouse.api.goodsWarehouse.domain.Warehouse;
import com.system.warehouse.api.goodsWarehouse.domain.WarehouseArea;
import com.system.warehouse.api.goodsWarehouse.service.IWarehouseAreaService;
import com.system.warehouse.api.goodsWarehouse.service.IWarehouseService;
import com.system.warehouse.api.inventoryInfo.domain.GoodsInventoryInfo;
import com.system.warehouse.api.inventoryInfo.params.GoodsInventoryInfoParam;
import com.system.warehouse.api.inventoryInfo.service.IGoodsInventoryInfoService;
import com.system.warehouse.api.inventoryInfo.vo.GoodsInventoryInfoVo;
import com.system.warehouse.impl.inventoryInfo.mapper.GoodsInventoryInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author lutong
 * @data 2025-2-19 019 16:53
 */

@Slf4j
@Service
public class GoodsInventoryInfoServiceImpl extends ServiceImpl<GoodsInventoryInfoMapper, GoodsInventoryInfo>
        implements IGoodsInventoryInfoService {

    private final IWarehouseGoodsService warehouseGoodsService;
    public GoodsInventoryInfoServiceImpl(@Lazy IWarehouseGoodsService warehouseGoodsService) {
        this.warehouseGoodsService = warehouseGoodsService;
    }

    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    private IWarehouseAreaService warehouseAreaService;

    @Autowired
    private IRedisLockerService redisLockerService;

    @Override
    public Page<GoodsInventoryInfoVo> getPage(GoodsInventoryInfoParam param) {
        if (param.getPageIndex() == null) {
            param.setPageIndex(1);
        }
        if (param.getPageSize() == null) {
            param.setPageSize(1);
        }

        Page<GoodsInventoryInfoVo> page = new Page<>(param.getPageIndex(), param.getPageSize());
        Integer count = this.baseMapper.getGoodsPageCount(param);
        page.setTotal(count);

        if (count > 0) {
            List<GoodsInventoryInfoVo> list = this.baseMapper.getGoodsPageList(param);
            if (list.size() > 0) {
                for (GoodsInventoryInfoVo vo : list) {
                    // 获取仓库名称
                    if (vo.getWarehouseId() != null) {
                        Warehouse warehouse = warehouseService.getById(vo.getWarehouseId());
                        vo.setWarehouseName(warehouse.getWarehouseName());
                    }
                    if (vo.getWarehouseAreaId() != null) {
                        WarehouseArea warehouseArea = warehouseAreaService.getById(vo.getWarehouseAreaId());
                        vo.setWarehouseAreaName(warehouseArea.getWarehouseAreaName());
                    }
                    // 获取库区数据
                }
            }
            page.setRecords(list);
        }
        return page;
    }

    @Override
    public void deleteByGoodsId(Integer goodsId) {
        GoodsInventoryInfo goodsInventoryInfo = this.getOneByGoodsId(goodsId);
        if (goodsInventoryInfo == null) {
            throw new CustomerAuthenticationException("不存在商品ID为【" + goodsId +"】的商品库存信息");
        }
        this.baseMapper.deleteById(goodsInventoryInfo);
    }

    @Override
    @Transactional
    public void addByGoodsId(Integer goodsId, Integer inventoryAlert, boolean alertAlready) {
        SysUserVo user = this.getLoginUser();
        WarehouseGoods warehouseGoods = this.getWarehouseGoodByGoodsId(goodsId);

        GoodsInventoryInfo info = new GoodsInventoryInfo();
        info.setGoodsId(goodsId);
        info.setQuantity(0);
        info.setAlertAlready(alertAlready);
        info.setCompanyUid(user.getCompanyUid());
        info.setCreateTime(new Date());
        info.setCreateUserName(user.getLoginId());
        info.setWarehouseId(warehouseGoods.getPreWarehouseId());
        info.setWarehouseAreaId(warehouseGoods.getPreWarehouseAreaId());
        info.setInventoryAlert(inventoryAlert);
        this.baseMapper.insert(info);
    }

    @Override
    @Transactional
    public void updateByGoodsId(Integer goodsId, Integer quantity) {
        GoodsInventoryInfo goodsInventoryInfo = this.getOneByGoodsId(goodsId);
        if (goodsInventoryInfo == null) {
            throw new CustomerAuthenticationException("不存在商品ID为【" + goodsId +"】的商品库存信息");
        }
        // 获取用户信息
        SysUserVo user = this.getLoginUser();

        // redis锁
        boolean lock = redisLockerService.lock("InventoryQuantity", 10);
        if (Boolean.TRUE.equals(lock)) {
            try {
                goodsInventoryInfo.setQuantity(quantity);
                goodsInventoryInfo.setUpdateTime(new Date());
                goodsInventoryInfo.setUpdateUserName(user.getLoginId());
                this.baseMapper.updateById(goodsInventoryInfo);
            } finally {
                // 解锁
                redisLockerService.unlock("InventoryQuantity");
            }
        } else {
            log.error("获取Redis锁异常, lock={}", lock);
            throw new CustomerAuthenticationException("获取Redis锁异常");
        }
    }


    @Override
    @Transactional
    public void updateInventoryAlert(Integer goodsId, Integer inventoryAlert, boolean alertAlready) {
        GoodsInventoryInfo goodsInventoryInfo = this.getOneByGoodsId(goodsId);
        if (goodsInventoryInfo == null) {
            throw new CustomerAuthenticationException("不存在商品ID为【" + goodsId +"】的商品库存信息");
        }
        // 获取用户信息
        SysUserVo user = this.getLoginUser();

        // redis锁
        boolean lock = redisLockerService.lock("InventoryAlert", 10);
        if (Boolean.TRUE.equals(lock)) {
            try {
                goodsInventoryInfo.setInventoryAlert(inventoryAlert);
                goodsInventoryInfo.setAlertAlready(alertAlready);
                goodsInventoryInfo.setUpdateTime(new Date());
                goodsInventoryInfo.setUpdateUserName(user.getLoginId());
                this.baseMapper.updateById(goodsInventoryInfo);
            } finally {
                // 解锁
                redisLockerService.unlock("InventoryAlert");
            }
        } else {
            log.error("获取Redis锁异常, lock={}", lock);
            throw new CustomerAuthenticationException("获取Redis锁异常");
        }
    }

    @Override
    public GoodsInventoryInfo getOneByGoodsId(Integer goodsId) {
        if (goodsId == null) {
            throw new CustomerAuthenticationException("商品ID不能为空");
        }
        SysUserVo user = this.getLoginUser();

        LambdaQueryWrapper<GoodsInventoryInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GoodsInventoryInfo::getGoodsId, goodsId)
                .eq(GoodsInventoryInfo::getCompanyUid, user.getCompanyUid());
        return this.baseMapper.selectOne(wrapper);
    }


    @Override
    public void updateAlertAlready(Integer id, String updateUserName) {
        GoodsInventoryInfo info = this.baseMapper.selectById(id);
        info.setAlertAlready(true);
        info.setUpdateTime(new Date());
        info.setUpdateUserName(updateUserName);
        this.baseMapper.updateById(info);
    }

    @Override
    public List<GoodsInventoryInfo> getByListAlert() {
        LambdaQueryWrapper<GoodsInventoryInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GoodsInventoryInfo::getAlertAlready, 0);
        wrapper.gt(GoodsInventoryInfo::getInventoryAlert, 0);
        wrapper.apply("QUANTITY <= INVENTORY_ALERT");
        return this.baseMapper.selectList(wrapper);
    }

    public SysUserVo getLoginUser() {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            throw new CustomerAuthenticationException("账号未登入，请重新登入!");
        }
        return user;
    }

    /**
     *  获取商品信息
     * @param goodsId
     * @return
     */
    public WarehouseGoods getWarehouseGoodByGoodsId(Integer goodsId) {
        WarehouseGoods warehouseGoods = warehouseGoodsService.getById(goodsId);
        if (warehouseGoods == null) {
            throw new CustomerAuthenticationException("商品库不存在商品ID为【 + goodsId +】的数据");
        }
        return warehouseGoods;
    }
}
