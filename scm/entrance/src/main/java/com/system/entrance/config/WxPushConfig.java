package com.system.entrance.config;

import com.system.common.vo.WxMpProperties;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lutong
 * @data 2024-12-12 012 15:48
 */

@Configuration
public class WxPushConfig {

    @Autowired
    private WxMpProperties wxMpProperties;

    /**
     * 微信客户端配置-存储
     *
     * @return
     */
    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        // 公众号appId
        configStorage.setAppId(wxMpProperties.getAppId());
        // 公众号appSecret
        configStorage.setSecret(wxMpProperties.getSecret());
        // 公众号Token
//        configStorage.setToken(wxMpProperties.getToken());
        // 公众号EncodingAESKey
//        configStorage.setAesKey(wxMpProperties.getAesKey());
        return configStorage;
    }

    /**
     * 声明实例
     *
     * @return
     */
    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }
}
