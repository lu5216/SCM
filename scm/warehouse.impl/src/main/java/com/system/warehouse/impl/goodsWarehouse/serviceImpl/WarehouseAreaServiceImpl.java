package com.system.warehouse.impl.goodsWarehouse.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import com.system.warehouse.api.goodsWarehouse.domain.Warehouse;
import com.system.warehouse.api.goodsWarehouse.domain.WarehouseArea;
import com.system.warehouse.api.goodsWarehouse.param.WarehouseAreaModuleParam;
import com.system.warehouse.api.goodsWarehouse.param.WarehouseAreaParam;
import com.system.warehouse.api.goodsWarehouse.service.IWarehouseAreaService;
import com.system.warehouse.api.goodsWarehouse.service.IWarehouseService;
import com.system.warehouse.api.goodsWarehouse.vo.WarehouseAreaListVo;
import com.system.warehouse.api.goodsWarehouse.vo.WarehouseAreaVo;
import com.system.warehouse.impl.goodsWarehouse.mapper.WarehouseAreaMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author lutong
 * @data 2025-1-7 007 9:13
 */

@Slf4j
@Service
public class WarehouseAreaServiceImpl extends ServiceImpl<WarehouseAreaMapper, WarehouseArea> implements IWarehouseAreaService {

    @Autowired
    private WarehouseAreaMapper warehouseAreaMapper;

    @Autowired
    private IWarehouseService warehouseService;

    @Override
    public Page<WarehouseAreaVo> selectPage(String warehouseCode, Integer pageIndex, Integer pageSize) {
        if (pageIndex == null) pageIndex = 1;
        if (pageSize == null) pageSize = 20;
        Page<WarehouseAreaVo> page = new Page<>(pageIndex, pageSize);

        Integer count = warehouseAreaMapper.getPageCount(warehouseCode);
        page.setTotal(count);
        if (count > 0) {
            List<WarehouseAreaVo> list = warehouseAreaMapper.getPageList(warehouseCode, pageIndex, pageSize);
            page.setRecords(list);
        }

        return page;
    }


