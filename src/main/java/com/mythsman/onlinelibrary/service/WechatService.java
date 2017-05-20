package com.mythsman.onlinelibrary.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by myths on 5/18/17.
 */
@Component
public class WechatService implements InitializingBean{

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.secret}")
    private String secret;

    @Value("${wechat.id}")
    private String id;

    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public String getAppid() {
        return appid;
    }

    public String getSecret() {
        return secret;
    }

    public String getId() {
        return id;
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

    public String doGet(String url){
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

   public String handleText(Document request){

        Document response = DocumentHelper.createDocument();
        Element xml = response.addElement( "xml" );
        xml.addElement("ToUserName").addCDATA(id);
        xml.addElement("FromUserName").addCDATA(request.selectSingleNode("//FromUserName").getText());
        xml.addElement("CreateTime").addText(String.valueOf(new Date().getTime()/1000));
        xml.addElement("MsgType").addCDATA("text");
        xml.addElement("Content").addCDATA("欢迎您的使用!");

        return response.getRootElement().asXML();
   }
}
