package com.example.config;

import com.example.interceptor.Authorizeinterceptor;
import jakarta.annotation.Resource;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Resource
    Authorizeinterceptor interceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns("/api/auth/**").excludePathPatterns("/api/product/**").excludePathPatterns("/api/file/**").excludePathPatterns("api/power/**");
    }
}
