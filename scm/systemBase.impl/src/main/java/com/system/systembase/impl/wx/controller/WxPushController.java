package com.system.systembase.impl.wx.controller;

import com.system.common.vo.Result;
import com.system.systembase.api.wx.param.TemplateParam;
import com.system.systembase.impl.wx.manager.WxPushManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author lutong
 * @data 2024-12-23 023 17:00
 */

@Slf4j
@RestController
@Api(tags = "接口-微信公众号消息推送")
@RequestMapping("/wx")
@Controller
public class WxPushController {

    /**
     * 微信消息推送
     */
    private final WxPushManager wxPushManager;

    /**
     * 构造注入
     */
    protected WxPushController(WxPushManager wxPushManager) {
        this.wxPushManager = wxPushManager;
    }

    /**
     * 发送微信模板消息
     * @return String
     */
    @ApiOperation("发送微信模板消息")
    @PostMapping(path = "/push")
    public Result<String> push(@RequestBody TemplateParam param) {
        try {
//            String msgId = wxPushManager.push(param);
//            if (msgId != null) {
//                log.info("小程序订阅消息推送数据:{}", msgId);
//                return Result.success("小程序订阅消息推送成功!");
//            } else {
//                return Result.failed("小程序订阅消息推送数据失败!");
//            }
            return Result.success("小程序订阅消息推送成功!");
        } catch (Exception e) {
            log.error("小程序订阅消息推送数据失败，失败原因："+ e);
            return Result.failed( "小程序订阅消息推送数据失败，失败原因：" + e);
        }
    }
}
