package com.megacoffee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpInterceptor())
                .addPathPatterns("/**") // 모든 경로에 인터셉터 적용
                .excludePathPatterns(
                    "/css/**", 
                    "/js/**", 
                    "/images/**", 
                    "/webjars/**", 
                    "/favicon.ico", 
                    "/error" // 에러 페이지 등도 제외
                );
    }
    
}
