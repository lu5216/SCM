package com.system.systembase.impl.basicConfiguration.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.vo.Result;
import com.system.systembase.api.basicConfiguration.domain.SysCurrent;
import com.system.systembase.api.basicConfiguration.service.ISysCurrentService;
import com.system.systembase.api.basicConfiguration.vo.SysCurrentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author lutong
 * @data 2025-1-8 008 9:46
 */


@Slf4j
@RestController
@Api(tags = "基础配置-货币设置")
@RequestMapping("/current")
@Controller
public class SysCurrentController {

    @Autowired
    private ISysCurrentService currentService;

    @ApiOperation(value = "分页查询货币数据")
    @GetMapping(value = "/selectPage")
    public Result<Page<SysCurrent>> selectPage (@RequestParam(value = "key", required = false) String key,
                                                @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        try {
            Page<SysCurrent> page = currentService.selectPage(key, pageIndex, pageSize);
            return Result.success("分页查询货币数据成功！", page);
        } catch (Exception e) {
            log.error("分页查询货币数据失败，失败原因：" + e);
            return Result.failed("分页查询货币数据失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "获取币别列表")
    @GetMapping(value = "/getCurrentList")
    public Result<List<SysCurrentVo>> getCurrentList (@RequestParam(value = "key", required = false) String key) {
        try {
            List<SysCurrentVo> list = currentService.getCurrentList(key);
            return Result.success("获取币别列表成功！", list);
        } catch (Exception e) {
            log.error("获取币别列表失败，失败原因：" + e);
            return Result.failed("获取币别列表失败，失败原因：" + e);
        }
    }



    @ApiOperation(value = "修改币别数据")
    @GetMapping(value = "/editCurrent")
    public Result<String> editCurrent(@RequestParam(value = "currentCode") String currentCode,
                                      @RequestParam(value = "preExchangeRate", required = false) BigDecimal preExchangeRate,
                                      @RequestParam(value = "buyExchangeRate", required = false) BigDecimal buyExchangeRate,
                                      @RequestParam(value = "sellExchangeRate", required = false) BigDecimal sellExchangeRate,
                                      @RequestParam(value = "remark", required = false) String remark) {
        try {
            String result = currentService.editCurrent(currentCode, preExchangeRate, buyExchangeRate, sellExchangeRate, remark);
            if (result != null) {
                return Result.failed(result);
            }
            return Result.success("修改币别数据成功！");
        } catch (Exception e) {
            log.error("修改币别数据失败，失败原因：" + e);
            return Result.failed("修改币别数据失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "增加币别")
    @GetMapping(value = "/addCurrent")
    public Result<String> addCurrent(@RequestParam(value = "currentName") String currentName,
                                     @RequestParam(value = "currentCode") String currentCode,
                                     @RequestParam(value = "preExchangeRate", required = false) BigDecimal preExchangeRate,
                                     @RequestParam(value = "buyExchangeRate", required = false) BigDecimal buyExchangeRate,
                                     @RequestParam(value = "sellExchangeRate", required = false) BigDecimal sellExchangeRate,
                                     @RequestParam(value = "remark", required = false) String remark) {
        try {
            String result = currentService.addCurrent(currentName, currentCode, preExchangeRate, buyExchangeRate, sellExchangeRate, remark);
            if (result != null) {
                return Result.failed(result);
            }
            return Result.success("增加币别成功！");
        } catch (Exception e) {
            log.error("增加币别失败，失败原因：" + e);
            return Result.failed("增加币别失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "删除币别数据")
    @GetMapping(value = "/deleteCurrent")
    public Result<List<SysCurrentVo>> deleteCurrent (@RequestParam(value = "currentCode") String currentCode) {
        try {
            String result = currentService.deleteCurrent(currentCode);
            if (result != null) {
                return Result.failed(result);
            }
            return Result.success("删除币别数据成功！");
        } catch (Exception e) {
            log.error("删除币别数据失败，失败原因：" + e);
            return Result.failed("删除币别数据失败，失败原因：" + e);
        }
    }
}
