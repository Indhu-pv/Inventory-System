package com.example.inventory.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_logs")
public class StockLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = true) // Changed to true to allow logs to remain if product is deleted
    private Product product;

    @Column(name = "product_name")
    private String productName;

    @Column(nullable = false)
    private String type; // "ADD" or "REDUCE" or "DELETE"

    @Column(nullable = false)
    private Integer quantityChanged;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public StockLog() {}

    public StockLog(Product product, String type, Integer quantityChanged) {
        this.product = product;
        this.productName = product != null ? product.getName() : "Unknown Product";
        this.type = type;
        this.quantityChanged = quantityChanged;
        this.timestamp = LocalDateTime.now();
    }

    public StockLog(String productName, String type, Integer quantityChanged) {
        this.product = null;
        this.productName = productName;
        this.type = type;
        this.quantityChanged = quantityChanged;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getQuantityChanged() { return quantityChanged; }
    public void setQuantityChanged(Integer quantityChanged) { this.quantityChanged = quantityChanged; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
