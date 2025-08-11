package com.system.transport.impl.address.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.vo.Result;
import com.system.transport.api.address.domain.AddressArea;
import com.system.transport.api.address.service.IAddressAreaService;
import com.system.transport.api.address.vo.AddressAreaVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author lutong
 * @data 2025-1-22 022 13:46
 */

@Slf4j
@RestController
@Api(tags = "地址库")
@RequestMapping("/transport/address/addressArea")
@Controller
public class AddressAreaController {

    @Autowired
    private IAddressAreaService addressAreaService;


    @ApiOperation(value = "分页查询地址库")
    @GetMapping(value = "/getAddressAreaVoPage")
    public Result<Page<AddressAreaVo>> getAddressAreaVoPage(@RequestParam(value = "key", required = false) String key,
                                                            @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
                                                            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        try {
            Page<AddressAreaVo> page = addressAreaService.getAddressAreaVoPage(key, pageIndex, pageSize);
            return Result.success("分页查询地址库成功！", page);
        } catch (Exception e) {
            log.error("分页查询地址库失败，失败原因：" + e);
            return Result.failed("分页查询地址库失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "新增地址库")
    @PostMapping(value = "/addAddressArea")
    public Result<String> addAddressArea(@RequestBody AddressArea addressArea) {
        try {
            String result = addressAreaService.addAddressArea(addressArea);
            if (result == null) {
                return Result.success("新增地址库成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("新增地址库失败，失败原因：" + e);
            return Result.failed("新增地址库失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "修改地址库")
    @PostMapping(value = "/updateAddressArea")
    public Result<String> updateAddressArea(@RequestBody AddressArea addressArea) {
        try {
            String result = addressAreaService.updateAddressArea(addressArea);
            if (result == null) {
                return Result.success("修改地址库成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("修改地址库失败，失败原因：" + e);
            return Result.failed("修改地址库失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "根据ID删除")
    @GetMapping(value = "/deleteAddressAreaById")
    public Result<String> deleteAddressAreaById(@RequestParam(value = "id") Integer id) {
        try {
            String result = addressAreaService.deleteAddressAreaById(id);
            if (result == null) {
                return Result.success("根据ID删除地址库成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("根据ID删除地址库失败，失败原因：" + e);
            return Result.failed("根据ID删除地址库失败，失败原因：" + e);
        }
    }

}
