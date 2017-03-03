package com.ehu.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * filter配置文件.
 *
 * @author demon
 * @since 2017-03-02 16:46.
 */
@Configuration
public class FilterConfig {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /************************************************************
     *
     *FilterRegistrationBean可以定制化Filter,Filter会过滤所有的请求
     *
     * **********************************************************
     */



    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(corsFilter());
        registration.addUrlPatterns("/*");
        registration.setName("corsFilter");
        registration.addInitParameter("author", "demon");
        return registration;
    }

    @Bean
    public Filter corsFilter() {
        return new Filter() {
            @Override
            public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
                logger.info("-------------------CorsFilter初始化成功:author-{}-------------------", filterConfig.getInitParameter("author"));
            }

            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT,OPTIONS");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Headers", "Accept,Pragma,DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,token");
                filterChain.doFilter(servletRequest, servletResponse);
            }

            @Override
            public void destroy() {
                logger.info("-------------------CorsFilter被销毁-------------------");
            }
        };
    }

    @Bean
    public Filter encodingFilter() {
        return new Filter() {
            @Override
            public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
                logger.info("-------------------EncodingFilter初始化成功-------------------");
            }

            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter("UTF-8", true);
                encodingFilter.doFilter(servletRequest, servletResponse, filterChain);
            }

            @Override
            public void destroy() {
                logger.info("-------------------EncodingFilter被销毁-------------------");
            }
        };
    }
}
