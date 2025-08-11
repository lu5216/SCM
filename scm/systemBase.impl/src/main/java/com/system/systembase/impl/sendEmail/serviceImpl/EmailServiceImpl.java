package com.system.systembase.impl.sendEmail.serviceImpl;

import com.power.common.util.CollectionUtil;
import com.system.common.exception.CustomerAuthenticationException;
import com.system.systembase.api.sendEmail.service.IEmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @author lutong
 * @data 2024-12-17 017 9:53
 */

@Slf4j
@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendText(String name, String form, String to, String subject, String content) {
        this.send(name, form, to, subject, content, false, null, null, null);
    }

    @Override
    public void sendHtml(String name, String form, String to, String subject, String content) {
        this.send(name, form, to, subject, content, true, null, null, null);
    }

    @Override
    public void send(String name, String form, String to, String subject, String content, Boolean isHtml, String cc, String bcc, List<File> files) {
        if (StringUtils.isAnyBlank(form, to, subject, content)) {
            throw new CustomerAuthenticationException("发送人,接收人,主题,内容均不可为空");
        }
        try {
            //true表示支持复杂类型
            MimeMessageHelper messageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            //邮件发信人
            messageHelper.setFrom(new InternetAddress(name + "<" + form + ">"));
            //邮件收信人
            messageHelper.setTo(to.split(","));
            //邮件主题
            messageHelper.setSubject(subject);
            //邮件内容
            messageHelper.setText(content, isHtml);
            //抄送
            if (!StringUtils.isEmpty(cc)) {
                messageHelper.setCc(cc.split(","));
            }
            //密送
            if (!StringUtils.isEmpty(bcc)) {
                messageHelper.setCc(bcc.split(","));
            }
            //添加邮件附件
            if (CollectionUtil.isNotEmpty(files)) {
                for (File file : files) {
                    messageHelper.addAttachment(file.getName(), file);
                }
            }
            // 邮件发送时间
            messageHelper.setSentDate(new Date());
            //正式发送邮件
            javaMailSender.send(messageHelper.getMimeMessage());
        } catch (Exception e) {
            throw new CustomerAuthenticationException("邮件发送失败, 失败原因：" + e);
        }
    }
}
