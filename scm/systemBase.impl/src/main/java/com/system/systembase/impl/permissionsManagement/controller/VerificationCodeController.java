package com.system.systembase.impl.permissionsManagement.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.system.common.vo.Result;
import com.system.systembase.api.redisTemplate.service.IRedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;


/**
 * @author lutong
 * @data 2024-12-10 010 15:03
 */

@Slf4j
@RestController
@Api(tags = "接口-验证码管理")
@RequestMapping("/verify")
@Controller
public class VerificationCodeController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private IRedisService redisService;

    @ApiOperation(value = "获取验证码图片")
    @GetMapping(value = "/getVerificationCodePhoto")
    public void getVerificationCodePhoto(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        byte[] captchaOutputStream = null;
        ByteArrayOutputStream imgOutputStream = new ByteArrayOutputStream();
        try {
            //生成验证码
            String verifyCode = defaultKaptcha.createText();
            log.info("验证码verifyCode：{}",verifyCode);
            //验证码字符串保存到redis
            long ttlMillis = 5 * 60 * 1000L;  // 过期时间（设为5分钟）
            redisService.addRedis("verifyCode", verifyCode, ttlMillis);
            BufferedImage challenge = defaultKaptcha.createImage(verifyCode);
            //设置写出图片的格式
            ImageIO.write(challenge, "jpg", imgOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        captchaOutputStream = imgOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }


    @ApiOperation("获取验证码")
    @GetMapping("/getVerificationCode")
    public Result<String> getVerificationCode(HttpServletRequest request) {
        String verifyCode = defaultKaptcha.createText();
        request.getSession().setAttribute("verifyCode", verifyCode);
        return Result.success("获取验证码成功", verifyCode);

    }
}
