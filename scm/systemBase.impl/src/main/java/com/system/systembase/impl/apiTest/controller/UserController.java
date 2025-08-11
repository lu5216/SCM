package com.system.systembase.impl.apiTest.controller;

import com.alibaba.fastjson.JSON;
import com.system.common.utils.UploadOrExportExcelUtil;
import com.system.systembase.api.apiTest.domain.User;
import com.system.systembase.api.apiTest.param.UserParam;
import com.system.systembase.api.apiTest.service.IUserService;
import com.system.systembase.api.apiTest.vo.TextDict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.system.common.vo.Result;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * @author lutong
 * @data 2024-11-27 027 14:56
 */


@Slf4j
@RestController
@Api(tags = "Api接口注释-user")
@RequestMapping("/apiTest")
@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "API接口注释")
    @GetMapping(value = "/test")
    public Result<String> test() {
        System.out.println("项目构建成功！");
        return Result.success("项目构建成功！");
    }


    @ApiOperation(value = "API接口注释")
    @PostMapping(value = "/selectById")
    public Result<User> selectById(@RequestBody UserParam param) {
        try {
            User data = userService.selectById(param);
            return Result.success("查询成功!", data);
        } catch (Exception e) {
            log.error("查询失败,失败原因: " + e);
            return Result.failed("查询失败,失败原因: " + e);
        }
    }

    @ApiOperation(value = "测试导出")
    @PostMapping(value = "/exportData")
    public void exportData(HttpServletResponse response, @RequestBody UserParam param) {
        log.info("导出-参数：" + (param != null ? JSON.toJSONString(param) : "NONE"));
        try {
            // 模拟数据
            List<User> userList = new ArrayList<>();
            userList.add(new User(1, "小米", "xiaoming"));
            userList.add(new User(2, "小明", "xiaomi"));
            // 导出
            UploadOrExportExcelUtil<User> excel = new UploadOrExportExcelUtil<>();
            excel.exportExcel(response, userList, User.class, "导出的Excel表名称", param.getSuffix());
        } catch (Exception e) {
            log.error("导出失败,原因：" + e);
            throw new RuntimeException(e);
        }
    }

    @ApiOperation(value = "测试导入")
    @PostMapping(value = "/uploadData")
    public Result<String> uploadData(@RequestParam MultipartFile file) {
        try {
            // 导入
            UploadOrExportExcelUtil<User> excel = new UploadOrExportExcelUtil<>();
            List<User> list = excel.uploadExport(file, User.class);
            log.info("测试导入数据：{}", JSON.toJSONString(list));

            // 保存到数据库操作
            list.forEach(System.out::println);
            return Result.success("导入成功！");
        } catch (Exception e) {
            log.error("导出失败,原因：" + e);
            throw new RuntimeException(e);
        }
    }


    @ApiOperation(value = "API接口注释")
    @GetMapping(value = "/testDict")
    public Result<TextDict> testDict() {
        TextDict dict = userService.testDict();
        return Result.success("测试Dict成功！", dict);
    }
}
