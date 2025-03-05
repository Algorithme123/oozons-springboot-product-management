package com.oozons.productManagement.serviceImpl;

import com.oozons.productManagement.models.Category;
import com.oozons.productManagement.repository.CategorieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategorieRepository categorieRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllCategories() {
        // Arrange
        List<Category> categories = Arrays.asList(new Category(1L, "TECH_CAT", "Tech"));
        when(categorieRepository.findAll()).thenReturn(categories);

        // Act
        List<Category> result = categoryService.getAllCategories();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Tech", result.get(0).getLibelle());
        verify(categorieRepository, times(1)).findAll();
    }

    @Test
    void shouldCreateCategory() {
        // Arrange
        Category category = new Category(2L, "FOOD_CAT", "Food");
        when(categorieRepository.save(any(Category.class))).thenReturn(category);

        // Act
        Category created = categoryService.createCategory(category);

        // Assert
        assertNotNull(created);
        assertEquals("Food", created.getLibelle());
        verify(categorieRepository, times(1)).save(any(Category.class));
    }

    @Test
    void shouldUpdateCategory() {
        // Arrange
        Category existing = new Category(3L, "OLD_CAT", "OldName");
        Category updated = new Category(3L, "NEW_CAT", "NewName");

        when(categorieRepository.findById(3L)).thenReturn(Optional.of(existing));
        when(categorieRepository.save(any(Category.class))).thenReturn(updated);

        // Act
        Category result = categoryService.updateCategory(3L, updated);

        // Assert
        assertNotNull(result);
        assertEquals("NewName", result.getLibelle());
        verify(categorieRepository, times(1)).save(any(Category.class));
    }

    @Test
    void shouldDeleteCategory() {
        // Arrange
        Category category = new Category(4L, "TECH_CAT", "Tech");
        when(categorieRepository.findById(4L)).thenReturn(Optional.of(category));

        // Act
        String result = categoryService.deleteCategory(4L);

        // Assert
        assertEquals("Categorie supprimer avec  Success", result);
        verify(categorieRepository, times(1)).delete(category);
    }

    @Test
    void shouldReturnCategoryByCode() {
        // Arrange
        Category category = new Category(5L, "TECH_CAT", "Tech");
        when(categorieRepository.getCategoriesByCode("TECH_CAT")).thenReturn(category);

        // Act
        Category result = categoryService.getCategoryByCode("TECH_CAT");

        // Assert
        assertNotNull(result);
        assertEquals("Tech", result.getLibelle());
        verify(categorieRepository, times(1)).getCategoriesByCode("TECH_CAT");
    }
}
