package com.mythsman.onlinelibrary.controller;

import com.mythsman.onlinelibrary.util.Digest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by myths on 5/14/17.
 */
@RestController
@RequestMapping(value = "/wechat", method = RequestMethod.GET)
public class WechatController {
    public static final String TOKEN = "mythsman";


    @RequestMapping(value = "/", method = RequestMethod.GET, params = {"signature", "timestamp", "nonce", "echostr"})
    public String check(
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("echostr") String echostr
    ) {
        List<String> list = new ArrayList<String>();
        list.add(TOKEN);
        list.add(signature);
        list.add(nonce);
        Collections.sort(list);
        StringBuffer sb = new StringBuffer();
        for (String s : list) {
            sb.append(s);
        }
        if (Digest.encodeBySHA1(sb.toString()).equals(signature)) {
            return echostr;
        } else {
            return "Invalid parameters.";
        }
    }

}
