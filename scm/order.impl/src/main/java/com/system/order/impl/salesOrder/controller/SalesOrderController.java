package com.system.order.impl.salesOrder.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.exception.CustomerAuthenticationException;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.Result;
import com.system.common.vo.SysUserVo;
import com.system.order.api.salesOrder.domain.SalesOrder;
import com.system.order.api.salesOrder.params.SalesOrderParam;
import com.system.order.api.salesOrder.params.SalesOrderSaveParam;
import com.system.order.api.salesOrder.service.ISalesOrderService;
import com.system.order.api.salesOrder.vo.SalesOrderVo;
import com.system.order.api.warehouseOrder.domain.WarehouseItem;
import com.system.order.api.warehouseOrder.service.IWarehouseItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lutong
 * @data 2025-2-20 020 17:03
 */

@Slf4j
@RestController
@Api(tags = "订单管理-销售管理")
@RequestMapping("/order/warehouseOrder/salesOrder")
@Controller
public class SalesOrderController {

    @Autowired
    private ISalesOrderService salesOrderService;

    @Autowired
    private IWarehouseItemService warehouseItemService;

    @ApiOperation("分页查询")
    @PostMapping("/selectPage")
    public Result<Page<SalesOrderVo>> selectPage(@RequestBody SalesOrderParam param) {
        // 维度不能为空
        if (param.getDimensionality() == null) {
            return Result.failed("维度参数不能为空");
        }

        // 获取企业UID
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return Result.failed("账号未登入，请重新登入!");
        }
        param.setCompanyUid(user.getCompanyUid());

        try {
            log.info("订单管理-销售管理-分页查询-参数：{}", param);
            Page<SalesOrderVo> page = salesOrderService.selectPage(param);
            return Result.success("分页查询成功！", page);
        } catch (Exception e) {
            log.error("分页查询失败，失败原因：", e);
            return Result.failed("分页查询失败，失败原因：" + e);
        }
    }


    @ApiOperation("新增或修改")
    @PostMapping("/save")
    public Result<String> save(@RequestBody SalesOrderSaveParam param) {
        try {
            log.info("订单管理-销售管理-新增或修改-参数：{}", param);
            String errMSG = salesOrderService.insertAndUpdate(param);
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

    @ApiOperation("接单")
    @GetMapping("/orderReceiving")
    public Result<String> orderReceiving(@RequestParam(value = "id") Integer id) {
        try {
            log.info("订单管理-销售订单-接单-参数：{}", id);
            String errMSG = salesOrderService.orderReceiving(id);
            if (errMSG == null) {
                return Result.success("接单完成！");
            } else {
                return Result.failed(errMSG);
            }
        } catch (Exception e) {
            log.error("接单失败，失败原因：", e);
            return Result.failed("接单失败，失败原因：" + e);
        }
    }

    @ApiOperation("根据销售订单创建出库订单")
    @GetMapping("/createOutWarehouseItem")
    public Result<String> createOutWarehouseItem(@RequestParam(value = "id") Integer id) {
        try {
            log.info("订单管理-销售订单-根据销售订单创建出库订单-参数：{}", id);
            String errMSG = salesOrderService.createOutWarehouseItem(id);
            if (errMSG == null) {
                return Result.success("创建出库订单完成！");
            } else {
                return Result.failed(errMSG);
            }
        } catch (Exception e) {
            log.error("根据销售订单创建出库订单失败，失败原因：", e);
            return Result.failed("根据销售订单创建出库订单失败，失败原因：" + e);
        }
    }


    @ApiOperation("审核")
    @GetMapping("/audit")
    public Result<String> audit(@RequestParam(value = "id") Integer id,
                                @RequestParam(value = "auditStatus") Integer auditStatus,
                                @RequestParam(value = "auditReason", required = false) String auditReason) {
        try {
            log.info("订单管理-销售订单-审核-参数：{}", id);
            String errMSG = salesOrderService.auditById(id, auditStatus, auditReason);
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
            log.info("订单管理-销售订单-取消-参数：{}", id);
            String errMSG = salesOrderService.cancelById(id, cancelReason);
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
    @GetMapping("/getBySalesOrderActive")
    public Result<Integer> getBySalesOrderActive(@RequestParam(value = "salesOrderNo") String salesOrderNo) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return Result.failed("获取用户信息异常!");
        }
        try {
            log.info("订单管理-销售订单-根据单号查询-参数：{}", salesOrderNo);
            SalesOrder salesOrder = salesOrderService.getOneBySalesOrderNo(salesOrderNo);
            Integer salesOrderActive = 1;
            if (salesOrder.getItemStatus() == 1) {
                salesOrderActive = 3;
            } else if (salesOrder.getItemStatus() == 2) {
                salesOrderActive = 4;
                List<WarehouseItem> warehouseItemList = warehouseItemService.findByAssociatedOrderNo(salesOrder.getItemNo(), user.getCompanyUid());
                if (warehouseItemList.size() > 0) {
                    salesOrderActive = 5;
                }
                if (salesOrder.getIsAudit() && salesOrder.getAuditStatus() == 1) {
                    salesOrderActive = 6;
                }
            } else if (salesOrder.getItemStatus() == 3) {
                salesOrderActive = 7;

            } else if (salesOrder.getItemStatus() == 4) {
                salesOrderActive = 7;
            }
            return Result.success("查询成功！", salesOrderActive);
        } catch (Exception e) {
            log.error("根据单号查询失败，失败原因：", e);
            return Result.failed("根据单号查询失败，失败原因：" + e);
        }
    }
}
