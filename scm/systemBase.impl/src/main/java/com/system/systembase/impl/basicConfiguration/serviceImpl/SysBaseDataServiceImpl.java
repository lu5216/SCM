package com.system.systembase.impl.basicConfiguration.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.exception.CustomerAuthenticationException;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.basicConfiguration.domain.SysBaseData;
import com.system.systembase.api.basicConfiguration.service.ISysBaseDataService;
import com.system.systembase.api.basicConfiguration.vo.SysBaseDataVo;
import com.system.systembase.impl.basicConfiguration.mapper.SysBaseDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lutong
 * @data 2024-12-11 011 14:42
 */

@Slf4j
@Service
public class SysBaseDataServiceImpl extends ServiceImpl<SysBaseDataMapper, SysBaseData> implements ISysBaseDataService {

    @Autowired
    private SysBaseDataMapper sysBaseDataMapper;

    @Override
    public List<SysBaseDataVo> selectParentDict(String name) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            throw new CustomerAuthenticationException("账号未登入，请重新登入");
        }
        List<SysBaseDataVo> dictList = sysBaseDataMapper.selectParentDict(name, user.getCompanyUid());
        return dictList;
    }

    @Override
    public List<SysBaseDataVo> selectDict(String name, String parentCode) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            throw new CustomerAuthenticationException("账号未登入，请重新登入");
        }
        List<SysBaseDataVo> dictList = sysBaseDataMapper.selectDict(name, parentCode, user.getCompanyUid());
        return dictList;
    }

    @Override
    public String updateDict(SysBaseData param) {
        // 先查询
        SysBaseData sysBaseData = this.baseMapper.selectById(param.getId());
        if (sysBaseData == null) {
            return "不存在改数据，请维护！";
        }
        this.baseMapper.updateById(param);
        return null;
    }

    @Override
    public String addDict(SysBaseData param) {
        if (StringUtils.isBlank(param.getCode())) {
            return "编码不能为空!";
        }
        if (StringUtils.isBlank(param.getParentCode())) {
            return "父节点编码不能为空!";
        }
        if (StringUtils.isBlank(param.getName())) {
            return "名称不能为空!";
        }

        LambdaQueryWrapper<SysBaseData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysBaseData::getParentCode, param.getParentCode());
        wrapper.eq(SysBaseData::getCode, param.getCode());
        SysBaseData data = this.baseMapper.selectOne(wrapper);
        if (data != null) {
            return "编码重复，请重新填写!";
        }
        this.baseMapper.insert(param);
        return null;
    }

    @Override
    public String deleteDict(Integer id) {
        // 先查询
        SysBaseData sysBaseData = this.baseMapper.selectById(id);
        if (sysBaseData == null) {
            return "该数据已删除，请勿重复删除！";
        }
        this.baseMapper.deleteById(id);
        return null;
    }
}
