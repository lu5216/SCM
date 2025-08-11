package com.system.warehouse.impl.goods.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.exception.CustomerAuthenticationException;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.basicConfiguration.domain.SysCurrent;
import com.system.systembase.api.basicConfiguration.service.ISysCurrentService;
import com.system.warehouse.api.goods.domain.WarehouseGoods;
import com.system.warehouse.api.goods.domain.WarehouseGoodsNumber;
import com.system.warehouse.api.goods.param.WarehouseGoodGetPageParam;
import com.system.warehouse.api.goods.service.IWarehouseGoodsNumberService;
import com.system.warehouse.api.goods.service.IWarehouseGoodsService;
import com.system.warehouse.api.goods.vo.*;
import com.system.warehouse.api.goodsWarehouse.domain.Warehouse;
import com.system.warehouse.api.goodsWarehouse.domain.WarehouseArea;
import com.system.warehouse.api.goodsWarehouse.service.IWarehouseAreaService;
import com.system.warehouse.api.goodsWarehouse.service.IWarehouseService;
import com.system.warehouse.api.inventoryInfo.domain.GoodsInventoryInfo;
import com.system.warehouse.api.inventoryInfo.service.IGoodsInventoryInfoService;
import com.system.warehouse.impl.goods.mapper.WarehouseGoodsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author lutong
 * @data 2025-1-10 010 10:48
 */

@Slf4j
@Service
public class WarehouseGoodsServiceImpl extends ServiceImpl<WarehouseGoodsMapper, WarehouseGoods> implements IWarehouseGoodsService {

    @Autowired
    private WarehouseGoodsMapper warehouseGoodsMapper;

    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    private IWarehouseAreaService warehouseAreaService;

    @Autowired
    private ISysCurrentService sysCurrentService;

    @Autowired
    private IGoodsInventoryInfoService goodsInventoryInfoService;

    @Autowired
    private IWarehouseGoodsNumberService warehouseGoodsNumberService;

    @Override
    public Page<WarehouseGoods> getGoodsPage(WarehouseGoodGetPageParam param) {
        if (param.getPageIndex() == null) param.setPageIndex(1);
        if (param.getPageSize() == null) param.setPageSize(20);

        Page<WarehouseGoods> page = new Page<>(param.getPageIndex(), param.getPageSize());
        Integer count = warehouseGoodsMapper.getGoodsPageCount(param);
        page.setTotal(count);
        if (count > 0) {
            List<WarehouseGoods> goodsList = this.getGoodsPageList(param);
            page.setRecords(goodsList);
        }
        return page;
    }


    @Override
    public List<WarehouseGoods> getGoodsPageList(WarehouseGoodGetPageParam param) {
        List<WarehouseGoods> goodsList = warehouseGoodsMapper.getGoodsPageList(param);
        if (goodsList.size() > 0) {
            for (WarehouseGoods warehouseGoods : goodsList) {
                // 获取仓库库区信息
                PreWarehouseGoodsVo vo = this.getPreWarehouseByGoodsCode(warehouseGoods.getGoodsCode());
                warehouseGoods.setPreWarehouseName(vo.getPreWarehouseName());
                warehouseGoods.setPreWarehouseAreaName(vo.getPreWarehouseAreaName());

                // 获取币制信息
                if (warehouseGoods.getCurrencyId() != null) {
                    SysCurrent current = sysCurrentService.getById(warehouseGoods.getCurrencyId());
                    if (current != null) {
                        warehouseGoods.setCurrencyName(current.getCurrentName());
                    }
                }
            }
        }
        return goodsList;
    }


