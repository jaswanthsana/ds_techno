package com.example.ecommerce.controllers;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping(params = "category")
    public ResponseEntity<Category> getCategoryById(@RequestParam Long category) {
        Category foundCategory = categoryService.getCategoryById(category);
        if (foundCategory != null) {
            return new ResponseEntity<>(foundCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping(params = "category")
    public ResponseEntity<Category> updateCategory(@RequestParam Long category, @RequestBody Category updatedCategory) {
        Category existingCategory = categoryService.getCategoryById(category);
        if (existingCategory != null) {
            updatedCategory.setId(category);
            Category updated = categoryService.updateCategory(updatedCategory);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(params = "category")
    public ResponseEntity<Void> deleteCategory(@RequestParam Long category) {
        Category existingCategory = categoryService.getCategoryById(category);
        if (existingCategory != null) {
            categoryService.deleteCategory(category);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
