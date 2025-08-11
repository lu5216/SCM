package com.system.transport.api.fleet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.transport.api.fleet.domain.VehicleManagement;
import com.system.transport.api.fleet.params.VehicleManagementParam;
import com.system.transport.api.fleet.vo.DriverVehicleListVo;

import java.util.List;

/**
 * @author lutong
 * @data 2025-2-10 010 17:09
 */
public interface IVehicleManagementService extends IService<VehicleManagement> {

    /**
     *  分页查询
     * @param param
     * @return
     */
    Page<VehicleManagement> getVehiclePage(VehicleManagementParam param);


    /**
     *  根据ID删除一个
     * @param id
     * @return
     */
    String deleteVehicle(Integer id);

    /**
     *  保存或修改
     * @param vehicleManagement
     * @return
     */
    String saveOrUpdateVehicle(VehicleManagement vehicleManagement);


    /**
     *  根据name模糊搜索司机
     * @param name
     * @param companyUid
     * @return
     */
    List<DriverVehicleListVo> getVehicleList(String name, String companyUid);

    /**
     *  导出数据
     * @param param
     * @return
     */
    List<VehicleManagement> export(VehicleManagementParam param);
}
