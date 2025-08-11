package com.system.systembase.api.basicConfiguration.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.systembase.api.basicConfiguration.domain.SysClient;
import com.system.systembase.api.basicConfiguration.param.SysClientParam;
import com.system.systembase.api.basicConfiguration.vo.SysCVListVo;
import com.system.systembase.api.basicConfiguration.vo.SysClientDetail;
import com.system.systembase.api.basicConfiguration.vo.SysClientVo;

import java.util.List;

/**
 * @author lutong
 * @data 2024-12-24 024 14:08
 */

public interface ISysClientService extends IService<SysClient> {
    /**
     *  分页查询客户数据
     * @param param
     * @return
     */
    Page<SysClientVo> selectClientPage(SysClientParam param);

    /**
     *  查询客户详情数据
     * @param clientCode
     * @return
     */
    SysClientDetail selectClientDetail(String clientCode);

    /**
     *  修改客户详细数据
     * @param detail
     * @return
     */
    String updateClientDetail(SysClientDetail detail);

    /**
     *  增加客户数据
     * @param client
     * @return
     */
    String addClientData(SysClient client);

    /**
     *  删除客户数据
     * @param id 客户ID
     * @return
     */
    String deleteClientData(Integer id);


    /**
     *  更改合作状态
     * @param clientCode
     * @return
     */
    String updateIsCooperation(String clientCode);

    /**
     *  更改生效状态
     * @param clientCode
     * @return
     */
    String updateIsValidated(String clientCode);

    /**
     *  获取客户列表
     * @param key
     * @return
     */
    List<SysCVListVo> getClientList(String key);


    /**
     *  根据客户编码查询一条数据
     * @param clientCode
     * @return
     */
    SysClient getByClientCode(String clientCode);
}
