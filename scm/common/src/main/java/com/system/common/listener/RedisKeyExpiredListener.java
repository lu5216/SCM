package com.system.common.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @author lutong
 * @data 2025-2-18 018 10:13
 */

@Slf4j
public class RedisKeyExpiredListener extends KeyExpirationEventMessageListener {

    public RedisKeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expireKey = message.toString();
        log.info("过期的key：" + expireKey);
        // 在这里做过期key的处理

    }
}
