package com.system.systembase.impl.rabbitMQ.listener;

import com.alibaba.fastjson.JSON;
import com.system.systembase.impl.rabbitMQ.constants.RabbitQueueConstant;
import com.system.systembase.impl.rabbitMQ.enums.RabbitEnum;
import com.system.common.exception.CustomerAuthenticationException;
import com.system.systembase.api.sendEmail.service.IEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @author lutong
 * @name MsgComponent
 * @data 2025-03-14
 */

@Slf4j
@Component
public class EmailRabbitListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IEmailService emailService;


    /**
     *  RabbitMQ-发送邮件-生产者
     * @author lutong
     * @data 2025-3-14 014
     * @param email
     * @param content
     * @return void
     **/
    public void sendEmailMQ(String email, String content) {
        List<String> list = new ArrayList<>();
        list.add(email);
        list.add(content);
        log.info("参数-email: " + email + ", content: " + content);
        try {
            rabbitTemplate.convertAndSend(RabbitEnum.EMAIL_DIRECT_EXCHANGE.getCode(), RabbitEnum.EMAIL_ROUTING_KEY.getCode(), list);
        } catch (Exception e) {
            log.error("sendEmailMQ-发送邮件失败!");
            throw new CustomerAuthenticationException("sendEmailMQ-发送邮件失败!");
        }
    }


    /**
     *  发送邮件监听事件
     * @author lutong
     * @data 2025-3-14 014
     * @param list
     * @return void
     **/
    @RabbitListener(queues = RabbitQueueConstant.EMAIL_QUEUE)
    public void sendEmailListener(List<String> list) {
        // 发送邮件业务
        log.info("sendEmailListener-监听数据list: {}", JSON.toJSONString(list));
        String email = list.get(0);
        String content = list.get(1);
        emailService.sendText("SCM物流供应链系统", "3894932596@qq.com", email, "库存信息-预警库存通知", content);
    }
}
