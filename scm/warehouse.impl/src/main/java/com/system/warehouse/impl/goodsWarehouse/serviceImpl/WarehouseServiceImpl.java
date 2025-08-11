package com.system.warehouse.impl.goodsWarehouse.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import com.system.warehouse.api.goodsWarehouse.domain.Warehouse;
import com.system.warehouse.api.goodsWarehouse.service.IWarehouseService;
import com.system.warehouse.api.goodsWarehouse.vo.WarehouseVo;
import com.system.warehouse.impl.goodsWarehouse.mapper.WarehouseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author lutong
 * @data 2025-1-6 006 15:30
 */

@Slf4j
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements IWarehouseService {

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Override
    public Page<Warehouse> selectPage(String warehouseCode, Integer pageIndex, Integer pageSize) {
        if (pageIndex == null) pageIndex = 1;
        if (pageSize == null) pageSize = 20;

        Page<Warehouse> page = new Page<>(pageIndex, pageSize);
        Integer count = warehouseMapper.getPageCount(warehouseCode);
        page.setTotal(count);
        if (count > 0) {
            List<Warehouse> list = warehouseMapper.getPageList(warehouseCode, pageIndex, pageSize);
            page.setRecords(list);
        }

        return page;
    }

    @Override
    public List<WarehouseVo> selectWarehouseList(String key) {
        ArrayList<WarehouseVo> list = new ArrayList<>();
        LambdaQueryWrapper<Warehouse> wrapper = new LambdaQueryWrapper<>();
        if (key != null) {
            wrapper.like(Warehouse::getWarehouseCode, key).or().like(Warehouse::getWarehouseName, key);
        }
        List<Warehouse> warehouseList = this.baseMapper.selectList(wrapper);
        if (warehouseList.size() > 0) {
            for (Warehouse warehouse : warehouseList) {
                WarehouseVo vo = new WarehouseVo();
                vo.setId(warehouse.getId());
                vo.setWarehouseCode(warehouse.getWarehouseCode());
                vo.setWarehouseName(warehouse.getWarehouseName());
                list.add(vo);
            }
        }
        return list;
    }


    @Override
    public String saveWarehouse(Warehouse warehouse) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        if (warehouse.getId() == null) {
            // 新增
            LambdaQueryWrapper<Warehouse> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Warehouse::getWarehouseCode, warehouse.getWarehouseCode());
            Warehouse one = this.baseMapper.selectOne(wrapper);
            if (one != null) {
                return "已存在编码为【" + warehouse.getWarehouseCode() + "】的仓库，请维护！";
            }
            warehouse.setStatus(true);
            warehouse.setCreateUserName(user.getLoginId());
            warehouse.setCreateTime(new Date());
            warehouse.setCompanyUid(user.getCompanyUid());
            this.baseMapper.insert(warehouse);
        } else {
            // 查询是否存在该编码的仓库
            Warehouse one = this.baseMapper.selectById(warehouse.getId());
            if (one == null) {
                return "不存在该仓库，请维护！";
            }
            // 校验是否存在
            if (!Objects.equals(warehouse.getWarehouseCode(), one.getWarehouseCode())) {
                LambdaQueryWrapper<Warehouse> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Warehouse::getWarehouseCode, warehouse.getWarehouseCode());
                Warehouse selectOne = this.baseMapper.selectOne(wrapper);
                if (selectOne != null) {
                    return "已存在编码为【" + warehouse.getWarehouseCode() + "】的仓库，请维护！";
                }
            }
            warehouse.setUpdateUserName(user.getLoginId());
            warehouse.setUpdateTime(new Date());
            this.baseMapper.updateById(warehouse);
        }
        return null;
    }


    @Override
    public String updateStatus(String warehouseCode) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        LambdaQueryWrapper<Warehouse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Warehouse::getWarehouseCode, warehouseCode);
        Warehouse warehouse = this.baseMapper.selectOne(wrapper);
        if (warehouse == null) {
            return "不存在该仓库，请维护！";
        }
        warehouse.setStatus(!warehouse.getStatus());
        warehouse.setUpdateUserName(user.getLoginId());
        warehouse.setUpdateTime(new Date());
        this.baseMapper.updateById(warehouse);
        return null;
    }

    @Override
    public String deleteWarehouse(String warehouseCode) {
        LambdaQueryWrapper<Warehouse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Warehouse::getWarehouseCode, warehouseCode);
        Warehouse warehouse = this.baseMapper.selectOne(wrapper);
        if (warehouse == null) {
            return "不存在该仓库，请维护！";
        }
        this.baseMapper.delete(wrapper);
        return null;
    }
}
