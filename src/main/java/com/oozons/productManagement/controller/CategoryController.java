package com.oozons.productManagement.controller;

import com.oozons.productManagement.exception.BadRequestException;
import com.oozons.productManagement.exception.ResourceNotFoundException;
import com.oozons.productManagement.exception.InternalServerException;
import com.oozons.productManagement.models.Category;
import com.oozons.productManagement.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("No categories found.");
        }
        return ResponseEntity.ok(categories);
    }

    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        try {
            this.categoryService.createCategory(category);
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            throw new InternalServerException("An error occurred while creating the category.");
        }
    }

    @RequestMapping(value = "/categories/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        try {
            String result = this.categoryService.deleteCategory(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Category with id " + id + " not found.");
        }
    }

    @RequestMapping(value = "/categories/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            Category updatedCategory = this.categoryService.updateCategory(id, category);
            return ResponseEntity.ok(updatedCategory);
        } catch (Exception e) {
            throw new InternalServerException("An error occurred while updating the category.");
        }
    }
}
