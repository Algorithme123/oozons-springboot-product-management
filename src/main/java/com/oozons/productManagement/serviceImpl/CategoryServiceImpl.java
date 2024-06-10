package com.oozons.productManagement.serviceImpl;

import com.oozons.productManagement.models.Category;
import com.oozons.productManagement.repository.CategorieRepository;
import com.oozons.productManagement.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategorieRepository categorieRepository;

    public CategoryServiceImpl(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @Override
    public Category createCategory(Category category) {

        category.setCode(this.generateCode(category));

       return this.categorieRepository.save(category);
    }


    @Override
    public List<Category> getAllCategories() {

        return this.categorieRepository.findAll();
    }

    @Override
    public Category updateCategory(Long id,Category categoryRequest) {

        Category category = categorieRepository.findById(id).orElseThrow();
        category.setLibelle(categoryRequest.getLibelle());
        category.setCode(this.generateCode(categoryRequest));
        return categorieRepository.save(category);

    }

    @Override
    public String deleteCategory(Long id) {
        Category category= categorieRepository.findById(id).orElseThrow(()-> new RuntimeException("Country not found with id"));
        this.categorieRepository.delete(category);

        return "Categorie supprimer avec  Success";
    }


    public String generateCode(Category category){

        String code = category.getLibelle().substring(0,3).toUpperCase() + "_CAT";

        return code;
    }
}
