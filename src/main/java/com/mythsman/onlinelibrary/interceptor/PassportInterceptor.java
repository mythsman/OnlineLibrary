package com.mythsman.onlinelibrary.interceptor;

/**
 * Created by myths on 5/20/17.
 */


import com.alibaba.fastjson.JSONObject;
import com.mythsman.onlinelibrary.component.UserComponent;
import com.mythsman.onlinelibrary.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * Created by myths on 5/4/17.
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Autowired
    UserComponent userComponent;
    @Autowired
    WechatService wechatService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String code=httpServletRequest.getParameter("code");
        if(code!=null){
            String url=String.format(" https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",wechatService.getAppid(),wechatService.getSecret(),code);
            String res=wechatService.doGet(url);
            String token=JSONObject.parseObject(res).getString("access_token");
            String openId=JSONObject.parseObject(res).getString("openid");

            res=wechatService.doGet(String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN",token,openId));
            JSONObject jsonObject=JSONObject.parseObject(res);
            String nickname=jsonObject.getString("nickname");
            String sex=jsonObject.getString("sex");
            String province=jsonObject.getString("province");
            String city=jsonObject.getString("city");
            String country=jsonObject.getString("country");
            String headimgurl=jsonObject.getString("headimgurl");
            System.out.println(nickname);
        }

        String ticket=null;
        if(httpServletRequest.getCookies()!=null) {
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }

        if(ticket==null){
            String callback= URLEncoder.encode("http://myths.mythsman.com","UTF-8");
            String redirect = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STATE#wechat_redirect", wechatService.getAppid(), callback ,"snsapi_userinfo");
            httpServletResponse.sendRedirect(redirect);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if(modelAndView!=null){
            modelAndView.addObject("user",userComponent.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        userComponent.clear();
    }
}