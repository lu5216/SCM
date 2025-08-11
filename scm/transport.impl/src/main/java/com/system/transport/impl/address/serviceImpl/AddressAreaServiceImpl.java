package com.system.transport.impl.address.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.redisTemplate.service.IRedisService;
import com.system.transport.api.address.domain.AddressArea;
import com.system.transport.api.address.service.IAddressAreaService;
import com.system.transport.api.address.vo.AddressAreaVo;
import com.system.transport.impl.address.mapper.AddressAreaMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lutong
 * @data 2025-1-22 022 13:46
 */

@Slf4j
@Service
public class AddressAreaServiceImpl extends ServiceImpl<AddressAreaMapper, AddressArea> implements IAddressAreaService {

    @Autowired
    private IRedisService redisService;

    @Override
    public Page<AddressAreaVo> getAddressAreaVoPage(String key, Integer pageIndex, Integer pageSize) {
        if (pageIndex == null) pageIndex = 1;
        if (pageSize == null) pageSize = 20;

        Page<AddressAreaVo> page = new Page<>(pageIndex, pageSize);
        Integer count = this.getAddressAreaCount(key);
        page.setTotal(count);
        if (count > 0) {
            List<AddressAreaVo> list = this.getAddressAreaVoList(key);
            int index = 0;
            for (AddressAreaVo vo : list) {
                // 序列
                index = index + 1;
                vo.setIndex(index);
                // 类型
                if (vo.getType() != null && !vo.getType().equals("")) {
                    String[] typeList = vo.getType().split(",");
                    String typeStr = "";
                    for (String type : typeList) {
                        if (Integer.parseInt(type) == 1) {
                            typeStr = typeStr + "装车地址,";
                        }
                        if (Integer.parseInt(type) == 2) {
                            typeStr = typeStr + "卸货地址,";
                        }
                    }
                    typeStr = typeStr.substring(0, typeStr.length() - 1);
                    vo.setTypeStr(typeStr);
                }
            }
            page.setRecords(list);
        }
        return page;
    }

    @Override
    public List<AddressAreaVo> getAddressAreaVoList(String key) {
        LambdaQueryWrapper<AddressArea> wrapper = new LambdaQueryWrapper<>();
        if (key != null && !key.equals("")) {
            wrapper.like(AddressArea::getProvince, key).or()
                    .like(AddressArea::getCity, key).or()
                    .like(AddressArea::getArea, key);
        }
        wrapper.orderByDesc(AddressArea::getCreateTime);
        List<AddressArea> list = this.baseMapper.selectList(wrapper);
        List<AddressAreaVo> vos = new ArrayList<>();
        for (AddressArea addressArea : list) {
            AddressAreaVo vo = new AddressAreaVo();
            vo.setId(addressArea.getId());
            vo.setType(addressArea.getType());
            vo.setAddress(addressArea.getAddress());
            vo.setProvince(addressArea.getProvince());
            vo.setCity(addressArea.getCity());
            vo.setArea(addressArea.getArea());
            vo.setRegion(addressArea.getRegion());
            vo.setDetailedAddress(addressArea.getProvince() + addressArea.getCity() + addressArea.getArea() + addressArea.getRegion());
            vo.setPhone(addressArea.getPhone());
            vo.setEmail(addressArea.getEmail());
            vo.setContact(addressArea.getContact());
            vo.setCompanyUid(addressArea.getCompanyUid());
            vo.setCreateUserName(addressArea.getCreateUserName());
            vo.setCreateTime(addressArea.getCreateTime());
            vos.add(vo);
        }
        return vos;
    }

    @Override
    public Integer getAddressAreaCount(String key) {
        LambdaQueryWrapper<AddressArea> wrapper = new LambdaQueryWrapper<>();
        if (key != null && !key.equals("")) {
            wrapper.like(AddressArea::getProvince, key).or()
                    .like(AddressArea::getCity, key).or()
                    .like(AddressArea::getArea, key);
        }
        return this.baseMapper.selectCount(wrapper);
    }


    @Override
    public String addAddressArea(AddressArea addressArea) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }

        // 按开头字母和天为维度生成递增的编码
        String addressCode = redisService.createNo("AA");
        addressArea.setAddressCode(addressCode);
        addressArea.setIdDel(false);
        addressArea.setCreateTime(new Date());
        addressArea.setCreateUserName(user.getLoginId());
        addressArea.setCompanyUid(user.getCompanyUid());
        this.baseMapper.insert(addressArea);
        return null;
    }

    @Override
    public String updateAddressArea(AddressArea addressArea) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }

        addressArea.setUpdateTime(new Date());
        addressArea.setUpdateUserName(user.getLoginId());
        this.baseMapper.updateById(addressArea);
        return null;
    }

    @Override
    public String deleteAddressAreaById(Integer id) {
        String verifiedId = this.verifyId(id);
        if (verifiedId != null) return verifiedId;
        this.baseMapper.deleteById(id);
        return null;
    }


    /**
     *  校验地址库ID
     * @param id
     * @return
     */
    public String verifyId(Integer id) {
        AddressArea addressArea = this.baseMapper.selectById(id);
        if (addressArea == null) {
            return "不存在该地址库ID，请重新填写!";
        }
        return null;
    }
}
