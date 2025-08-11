package com.system.transport.impl.fleet.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.exception.CustomerAuthenticationException;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import com.system.transport.api.fleet.domain.DriverManagement;
import com.system.transport.api.fleet.domain.VehicleManagement;
import com.system.transport.api.fleet.params.DriverManagementParam;
import com.system.transport.api.fleet.service.IDriverManagementService;
import com.system.transport.api.fleet.service.IVehicleManagementService;
import com.system.transport.api.fleet.vo.DriverVehicleListVo;
import com.system.transport.api.fleet.vo.DriverVehicleVo;
import com.system.transport.impl.fleet.mapper.DriverManagementMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lutong
 * @data 2025-2-7 007 14:55
 */

@Slf4j
@Service
public class DriverManagementServiceImpl extends ServiceImpl<DriverManagementMapper, DriverManagement>
        implements IDriverManagementService {

    @Autowired
    private DriverManagementMapper driverManagementMapper;

    @Autowired
    private IVehicleManagementService vehicleManagementService;

    @Override
    public Page<DriverManagement> getDriverPage(DriverManagementParam param) {
        if (param.getPageIndex() == null) param.setPageIndex(1);
        if (param.getPageSize() == null) param.setPageSize(20);
        Page<DriverManagement> page = new Page<>(param.getPageIndex(), param.getPageSize());
        Integer count = driverManagementMapper.getDriverCount(param);
        page.setTotal(count);

        if (count > 0) {
            List<DriverManagement> driverList = driverManagementMapper.getDriverList(param);
            page.setRecords(driverList);
        }
        return page;
    }


    @Override
    public String deleteDriver(Integer id) {
        DriverManagement driverManagement = this.getDriverOne(id);
        if (driverManagement == null) {
            return "不存在该数据";
        }
        this.baseMapper.deleteById(id);
        return null;
    }


    @Override
    public String saveOrUpdateDriver(DriverManagement driverManagement) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        if (driverManagement.getId() == null) {
            // 新增
            driverManagement.setCompanyUid(user.getCompanyUid());
            driverManagement.setCreateUserName(user.getLoginId());
            driverManagement.setCreateTime(new Date());
            this.baseMapper.insert(driverManagement);
        } else {
            // 更新
            driverManagement.setUpdateTime(new Date());
            driverManagement.setUpdateUserName(user.getLoginId());
            this.baseMapper.updateById(driverManagement);
        }
        return null;
    }


    @Override
    public List<DriverVehicleVo> getAssociatedVehicle(Integer id, String companyUid) {
        // 获取司机名称
        DriverManagement driverManagement = this.baseMapper.selectById(id);
        if (driverManagement == null) {
            throw new CustomerAuthenticationException("不存在ID为【" + id + "】的司机");
        }

        // 获取车辆数据
        LambdaQueryWrapper<VehicleManagement> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(VehicleManagement::getAssociatedDriverId, id);
        wrapper.eq(VehicleManagement::getCompanyUid, companyUid);
        List<VehicleManagement> list = vehicleManagementService.list(wrapper);

        // 赋值
        List<DriverVehicleVo> vos = new ArrayList<>();
        if (list.size() > 0) {
            for (VehicleManagement vehicleManagement : list) {
                DriverVehicleVo vo = new DriverVehicleVo();
                vo.setDriverName(driverManagement.getName());
                vo.setLicenseNumber(vehicleManagement.getLicenseNumber());
                vo.setSpecialLicenseNumber(vehicleManagement.getSpecialLicenseNumber());
                vo.setVehicleType(vehicleManagement.getVehicleType());
                vo.setCapacity(vehicleManagement.getCapacity());
                vos.add(vo);
            }
        }
        return vos;
    }


    @Override
    public List<DriverVehicleListVo> getDriverList(String name, String companyUid) {
        LambdaQueryWrapper<DriverManagement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DriverManagement::getCompanyUid, companyUid);
        if (name != null && !name.equals("")) {
            wrapper.like(DriverManagement::getName, name);
        }

        List<DriverVehicleListVo> vos = new ArrayList<>();
        List<DriverManagement> list = this.baseMapper.selectList(wrapper);
        if (list.size() > 0) {
            for (DriverManagement driverManagement : list) {
                DriverVehicleListVo vo = new DriverVehicleListVo();
                vo.setId(driverManagement.getId());
                vo.setName(driverManagement.getName());
                vos.add(vo);
            }
        }
        return vos;
    }


    @Override
    public DriverVehicleListVo getDriverNameById(Integer id) {
        DriverManagement driverManagement = this.baseMapper.selectById(id);

        DriverVehicleListVo vo = new DriverVehicleListVo();
        if (driverManagement != null) {
            vo.setId(driverManagement.getId());
            vo.setName(driverManagement.getName());
        }
        return vo;
    }


    @Override
    public List<DriverManagement> export(DriverManagementParam param) {
        List<DriverManagement> driverList = driverManagementMapper.getDriverList(param);
        log.info("车队管理-车辆管理导出的数据：{}", JSON.toJSONString(driverList));
        return driverList;
    }

    public DriverManagement getDriverOne(Integer id) {
        return this.baseMapper.selectById(id);
    }
}
