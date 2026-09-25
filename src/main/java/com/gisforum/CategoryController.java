package com.gisforum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository repo;

    // 1. Lấy danh sách danh mục (GET)
    @GetMapping
    public List<Category> getAllCategories() {
        return repo.findAll();
    }

    // 2. Thêm mới danh mục (POST)
    @PostMapping("/add")
    public Category addCategory(@RequestBody Category category) {
        return repo.save(category);
    }

    // 3. Cập nhật danh mục (PUT) - ĐỂ FIX LỖI SỬA 404
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        Category category = repo.findById(id).orElse(null);
        if (category != null) {
            category.setName(categoryDetails.getName());
            category.setDescription(categoryDetails.getDescription());
            repo.save(category);
            return ResponseEntity.ok(category);
        }
        return ResponseEntity.status(404).body("Không tìm thấy danh mục để cập nhật!");
    }

    // 4. Xóa danh mục (DELETE)
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        repo.deleteById(id);
    }
}