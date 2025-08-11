package com.system.systembase.impl.basicConfiguration.controller;


import com.system.common.utils.SecurityUtils;
import com.system.common.vo.Result;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.basicConfiguration.domain.SysBaseData;
import com.system.systembase.api.basicConfiguration.service.ISysBaseDataService;
import com.system.systembase.api.basicConfiguration.vo.SysBaseDataVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lutong
 * @data 2024-12-11 011 14:41
 */

@Slf4j
@RestController
@Api(tags = "基础配置-数据字典")
@RequestMapping("/dict")
@Controller
public class SysBaseDataController  {

    @Autowired
    private ISysBaseDataService sysBaseDataService;

    @ApiOperation(value = "查询数据字典列表数据")
    @GetMapping(value = "/selectDict")
    public Result<List<SysBaseDataVo>> selectDict(@RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "parentCode", required = false) String parentCode) {
        try {
            if (parentCode == null || parentCode.equals("")) {
                List<SysBaseDataVo> dict = sysBaseDataService.selectParentDict(name);
                return Result.success("父列表查询成功！", dict);
            } else {
                List<SysBaseDataVo> dict = sysBaseDataService.selectDict(name, parentCode);
                return Result.success("子列表查询成功！", dict);
            }
        } catch (Exception e) {
            log.error("查询数据字典列表数据失败，失败原因：" + e);
            return Result.failed("查询数据字典列表数据失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "修改数据字典")
    @PostMapping(value = "/updateDict")
    public Result<String> selectDict(@RequestBody SysBaseData param) {
        try {
            String result = sysBaseDataService.updateDict(param);
            if (result == null) {
                return Result.success("修改数据字典成功！");
            } else {
                return Result.success("修改数据字典失败！");
            }
        } catch (Exception e) {
            log.error("修改数据字典失败，失败原因：" + e);
            return Result.failed("修改数据字典失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "新增数据字典")
    @PostMapping(value = "/addDict")
    public Result<String> addDict(@RequestBody SysBaseData param) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return Result.failed("无法获取用户信息，请重新登入!");
        } else {
            param.setCompanyUid(user.getCompanyUid());
        }
        try {
            String result = sysBaseDataService.addDict(param);
            if (result == null) {
                return Result.success("新增数据字典成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("新增数据字典失败，失败原因：" + e);
            return Result.failed("新增数据字典失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "删除数据字典")
    @GetMapping(value = "/deleteDict")
    public Result<String> deleteDict(@RequestParam(value = "id") Integer id) {
        try {
            String result = sysBaseDataService.deleteDict(id);
            if (result == null) {
                return Result.success("删除数据字典成功！");
            } else {
                return Result.success("删除数据字典失败！");
            }
        } catch (Exception e) {
            log.error("删除数据字典失败，失败原因：" + e);
            return Result.failed("删除数据字典失败，失败原因：" + e);
        }
    }
}
