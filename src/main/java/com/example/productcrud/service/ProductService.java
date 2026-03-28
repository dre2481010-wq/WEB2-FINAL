package com.example.productcrud.service;

import com.example.productcrud.model.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();
    private Long nextId = 7L;

    public ProductService() {
        products.add(new Product(1L, "Laptop ASUS ROG", "Elektronik",
                18500000, 8, "Laptop gaming ASUS ROG dengan prosesor terbaru dan kartu grafis RTX",
                true, LocalDate.of(2025, 1, 15)));

        products.add(new Product(2L, "Mouse Logitech MX Master", "Elektronik",
                1200000, 35, "Mouse wireless ergonomis dengan sensor presisi tinggi",
                true, LocalDate.of(2025, 2, 10)));

        products.add(new Product(3L, "Buku Java Programming", "Buku",
                150000, 30, "Buku panduan lengkap pemrograman Java dari dasar hingga mahir",
                true, LocalDate.of(2025, 3, 5)));

        products.add(new Product(4L, "Kopi Arabica Toraja 250g", "Makanan",
                85000, 100, "Kopi arabica premium dari Toraja dengan cita rasa khas",
                true, LocalDate.of(2025, 4, 20)));

        products.add(new Product(5L, "Headphone Sony WH-1000XM5", "Elektronik",
                4500000, 15, "Headphone wireless dengan noise cancelling terbaik di kelasnya",
                true, LocalDate.of(2025, 5, 1)));

        products.add(new Product(6L, "Kemeja Batik Premium", "Pakaian",
                350000, 50, "Kemeja batik premium motif parang dengan bahan katun berkualitas",
                false, LocalDate.of(2025, 6, 12)));
    }

    public List<Product> findAll() {
        return products;
    }

    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(nextId++);
            products.add(product);
        } else {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getId().equals(product.getId())) {
                    products.set(i, product);
                    break;
                }
            }
        }
        return product;
    }

    public void deleteById(Long id) {
        products.removeIf(p -> p.getId().equals(id));
    }

    public List<String> getAllCategories() {
        return products.stream()
                .map(Product::getCategory)
                .distinct()
                .sorted()
                .toList();
    }
}
