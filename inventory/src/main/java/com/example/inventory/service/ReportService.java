package com.example.inventory.service;

import com.example.inventory.model.Product;
import com.example.inventory.model.StockLog;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.repository.StockLogRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;

@Service
public class ReportService {

    private final ProductRepository productRepository;
    private final StockLogRepository stockLogRepository;
    private final JavaMailSender mailSender;

    public ReportService(ProductRepository productRepository, StockLogRepository stockLogRepository, JavaMailSender mailSender) {
        this.productRepository = productRepository;
        this.stockLogRepository = stockLogRepository;
        this.mailSender = mailSender;
    }

    public byte[] generateProductsCsv() {
        List<Product> products = productRepository.findAll();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);

        writer.println("Product ID,Name,Category,Price,Quantity,Date");
        for (Product p : products) {
            writer.printf("\"%d\",\"%s\",\"%s\",\"%.2f\",\"%d\",\"%s\"%n",
                    p.getId(), p.getName(), p.getCategory(), p.getPrice(), p.getQuantity(), p.getUpdatedAt());
        }
        writer.flush();
        return out.toByteArray();
    }

    public byte[] generateStockLogsCsv() {
        List<StockLog> logs = stockLogRepository.findAll();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);

        writer.println("Log ID,Product ID,Type,Quantity Changed,Timestamp");
        for (StockLog log : logs) {
            writer.printf("\"%d\",\"%d\",\"%s\",\"%d\",\"%s\"%n",
                    log.getId(), log.getProduct().getId(), log.getType(), log.getQuantityChanged(), log.getTimestamp());
        }
        writer.flush();
        return out.toByteArray();
    }

    public void sendProductsEmailReport(String toAddress) {
        try {
            List<Product> products = productRepository.findAll();
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toAddress);
            helper.setFrom("indhustudies19@gmail.com");
            helper.setSubject("Inventory Products Report");

            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<h3>Inventory Report</h3>");
            htmlContent.append("<table border='1' cellpadding='5' cellspacing='0'>");
            htmlContent.append("<tr><th>ID</th><th>Name</th><th>Category</th><th>Price</th><th>Quantity</th></tr>");
            for (Product p : products) {
                htmlContent.append("<tr>")
                           .append("<td>").append(p.getId()).append("</td>")
                           .append("<td>").append(p.getName()).append("</td>")
                           .append("<td>").append(p.getCategory()).append("</td>")
                           .append("<td>").append(p.getPrice()).append("</td>")
                           .append("<td>").append(p.getQuantity()).append("</td>")
                           .append("</tr>");
            }
            htmlContent.append("</table>");

            helper.setText(htmlContent.toString(), true);

            // Attach CSV
            byte[] csvBytes = generateProductsCsv();
            helper.addAttachment("products_report.csv", new ByteArrayResource(csvBytes));

            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
