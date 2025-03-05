package com.oozons.productManagement.controller;


import com.oozons.productManagement.models.Category;
import com.oozons.productManagement.serviceImpl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryServiceImpl categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllCategories() {
        // Arrange
        List<Category> categories = Arrays.asList(new Category(1L, "Tech", "TECH_CAT"));
        when(categoryService.getAllCategories()).thenReturn(categories);

        // Act
        ResponseEntity<List<Category>> response = categoryController.getAllCategories();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }


    @Test
    void shouldSaveCategory() {
        // Arrange
        Category category = new Category(1L, "FOO_CAT", "Food");  // Correction de l'ordre des paramètres
        when(categoryService.createCategory(any(Category.class))).thenReturn(category);

        // Act
        ResponseEntity<Category> response = categoryController.saveCategory(category);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("FOO_CAT", response.getBody().getCode());  // Vérification du code
        assertEquals("Food", response.getBody().getLibelle());  // Vérification du libellé
    }


    @Test
    void shouldDeleteCategory() {
        // Arrange
        when(categoryService.deleteCategory(1L)).thenReturn("Category deleted successfully");

        // Act
        ResponseEntity<String> response = categoryController.deleteCategory(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Category deleted successfully", response.getBody());
    }
}
