package com.example.inventory.controller;

import com.example.inventory.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final ProductService productService;

    public DashboardController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("totalProducts", productService.getTotalProducts());
        model.addAttribute("totalValue", productService.getTotalInventoryValue());
        model.addAttribute("lowStockCount", productService.getLowStockProducts().size());
        return "dashboard";
    }
}
