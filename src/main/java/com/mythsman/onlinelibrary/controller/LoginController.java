package com.mythsman.onlinelibrary.controller;

import com.mythsman.onlinelibrary.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by myths on 5/20/17.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    WechatService wechatService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String login() {
        String callback="http://myths.mythsman.com";
        String redirect = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s#wechat_redirect", wechatService.getAppid(), callback ,"snsapi_userinfo");
        return "";
    }
}
