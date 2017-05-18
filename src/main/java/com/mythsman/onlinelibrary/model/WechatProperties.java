package com.mythsman.onlinelibrary.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Created by myths on 5/18/17.
 */

@ConfigurationProperties(prefix = "wechat")
public class WechatProperties {

    @NestedConfigurationProperty
    private String token;
    private String appid;
    private String secret;

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
