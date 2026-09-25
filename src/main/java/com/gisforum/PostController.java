package com.gisforum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
// Đã có file CorsConfig nên không cần @CrossOrigin ở đây nữa để tránh lỗi 500
public class PostController {

    @Autowired
    private PostRepository postRepository;

    // 1. LẤY DANH SÁCH BÀI VIẾT CÓ PHÂN TRANG (Để hiện lại nút phân trang bên Vue)
    @GetMapping
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    // 2. THÊM MỚI BÀI VIẾT
    @PostMapping
    public Post addPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    // 3. CẬP NHẬT DỮ LIỆU (Hàm cho nút Sửa trong Admin)
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Post updatedData) {
        Post existingPost = postRepository.findById(id).orElse(null);
        
        if (existingPost != null) {
            existingPost.setTitle(updatedData.getTitle());
            existingPost.setContent(updatedData.getContent());
            existingPost.setLat(updatedData.getLat());
            existingPost.setLng(updatedData.getLng());
            
            // Cập nhật cả ảnh và danh mục nếu có thay đổi
            if (updatedData.getImageUrl() != null) existingPost.setImageUrl(updatedData.getImageUrl());
            if (updatedData.getCategory() != null) existingPost.setCategory(updatedData.getCategory());
            
            postRepository.save(existingPost);
            return ResponseEntity.ok(existingPost);
        }
        
        return ResponseEntity.status(404).body("Không tìm thấy bài viết!");
    }

    // 4. XÓA BÀI VIẾT
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
    }

    // 5. LỌC BÀI VIẾT THEO DANH MỤC (Cho trang chủ)
    @GetMapping("/category/{categoryId}")
    public List<Post> getPostsByCategory(@PathVariable Long categoryId) {
        return postRepository.findByCategoryId(categoryId);
    }

    // 6. XEM CHI TIẾT BÀI VIẾT (Có tích hợp logic kiểm tra VIP sau này)
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        Post post = postRepository.findById(id).orElse(null);
        
        if (post != null) {
            // Sau này ông giáo thêm cột isPremium vào Post.java thì bật logic này lên:
            /*
            if (post.getIsPremium() && !user.getTier().equals("VIP")) {
                return ResponseEntity.status(403).body("Nội dung này chỉ dành cho đại gia VIP!");
            }
            */
            return ResponseEntity.ok(post);
        }
        
        return ResponseEntity.status(404).body("Bài viết này không tồn tại!");
    }
}