package com.mythsman.onlinelibrary.configuration;

import com.mythsman.onlinelibrary.interceptor.AppInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by myths on 5/20/17.
 */
@Component
public class OnlineLibraryWebConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    AppInterceptor appInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(appInterceptor).addPathPatterns("/app/**");
        super.addInterceptors(registry);
    }
}
