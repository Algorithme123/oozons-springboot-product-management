package com.oozons.productManagement.service;

import com.oozons.productManagement.models.Category;

import java.util.List;

public interface CategoryService {


    Category getCategoryByCode(String code);

    Category createCategory(Category category);

    List<Category> getAllCategories();

    Category updateCategory(Long id,Category category);

    String deleteCategory(Long id);

}
