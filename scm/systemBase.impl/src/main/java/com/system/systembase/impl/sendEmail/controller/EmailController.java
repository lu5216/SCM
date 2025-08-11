package com.system.systembase.impl.sendEmail.controller;

import com.system.common.vo.Result;
import com.system.systembase.api.sendEmail.vo.SendHTMLVo;
import com.system.systembase.api.sendEmail.service.IEmailService;
import com.system.systembase.api.sendEmail.vo.EmailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author lutong
 * @data 2024-12-17 017 10:05
 */

@Slf4j
@RestController
@Api(tags = "接口-发送邮件Email")
@RequestMapping("/email")
@Controller
public class EmailController {

    @Autowired
    private IEmailService emailService;

    @Autowired
    private TemplateEngine templateEngine;


    @ApiOperation(value = "发送Text邮件")
    @PostMapping(value = "/sendText")
    public Result<String> sendText(@RequestBody EmailVo<String> vo) {
        try {
            emailService.sendText(vo.getName(), vo.getForm(), vo.getTo(), vo.getSubject(), vo.getContent());
            return Result.success("邮件发送成功！");
        } catch (Exception e) {
            log.error("邮件发送失败，失败原因：" + e);
            return Result.failed("邮件发送失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "发送HTML邮件-用户名密码变更")
    @PostMapping(value = "/sendHTML/userChange")
    public Result<String> sendHTML(@RequestBody EmailVo<SendHTMLVo> vo) {
        try {
            Context context = new Context();
            SendHTMLVo htmlVo = vo.content;
            context.setVariable("recipientName", htmlVo.getRecipientName());
            context.setVariable("url", htmlVo.getUrl());
            context.setVariable("avatar", htmlVo.getAvatar());
            String htmlContent = templateEngine.process("userChange.html", context);
            emailService.sendHtml(vo.getName(), vo.getForm(), vo.getTo(), vo.getSubject(), htmlContent);
            return Result.success("邮件发送成功！");
        } catch (Exception e) {
            log.error("邮件发送失败，失败原因：" + e);
            return Result.failed("邮件发送失败，失败原因：" + e);
        }
    }


    @ApiOperation(value = "发送HTML邮件-邀请客户注册")
    @GetMapping(value = "/sendHTML/inviteClientRegister")
    public Result<String> inviteClientRegister(@RequestParam(value = "emailList") String emailList) {
        String[] emails = emailList.split(",");
        try {
            for (String email : emails) {
                Context context = new Context();
                SendHTMLVo htmlVo = new SendHTMLVo();
                context.setVariable("recipientName", email);
                context.setVariable("url", "https://www.baidu.com/");
                context.setVariable("avatar", htmlVo.getAvatar());
                context.setVariable("qrCode", htmlVo.getQrCode());
                String process = templateEngine.process("inviteClientRegister.html", context);
                emailService.sendHtml("SCM智能供应链系统", "3894932596@qq.com", email, "邀请加入我们！", process);
            }
            return Result.success("邮件发送成功！");
        } catch (Exception e) {
            log.error("邮件发送失败，失败原因：" + e);
            return Result.failed("邮件发送失败，失败原因：" + e);
        }
    }

    @ApiOperation(value = "发送HTML邮件-邀请供应商注册")
    @GetMapping(value = "/sendHTML/inviteVendorRegister")
    public Result<String> inviteVendorRegister(@RequestParam(value = "emailList") String emailList) {
        String[] emails = emailList.split(",");
        try {
            for (String email : emails) {
                Context context = new Context();
                SendHTMLVo htmlVo = new SendHTMLVo();
                context.setVariable("recipientName", email);
                context.setVariable("url", "https://www.baidu.com/");
                context.setVariable("avatar", htmlVo.getAvatar());
                context.setVariable("qrCode", htmlVo.getQrCode());
                String process = templateEngine.process("inviteVendorRegister.html", context);
                emailService.sendHtml("SCM智能供应链系统", "3894932596@qq.com", email, "邀请加入我们！", process);
            }
            return Result.success("邮件发送成功！");
        } catch (Exception e) {
            log.error("邮件发送失败，失败原因：" + e);
            return Result.failed("邮件发送失败，失败原因：" + e);
        }
    }
}