    @Override
    public List<WarehouseAreaListVo> selectByWarehouseId(Integer warehouseId) {
        List<WarehouseAreaListVo> list = new ArrayList<>();
        LambdaQueryWrapper<WarehouseArea> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WarehouseArea::getWarehouseId, warehouseId);
        wrapper.eq(WarehouseArea::getIsDelete, 0);
        List<WarehouseArea> warehouseAreaList = this.baseMapper.selectList(wrapper);
        for (WarehouseArea warehouseArea : warehouseAreaList) {
            WarehouseAreaListVo vo = new WarehouseAreaListVo();
            vo.setId(warehouseArea.getId());
            vo.setWarehouseAreaName(warehouseArea.getWarehouseAreaName());
            vo.setWarehouseAreaCode(warehouseArea.getWarehouseAreaCode());
            list.add(vo);
        }
        return list;
    }


    @Override
    public String saveWarehouseArea(WarehouseAreaParam param) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        Warehouse warehouse = warehouseService.getById(param.getWarehouseId());
        if (warehouse == null) {
            return "不存在该仓库，请维护！";
        }
        if (param.getId() == null) {
            // 保存
            LambdaQueryWrapper<WarehouseArea> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(WarehouseArea::getWarehouseAreaCode, param.getWarehouseAreaCode());
            WarehouseArea one = this.baseMapper.selectOne(wrapper);
            if (one != null) {
                return "已存在编码为【" + param.getWarehouseAreaCode() + "】的库区，请维护！";
            }
            WarehouseArea area = new WarehouseArea();
            area.setWarehouseId(param.getWarehouseId());
            area.setWarehouseAreaCode(param.getWarehouseAreaCode());
            area.setWarehouseAreaName(param.getWarehouseAreaName());
            area.setRemark(param.getRemark());
            area.setIsDelete(false);
            area.setStatus(true);
            area.setCreateUserName(user.getLoginId());
            area.setCreateTime(new Date());
            area.setCompanyUid(user.getCompanyUid());
            this.baseMapper.insert(area);
        } else {
            // 修改
            WarehouseArea warehouseArea = this.baseMapper.selectById(param.getId());
            if (warehouseArea == null) {
                return "不存在该库区，请维护！";
            }
            if (!Objects.equals(param.getWarehouseAreaCode(), warehouseArea.getWarehouseAreaCode())) {
                LambdaQueryWrapper<WarehouseArea> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(WarehouseArea::getWarehouseAreaCode, param.getWarehouseAreaCode());
                WarehouseArea one = this.baseMapper.selectOne(wrapper);
                if (one != null) {
                    return "已存在编码为【" + param.getWarehouseAreaCode() + "】的库区编码！";
                }
            }
            WarehouseArea area = new WarehouseArea();
            area.setId(Long.valueOf(param.getId()));
            area.setWarehouseId(param.getWarehouseId());
            area.setWarehouseAreaCode(param.getWarehouseAreaCode());
            area.setWarehouseAreaName(param.getWarehouseAreaName());
            area.setModelLength(null);
            area.setModelWidth(null);
            area.setModelX(null);
            area.setModelY(null);
            area.setRemark(param.getRemark());
            area.setUpdateUserName(user.getLoginId());
            area.setUpdateTime(new Date());
            this.baseMapper.updateById(area);
        }
        return null;
    }

    @Override
    public String updateStatus(String warehouseAreaCode) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        LambdaQueryWrapper<WarehouseArea> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WarehouseArea::getWarehouseAreaCode, warehouseAreaCode);
        WarehouseArea area = this.baseMapper.selectOne(wrapper);
        if (area == null) {
            return "不存在该库区，请维护！";
        }
        area.setStatus(!area.getStatus());
        area.setUpdateUserName(user.getLoginId());
        area.setUpdateTime(new Date());
        this.baseMapper.updateById(area);
        return null;
    }


    @Override
    public String deleteWarehouseArea(String warehouseAreaCode) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        LambdaQueryWrapper<WarehouseArea> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WarehouseArea::getWarehouseAreaCode, warehouseAreaCode);
        WarehouseArea area = this.baseMapper.selectOne(wrapper);
        if (area == null) {
            return "不存在该库区，请维护！";
        }
        if (area.getIsDelete()) {
            return "库区已删除，请勿重复删除！";
        }
        area.setIsDelete(true);
        area.setUpdateUserName(user.getLoginId());
        area.setUpdateTime(new Date());
        this.baseMapper.updateById(area);
        return null;
    }


    @Override
    public String editModel(WarehouseAreaModuleParam param) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        LambdaQueryWrapper<WarehouseArea> wrapperList = new LambdaQueryWrapper<>();
        wrapperList.eq(WarehouseArea::getWarehouseId, param.getWarehouseId());
        List<WarehouseArea> warehouseAreaList = this.baseMapper.selectList(wrapperList);
        Warehouse warehouse = warehouseService.getById(param.getWarehouseId());
        if (warehouse == null) {
            return "不存在该仓库，请维护！";
        }
        LambdaQueryWrapper<WarehouseArea> wrapperOne = new LambdaQueryWrapper<>();
        wrapperOne.eq(WarehouseArea::getWarehouseAreaCode, param.getWarehouseAreaCode());
        WarehouseArea result = this.baseMapper.selectOne(wrapperOne);
        if (result == null) {
            return "不存在该库区，请维护！";
        }
        if (!Objects.equals(result.getWarehouseId(), warehouse.getId())) {
            return "仓库与库区不匹配，请维护！";
        }

        // 校验
        BigDecimal areaSum = BigDecimal.ZERO;
        for (WarehouseArea area : warehouseAreaList) {
            // 计算面积和
            BigDecimal length = area.getModelLength() != null ? area.getModelLength() : BigDecimal.ZERO;
            BigDecimal width = area.getModelWidth() != null ? area.getModelWidth() : BigDecimal.ZERO;
            areaSum = areaSum.add(length.multiply(width));
        }
        // 减去修改的库区的原有面积
        if (result.getModelLength() != null && result.getModelWidth() != null) {
            BigDecimal resultArea = result.getModelLength().multiply(result.getModelWidth());
            areaSum = areaSum.subtract(resultArea);
        }
        // 加上修改后的库区的面积
        if (param.getModelWidth() != null && param.getModelLength() != null) {
            BigDecimal area = param.getModelLength().multiply(param.getModelWidth());
            areaSum = areaSum.add(area);
        }
        // 面积校验
        if (warehouse.getModelLength() == null || warehouse.getModelWidth() == null ||
                areaSum.compareTo(warehouse.getModelLength().multiply(warehouse.getModelWidth())) > 0) {
            return "库区面积和不能大于仓库面积，请重新维护！";
        }
        // 设置库区参数
        result.setWarehouseId(param.getWarehouseId());
        result.setModelWidth(param.getModelWidth());
        result.setModelLength(param.getModelLength());
        result.setModelX(param.getModelX());
        result.setModelY(param.getModelY());
        result.setUpdateTime(new Date());
        result.setUpdateUserName(user.getLoginId());
        this.baseMapper.updateById(result);
        return null;
    }
}
