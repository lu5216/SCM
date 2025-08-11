package com.system.systembase.impl.basicConfiguration.controller;

import com.system.common.vo.Result;
import com.system.systembase.api.basicConfiguration.domain.SysCompany;
import com.system.systembase.api.basicConfiguration.domain.SysCompanyDetail;
import com.system.systembase.api.basicConfiguration.param.SysCompanyParam;
import com.system.systembase.api.basicConfiguration.service.ISysCompanyDetailService;
import com.system.systembase.api.basicConfiguration.service.ISysCompanyService;
import com.system.systembase.api.basicConfiguration.vo.CompanyDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lutong
 * @data 2024-12-13 013 14:05
 */

@Slf4j
@RestController
@Api(tags = "接口-公司")
@RequestMapping("/company")
@Controller
public class SysCompanyController {

    @Autowired
    private ISysCompanyService sysCompanyService;

    @Autowired
    private ISysCompanyDetailService sysCompanyDetailService;

    @ApiOperation(value = "查询公司列表数据")
    @PostMapping(value = "/selectCompany")
    public Result<List<CompanyDetailVo>> selectCompany(@RequestBody SysCompanyParam param) {
        try {
            ArrayList<CompanyDetailVo> detailList = new ArrayList<>();
            List<SysCompany> companyList = sysCompanyService.selectCompany(param);
            if (companyList.size() > 0) {
                for (SysCompany company : companyList) {
                    CompanyDetailVo detailVo = new CompanyDetailVo();
                    BeanUtils.copyProperties(company, detailVo);
                    if (company.getCompanyUid() != null) {
                        SysCompanyDetail detail = sysCompanyDetailService.selectDetailByCompanyUID(company.getCompanyUid());
                        if (detail != null) {
                            detailVo.setDetail(detail);
                        }
                    }
                    detailList.add(detailVo);
                }
            }
            return Result.success("查询公司列表数据成功！", detailList);
        } catch (Exception e) {
            log.error("查询公司列表数据失败，失败原因：" + e);
            return Result.failed("查询公司列表数据失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "启用-停用企业")
    @GetMapping(value = "/isValidated")
    public Result<String> isValidated(@RequestParam(value = "id") Integer id) {
        try {
            Result<String> result = sysCompanyService.isValidated(id);
            log.info(result.getMsg());
            return result;
        } catch (Exception e) {
            log.error("启用-停用企业失败，失败原因：" + e);
            return Result.failed("启用-停用企业失败，失败原因：" + e);
        }
    }

}
