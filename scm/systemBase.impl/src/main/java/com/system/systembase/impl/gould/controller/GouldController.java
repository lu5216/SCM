package com.system.systembase.impl.gould.controller;

import com.alibaba.fastjson.JSONObject;
import com.system.common.vo.CompanyEnumVo;
import com.system.common.vo.Result;
import com.system.systembase.api.gould.enums.StrategyEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.system.common.component.GouldComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author lutong
 * @data 2025-3-5 005 14:11
 */

@Slf4j
@RestController
@Api(tags = "接口-调用高德API")
@RequestMapping("/gould")
@Controller
public class GouldController {

    @Autowired
    private GouldComponent gouldComponent;

    @ApiOperation(value = "驾车算路策略列表")
    @GetMapping(value = "/selectStrategyList")
    public Result<List<CompanyEnumVo>> selectStrategyList() {
        try {
            List<CompanyEnumVo> list = new ArrayList<>();
            for (StrategyEnum strategyEnum : StrategyEnum.values()) {
                CompanyEnumVo vo = new CompanyEnumVo();
                vo.setId(strategyEnum.getId());
                vo.setName(strategyEnum.getName());
                list.add(vo);
            }
            return Result.success("查询驾车算路策略列表成功！", list);
        } catch (Exception e) {
            log.error("查询驾车算路策略列表失败，失败原因：" + e);
            return Result.failed("查询驾车算路策略列表失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "根据经纬度获取地址")
    @PostMapping(value = "/getAddress")
    public Result<String> getAddress(String longitude, String lat) {
        try {
            String address = gouldComponent.getAMapByLngAndLat(longitude, lat);
            if (Objects.equals(address, "-1")) {
                return Result.failed("根据经纬度获取地址-异常");
            }
            return Result.success("根据经纬度获取地址-成功!", address);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed("根据经纬度获取地址-异常,异常原因：" + e);
        }
    }


    @ApiOperation(value = "根据地址获取经纬度")
    @GetMapping(value = "/getLonLat")
    public Result<String> getLonLat(@RequestParam(value = "address") String address) {
        try {
            String result = gouldComponent.getLonLat(address);
            return Result.success("根据地址获取经纬度-成功!", result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed("根据地址获取经纬度-异常,异常原因：" + e);
        }
    }


    @ApiOperation(value = "通过地址算出两个地址之间的距离")
    @GetMapping(value = "/getDistanceByAddress")
    public Result<Long> getDistanceByAddress(@RequestParam(value = "start") String start,
                                             @RequestParam(value = "end") String end) {
        try {
            long distance = gouldComponent.getDistanceByAddress(start, end);
            return Result.success("通过地址算出两个地址之间的距离-成功!", distance);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed("通过地址算出两个地址之间的距离-异常,异常原因：" + e);
        }
    }


    @ApiOperation(value = "路径规划-坐标")
    @GetMapping(value = "/pathPlanning_LAL")
    public Result<JSONObject> pathPlanning_LAL(@RequestParam(value = "origin") String origin,
                                               @RequestParam(value = "destination") String destination,
                                               @RequestParam(value = "strategy") Integer strategy) {
        try {
            JSONObject route = gouldComponent.pathPlanning(origin, destination, strategy);
            return Result.success("路径规划-成功!", route);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed("路径规划-异常,异常原因：" + e);
        }
    }


    @ApiOperation(value = "路径规划-名称")
    @GetMapping(value = "/pathPlanningName")
    public Result<JSONObject> pathPlanning_Name(@RequestParam(value = "start") String start,
                                                @RequestParam(value = "end") String end,
                                                @RequestParam(value = "strategy") Integer strategy) {
        try {
            String origin = gouldComponent.getLonLat(start);
            String destination = gouldComponent.getLonLat(end);
            JSONObject route = gouldComponent.pathPlanning(origin, destination, strategy);
            return Result.success("路径规划-成功!", route);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed("路径规划-异常,异常原因：" + e);
        }
    }
}
