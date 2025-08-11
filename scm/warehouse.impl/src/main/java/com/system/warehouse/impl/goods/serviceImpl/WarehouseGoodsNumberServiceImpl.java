package com.system.warehouse.impl.goods.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import com.system.warehouse.api.goods.domain.WarehouseGoods;
import com.system.warehouse.api.goods.domain.WarehouseGoodsNumber;
import com.system.warehouse.api.goods.service.IWarehouseGoodsNumberService;
import com.system.warehouse.api.goods.service.IWarehouseGoodsService;
import com.system.warehouse.impl.goods.mapper.WarehouseGoodsNumberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lutong
 * @data 2025-1-10 010 17:20
 */

@Slf4j
@Service
public class WarehouseGoodsNumberServiceImpl extends ServiceImpl<WarehouseGoodsNumberMapper, WarehouseGoodsNumber> implements IWarehouseGoodsNumberService {

    private final IWarehouseGoodsService warehouseGoodsService;
    public WarehouseGoodsNumberServiceImpl(@Lazy IWarehouseGoodsService warehouseGoodsService) {
        this.warehouseGoodsService = warehouseGoodsService;
    }

    @Autowired
    private WarehouseGoodsNumberMapper warehouseGoodsNumberMapper;


    @Override
    public List<WarehouseGoodsNumber> getByGoodsCode(String goodsCode) {
        List<WarehouseGoodsNumber> warehouseGoodsNumber = warehouseGoodsNumberMapper.getByGoodsCode(goodsCode);
        log.info("根据商品识别码【{}】查询出来的商品条码数据列表：{}", goodsCode, JSON.toJSONString(warehouseGoodsNumber));
        return warehouseGoodsNumber;
    }

    @Override
    public String addGoodsNumber(String goodsCode, String goodsNumber) {
        // 获取用户数据
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }

        // 校验
        String verifyGoodNumber = this.verifyGoodNumber(goodsCode, goodsNumber, user);
        if (verifyGoodNumber != null) return verifyGoodNumber;

        WarehouseGoodsNumber warehouseGoodsNumber = new WarehouseGoodsNumber();

        warehouseGoodsNumber.setCompanyUid(user.getCompanyUid());
        warehouseGoodsNumber.setCreateUserName(user.getLoginId());
        warehouseGoodsNumber.setCreateTime(new Date());

        // 获取商品信息
        WarehouseGoods goods = warehouseGoodsService.findOneByGoodsCode(goodsCode, user.getCompanyUid());
        if (goods == null) {
            return "不存在编码为【" + goodsCode + "】的商品！";
        }
        warehouseGoodsNumber.setGoodsId(goods.getId());
        warehouseGoodsNumber.setGoodsCode(goods.getGoodsCode());

        warehouseGoodsNumber.setGoodsNumber(goodsNumber);
        warehouseGoodsNumber.setStatus(true);
        warehouseGoodsNumber.setIsDel(false);
        this.baseMapper.insert(warehouseGoodsNumber);
        return null;
    }

    @Override
    public String deleteByGoodsCode(String goodsCode) {
        // 校验
        List<WarehouseGoodsNumber> warehouseGoodsNumber = warehouseGoodsNumberMapper.getByGoodsCode(goodsCode);
        if (warehouseGoodsNumber == null) {
            return "不存在与商品识别码【" + goodsCode + "】关联的商品条码！";
        }
        // 获取用户数据
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        for (WarehouseGoodsNumber number : warehouseGoodsNumber) {
            number.setIsDel(true);
            number.setUpdateTime(new Date());
            number.setUpdateUserName(user.getLoginId());
            this.baseMapper.updateById(number);
        }
        return null;
    }

    @Override
    public String deleteByGoodsNumber(String goodsNumber) {
        // 校验
        WarehouseGoodsNumber warehouseGoodsNumber = warehouseGoodsNumberMapper.getByGoodsNumber(goodsNumber);
        if (warehouseGoodsNumber == null) {
            return "商品条码【" + goodsNumber + "】不存在！";
        }
        // 获取用户数据
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        warehouseGoodsNumber.setIsDel(true);
        warehouseGoodsNumber.setUpdateTime(new Date());
        warehouseGoodsNumber.setUpdateUserName(user.getLoginId());
        this.baseMapper.updateById(warehouseGoodsNumber);
        return null;
    }

    @Override
    public String updateStatus(String goodsNumber) {
        // 校验
        WarehouseGoodsNumber warehouseGoodsNumber = warehouseGoodsNumberMapper.getByGoodsNumber(goodsNumber);
        if (warehouseGoodsNumber == null) {
            return "商品条码【" + goodsNumber + "】不存在！";
        }
        // 获取用户数据
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        warehouseGoodsNumber.setStatus(!warehouseGoodsNumber.getStatus());
        warehouseGoodsNumber.setUpdateTime(new Date());
        warehouseGoodsNumber.setUpdateUserName(user.getLoginId());
        this.baseMapper.updateById(warehouseGoodsNumber);
        return null;
    }

    /**
     *  校验条码
     * @param goodsNumber
     * @return
     */
    public String verifyGoodNumber(String goodsCode, String goodsNumber, SysUserVo user) {
        WarehouseGoodsNumber warehouseGoodsNumber = warehouseGoodsNumberMapper.getByGoodsNumber(goodsNumber);
        if (warehouseGoodsNumber != null && warehouseGoodsNumber.getIsDel()) {
            // 将已经删除的修改为未删除
            warehouseGoodsNumber.setGoodsCode(goodsCode);
            warehouseGoodsNumber.setIsDel(false);
            warehouseGoodsNumber.setUpdateTime(new Date());
            warehouseGoodsNumber.setUpdateUserName(user.getLoginId());
        }
        else if (warehouseGoodsNumber != null) {
            return "条码【" + goodsNumber + "】已存在！";
        }
        return null;
    }
}
