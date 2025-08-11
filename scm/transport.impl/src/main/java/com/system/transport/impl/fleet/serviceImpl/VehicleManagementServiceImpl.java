package com.system.transport.impl.fleet.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import com.system.transport.api.fleet.domain.VehicleManagement;
import com.system.transport.api.fleet.params.VehicleManagementParam;
import com.system.transport.api.fleet.service.IVehicleManagementService;
import com.system.transport.api.fleet.vo.DriverVehicleListVo;
import com.system.transport.impl.fleet.mapper.VehicleManagementMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lutong
 * @data 2025-2-10 010 17:16
 */

@Slf4j
@Service
public class VehicleManagementServiceImpl extends ServiceImpl<VehicleManagementMapper, VehicleManagement>
        implements IVehicleManagementService {

    @Autowired
    private VehicleManagementMapper vehicleManagementMapper;

    @Override
    public Page<VehicleManagement> getVehiclePage(VehicleManagementParam param) {
        if (param.getPageIndex() == null) param.setPageIndex(1);
        if (param.getPageSize() == null) param.setPageSize(20);
        Page<VehicleManagement> page = new Page<>();
        Integer count = vehicleManagementMapper.getVehicleCount(param);
        page.setTotal(count);

        if (count > 0) {
            List<VehicleManagement> vehicleList = vehicleManagementMapper.getVehicleList(param);
            page.setRecords(vehicleList);
        }
        return page;
    }

    @Override
    public String deleteVehicle(Integer id) {
        String result = this.deleteValidate(id);
        if (result != null) {
            return result;
        }

        this.baseMapper.deleteById(id);
        return null;
    }

    @Override
    public String saveOrUpdateVehicle(VehicleManagement vehicleManagement) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        if (vehicleManagement.getId() == null) {
            // 新增
            String result = this.saveValidate(vehicleManagement);
            if (result != null) {
                return result;
            }
            vehicleManagement.setCompanyUid(user.getCompanyUid());
            vehicleManagement.setCreateTime(new Date());
            vehicleManagement.setCreateUserName(user.getLoginId());
            this.baseMapper.insert(vehicleManagement);

        } else {
            // 更新
            String result = this.updateValidate(vehicleManagement);
            if (result != null) {
                return result;
            }
            vehicleManagement.setUpdateTime(new Date());
            vehicleManagement.setUpdateUserName(user.getLoginId());
            this.baseMapper.updateById(vehicleManagement);
        }
        return null;
    }


    @Override
    public List<DriverVehicleListVo> getVehicleList(String name, String companyUid) {
        LambdaQueryWrapper<VehicleManagement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VehicleManagement::getCompanyUid, companyUid);
        if (name != null && !name.equals("")) {
            wrapper.like(VehicleManagement::getLicenseNumber, name);
        }

        List<DriverVehicleListVo> vos = new ArrayList<>();
        List<VehicleManagement> list = this.baseMapper.selectList(wrapper);
        if (list.size() > 0) {
            for (VehicleManagement vehicleManagement : list) {
                DriverVehicleListVo vo = new DriverVehicleListVo();
                vo.setId(vehicleManagement.getId());
                vo.setName(vehicleManagement.getLicenseNumber());
                vos.add(vo);
            }
        }
        return vos;
    }


    @Override
    public List<VehicleManagement> export(VehicleManagementParam param) {
        List<VehicleManagement> vehicleList = vehicleManagementMapper.getVehicleList(param);
        log.info("车队管理-车辆管理导出的数据：{}", JSON.toJSONString(vehicleList));
        return vehicleList;
    }

    /**
     *  删除校验
     * @param id
     * @return
     */
    private String deleteValidate(Integer id) {
        if (id == null){
            return "参数不能为空";
        }
        VehicleManagement vehicleManagement = this.baseMapper.selectById(id);
        if (vehicleManagement == null) {
            return "不存在该数据";
        }
        return null;
    }

    /**
     *  保存校验
     * @param vehicleManagement
     * @return
     */
    private String saveValidate(VehicleManagement vehicleManagement) {
        if (vehicleManagement == null) {
            return "参数不能为空!";
        }
        if (vehicleManagement.getId() == null) {
            if (StringUtils.isEmpty(vehicleManagement.getLicenseNumber())) {
                return "车牌号不能为空!";
            }
            if (StringUtils.isEmpty(vehicleManagement.getVehicleType())) {
                return "车型不能为空!";
            }
            if (StringUtils.isEmpty(vehicleManagement.getCapacity())) {
                return "载量不能为空!";
            }
            if (vehicleManagement.getIsGps() == null) {
                return "是否装载GPS不能为空!";
            }
            if (vehicleManagement.getIsCsf() == null) {
                return "是否海关监管备案不能为空!";
            }
            if (vehicleManagement.getIsHgq() == null) {
                return "是否危品备案车辆不能为空!";
            }
        }
        return null;
    }


    /**
     *  修改校验
     * @param vehicleManagement
     * @return
     */
    private String updateValidate(VehicleManagement vehicleManagement) {
        if (vehicleManagement == null) {
            return "参数不能为空!";
        }
        if (vehicleManagement.getId() == null) {
            return "id不能为空";
        }
        return null;
    }
}
