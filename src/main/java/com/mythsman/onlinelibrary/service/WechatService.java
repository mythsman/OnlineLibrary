package com.mythsman.onlinelibrary.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by myths on 5/18/17.
 */
@Component
public class WechatService implements InitializingBean{

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
        String res=doGet(url);
        JSONObject jsonObject=JSON.parseObject(res);
        accessToken=jsonObject.get("access_token").toString();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        updateAccessToken();
    }

    private String doGet(String url){
        try {

            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();

            InputStream inputStream = urlConnection.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
            inputStream.close();
            urlConnection.disconnect();
            return sb.toString();

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static void parseXml(String xml){
        try {
            Document document = DocumentHelper.parseText(xml);
            Element element=document.getRootElement();
            Node node=element.node(1);
            System.out.println(node.getText());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        String s="<xml><ToUserName><![CDATA[gh_51cef5288b63]]></ToUserName><FromUserName><![CDATA[oRSKrv-_0Mb833fVGEeJMcj33AIo]]></FromUserName> <CreateTime>1495179355</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[22]]></Content><MsgId>6421746431966456657</MsgId></xml>";
        parseXml(s);
    }
}
