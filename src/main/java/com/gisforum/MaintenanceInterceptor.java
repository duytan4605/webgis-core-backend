package com.gisforum;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MaintenanceInterceptor implements HandlerInterceptor {

    @Autowired
    private SystemSettingsRepository settingsRepo;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 👉 CHÌA KHÓA VÀNG CHỐNG LỖI CORS: Bỏ qua các request 'OPTIONS' từ Vue.js
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        try {
            SystemSettings settings = settingsRepo.findById(1L).orElse(new SystemSettings());
            
            if (settings.isMaintenance()) {
                String uri = request.getRequestURI();
                // Chừa đường lui cho Admin
                if (uri.contains("/api/settings") || uri.contains("/api/login") || uri.contains("/api/users/login")) {
                    return true;
                }
                response.sendError(503, "Hệ thống đang bảo trì!");
                return false;
            }
        } catch (Exception e) {
            // Nếu Database lỗi (chưa tạo bảng), in ra console và CHO PHÉP web chạy tiếp để không bị lỗi 500
            System.out.println("⚠️ Lỗi Interceptor (Có thể chưa tạo bảng system_settings): " + e.getMessage());
        }
        
        return true;
    }
}