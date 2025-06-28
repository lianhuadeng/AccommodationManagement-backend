package com.scu.accommodationmanagement.conifg;

import com.scu.accommodationmanagement.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //指定需要拦截的路径，并添加拦截器

        //user拦截器
        registry.addInterceptor(loginInterceptor).addPathPatterns(
                //需要拦截的路径
                "/**"
        ).excludePathPatterns("/login", "/logout", "/image/**", "/error");
    }
}
