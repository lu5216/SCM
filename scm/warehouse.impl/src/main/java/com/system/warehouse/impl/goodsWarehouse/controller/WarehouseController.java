package com.system.warehouse.impl.goodsWarehouse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.vo.Result;
import com.system.warehouse.api.goodsWarehouse.domain.Warehouse;
import com.system.warehouse.api.goodsWarehouse.service.IWarehouseService;
import com.system.warehouse.api.goodsWarehouse.vo.WarehouseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author lutong
 * @data 2025-1-6 006 15:27
 */
@Slf4j
@RestController
@Api(tags = "仓库设置-仓库管理")
@RequestMapping("/warehouse/goodsWarehouse/warehouse")
@Controller
public class WarehouseController {

    @Autowired
    private IWarehouseService warehouseService;

    @ApiOperation(value = "分页查询仓库")
    @GetMapping(value = "/selectPage")
    public Result<Page<Warehouse>> selectPage(@RequestParam(value = "warehouseCode", required = false) String warehouseCode,
                                         @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        try {
            Page<Warehouse> page = warehouseService.selectPage(warehouseCode, pageIndex, pageSize);
            return Result.success("分页查询仓库成功！", page);
        } catch (Exception e) {
            log.error("分页查询仓库失败，失败原因：" + e);
            return Result.failed("分页查询仓库失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "查询仓库列表-模糊查询")
    @GetMapping(value = "/selectWarehouseList")
    public Result<List<WarehouseVo>> selectWarehouseList(@RequestParam(value = "key", required = false) String key) {
        try {
            List<WarehouseVo> list = warehouseService.selectWarehouseList(key);
            return Result.success("查询仓库列表成功！", list);
        } catch (Exception e) {
            log.error("查询仓库列表失败，失败原因：" + e);
            return Result.failed("查询仓库列表失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "保存仓库信息")
    @PostMapping(value = "/saveWarehouse")
    public Result<String> saveWarehouse(@RequestBody Warehouse warehouse) {
        try {
            String result = warehouseService.saveWarehouse(warehouse);
            if (result == null) {
                return Result.success("保存仓库信息成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("保存仓库信息失败，失败原因：" + e);
            return Result.failed("保存仓库信息失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "启用-停用仓库")
    @GetMapping(value = "/updateStatus")
    public Result<String> updateStatus(@RequestParam(value = "warehouseCode") String warehouseCode) {
        try {
            String result = warehouseService.updateStatus(warehouseCode);
            if (result == null) {
                return Result.success("启用-停用仓库成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("启用-停用仓库失败，失败原因：" + e);
            return Result.failed("启用-停用仓库失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "删除仓库")
    @GetMapping(value = "/deleteWarehouse")
    public Result<String> deleteWarehouse(@RequestParam(value = "warehouseCode") String warehouseCode) {
        try {
            String result = warehouseService.deleteWarehouse(warehouseCode);
            if (result == null) {
                return Result.success("删除仓库成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("删除仓库失败，失败原因：" + e);
            return Result.failed("删除仓库失败，失败原因：" + e);
        }
    }
}