    @Override
    @Transactional
    public String saveBaseInfo(GoodsSaveBaseInfoVo vo) {
        SysUserVo user = SecurityUtils.getUser();
        String verifySecurityUser = this.verifySecurityUser(user);
        if (verifySecurityUser != null) return verifySecurityUser;

        if (vo.getId() == null) {
            String verifyGoodsCode = this.verifyGoodsCode(vo.getGoodsCode(), user.getCompanyUid());
            if (verifyGoodsCode != null) return verifyGoodsCode;
            String verifyGoodsName = this.verifyGoodsName(vo.getGoodsName(), user.getCompanyUid());
            if (verifyGoodsName != null) return verifyGoodsName;
        }
        // 校验仓库
        Warehouse warehouse = warehouseService.getById(vo.getPreWarehouseId());
        if (warehouse == null) {
            return "不存在ID为【" + vo.getPreWarehouseId() + "】的仓库，请维护!";
        }
        // 校验库区
        WarehouseArea warehouseArea = warehouseAreaService.getById(vo.getPreWarehouseAreaId());
        if (warehouseArea == null) {
            return "不存在ID为【" + vo.getPreWarehouseAreaId() + "】的库区，请维护!";
        }
        if (!Objects.equals(warehouseArea.getWarehouseId(), warehouse.getId())) {
            return "ID为【" + vo.getPreWarehouseAreaId() + "】的库区不属于ID为【" + vo.getPreWarehouseId() + "】的仓库，请维护!";
        }
        // 校验预警库存
        if (vo.getInventoryAlert() == null) {
            vo.setInventoryAlert(0);
        }
        if (vo.getInventoryAlert() < 0) {
           return "预警库存不能小于0!";
        }
        if (vo.getAlertAlready() == null) {
            vo.setAlertAlready(true);
        }

        if (vo.getId() == null) {
            // 新增
            WarehouseGoods goods = new WarehouseGoods();
            BeanUtils.copyProperties(vo, goods);
            // 设置默认参数
            goods.setSupplierSettlement(1);
            goods.setStatus(1);
            goods.setPreWarehouseId(warehouse.getId());
            goods.setPreWarehouseAreaId(warehouseArea.getId().intValue());
            goods.setCreateUserName(user.getLoginId());
            goods.setCreateTime(new Date());
            goods.setCompanyUid(user.getCompanyUid());
            this.baseMapper.insert(goods);
            // 根据ID新增库存信息
            goodsInventoryInfoService.addByGoodsId(goods.getId(), vo.getInventoryAlert(), vo.getAlertAlready());
        } else {
            // 修改
            WarehouseGoods warehouseGoods = this.baseMapper.selectById(vo.getId());
            if (warehouseGoods == null) {
                return "保存失败,根据ID无法查询出对应商品信息";
            }
            warehouseGoods.setGoodsName(vo.getGoodsName());
            warehouseGoods.setGoodsModel(vo.getGoodsModel());
            warehouseGoods.setGoodsSpec(vo.getGoodsSpec());
            warehouseGoods.setGoodsType(vo.getGoodsType());
            warehouseGoods.setGoodsSpeciesName(vo.getGoodsSpeciesName());
            warehouseGoods.setPreWarehouseId(warehouse.getId());
            warehouseGoods.setPreWarehouseAreaId(warehouseArea.getId().intValue());
            warehouseGoods.setPackaging(vo.getPackaging());
            warehouseGoods.setGoodsDescribe(vo.getGoodsDescribe());
            warehouseGoods.setGoodsPicture(vo.getGoodsPicture());
            warehouseGoods.setUpdateUserName(user.getLoginId());
            warehouseGoods.setUpdateTime(new Date());
            this.baseMapper.updateById(warehouseGoods);
            // 根据ID修改库存信息
            goodsInventoryInfoService.updateInventoryAlert(vo.getId(), vo.getInventoryAlert(), vo.getAlertAlready());
        }
        return null;
    }

    @Override
    public String savePriceInfo(GoodsSavePriceInfoVo vo) {
        LambdaQueryWrapper<WarehouseGoods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WarehouseGoods::getGoodsCode, vo.getGoodsCode());
        WarehouseGoods goods = this.baseMapper.selectOne(wrapper);
        if (goods == null) {
            return "不存在该识别码为【" + vo.getGoodsCode() + "】的商品，请维护！";
        }
        if (vo.getCurrencyId() != null) {
            SysCurrent current = sysCurrentService.getById(vo.getCurrencyId());
            if (current == null) {
                return "不存在ID为【" + vo.getCurrencyId() + "】的货币，请维护!";
            }
        }

        SysUserVo user = SecurityUtils.getUser();
        String verifySecurityUser = this.verifySecurityUser(user);
        if (verifySecurityUser != null) return verifySecurityUser;

