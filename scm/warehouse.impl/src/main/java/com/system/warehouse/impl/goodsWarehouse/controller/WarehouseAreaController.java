package com.system.warehouse.impl.goodsWarehouse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.vo.Result;
import com.system.warehouse.api.goodsWarehouse.domain.Warehouse;
import com.system.warehouse.api.goodsWarehouse.domain.WarehouseArea;
import com.system.warehouse.api.goodsWarehouse.param.WarehouseAreaModuleParam;
import com.system.warehouse.api.goodsWarehouse.param.WarehouseAreaParam;
import com.system.warehouse.api.goodsWarehouse.service.IWarehouseAreaService;
import com.system.warehouse.api.goodsWarehouse.vo.WarehouseAreaListVo;
import com.system.warehouse.api.goodsWarehouse.vo.WarehouseAreaVo;
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
 * @data 2025-1-7 007 9:12
 */

@Slf4j
@RestController
@Api(tags = "仓库设置-库区管理")
@RequestMapping("/warehouse/goodsWarehouse/warehouseArea")
@Controller
public class WarehouseAreaController {

    @Autowired
    private IWarehouseAreaService warehouseAreaService;

    @ApiOperation(value = "分页查询仓库")
    @GetMapping(value = "/selectPage")
    public Result<Page<WarehouseAreaVo>> selectPage(@RequestParam(value = "warehouseCode", required = false) String warehouseCode,
                                              @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        try {
            Page<WarehouseAreaVo> page = warehouseAreaService.selectPage(warehouseCode, pageIndex, pageSize);
            return Result.success("分页查询仓库成功！", page);
        } catch (Exception e) {
            log.error("分页查询仓库失败，失败原因：" + e);
            return Result.failed("分页查询仓库失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "查询库区列表-根据仓库ID")
    @GetMapping(value = "/selectByWarehouseId")
    public Result<List<WarehouseAreaListVo>> selectByWarehouseId(@RequestParam(value = "warehouseId") Integer warehouseId) {
        try {
            List<WarehouseAreaListVo> warehouseAreaList = warehouseAreaService.selectByWarehouseId(warehouseId);
            return Result.success("查询库区列表成功！", warehouseAreaList);
        } catch (Exception e) {
            log.error("查询库区列表失败，失败原因：" + e);
            return Result.failed("查询库区列表失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "保存库区信息")
    @PostMapping(value = "/saveWarehouseArea")
    public Result<String> saveWarehouseArea(@RequestBody WarehouseAreaParam param) {
        try {
            String result = warehouseAreaService.saveWarehouseArea(param);
            if (result == null) {
                return Result.success("保存库区信息成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("保存库区信息失败，失败原因：" + e);
            return Result.failed("保存库区信息失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "启用-停用库区")
    @GetMapping(value = "/updateStatus")
    public Result<String> updateStatus(@RequestParam(value = "warehouseAreaCode") String warehouseAreaCode) {
        try {
            String result = warehouseAreaService.updateStatus(warehouseAreaCode);
            if (result == null) {
                return Result.success("启用-停用库区成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("启用-停用库区失败，失败原因：" + e);
            return Result.failed("启用-停用库区失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "删除库区")
    @GetMapping(value = "/deleteWarehouseArea")
    public Result<String> deleteWarehouseArea(@RequestParam(value = "warehouseAreaCode") String warehouseAreaCode) {
        try {
            String result = warehouseAreaService.deleteWarehouseArea(warehouseAreaCode);
            if (result == null) {
                return Result.success("删除库区成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("删除库区失败，失败原因：" + e);
            return Result.failed("删除库区失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "编辑库区面积（未有覆盖校验）")
    @PostMapping(value = "/editModel")
    public Result<String> editModel(@RequestBody WarehouseAreaModuleParam param) {
        try {
            String result = warehouseAreaService.editModel(param);
            if (result == null) {
                return Result.success("编辑库区面积成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("编辑库区面积失败，失败原因：" + e);
            return Result.failed("编辑库区面积失败，失败原因：" + e);
        }
    }

}
