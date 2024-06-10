package com.oozons.productManagement.service;

import com.oozons.productManagement.models.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    List<Product> getProducts();
    Product updateProduct(Long id,Product product);
    String deleteProduct(Product product);

}
