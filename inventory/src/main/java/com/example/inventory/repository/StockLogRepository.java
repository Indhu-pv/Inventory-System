package com.example.inventory.repository;

import com.example.inventory.model.StockLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockLogRepository extends JpaRepository<StockLog, Long> {
    List<StockLog> findByProductId(Long productId);
}
