package com.oozons.productManagement.controller;

import com.oozons.productManagement.exception.ResourceNotFoundException;
import com.oozons.productManagement.models.Category;
import com.oozons.productManagement.models.Product;
import com.oozons.productManagement.service.ProductService;
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

class ProductControllerTest {

    @Mock
    private ProductService productService;  // Simule le service

    @InjectMocks
    private ProductController productController; // Contrôleur testé

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllProducts() {
        // Arrange (Préparer)
        Category category = new Category(1L, "ELEC_CAT", "Electronics");
        List<Product> products = Arrays.asList(
                new Product(1L, "PROD_001", "Laptop", "High-end laptop", 1000.0, category)
        );
        when(productService.getProducts()).thenReturn(products);

        // Act (Exécuter)
        ResponseEntity<List<Product>> response = productController.getAllProducts();

        // Assert (Vérifier)
        assertEquals(200, response.getStatusCodeValue()); // Vérifie que la réponse est OK
        assertNotNull(response.getBody()); // Vérifie que le body n'est pas null
        assertEquals(1, response.getBody().size()); // Vérifie qu'il y a bien un produit dans la liste
        assertEquals("Laptop", response.getBody().get(0).getLibelle()); // Vérifie le nom du produit
    }


    @Test
    void shouldThrowExceptionWhenNoProductsFound() {
        // Arrange
        when(productService.getProducts()).thenReturn(List.of()); // Simule une liste vide

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productController.getAllProducts();
        });

        assertEquals("No products found.", exception.getMessage()); // Vérifie le message d'erreur
    }


    @Test
    void shouldSaveProduct() {
        // Arrange
        Category category = new Category(2L, "MOBILE_CAT", "Mobile Devices");
        Product product = new Product(2L, "PROD_002", "Phone", "Latest model", 500.0, category);
        when(productService.saveProduct(any(Product.class))).thenReturn(product);

        // Act
        ResponseEntity<Product> response = productController.saveProduct(product);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Phone", response.getBody().getLibelle());
    }

    @Test
    void shouldUpdateProduct() {
        // Arrange
        Category category = new Category(3L, "TABLET_CAT", "Tablets");
        Product product = new Product(3L, "PROD_003", "Tablet", "10-inch screen", 300.0, category);
        when(productService.updateProduct(eq(3L), any(Product.class))).thenReturn(product);

        // Act
        ResponseEntity<Product> response = productController.updateProduct(3L, product);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Tablet", response.getBody().getLibelle());
    }

    @Test
    void shouldDeleteProduct() {
        // Arrange
        Category category = new Category(4L, "DISPLAY_CAT", "Displays");
        Product product = new Product(4L, "PROD_004", "Monitor", "4K Display", 200.0, category);

        when(productService.getProducts()).thenReturn(List.of(product)); // S'assurer que le produit existe
        when(productService.deleteProduct(any(Product.class))).thenReturn("Product deleted successfully");

        // Act
        ResponseEntity<String> response = productController.deleteProduct(4L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product deleted successfully", response.getBody());
    }


    @Test
    void shouldThrowExceptionWhenDeletingNonExistingProduct() {
        // Arrange
        when(productService.getProducts()).thenReturn(List.of());

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productController.deleteProduct(999L);
        });

        assertEquals("Product with id 999 not found.", exception.getMessage());
    }
}
