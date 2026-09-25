package com.gisforum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Properties;

@SpringBootApplication
public class BackendGisApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BackendGisApplication.class);
        
        Properties props = new Properties();
        props.put("server.port", "8085");
        props.put("spring.h2.console.enabled", "true");
        props.put("spring.h2.console.path", "/h2-console");
        
        app.setDefaultProperties(props);
        app.run(args);
        
        System.out.println("\n===============================================");
        System.out.println("SERVER ĐÃ CHẠY TẠI CỔNG 8085");
        System.out.println("VÀO ĐÂY: http://localhost:8085/h2-console");
        System.out.println("VÀO ĐÂY: http://localhost:8085/api/posts");
        System.out.println("===============================================\n");
    }
}