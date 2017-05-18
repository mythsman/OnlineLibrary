package com.mythsman.onlinelibrary.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by myths on 5/18/17.
 */
@Component
public class AccessTokenComponent implements InitializingBean{

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.secret}")
    private String secret;

    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    @Scheduled(fixedDelay = 3600000)
    public void updateAccessToken(){
        String url=String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",appid,secret);
        String res=ApiComponent.doGet(url);
        JSONObject jsonObject=JSON.parseObject(res);
        accessToken=jsonObject.get("access_token").toString();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        updateAccessToken();
    }

}
