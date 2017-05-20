package com.mythsman.onlinelibrary.controller;

import com.alibaba.fastjson.JSONObject;
import com.mythsman.onlinelibrary.dao.TicketDao;
import com.mythsman.onlinelibrary.dao.UserDao;
import com.mythsman.onlinelibrary.service.WechatService;
import com.mythsman.onlinelibrary.util.Digest;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by myths on 5/14/17.
 */
@Controller
@RequestMapping(value = "/wechat", method = RequestMethod.GET)
public class WechatController {

    Logger logger = LoggerFactory.getLogger(WechatController.class);

    @Autowired
    WechatService wechatService;

    @Autowired
    UserDao userDao;
    @Autowired
    TicketDao ticketDao;


    @Value("${wechat.token}")
    private String TOKEN;

    @RequestMapping(method = RequestMethod.GET, params = {"signature", "timestamp", "nonce", "echostr"})
    @ResponseBody
    public String check(
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("echostr") String echostr
    ) {
        List<String> list = new ArrayList<>();
        list.add(TOKEN);
        list.add(timestamp);
        list.add(nonce);
        Collections.sort(list);
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
        }
        if (Digest.encodeBySHA1(sb.toString()).equals(signature)) {
            return echostr;
        } else {
            return "";
        }
    }

    @RequestMapping(method = RequestMethod.GET, params = {"code"})
    public String check(@RequestParam("code") String code, HttpServletResponse httpServletResponse) {
        String url = String.format(" https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", wechatService.getAppid(), wechatService.getSecret(), code);
        String res = wechatService.doGet(url);
        String token = JSONObject.parseObject(res).getString("access_token");
        String openId = JSONObject.parseObject(res).getString("openid");

        res = wechatService.doGet(String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN", token, openId));
        JSONObject jsonObject = JSONObject.parseObject(res);
        String nickname = jsonObject.getString("nickname");
        int sex = Integer.parseInt(jsonObject.getString("sex"));
        String province = jsonObject.getString("province");
        String city = jsonObject.getString("city");
        String country = jsonObject.getString("country");
        String headimgurl = jsonObject.getString("headimgurl");

        if (userDao.selectByOpenid(openId) == null) {
            userDao.insert(nickname, sex, province, city, country, headimgurl, openId);
        }

        int uid = Integer.parseInt(userDao.selectByOpenid(openId).getOpenid());

        Date expire = new Date(new Date().getTime() + 1000 * 60 * 60 * 23L);
        String ticket = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
        ticketDao.insert(uid, ticket, expire);
        httpServletResponse.addCookie(new Cookie("ticket", ticket));

        return "redirect:/app";
    }

    @RequestMapping(method = {RequestMethod.POST})
    @ResponseBody
    public String post(@RequestBody String xml) {
        try {
            Document document = DocumentHelper.parseText(xml);
            String msgType = document.selectSingleNode("//MsgType").getText();
            switch (msgType) {
                case "text":
                    return wechatService.handleText(document);
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
