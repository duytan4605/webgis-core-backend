package com.gisforum; // Sửa lại cho đúng package của ông

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Tìm tất cả bình luận của một bài viết dựa trên ID
    java.util.List<Comment> findByPostId(Long postId);
    
}