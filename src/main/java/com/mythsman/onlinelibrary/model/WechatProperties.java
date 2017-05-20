package com.mythsman.onlinelibrary.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by myths on 5/18/17.
 */

@ConfigurationProperties(prefix = "wechat")
public class WechatProperties {

    private String token;
    private String appid;
    private String secret;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
