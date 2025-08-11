package com.system.systembase.impl.rabbitMQ.config;

import com.system.systembase.impl.rabbitMQ.constants.RabbitQueueConstant;
import com.system.systembase.impl.rabbitMQ.enums.RabbitEnum;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lutong
 * @name WxSARabbitConfig
 * @data 2025-03-14
 */

@Configuration
public class WxSARabbitConfig {

    @Bean(name = "scm_wx_sa_queue")
    Queue queue() {
        return new Queue(RabbitQueueConstant.WX_SA_QUEUE);
    }

    @Bean(name = "scm_wx_sa_direct_exchange")
    DirectExchange directExchange() {
        return new DirectExchange(RabbitEnum.WX_SA_DIRECT_EXCHANGE.getCode());
    }

    @Bean(name = "scm_wx_sa_routing_key")
    Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(RabbitEnum.WX_SA_ROUTING_KEY.getCode());
    }

    /**
     *  序列化
     */
    @Bean(name = "scm_wx_sa_jackson2")
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
