package com.gisforum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
@CrossOrigin(originPatterns = "*")
public class SettingsController {

    @Autowired
    private SystemSettingsRepository settingsRepo;

    @GetMapping
    public ResponseEntity<?> getSettings() {
        try {
            SystemSettings settings = settingsRepo.findById(1L).orElse(new SystemSettings());
            return ResponseEntity.ok(settings);
        } catch (Exception e) {
            // Trả về mặc định nếu DB lỗi để Vue.js không bị chết đỏ màn hình
            return ResponseEntity.ok(new SystemSettings());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateSettings(@RequestBody SystemSettings newSettings) {
        try {
            SystemSettings settings = settingsRepo.findById(1L).orElse(new SystemSettings());
            
            settings.setId(1L);
            settings.setMaintenance(newSettings.isMaintenance());
            settings.setAllowSignup(newSettings.isAllowSignup());
            
            settingsRepo.save(settings);
            return ResponseEntity.ok("Cập nhật thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi Backend: " + e.getMessage());
        }
    }
}