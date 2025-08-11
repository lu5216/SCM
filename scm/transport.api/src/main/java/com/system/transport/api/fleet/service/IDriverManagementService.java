package com.system.transport.api.fleet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.transport.api.fleet.domain.DriverManagement;
import com.system.transport.api.fleet.params.DriverManagementParam;
import com.system.transport.api.fleet.vo.DriverVehicleListVo;
import com.system.transport.api.fleet.vo.DriverVehicleVo;

import java.util.List;

/**
 * @author lutong
 * @data 2025-2-7 007 14:54
 */
public interface IDriverManagementService extends IService<DriverManagement> {

    /**
     *  分页查询
     * @param param
     * @return
     */
    Page<DriverManagement> getDriverPage(DriverManagementParam param);

    /**
     *  根据ID删除一个
     * @param id
     * @return
     */
    String deleteDriver(Integer id);

    /**
     *  保存或修改
     * @param driverManagement
     * @return
     */
    String saveOrUpdateDriver(DriverManagement driverManagement);

    /**
     *  根据司机ID查询关联车辆
     * @param id
     * @param companyUid
     * @return
     */
    List<DriverVehicleVo> getAssociatedVehicle(Integer id, String companyUid);


    /**
     *  根据name模糊搜索司机
     * @param name
     * @param companyUid
     * @return
     */
    List<DriverVehicleListVo> getDriverList(String name, String companyUid);


    /**
     *  根据ID获取Name
     * @param id
     * @return
     */
    DriverVehicleListVo getDriverNameById(Integer id);

    /**
     *  导出数据
     * @param param
     * @return
     */
    List<DriverManagement> export(DriverManagementParam param);
}
