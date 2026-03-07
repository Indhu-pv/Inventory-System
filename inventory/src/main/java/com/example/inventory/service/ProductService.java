package com.example.inventory.service;

import com.example.inventory.model.Product;
import com.example.inventory.model.StockLog;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.repository.StockLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final StockLogRepository stockLogRepository;

    public ProductService(ProductRepository productRepository, StockLogRepository stockLogRepository) {
        this.productRepository = productRepository;
        this.stockLogRepository = stockLogRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getLowStockProducts() {
        return productRepository.findLowStockProducts();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void addStock(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + productId));
        
        product.setQuantity(product.getQuantity() + 1);
        productRepository.save(product);
        
        StockLog log = new StockLog(product, "ADD", 1);
        stockLogRepository.save(log);
    }

    @Transactional
    public void reduceStock(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + productId));
        
        if (product.getQuantity() > 0) {
            product.setQuantity(product.getQuantity() - 1);
            productRepository.save(product);
            
            StockLog log = new StockLog(product, "REDUCE", 1);
            stockLogRepository.save(log);
        }
    }

    @Transactional
    public void deleteProduct(Long productId) {
       Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + productId));
       
       StockLog log = new StockLog(product.getName(), "DELETE", product.getQuantity());
       stockLogRepository.save(log);
       
       // Remove association from existing logs so we don't hit foreign key constraints
       List<StockLog> logs = stockLogRepository.findByProductId(productId);
       for(StockLog existingLog : logs) {
           existingLog.setProduct(null);
           stockLogRepository.save(existingLog);
       }
       
       productRepository.delete(product);
    }

    public long getTotalProducts() {
        return productRepository.count();
    }

    public Double getTotalInventoryValue() {
        Double val = productRepository.calculateTotalInventoryValue();
        return val != null ? val : 0.0;
    }
}
