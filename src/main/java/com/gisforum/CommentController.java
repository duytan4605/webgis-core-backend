package com.gisforum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    // 1. Dành cho trang Admin (Lấy TẤT CẢ bình luận trong hệ thống)
    @GetMapping
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    // 2. 👉 ĐÃ THÊM: Dành cho trang PostDetail (Chỉ lấy bình luận của 1 bài viết)
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    // 3. Đăng bình luận mới
    @PostMapping
    public Comment addComment(@RequestBody Comment comment) {
        return commentRepository.save(comment);
    }
    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment commentDetails) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if(comment != null) {
            comment.setContent(commentDetails.getContent());
            return commentRepository.save(comment);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
    }
}