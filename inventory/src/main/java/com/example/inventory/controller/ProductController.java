package com.example.inventory.controller;

import com.example.inventory.model.Product;
import com.example.inventory.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String viewProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/products/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        
        List<String> categories = productService.getAllProducts().stream()
                .map(Product::getCategory)
                .distinct()
                .collect(Collectors.toList());
        model.addAttribute("categories", categories);
        
        return "add-product";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product) {
        product.setQuantity(product.getQuantity() != null ? product.getQuantity() : 0);
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @PostMapping("/products/{id}/add-stock")
    @ResponseBody
    public String addStock(@PathVariable Long id) {
        productService.addStock(id);
        return "Stock added successfully";
    }

    @PostMapping("/products/{id}/reduce-stock")
    @ResponseBody
    public String reduceStock(@PathVariable Long id) {
        productService.reduceStock(id);
        return "Stock reduced successfully";
    }

    @PostMapping("/products/{id}/delete")
    @ResponseBody
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }

    @GetMapping("/low-stock")
    public String viewLowStock(Model model) {
        model.addAttribute("products", productService.getLowStockProducts());
        return "low-stock";
    }
}
