package com.mythsman.onlinelibrary.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by myths on 5/23/17.
 */
@Component
public class RedisAdapter {
    @Autowired
    private StringRedisTemplate template;

    public void test(){
        template.opsForValue();
    }
}
