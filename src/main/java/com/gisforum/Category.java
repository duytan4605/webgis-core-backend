package com.gisforum;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // ĐỔI "title" THÀNH "name" CHO KHỚP VỚI VUE BÊN FRONTEND
    private String name;      
    private String description;

    public Category() {}

    // Getters và Setters chuẩn chỉ
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getName() { 
        return name; 
    }
    
    // HÀM NÀY ĐÃ ĐƯỢC FIX, KHÔNG CÒN ĐỂ TRỐNG NỮA!
    public void setName(String name) { 
        this.name = name; 
    }
    
    public String getDescription() { 
        return description; 
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }
}