package com.ehu.config;

import com.ehu.interceptor.RequestInterceptor;
import com.ehu.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * write something to describe this file.
 *
 * @author demon
 * @since 2017-03-02 17:22.
 */
@Configuration
public class WebApplicationConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestInterceptor()).addPathPatterns("/*");
        registry.addInterceptor(new TokenInterceptor()).excludePathPatterns("/v2/*", "/user/login", "/swagger-ui.html");
    }
}
