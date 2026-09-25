package com.gisforum;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String email;
    private String password;
    private String role;

    // --- THÊM CỘT TIER ĐỂ PHÂN BIỆT VIP VÀ FREE ---
    @Column(columnDefinition = "varchar(20) default 'FREE'")
    private String tier = "FREE";

    public User() {}

    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.tier = "FREE"; // Mặc định tạo tài khoản là FREE
    }

    // --- GETTERS VÀ SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    // Getter và Setter cho quyền VIP
    public String getTier() { return tier; }
    public void setTier(String tier) { this.tier = tier; }
}