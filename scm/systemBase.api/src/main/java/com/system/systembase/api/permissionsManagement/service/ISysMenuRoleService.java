package com.system.systembase.api.permissionsManagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.system.systembase.api.permissionsManagement.domain.SysMenuRole;
import com.system.systembase.api.permissionsManagement.param.EditMenuRoleParam;
import com.system.systembase.api.permissionsManagement.param.SysMenuRoleParam;
import com.system.systembase.api.permissionsManagement.vo.SysMenuRoleVo;

import java.util.List;

/**
 * @author lutong
 * @data 2024-12-9 009 17:01
 */
public interface ISysMenuRoleService extends IService<SysMenuRole> {

    /**
     *  编辑角色菜单
     * @param param param
     * @return String
     */
    String editRoleMenu(EditMenuRoleParam param);


    /**
     *  展示角色菜单数据
     * @param param param
     * @return Page
     */
    List<SysMenuRoleVo> selectMenuRoleList(SysMenuRoleParam param);

    /**
     *  根据菜单编码查询
     * @param menuCode 菜单编码
     * @return String
     */
    List<SysMenuRoleVo> selectMenuRoleByMenuCode(String menuCode);

    /**
     *  根据角色Uid查询
     * @param roleUid 角色Uid
     * @return String
     */
    List<SysMenuRoleVo> selectMenuRoleByRoleUid(String roleUid);

    /**
     *  根据菜单编码和角色Uid查询
     * @param menuCode 菜单编码
     * @param roleUid 角色Uid
     * @return String
     */
    SysMenuRoleVo selectMenuRoleByMenuCodeAndRoleUid(String menuCode, String roleUid);
}
