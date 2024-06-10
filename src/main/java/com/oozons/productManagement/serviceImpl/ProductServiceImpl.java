package com.oozons.productManagement.serviceImpl;

import com.oozons.productManagement.models.Category;
import com.oozons.productManagement.models.Product;
import com.oozons.productManagement.repository.ProductRepository;
import com.oozons.productManagement.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl  implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Product saveProduct(Product product) {
        product.setCode(this.generateCode(product));
        return this.productRepository.save(product);
    }

    @Override
    public List<Product> getProducts() {

        return this.productRepository.findAll();
    }

    @Override
    public Product updateProduct(Long id, Product product) {

        Product product1 = this.productRepository.findById(id).get();
        product1.setCode(this.generateCode(product));
        product1.setLibelle(product.getLibelle());
        product1.setDescription(product.getDescription());
        product1.setPrice(product.getPrice());

        return  this.productRepository.save(product1);

    }

    @Override
    public String deleteProduct(Product product) {
        this.productRepository.delete(product);
        return "Ok";

    }
    public String generateCode(Product product){

        String code = product.getLibelle().substring(0,3).toUpperCase() + "_PROD";

        return code;
    }

}
