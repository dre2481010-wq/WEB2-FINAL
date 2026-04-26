package com.example.productcrud.service;

import com.example.productcrud.model.Category;
import com.example.productcrud.model.Product;
import com.example.productcrud.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> findFiltered (String keyword, Category category, int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        if(keyword != null && !keyword.isBlank() && category != null){
            return productRepository.findByNameContainingIgnoreCaseAndCategory(keyword, category, pageable);
        }else if (keyword != null && !keyword.isBlank()){
            return productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        }else if (category != null){
            return productRepository.findByCategory(category, pageable);
        }else{
            return productRepository.findAll(pageable);
        }
    }

    public Map<String, Object> getDashboardStats() {
        List<Product> allProducts = productRepository.findAll();
        Map<String, Object> stats = new HashMap<>();

        long totalProducts = allProducts.size();

        long totalInventoryValue = allProducts.stream()
                .mapToLong(p -> p.getPrice() * p.getStock()).sum();

        long activeCount = allProducts.stream().filter(Product::isActive).count();
        long inactiveCount = totalProducts - activeCount;

        Map<Category, Long> productsByCategory = allProducts.stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));

        List<Product> lowStockProducts = allProducts.stream()
                .filter(p -> p.getStock() <= 5).collect(Collectors.toList());

        stats.put("totalProducts", totalProducts);
        stats.put("totalValue", totalInventoryValue);
        stats.put("activeCount", activeCount);
        stats.put("inactiveCount", inactiveCount);
        stats.put("categoryStats", productsByCategory);
        stats.put("lowStockList", lowStockProducts);

        return stats;
    }

}