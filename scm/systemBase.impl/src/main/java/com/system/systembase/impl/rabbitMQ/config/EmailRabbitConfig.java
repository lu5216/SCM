package com.system.systembase.impl.rabbitMQ.config;

import com.system.systembase.impl.rabbitMQ.constants.RabbitQueueConstant;
import com.system.systembase.impl.rabbitMQ.enums.RabbitEnum;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author lutong
 * @data 2025-3-11 011 17:14
 */

@Configuration
public class EmailRabbitConfig {

    @Bean(name = "scm_email_queue")
    Queue queue() {
        return new Queue(RabbitQueueConstant.EMAIL_QUEUE);
    }

    @Bean(name = "scm_email_direct_exchange")
    DirectExchange directExchange() {
        return new DirectExchange(RabbitEnum.EMAIL_DIRECT_EXCHANGE.getCode());
    }

    @Bean(name = "scm_email_routing_key")
    Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(RabbitEnum.EMAIL_ROUTING_KEY.getCode());
    }

    /**
     *  序列化
     */
    @Bean(name = "scm_email_jackson2")
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