        goods.setNetWeight(vo.getNetWeight());
        goods.setGrossWeight(vo.getGrossWeight());
        goods.setVolume(vo.getVolume());
        goods.setUnitPrice(vo.getUnitPrice());
        goods.setFirstQuantityUnit(vo.getFirstQuantityUnit());
        goods.setFirstQuantityUnitCode(vo.getFirstQuantityUnitCode());
        goods.setSecondQuantityUnit(vo.getSecondQuantityUnit());
        goods.setSecondQuantityUnitCode(vo.getSecondQuantityUnitCode());
        goods.setWeightUnit(vo.getWeightUnit());
        goods.setWeightUnitCode(vo.getWeightUnitCode());
        goods.setCurrencyId(vo.getCurrencyId());
        goods.setSupplierSettlement(vo.getSupplierSettlement() != null ? vo.getSupplierSettlement() : 1);
        goods.setCostCalculationWay(vo.getCostCalculationWay());
        goods.setUpdateTime(new Date());
        goods.setUpdateUserName(user.getLoginId());
        this.baseMapper.updateById(goods);
        return null;
    }

    @Override
    public String saveKeepInfo(GoodsSaveKeepInfoVo vo) {
        LambdaQueryWrapper<WarehouseGoods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WarehouseGoods::getGoodsCode, vo.getGoodsCode());
        WarehouseGoods goods = this.baseMapper.selectOne(wrapper);
        if (goods == null) {
            return "不存在该识别码为【" + vo.getGoodsCode() + "】的商品，请维护！";
        }

        SysUserVo user = SecurityUtils.getUser();
        String verifySecurityUser = this.verifySecurityUser(user);
        if (verifySecurityUser != null) return verifySecurityUser;

        goods.setOriginCountry(vo.getOriginCountry());
        goods.setShelfLifeNo(vo.getShelfLifeNo());
        goods.setShelfLifeUnit(vo.getShelfLifeUnit());
        goods.setUpdateTime(new Date());
        goods.setUpdateUserName(user.getLoginId());
        this.baseMapper.updateById(goods);
        return null;
    }

    @Override
    public PreWarehouseGoodsVo getPreWarehouseByGoodsCode(String goodsCode) {
        LambdaQueryWrapper<WarehouseGoods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WarehouseGoods::getGoodsCode, goodsCode);
        WarehouseGoods goods = this.baseMapper.selectOne(wrapper);
        if (goods == null) {
            throw new CustomerAuthenticationException("不存在该识别码为【" + goodsCode + "】的商品，请维护！");
        }

        PreWarehouseGoodsVo vo = new PreWarehouseGoodsVo();
        vo.setGoodsId(goods.getId());
        vo.setGoodsCode(goods.getGoodsCode());
        vo.setGoodsName(goods.getGoodsName());

        // 获取仓库库区信息
        vo.setPreWarehouseId(goods.getPreWarehouseId());
        Warehouse warehouse = warehouseService.getById(goods.getPreWarehouseId());
        if (warehouse != null) {
            vo.setPreWarehouseName(warehouse.getWarehouseName());
            vo.setPreWarehouseCode(warehouse.getWarehouseCode());
        }
        vo.setPreWarehouseAreaId(goods.getPreWarehouseId());
        WarehouseArea warehouseArea = warehouseAreaService.getById(goods.getPreWarehouseAreaId());
        if (warehouseArea != null) {
            vo.setPreWarehouseAreaName(warehouseArea.getWarehouseAreaName());
            vo.setPreWarehouseAreaCode(warehouseArea.getWarehouseAreaCode());
        }
        return vo;
    }

    @Override
    public List<WarehouseGoodsListVo> getWarehouseGoodsList(String key) {
        LambdaQueryWrapper<WarehouseGoods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WarehouseGoods::getStatus, 1);
        if (key != null  && key.equals("")) {
            wrapper.like(WarehouseGoods::getGoodsCode, key).or().like(WarehouseGoods::getGoodsName, key);
        }

        List<WarehouseGoods> goodsList = this.baseMapper.selectList(wrapper);
        List<WarehouseGoodsListVo> list = new ArrayList<>();
        for (WarehouseGoods goods : goodsList) {
            WarehouseGoodsListVo vo = new WarehouseGoodsListVo();
            vo.setGoodsId(goods.getId());
            vo.setGoodsName(goods.getGoodsName());
            vo.setGoodsCode(goods.getGoodsCode());
            list.add(vo);
        }
        log.info("获取到的全部商品数据：{}", JSON.toJSONString(list));
        return list;
    }

    @Override
    @Transactional
    public String deleteWarehouseGoods(Integer goodsId) {
        // 删除和goodsId关联的商品条码
        LambdaQueryWrapper<WarehouseGoodsNumber> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WarehouseGoodsNumber::getGoodsId, goodsId);
        List<WarehouseGoodsNumber> warehouseGoodsNumberList = warehouseGoodsNumberService.list(wrapper);
        if (warehouseGoodsNumberList.size() > 0) {
            List<Integer> ids = new ArrayList<>();
            for (WarehouseGoodsNumber warehouseGoodsNumber : warehouseGoodsNumberList) {
                ids.add(warehouseGoodsNumber.getId());
            }
            warehouseGoodsNumberService.removeByIds(ids);
        }

        // 删除和goodsId关联的库存信息数据
        goodsInventoryInfoService.deleteByGoodsId(goodsId);

        // 删除商品
        String verifyGoodsId = this.verifyGoodsId(goodsId);
        if (verifyGoodsId != null) {
            return verifyGoodsId;
        }
        this.baseMapper.deleteById(goodsId);
        return null;
    }


    @Override
    public String editStatus(Integer goodsId) {
        WarehouseGoods warehouseGoods = this.baseMapper.selectById(goodsId);
        if (warehouseGoods == null) {
            return "不存在该ID对应的商品!";
        }
        SysUserVo user = SecurityUtils.getUser();
        String errMSG = this.verifySecurityUser(user);
        if (errMSG != null) return errMSG;
        if (warehouseGoods.getStatus() == 1) {
            warehouseGoods.setStatus(2);
        } else {
            warehouseGoods.setStatus(1);
        }
        warehouseGoods.setUpdateUserName(user.getLoginId());
        warehouseGoods.setUpdateTime(new Date());
        this.baseMapper.updateById(warehouseGoods);
        return null;
    }


    @Override
    public GoodsDetailsVo findDetailById(Integer goodsId) {
        // 获取数据
        WarehouseGoods goods = this.baseMapper.selectById(goodsId);
        if (goods == null) {
            throw new CustomerAuthenticationException("该goodsId未在商品库找到对应数据！");
        }

        GoodsDetailsVo detail = new GoodsDetailsVo();
        detail.setGoodsId(goodsId);
        detail.setGoodsCode(goods.getGoodsCode());
        detail.setGoodsName(goods.getGoodsName());

        // 商品条码数据
        List<WarehouseGoodsNumber> warehouseGoodsNumberList = warehouseGoodsNumberService.getByGoodsCode(goods.getGoodsCode());
        detail.setWarehouseGoodsNumberList(warehouseGoodsNumberList);

        // 基础数据
        GoodsSaveBaseInfoVo baseInfoVo = new GoodsSaveBaseInfoVo();
        baseInfoVo.setGoodsCode(goods.getGoodsCode());
        baseInfoVo.setGoodsName(goods.getGoodsName());
        baseInfoVo.setGoodsModel(goods.getGoodsModel());
        baseInfoVo.setGoodsSpec(goods.getGoodsSpec());
        baseInfoVo.setGoodsType(goods.getGoodsType());
        baseInfoVo.setGoodsSpeciesName(goods.getGoodsSpeciesName());
        baseInfoVo.setPreWarehouseId(goods.getPreWarehouseId());
        baseInfoVo.setPreWarehouseAreaId(goods.getPreWarehouseAreaId());
        // 获取仓库库区信息
        PreWarehouseGoodsVo vo = this.getPreWarehouseByGoodsCode(goods.getGoodsCode());
        baseInfoVo.setPreWarehouseName(vo.getPreWarehouseName() + "(" + vo.getPreWarehouseCode() + ")");
        baseInfoVo.setPreWarehouseAreaName(vo.getPreWarehouseAreaName() + "(" + vo.getPreWarehouseAreaCode() + ")");
        baseInfoVo.setPackaging(goods.getPackaging());
        // 获取预警库存
        GoodsInventoryInfo goodsInventoryInfo = goodsInventoryInfoService.getOneByGoodsId(goodsId);
        baseInfoVo.setInventoryAlert(goodsInventoryInfo.getInventoryAlert());
        baseInfoVo.setAlertAlready(goodsInventoryInfo.getAlertAlready());
        baseInfoVo.setGoodsDescribe(goods.getGoodsDescribe());
        baseInfoVo.setGoodsPicture(goods.getGoodsPicture());
        detail.setBaseInfoVo(baseInfoVo);

        // 价格数据
        GoodsSavePriceInfoVo priceInfoVo = new GoodsSavePriceInfoVo();
        priceInfoVo.setGoodsCode(goods.getGoodsCode());
        priceInfoVo.setNetWeight(goods.getNetWeight());
        priceInfoVo.setGrossWeight(goods.getGrossWeight());
        priceInfoVo.setUnitPrice(goods.getUnitPrice());
        priceInfoVo.setVolume(goods.getVolume());
        priceInfoVo.setFirstQuantityUnit(goods.getFirstQuantityUnit());
        priceInfoVo.setFirstQuantityUnitCode(goods.getFirstQuantityUnitCode());
        priceInfoVo.setWeightUnit(goods.getWeightUnit());
        priceInfoVo.setWeightUnitCode(goods.getWeightUnitCode());
        priceInfoVo.setSecondQuantityUnit(goods.getSecondQuantityUnit());
        priceInfoVo.setSecondQuantityUnitCode(goods.getSecondQuantityUnitCode());
        priceInfoVo.setCurrencyId(goods.getCurrencyId());
        // 获取币制信息
        if (goods.getCurrencyId() != null) {
            SysCurrent current = sysCurrentService.getById(goods.getCurrencyId());
            priceInfoVo.setCurrencyName(current != null ? current.getCurrentName() : null);
        }
        priceInfoVo.setCostCalculationWay(goods.getCostCalculationWay());
        priceInfoVo.setSupplierSettlement(goods.getSupplierSettlement());
        detail.setPriceInfoVo(priceInfoVo);

        // 维护信息
        GoodsSaveKeepInfoVo keepInfoVo = new GoodsSaveKeepInfoVo();
        keepInfoVo.setGoodsCode(goods.getGoodsCode());
        keepInfoVo.setOriginCountry(goods.getOriginCountry());
        keepInfoVo.setShelfLifeNo(goods.getShelfLifeNo());
        keepInfoVo.setShelfLifeUnit(goods.getShelfLifeUnit());
        detail.setKeepInfoVo(keepInfoVo);
        return detail;
    }

    @Override
    public WarehouseGoods findOneByGoodsCode(String goodsCode, String companyUid) {
        LambdaQueryWrapper<WarehouseGoods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WarehouseGoods::getGoodsCode, goodsCode);
        wrapper.eq(WarehouseGoods::getCompanyUid, companyUid);
        return this.baseMapper.selectOne(wrapper);
    }

    /**
     *  校验商品ID
     * @param goodsId
     * @return
     */
    public String verifyGoodsId(Integer goodsId) {
        LambdaQueryWrapper<WarehouseGoods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WarehouseGoods::getId, goodsId);
        WarehouseGoods warehouseGoods = this.baseMapper.selectOne(wrapper);
        if (warehouseGoods == null) {
            return "不存在该商品ID，无法删除！";
        }
        return null;
    }

    /**
     *  校验商品识别码
     * @param goodsCode
     * @return
     */
    public String verifyGoodsCode(String goodsCode, String companyUid) {
        LambdaQueryWrapper<WarehouseGoods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WarehouseGoods::getGoodsCode, goodsCode);
        wrapper.eq(WarehouseGoods::getCompanyUid, companyUid);
        WarehouseGoods warehouseGoods = this.baseMapper.selectOne(wrapper);
        if (warehouseGoods != null) {
            return "已存在该商品识别码，请修改！";
        }
        return null;
    }

    /**
     *  校验商品名称
     * @param goodsName
     * @return
     */
    public String verifyGoodsName(String goodsName, String companyUid) {
        LambdaQueryWrapper<WarehouseGoods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WarehouseGoods::getGoodsName, goodsName);
        wrapper.eq(WarehouseGoods::getCompanyUid, companyUid);
        WarehouseGoods warehouseGoods = this.baseMapper.selectOne(wrapper);
        if (warehouseGoods != null) {
            return "已存在该商品名称，请修改！";
        }
        return null;
    }

    /**
     *  校验用户
     * @param user
     * @return
     */
    public String verifySecurityUser(SysUserVo user) {
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        return null;
    }
}
