package com.oozons.productManagement.repository;

import com.oozons.productManagement.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository extends JpaRepository<Category, Long> {
}
