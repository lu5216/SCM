package com.system.systembase.impl.basicConfiguration.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.Result;
import com.system.common.vo.SysUserVo;
import com.system.systembase.api.basicConfiguration.domain.SysVendor;
import com.system.systembase.api.basicConfiguration.param.SysVendorParam;
import com.system.systembase.api.basicConfiguration.service.ISysVendorService;
import com.system.systembase.api.basicConfiguration.vo.SysCVListVo;
import com.system.systembase.api.basicConfiguration.vo.SysVendorDetail;
import com.system.systembase.api.basicConfiguration.vo.SysVendorVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author lutong
 * @data 2024-12-24 024 14:06
 */

@Slf4j
@RestController
@Api(tags = "基础配置-供应商管理")
@RequestMapping("/vendor")
@Controller
public class SysVendorController {

    @Autowired
    private ISysVendorService sysVendorService;

    @ApiOperation(value = "分页查询供应商数据")
    @PostMapping(value = "/selectVendor")
    public Result<Page<SysVendorVo>> selectVendorPage (@RequestBody SysVendorParam param) {
        try {
            Page<SysVendorVo> page = sysVendorService.selectVendorPage(param);
            return Result.success("分页查询供应商数据成功！", page);
        } catch (Exception e) {
            log.error("分页查询供应商数据失败，失败原因：" + e);
            return Result.failed("分页查询供应商数据失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "查询供应商详情数据")
    @GetMapping(value = "/selectVendorDetail")
    public Result<SysVendorDetail> selectVendorDetail(@RequestParam(value = "vendorCode") String vendorCode) {
        try {
            SysVendorDetail vendorVo = sysVendorService.selectVendorDetail(vendorCode);
            return Result.success("查询供应商详情数据成功！", vendorVo);
        } catch (Exception e) {
            log.error("查询供应商详情数据失败，失败原因：" + e);
            return Result.failed("查询供应商详情数据失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "修改供应商详情数据")
    @PostMapping(value = "/updateVendorDetail")
    public Result<String> updateVendorDetail(@RequestBody SysVendorDetail detail) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return Result.failed("账号未登入，请重新登入!");
        }
        detail.setUpdateTime(new Date());
        detail.setUpdateUserName(user.getLoginId());
        try {
            String result = sysVendorService.updateVendorDetail(detail);
            if (result != null) {
                return Result.failed(result);
            }
            return Result.success("修改供应商详情数据成功！");
        } catch (Exception e) {
            log.error("修改供应商详情数据失败，失败原因：" + e);
            return Result.failed("修改供应商详情数据失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "增加供应商数据")
    @PostMapping(value = "/addVendorData")
    public Result<String> addVendorData(@RequestBody SysVendor sysVendor) {
        try {
            String result = sysVendorService.addVendorData(sysVendor);
            if (result != null) {
                return Result.failed(result);
            }
            return Result.success("增加供应商数据成功！");
        } catch (Exception e) {
            log.error("增加供应商数据失败，失败原因：" + e);
            return Result.failed("增加供应商数据失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "删除供应商数据")
    @GetMapping(value = "/deleteVendorData")
    public Result<String> deleteVendorData(@RequestParam(value = "id") Integer id) {
        try {
            String result = sysVendorService.deleteVendorData(id);
            if (result != null) {
                return Result.failed(result);
            }
            return Result.success("删除供应商数据成功！");
        } catch (Exception e) {
            log.error("删除供应商数据失败，失败原因：" + e);
            return Result.failed("删除供应商数据失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "更改合作状态")
    @GetMapping(value = "/updateIsCooperation")
    public Result<String> updateIsCooperation(@RequestParam(value = "vendorCode") String vendorCode) {
        try {
            String result = sysVendorService.updateIsCooperation(vendorCode);
            if (result != null) {
                return Result.failed(result);
            }
            return Result.success("修改成功！");
        } catch (Exception e) {
            log.error("修改失败，失败原因：" + e);
            return Result.failed("修改失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "更改启用状态")
    @GetMapping(value = "/updateIsEnable")
    public Result<String> updateIsEnable(@RequestParam(value = "vendorCode") String vendorCode) {
        try {
            String result = sysVendorService.updateIsEnable(vendorCode);
            if (result != null) {
                return Result.failed(result);
            }
            return Result.success("修改成功！");
        } catch (Exception e) {
            log.error("修改失败，失败原因：" + e);
            return Result.failed("修改失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "明细-更改生效状态")
    @GetMapping(value = "/updateIsValidated")
    public Result<String> updateIsValidated(@RequestParam(value = "vendorCode") String vendorCode) {
        try {
            String result = sysVendorService.updateIsValidated(vendorCode);
            if (result != null) {
                return Result.failed(result);
            }
            return Result.success("修改成功！");
        } catch (Exception e) {
            log.error("修改失败，失败原因：" + e);
            return Result.failed("修改失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "获取供应商列表")
    @GetMapping(value = "/getVendorList")
    public Result<List<SysCVListVo>> getVendorList(@RequestParam(value = "key", required = false) String key) {
        try {
            List<SysCVListVo> list = sysVendorService.getVendorList(key);
            return Result.success("获取供应商列表成功！", list);
        } catch (Exception e) {
            log.error("获取供应商列表失败，失败原因：" + e);
            return Result.failed("获取供应商列表失败，失败原因：" + e);
        }
    }
}
