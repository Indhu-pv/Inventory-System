package com.example.inventory.service;

import com.example.inventory.model.StockLog;
import com.example.inventory.repository.StockLogRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockLogService {

    private final StockLogRepository stockLogRepository;

    public StockLogService(StockLogRepository stockLogRepository) {
        this.stockLogRepository = stockLogRepository;
    }

    public List<StockLog> getAllLogs() {
        return stockLogRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }
}
