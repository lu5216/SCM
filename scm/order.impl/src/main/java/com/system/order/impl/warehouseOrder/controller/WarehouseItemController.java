package com.system.order.impl.warehouseOrder.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.Result;
import com.system.common.vo.SysUserVo;
import com.system.order.api.salesOrder.domain.SalesOrder;
import com.system.order.api.warehouseOrder.domain.WarehouseItem;
import com.system.order.api.warehouseOrder.params.WarehouseItemParam;
import com.system.order.api.warehouseOrder.params.WarehouseItemSaveParam;
import com.system.order.api.warehouseOrder.service.IWarehouseItemService;
import com.system.order.api.warehouseOrder.vo.WarehouseItemDetail;
import com.system.order.api.warehouseOrder.vo.WarehouseItemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lutong
 * @data 2025-2-14 014 14:34
 */

@Slf4j
@RestController
@Api(tags = "订单管理-出入库管理")
@RequestMapping("/order/warehouseOrder/warehouseItem")
@Controller
public class WarehouseItemController {

    @Autowired
    private IWarehouseItemService warehouseItemService;

    @ApiOperation("分页查询")
    @PostMapping("/selectPage")
    public Result<Page<WarehouseItemVo>> selectPage(@RequestBody WarehouseItemParam param) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return Result.failed("账号未登入，请重新登入!");
        }
        param.setCompanyUid(user.getCompanyUid());
        try {
            log.info("订单管理-出入库管理-分页查询-参数：{}", param);
            Page<WarehouseItemVo> page = warehouseItemService.selectPage(param);
            return Result.success("分页查询成功！", page);
        } catch (Exception e) {
            log.error("分页查询失败，失败原因：", e);
            return Result.failed("分页查询失败，失败原因：" + e);
        }
    }


    @ApiOperation("查询出入库订单明细")
    @GetMapping("/getDetailById")
    public Result<WarehouseItemDetail> getDetailById(@RequestParam(value = "id") Integer id) {
        try {
            log.info("订单管理-出入库管理-查询出入库订单明细-参数：{}", id);
            WarehouseItemDetail detail = warehouseItemService.getDetailById(id);
            return Result.success("查询出入库订单明细成功！", detail);
        } catch (Exception e) {
            log.error("查询出入库订单明细失败，失败原因：", e);
            return Result.failed("查询出入库订单明细失败，失败原因：" + e);
        }
    }


    @ApiOperation("新增或修改")
    @PostMapping("/save")
    public Result<String> save(@RequestBody WarehouseItemSaveParam param) {
        try {
            log.info("订单管理-出入库管理-新增或修改-参数：{}", param);
            String errMSG = warehouseItemService.insertAndUpdate(param);
            if (errMSG == null) {
                return Result.success("保存成功！");
            } else {
                return Result.failed(errMSG);
            }
        } catch (Exception e) {
            log.error("保存失败，失败原因：", e);
            return Result.failed("保存失败，失败原因：" + e);
        }
    }


    @ApiOperation("开始出入库")
    @GetMapping("/begin")
    public Result<String> begin(@RequestParam(value = "id") Integer id) {
        try {
            log.info("订单管理-出入库管理-开始出入库-参数：{}", id);
            String errMSG = warehouseItemService.begin(id);
            if (errMSG == null) {
                return Result.success("开始出入库！");
            } else {
                return Result.failed(errMSG);
            }
        } catch (Exception e) {
            log.error("开始出入库失败，失败原因：", e);
            return Result.failed("开始出入库失败，失败原因：" + e);
        }
    }


    @ApiOperation("确认出入库完成")
    @GetMapping("/confirm")
    public Result<String> confirmFinished(@RequestParam(value = "id") Integer id) {
        try {
            log.info("订单管理-出入库管理-开始出入库-参数：{}", id);
            String errMSG = warehouseItemService.confirmFinished(id);
            if (errMSG == null) {
                return Result.success("确认出入库完成！");
            } else {
                return Result.failed(errMSG);
            }
        } catch (Exception e) {
            log.error("确认出入库完成失败，失败原因：", e);
            return Result.failed("确认出入库完成失败，失败原因：" + e);
        }
    }


    @ApiOperation("审核")
    @GetMapping("/audit")
    public Result<String> audit(@RequestParam(value = "id") Integer id,
                                @RequestParam(value = "auditStatus") Integer auditStatus,
                                @RequestParam(value = "auditReason", required = false) String auditReason) {
        try {
            log.info("订单管理-出入库管理-审核-参数：{}", id);
            String errMSG = warehouseItemService.auditById(id, auditStatus, auditReason);
            if (errMSG == null) {
                return Result.success("审核完成！");
            } else {
                return Result.failed(errMSG);
            }
        } catch (Exception e) {
            log.error("审核失败，失败原因：", e);
            return Result.failed("审核失败，失败原因：" + e);
        }
    }


    @ApiOperation("取消")
    @GetMapping("/cancel")
    public Result<String> cancel(@RequestParam(value = "id") Integer id,
                                 @RequestParam(value = "cancelReason", required = false) String cancelReason) {
        try {
            log.info("订单管理-出入库管理-取消-参数：{}", id);
            String errMSG = warehouseItemService.cancelById(id, cancelReason);
            if (errMSG == null) {
                return Result.success("取消完成！");
            } else {
                return Result.failed(errMSG);
            }
        } catch (Exception e) {
            log.error("取消失败，失败原因：", e);
            return Result.failed("取消失败，失败原因：" + e);
        }
    }

    @ApiOperation("根据单号查询")
    @GetMapping("/getByItemNoActive")
    public Result<Integer> getByItemNoActive(@RequestParam(value = "itemNo") String itemNo) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return Result.failed("获取用户信息异常!");
        }
        try {
            log.info("订单管理-出入库订单-根据单号查询-参数：{}", itemNo);
            WarehouseItem warehouseItem = warehouseItemService.findByItemNo(itemNo, user.getCompanyUid());
            Integer itemNoActive = 1;
            if (warehouseItem.getItemStatus() == 1 && warehouseItem.getInOutStatus() == 1) {
                itemNoActive = 3;
            } else if (warehouseItem.getItemStatus() == 1 && warehouseItem.getInOutStatus() == 2) {
                itemNoActive = 4;
            } else if (warehouseItem.getItemStatus() == 2 && warehouseItem.getInOutStatus() == 3) {
                itemNoActive = 5;
                if (warehouseItem.getIsAudit() && warehouseItem.getAuditStatus() == 1) {
                    itemNoActive = 6;
                }
            } else if (warehouseItem.getItemStatus() == 3) {
                itemNoActive = 7;
            } else if (warehouseItem.getItemStatus() == 4) {
                itemNoActive = 7;
            }
            return Result.success("查询成功！", itemNoActive);
        } catch (Exception e) {
            log.error("根据单号查询失败，失败原因：", e);
            return Result.failed("根据单号查询失败，失败原因：" + e);
        }
    }
}
