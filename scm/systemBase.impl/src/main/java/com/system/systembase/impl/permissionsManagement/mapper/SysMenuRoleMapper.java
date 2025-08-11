package com.system.systembase.impl.permissionsManagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.systembase.api.permissionsManagement.domain.SysMenuRole;
import com.system.systembase.api.permissionsManagement.param.EditMenuRoleParam;
import com.system.systembase.api.permissionsManagement.param.SysMenuRoleParam;
import com.system.systembase.api.permissionsManagement.vo.SysMenuRoleVo;
import com.system.systembase.impl.permissionsManagement.provider.SysMenuRoleProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author lutong
 * @data 2024-12-9 009 17:02
 */

@Mapper
public interface SysMenuRoleMapper extends BaseMapper<SysMenuRole> {

    /**
     * 编辑角色菜单
     *
     * @param param param
     */
    @SelectProvider(type = SysMenuRoleProvider.class, method = "editRoleMenu")
    void editRoleMenu(EditMenuRoleParam param);

    /**
     *  展示角色菜单数据
     * @param param param
     * @return Page
     */
    @SelectProvider(type = SysMenuRoleProvider.class, method = "selectMenuRoleList")
    List<SysMenuRoleVo> selectMenuRoleList(SysMenuRoleParam param);

    /**
     *  根据菜单编码查询
     * @param menuCode 菜单编码
     * @return String
     */
    @SelectProvider(type = SysMenuRoleProvider.class, method = "selectMenuRoleByMenuCode")
    List<SysMenuRoleVo> selectMenuRoleByMenuCode(String menuCode);

    /**
     *  根据角色Uid查询
     * @param roleUid 角色Uid
     * @return String
     */
    @SelectProvider(type = SysMenuRoleProvider.class, method = "selectMenuRoleByRoleUid")
    List<SysMenuRoleVo> selectMenuRoleByRoleUid(String roleUid);

    /**
     *  根据菜单编码和角色Uid查询
     * @param menuCode 菜单编码
     * @param roleUid 角色Uid
     * @return String
     */
    @SelectProvider(type = SysMenuRoleProvider.class, method = "selectMenuRoleByMenuCodeAndRoleUid")
    SysMenuRoleVo selectMenuRoleByMenuCodeAndRoleUid(String menuCode, String roleUid);
}
