package com.system.warehouse.impl.goods.controller;

import com.system.common.vo.Result;
import com.system.warehouse.api.goods.domain.WarehouseGoodsNumber;
import com.system.warehouse.api.goods.service.IWarehouseGoodsNumberService;
import com.system.warehouse.api.goods.vo.GoodsSaveKeepInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lutong
 * @data 2025-1-10 010 17:21
 */

@Slf4j
@RestController
@Api(tags = "商品管理-商品条码")
@RequestMapping("/warehouse/goods/warehouseGoodNumber")
@Controller
public class WarehouseGoodsNumberController {

    @Autowired
    private IWarehouseGoodsNumberService warehouseGoodsNumberService;


    @ApiOperation(value = "根据商品识别码查询")
    @GetMapping(value = "/getByGoodsCode")
    public Result<List<WarehouseGoodsNumber>> getByGoodsCode(@RequestParam(value = "goodsCode") String goodsCode) {
        try {
            List<WarehouseGoodsNumber> list = warehouseGoodsNumberService.getByGoodsCode(goodsCode);
            return Result.success("根据商品识别码查询成功！", list);
        } catch (Exception e) {
            log.error("根据商品识别码查询失败，失败原因：" + e);
            return Result.failed("根据商品识别码查询失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "添加商品条码信息")
    @GetMapping(value = "/addGoodsNumber")
    public Result<String> addGoodsNumber(@RequestParam(value = "goodsCode") String goodsCode,
                                                       @RequestParam(value = "goodsNumber") String goodsNumber) {
        try {
            String result = warehouseGoodsNumberService.addGoodsNumber(goodsCode, goodsNumber);
            if (result == null) {
                return Result.success("添加商品条码信息成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("添加商品条码信息失败，失败原因：" + e);
            return Result.failed("添加商品条码信息失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "根据商品识别码批量删除条码")
    @GetMapping(value = "/deleteByGoodsCode")
    public Result<String> deleteByGoodsCode(@RequestParam(value = "goodsCode") String goodsCode) {
        try {
            String result = warehouseGoodsNumberService.deleteByGoodsCode(goodsCode);
            if (result == null) {
                return Result.success("根据商品识别码批量删除条码成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("根据商品识别码批量删除条码失败，失败原因：" + e);
            return Result.failed("根据商品识别码批量删除条码失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "删除条码")
    @GetMapping(value = "/deleteByGoodsNumber")
    public Result<String> deleteByGoodsNumber(@RequestParam(value = "goodsNumber") String goodsNumber) {
        try {
            String result = warehouseGoodsNumberService.deleteByGoodsNumber(goodsNumber);
            if (result == null) {
                return Result.success("根据商品条码删除条码成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("根据商品条码删除条码失败，失败原因：" + e);
            return Result.failed("根据商品条码删除条码失败，失败原因：" + e);
        }
    }



    @ApiOperation(value = "修改状态")
    @GetMapping(value = "/updateStatus")
    public Result<String> updateStatus(@RequestParam(value = "goodsNumber") String goodsNumber) {
        try {
            String result = warehouseGoodsNumberService.updateStatus(goodsNumber);
            if (result == null) {
                return Result.success("根据商品条码修改状态成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("根据商品条码修改状态失败，失败原因：" + e);
            return Result.failed("根据商品条码修改状态失败，失败原因：" + e);
        }
    }
}
