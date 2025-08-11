package com.system.systembase.impl.basicConfiguration.controller;

import com.system.common.enums.api.basicConfiguration.*;
import com.system.systembase.api.basicConfiguration.enums.CreditCodeTypeEnum;
import com.system.common.vo.Result;
import com.system.common.vo.CompanyEnumVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lutong
 * @data 2024-12-16 016 14:45
 */

@Slf4j
@RestController
@Api(tags = "接口-公司Enum")
@RequestMapping("/company/enum")
@Controller
public class CompanyEnumController {

    @ApiOperation(value = "查询公司类型列表数据")
    @GetMapping(value = "/selectCompanyType")
    public Result<List<CompanyEnumVo>> selectCompanyType(@RequestParam(value = "name", required = false) String name,
                                                         @RequestParam(value = "code", required = false) String code) {
        try {
            List<CompanyEnumVo> list = new ArrayList<>();
            for (CompanyTypeEnum typeEnum : CompanyTypeEnum.values()) {
                CompanyEnumVo vo = new CompanyEnumVo();
                vo.setId(typeEnum.getId());
                vo.setName(typeEnum.getName());
                vo.setCode(typeEnum.getCode());
                list.add(vo);
            }
            // filter进行模糊匹配
            if (name != null) {
                list = list.stream().filter(x -> x != null && x.getName().contains(name)).collect(Collectors.toList());
            }
            if (code != null) {
                list = list.stream().filter(x -> x != null && x.getCode().contains(code)).collect(Collectors.toList());
            }
            return Result.success("查询公司类型列表数据成功！", list);
        } catch (Exception e) {
            log.error("查询公司类型列表数据失败，失败原因：" + e);
            return Result.failed("查询公司类型列表数据失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "查询公司业务列表数据")
    @GetMapping(value = "/selectCompanyBusiness")
    public Result<List<CompanyEnumVo>> selectCompanyBusiness(@RequestParam(value = "name", required = false) String name,
                                                             @RequestParam(value = "code", required = false) String code) {
        try {
            List<CompanyEnumVo> list = new ArrayList<>();
            for (CompanyBusinessEnum businessEnum : CompanyBusinessEnum.values()) {
                CompanyEnumVo vo = new CompanyEnumVo();
                vo.setId(businessEnum.getId());
                vo.setName(businessEnum.getName());
                vo.setCode(businessEnum.getCode());
                list.add(vo);
            }
            // filter进行模糊匹配
            if (name != null) {
                list = list.stream().filter(x -> x != null && x.getName().contains(name)).collect(Collectors.toList());
            }
            if (code != null) {
                list = list.stream().filter(x -> x != null && x.getCode().contains(code)).collect(Collectors.toList());
            }
            return Result.success("查询公司业务列表数据成功！", list);
        } catch (Exception e) {
            log.error("查询公司业务列表数据失败，失败原因：" + e);
            return Result.failed("查询公司业务列表数据失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "查询公司经营状态列表数据")
    @GetMapping(value = "/selectCompanyStatus")
    public Result<List<CompanyEnumVo>> selectCompanyStatus(@RequestParam(value = "name", required = false) String name,
                                                           @RequestParam(value = "code", required = false) String code) {
        try {
            List<CompanyEnumVo> list = new ArrayList<>();
            for (CompanyStatusEnum statusEnum : CompanyStatusEnum.values()) {
                CompanyEnumVo vo = new CompanyEnumVo();
                vo.setId(statusEnum.getId());
                vo.setName(statusEnum.getName());
                vo.setCode(statusEnum.getCode());
                list.add(vo);
            }
            // filter进行模糊匹配
            if (name != null) {
                list = list.stream().filter(x -> x != null && x.getName().contains(name)).collect(Collectors.toList());
            }
            if (code != null) {
                list = list.stream().filter(x -> x != null && x.getCode().contains(code)).collect(Collectors.toList());
            }
            return Result.success("查询公司经营状态列表数据成功！", list);
        } catch (Exception e) {
            log.error("查询公司经营状态列表数据失败，失败原因：" + e);
            return Result.failed("查询公司经营状态列表数据失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "查询供应商类型数据")
    @GetMapping(value = "/selectVendorType")
    public Result<List<CompanyEnumVo>> selectVendorType(@RequestParam(value = "name", required = false) String name,
                                                        @RequestParam(value = "code", required = false) String code) {
        try {
            List<CompanyEnumVo> list = new ArrayList<>();
            for (VendorTypeEnum typeEnum : VendorTypeEnum.values()) {
                CompanyEnumVo vo = new CompanyEnumVo();
                vo.setId(typeEnum.getId());
                vo.setName(typeEnum.getName());
                vo.setCode(typeEnum.getCode());
                list.add(vo);
            }
            // filter进行模糊匹配
            if (name != null) {
                list = list.stream().filter(x -> x != null && x.getName().contains(name)).collect(Collectors.toList());
            }
            if (code != null) {
                list = list.stream().filter(x -> x != null && x.getCode().contains(code)).collect(Collectors.toList());
            }
            return Result.success("查询供应商类型数据成功！", list);
        } catch (Exception e) {
            log.error("查询供应商类型数据失败，失败原因：" + e);
            return Result.failed("查询供应商类型数据失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "查询用户类型数据")
    @GetMapping(value = "/selectClientType")
    public Result<List<CompanyEnumVo>> selectClientType(@RequestParam(value = "name", required = false) String name,
                                                        @RequestParam(value = "code", required = false) String code) {
        try {
            List<CompanyEnumVo> list = new ArrayList<>();
            for (ClientTypeMenu typeMenu : ClientTypeMenu.values()) {
                CompanyEnumVo vo = new CompanyEnumVo();
                vo.setId(typeMenu.getId());
                vo.setName(typeMenu.getName());
                vo.setCode(typeMenu.getCode());
                list.add(vo);
            }
            // filter进行模糊匹配
            if (name != null) {
                list = list.stream().filter(x -> x != null && x.getName().contains(name)).collect(Collectors.toList());
            }
            if (code != null) {
                list = list.stream().filter(x -> x != null && x.getCode().contains(code)).collect(Collectors.toList());
            }
            return Result.success("查询用户类型数据成功！", list);
        } catch (Exception e) {
            log.error("查询用户类型数据失败，失败原因：" + e);
            return Result.failed("查询用户类型数据失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "查询企业代码类型数据")
    @GetMapping(value = "/selectCreditCodeType")
    public Result<List<CompanyEnumVo>> selectCreditCodeType(@RequestParam(value = "name", required = false) String name,
                                                        @RequestParam(value = "code", required = false) String code) {
        try {
            List<CompanyEnumVo> list = new ArrayList<>();
            for (CreditCodeTypeEnum typeMenu : CreditCodeTypeEnum.values()) {
                CompanyEnumVo vo = new CompanyEnumVo();
                vo.setId(typeMenu.getId());
                vo.setName(typeMenu.getName());
                vo.setCode(typeMenu.getCode());
                list.add(vo);
            }
            // filter进行模糊匹配
            if (name != null) {
                list = list.stream().filter(x -> x != null && x.getName().contains(name)).collect(Collectors.toList());
            }
            if (code != null) {
                list = list.stream().filter(x -> x != null && x.getCode().contains(code)).collect(Collectors.toList());
            }
            return Result.success("查询企业代码类型数据成功！", list);
        } catch (Exception e) {
            log.error("查询企业代码类型数据失败，失败原因：" + e);
            return Result.failed("查询企业代码类型数据失败，失败原因：" + e);
        }
    }
}
