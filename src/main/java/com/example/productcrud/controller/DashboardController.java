package com.example.productcrud.controller;

import com.example.productcrud.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Map;

@Controller
public class DashboardController {

    private final ProductService productService;

    public DashboardController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/dashboard")
    public String index(Model model) {
        Map<String, Object> stats = productService.getDashboardStats();
        model.addAllAttributes(stats);
        return "index";
    }
}
