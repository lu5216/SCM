package com.system.warehouse.impl.goods.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.exception.CustomerAuthenticationException;
import com.system.common.utils.SecurityUtils;
import com.system.common.utils.UploadOrExportExcelUtil;
import com.system.common.vo.Result;
import com.system.common.vo.SysUserVo;
import com.system.warehouse.api.goods.domain.WarehouseGoods;
import com.system.warehouse.api.goods.param.WarehouseGoodGetPageParam;
import com.system.warehouse.api.goods.service.IWarehouseGoodsService;
import com.system.warehouse.api.goods.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * @author lutong
 * @data 2025-1-9 009 15:25
 */

@Slf4j
@RestController
@Api(tags = "商品管理-商品库")
@RequestMapping("/warehouse/goods/warehouseGoods")
@Controller
public class WarehouseGoodsController {

    @Autowired
    private IWarehouseGoodsService warehouseGoodsService;



    @ApiOperation(value = "分页查询商品库")
    @PostMapping(value = "/getGoodsPage")
    public Result<Page<WarehouseGoods>> getGoodsPage(@RequestBody WarehouseGoodGetPageParam param) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return Result.failed("账号未登入，请重新登入!");
        }
        try {
            if (null == param.getCompanyUid() || Objects.equals(param.getCompanyUid(), "")) {
                param.setCompanyUid(user.getCompanyUid());
            }
            Page<WarehouseGoods> page = warehouseGoodsService.getGoodsPage(param);
            return Result.success("分页查询商品库成功！", page);
        } catch (Exception e) {
            log.error("分页查询商品库失败，失败原因：" + e);
            return Result.failed("分页查询商品库失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "保存基础信息")
    @PostMapping(value = "/saveBaseInfo")
    public Result<String> saveBaseInfo(@RequestBody GoodsSaveBaseInfoVo vo) {
        try {
            String result = warehouseGoodsService.saveBaseInfo(vo);
            if (result == null) {
                return Result.success("保存基础信息成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("保存基础信息失败，失败原因：" + e);
            return Result.failed("保存基础信息失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "保存价格信息")
    @PostMapping(value = "/savePriceInfo")
    public Result<String> savePriceInfo(@RequestBody GoodsSavePriceInfoVo vo) {
        try {
            String result = warehouseGoodsService.savePriceInfo(vo);
            if (result == null) {
                return Result.success("保存价格信息成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("保存价格信息失败，失败原因：" + e);
            return Result.failed("保存价格信息失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "保存维护信息")
    @PostMapping(value = "/saveKeepInfo")
    public Result<String> saveKeepInfo(@RequestBody GoodsSaveKeepInfoVo vo) {
        try {
            String result = warehouseGoodsService.saveKeepInfo(vo);
            if (result == null) {
                return Result.success("保存维护信息成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("保存维护信息失败，失败原因：" + e);
            return Result.failed("保存维护信息失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "导出商品库数据")
    @PostMapping(value = "/exportData")
    public void exportData(HttpServletResponse response, @RequestBody WarehouseGoodGetPageParam param) {
        log.info("导出-参数：" + (param != null ? JSON.toJSONString(param) : "NONE"));
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            throw new CustomerAuthenticationException("账号未登入，请重新登入!");
        }
        try {
            if (param != null) {
                param.setPageIndex(1);
                param.setPageSize(20);
                param.setCompanyUid(user.getCompanyUid());
            }
            List<WarehouseGoods> goodsList = warehouseGoodsService.getGoodsPageList(param);
            log.info("导出的数据：{}", JSON.toJSONString(goodsList));
            // 导出
            UploadOrExportExcelUtil<WarehouseGoods> excel = new UploadOrExportExcelUtil<>();
            excel.exportExcel(response, goodsList, WarehouseGoods.class, "商品库数据", param != null ? param.getSuffix() : null);
        } catch (Exception e) {
            log.error("导出失败,原因：" + e);
            throw new RuntimeException(e);
        }
    }


    @ApiOperation(value = "根据商品识别码获取推荐仓库信息")
    @PostMapping(value = "/getPreWarehouseByGoodsCode")
    public Result<PreWarehouseGoodsVo> getPreWarehouseByGoodsCode(@RequestParam(value = "goodsCode") String goodsCode) {
        try {
            PreWarehouseGoodsVo vo = warehouseGoodsService.getPreWarehouseByGoodsCode(goodsCode);
            log.info("查询成功：{}", JSON.toJSONString(vo));
            return Result.success("查询成功：", vo);
        } catch (Exception e) {
            log.error("查询失败,原因：" + e);
            return Result.failed("查询失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "获取全部商品列表")
    @GetMapping(value = "/getWarehouseGoodsList")
    public Result<List<WarehouseGoodsListVo>> getWarehouseGoodsList(@RequestParam(value = "key", required = false) String key) {
        try {
            List<WarehouseGoodsListVo> goodsList = warehouseGoodsService.getWarehouseGoodsList(key);
            log.info("全部商品列表查询成功：{}", JSON.toJSONString(goodsList));
            return Result.success("全部商品列表查询成功：", goodsList);
        } catch (Exception e) {
            log.error("查询失败,原因：" + e);
            return Result.failed("查询失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "删除商品")
    @GetMapping(value = "/deleteWarehouseGoods")
    public Result<String> deleteWarehouseGoods(@RequestParam(value = "goodsId") Integer goodsId) {
        try {
            String result = warehouseGoodsService.deleteWarehouseGoods(goodsId);
            if (result == null) {
                return Result.success("删除商品成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("删除商品失败,原因：" + e);
            return Result.failed("删除商品失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "变更状态")
    @GetMapping(value = "/editStatus")
    public Result<String> editStatus(@RequestParam(value = "goodsId") Integer goodsId) {
        try {
            String result = warehouseGoodsService.editStatus(goodsId);
            if (result == null) {
                return Result.success("变更状态成功！");
            } else {
                return Result.failed(result);
            }
        } catch (Exception e) {
            log.error("变更状态失败,原因：" + e);
            return Result.failed("变更状态失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "查询明细")
    @GetMapping(value = "/findDetailById")
    public Result<GoodsDetailsVo> findDetailById(@RequestParam(value = "goodsId") Integer goodsId) {
        try {
            GoodsDetailsVo detail = warehouseGoodsService.findDetailById(goodsId);
            return Result.success("查询明细成功！", detail);
        } catch (Exception e) {
            log.error("查询明细失败,原因：" + e);
            return Result.failed("查询明细失败，失败原因：" + e);
        }
    }
}
