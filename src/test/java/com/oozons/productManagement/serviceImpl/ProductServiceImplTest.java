package com.oozons.productManagement.serviceImpl;

import com.oozons.productManagement.models.Category;
import com.oozons.productManagement.models.Product;
import com.oozons.productManagement.repository.ProductRepository;
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

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;  // Simule la base de données

    @InjectMocks
    private ProductServiceImpl productService;  // Service que l'on teste

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveProduct() {
        // Arrange
        Category category = new Category(1L, "ELEC_CAT", "Electronics");
        Product product = new Product(null, "UNKNOWN", "Laptop", "High-end laptop", 1000.0, category);

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product p = invocation.getArgument(0);
            p.setId(1L);
            return p;
        });

        // Act
        Product savedProduct = productService.saveProduct(product);

        // Assert
        assertNotNull(savedProduct);
        assertEquals("Laptop", savedProduct.getLibelle());
        assertEquals("LAP_PROD", savedProduct.getCode()); // Vérification du code généré
        verify(productRepository, times(1)).save(any(Product.class));
    }


    @Test
    void shouldReturnAllProducts() {
        // Arrange
        Category category = new Category(1L, "ELEC_CAT", "Electronics");
        List<Product> products = Arrays.asList(
                new Product(1L, "PROD_001", "Laptop", "High-end laptop", 1000.0, category),
                new Product(2L, "PROD_002", "Phone", "Latest model", 500.0, category)
        );
        when(productRepository.findAll()).thenReturn(products);

        // Act
        List<Product> result = productService.getProducts();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getLibelle());
        assertEquals("Phone", result.get(1).getLibelle());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void shouldUpdateProduct() {
        // Arrange
        Category category = new Category(1L, "ELEC_CAT", "Electronics");
        Product existingProduct = new Product(3L, "OLD_PROD", "Old Laptop", "Old description", 800.0, category);
        Product updatedProduct = new Product(3L, "NEW_PROD", "New Laptop", "Updated description", 1200.0, category);

        when(productRepository.findById(3L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // Act
        Product result = productService.updateProduct(3L, updatedProduct);

        // Assert
        assertNotNull(result);
        assertEquals("New Laptop", result.getLibelle());
        assertEquals("Updated description", result.getDescription());
        assertEquals(1200.0, result.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldDeleteProduct() {
        // Arrange
        Category category = new Category(1L, "ELEC_CAT", "Electronics");
        Product product = new Product(4L, "PROD_004", "Monitor", "4K Display", 200.0, category);

        doNothing().when(productRepository).delete(any(Product.class));

        // Act
        String result = productService.deleteProduct(product);

        // Assert
        assertEquals("Ok", result);
        verify(productRepository, times(1)).delete(any(Product.class));
    }

    @Test
    void shouldGenerateProductCode() {
        // Arrange
        Product product = new Product(null, "UNKNOWN", "Headphones", "Noise cancelling", 250.0, null);

        // Act
        String code = productService.generateCode(product);

        // Assert
        assertEquals("HEA_PROD", code);  // Vérifie si le code généré correspond
    }
}
