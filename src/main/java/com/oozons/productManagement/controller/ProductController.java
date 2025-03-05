package com.oozons.productManagement.controller;

import com.oozons.productManagement.exception.BadRequestException;
import com.oozons.productManagement.exception.ResourceNotFoundException;
import com.oozons.productManagement.exception.InternalServerException;
import com.oozons.productManagement.models.Product;
import com.oozons.productManagement.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getProducts();
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found.");
        }
        return ResponseEntity.ok(products);
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        try {
            Product savedProduct = this.productService.saveProduct(product);
            return ResponseEntity.ok(savedProduct);
        } catch (Exception e) {
            throw new InternalServerException("An error occurred while creating the product.");
        }
    }

    @RequestMapping(value = "/products/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            Product product = productService.getProducts().stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found."));

            String result = this.productService.deleteProduct(product);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Product with id " + id + " not found.");
        }
    }


    @RequestMapping(value = "/products/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            Product updatedProduct = this.productService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            throw new InternalServerException("An error occurred while updating the product.");
        }
    }
}
