package com.gisforum;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // Bổ sung thư viện List

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // Phải có Pageable ở cuối thì mới trả về Page được
    Page<Post> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    
    // --- HÀM MỚI ĐƯỢC THÊM VÀO ĐỂ FIX LỖI 404 ---
    // Lệnh cho Database tìm tất cả bài viết thuộc về một Category ID cụ thể
    List<Post> findByCategoryId(Long categoryId);
}