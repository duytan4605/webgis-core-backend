package com.gisforum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // 👉 BƯỚC 1: Gọi kho chứa Cấu hình hệ thống vào đây
    @Autowired
    private SystemSettingsRepository settingsRepo; 

    // 1. Lấy hết danh sách (Dùng cho trang Admin)
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 2. Thêm mới User (👉 BƯỚC 2: ĐÃ GẮN CÔNG TẮC KHÓA ĐĂNG KÝ)
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        // Lấy cấu hình lên kiểm tra
        SystemSettings settings = settingsRepo.findById(1L).orElse(new SystemSettings());
        
        // Nếu nút "Cho phép đăng ký" đang tắt, và người tạo không phải ADMIN -> ĐÁ VĂNG
        if (!settings.isAllowSignup() && !"ADMIN".equals(user.getRole())) {
            return ResponseEntity.status(403).body("Hệ thống đang tạm khóa đăng ký thành viên mới!");
        }
        
        // Nếu được phép thì lưu bình thường
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    // 3. Cập nhật User
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedData) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setName(updatedData.getName());
            existingUser.setEmail(updatedData.getEmail());
            existingUser.setRole(updatedData.getRole());
            if (updatedData.getTier() != null) {
                existingUser.setTier(updatedData.getTier()); 
            }
            if (updatedData.getPassword() != null && !updatedData.getPassword().isEmpty()) {
                existingUser.setPassword(updatedData.getPassword());
            }
            userRepository.save(existingUser);
            return ResponseEntity.ok(existingUser);
        }
        return ResponseEntity.status(404).body("Không tìm thấy tài khoản để cập nhật!");
    }

    // 4. Xóa User
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    // 5. Kiểm tra Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginData) {
        User user = userRepository.findByEmail(loginData.getEmail());
        if (user != null && user.getPassword().equals(loginData.getPassword())) {
            return ResponseEntity.ok(user); 
        }
        return ResponseEntity.status(401).body("Tài khoản hoặc mật khẩu không chính xác!");
    }

    // 6. API NÂNG CẤP VIP 
    @PutMapping("/{id}/upgrade-vip")
    public ResponseEntity<?> upgradeToVip(@PathVariable Long id) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setTier("VIP"); // Gắn mác đại gia
            userRepository.save(existingUser); // Lưu vào CSDL
            return ResponseEntity.ok(existingUser);
        }
        return ResponseEntity.status(404).body("Không tìm thấy tài khoản để nâng cấp!");
    }
}