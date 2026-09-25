package com.gisforum;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "system_settings")
public class SystemSettings {
    
    @Id
    private Long id = 1L; // Cố định ID là 1 (Chỉ có 1 dòng cấu hình)
    
    private boolean maintenance = false; // Mặc định: tắt bảo trì
    private boolean allowSignup = true;  // Mặc định: cho phép đăng ký
    
    public SystemSettings() {}

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public boolean isMaintenance() { return maintenance; }
    public void setMaintenance(boolean maintenance) { this.maintenance = maintenance; }

    public boolean isAllowSignup() { return allowSignup; }
    public void setAllowSignup(boolean allowSignup) { this.allowSignup = allowSignup; }
}