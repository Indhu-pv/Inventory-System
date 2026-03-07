package com.example.inventory.repository;

import com.example.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Query("SELECT p FROM Product p WHERE p.quantity < 100")
    List<Product> findLowStockProducts();

    @Query("SELECT SUM(p.price * p.quantity) FROM Product p")
    Double calculateTotalInventoryValue();
}
