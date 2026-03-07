package com.example.inventory.controller;

import com.example.inventory.service.ReportService;
import com.example.inventory.service.StockLogService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class ReportController {

    private final ReportService reportService;
    private final StockLogService stockLogService;

    public ReportController(ReportService reportService, StockLogService stockLogService) {
        this.reportService = reportService;
        this.stockLogService = stockLogService;
    }

    @GetMapping("/stock-logs")
    public String viewStockLogs(Model model) {
        model.addAttribute("logs", stockLogService.getAllLogs());
        return "stock-logs";
    }

    @GetMapping("/reports/products/csv")
    public ResponseEntity<ByteArrayResource> exportProductsCsv(Principal principal) {
        
        // Always send to the admin's email as per requirement
        reportService.sendProductsEmailReport("indhustudies19@gmail.com");

        byte[] csvBytes = reportService.generateProductsCsv();
        ByteArrayResource resource = new ByteArrayResource(csvBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=products.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }

    @GetMapping("/reports/logs/csv")
    public ResponseEntity<ByteArrayResource> exportLogsCsv() {
        
        byte[] csvBytes = reportService.generateStockLogsCsv();
        ByteArrayResource resource = new ByteArrayResource(csvBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=stock_logs.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }
}
