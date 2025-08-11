package com.system.warehouse.impl.inventoryInfo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.Result;
import com.system.common.vo.SysUserVo;
import com.system.warehouse.api.inventoryInfo.params.GoodsInventoryInfoParam;
import com.system.warehouse.api.inventoryInfo.service.IGoodsInventoryInfoService;
import com.system.warehouse.api.inventoryInfo.vo.GoodsInventoryInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author lutong
 * @data 2025-2-19 019 16:52
 */

@Slf4j
@RestController
@Api(tags = "仓库设置-库存信息")
@RequestMapping("/warehouse/inventoryInfo/goodsInventoryInfo")
@Controller
public class GoodsInventoryInfoController {

    @Autowired
    private IGoodsInventoryInfoService goodsInventoryInfoService;

    @ApiOperation(value = "分页查询库存信息")
    @PostMapping(value = "/getGoodsPage")
    public Result<Page<GoodsInventoryInfoVo>> getGoodsPage(@RequestBody GoodsInventoryInfoParam param) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return Result.failed("账号未登入，请重新登入!");
        }
        try {
            if (null == param.getCompanyUid() || Objects.equals(param.getCompanyUid(), "")) {
                param.setCompanyUid(user.getCompanyUid());
            }
            Page<GoodsInventoryInfoVo> page = goodsInventoryInfoService.getPage(param);
            return Result.success("分页查询库存信息成功！", page);
        } catch (Exception e) {
            log.error("分页查询库存信息失败，失败原因：" + e);
            return Result.failed("分页查询库存信息失败，失败原因：" + e);
        }
    }

}
