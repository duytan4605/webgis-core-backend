package com.gisforum;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Áp dụng cho TOÀN BỘ API trong project
                .allowedOriginPatterns("*") // Cho phép Vue.js (8080) gọi sang
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Bắt buộc phải có OPTIONS để qua ải Preflight
                .allowedHeaders("*") // Cho phép gửi Token thoải mái
                .allowCredentials(false) // Tránh xung đột bảo mật khi dùng dấu *
                .maxAge(3600);
    }
}