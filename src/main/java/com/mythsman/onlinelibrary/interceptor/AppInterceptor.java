package com.mythsman.onlinelibrary.interceptor;

/**
 * Created by myths on 5/20/17.
 */


import com.mythsman.onlinelibrary.component.UserComponent;
import com.mythsman.onlinelibrary.dao.TicketDao;
import com.mythsman.onlinelibrary.dao.UserDao;
import com.mythsman.onlinelibrary.model.Ticket;
import com.mythsman.onlinelibrary.model.User;
import com.mythsman.onlinelibrary.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by myths on 5/4/17.
 */
@Component
public class AppInterceptor implements HandlerInterceptor {


    @Autowired
    UserComponent userComponent;
    @Autowired
    WechatService wechatService;

    @Autowired
    TicketDao ticketDao;

    @Autowired
    UserDao userDao;

    @Value("debug")
    boolean debug;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if(debug){
            User user = userDao.selectById(1);
            userComponent.setUser(user);
            return true;
        }
        String ticket = null;
        if (httpServletRequest.getCookies() != null) {
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }

        if (ticket == null) {
            String callback = URLEncoder.encode("http://myths.mythsman.com/wechat", "UTF-8");
            String redirect = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STATE#wechat_redirect", wechatService.getAppid(), callback, "snsapi_userinfo");
            httpServletResponse.sendRedirect(redirect);
            return true;
        }

        Ticket loginTicket = ticketDao.selectByTicket(ticket);
        if (loginTicket == null || loginTicket.getExpire().before(new Date())) {
            String callback = URLEncoder.encode("http://myths.mythsman.com/wechat", "UTF-8");
            String redirect = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STATE#wechat_redirect", wechatService.getAppid(), callback, "snsapi_userinfo");
            httpServletResponse.sendRedirect(redirect);
            return true;
        }

        User user = userDao.selectById(loginTicket.getUid());
        userComponent.setUser(user);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            modelAndView.addObject("user", userComponent.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        userComponent.clear();
    }
}