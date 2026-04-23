package com.example.productcrud.repository;
import com.example.productcrud.model.Category;
import com.example.productcrud.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findByNameContainingIgnoreCaseAndCategory(
            String keyword, Category category, Pageable pageable
    );

    Page<Product> findByNameContainingIgnoreCase(
            String keyword, Pageable pageable
    );

    Page<Product> findByCategory(Category category, Pageable pageable);
}