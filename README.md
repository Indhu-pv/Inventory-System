Inventory Monitoring and Reporting System Implementation Plan

This document outlines the proposed architecture and implementation steps for the Inventory Monitoring and Reporting System using Spring Boot, Hibernate, MySQL, Thymeleaf (HTML, CSS, JS), and Spring Security.

Architecture & Technology Stack 

Backend Framework: Spring Boot 3.x with Java 17

Database & Migration: MySQL Community Server, Spring Data JPA, Hibernate ORM

Security: Spring Security (Form Login Authentication, Role-Based Authorization)

Frontend Template Engine: Thymeleaf (server-side rendering) with Vanilla CSS + JavaScript

Reporting Utilities: spring-boot-starter-mail for EMails, java.io / standard CSV libraries for exporting CSVs.

Roles:
USER: Only has access to dashboard (/dashboard). Restricted paths will trigger a 403 Access Denied response handler.
ADMIN: Has full access to /dashboard, /products, /low-stock, /stock-logs, and all reports functionality.


Verification Plan
Manual Verification
Database Connect: Test 
application.properties
 connection to jdbc:mysql://localhost:3306/inventory_db.
Access Control: Perform login with USER credentials and attempt to visit /products verify correct redirect to 403 page. Then perform as ADMIN and verify successful entry.
Core Functions Workflow: Insert stock, modify quantity incrementally, go to Logs to ensure the action created a valid timestamp log, then visit dashboard to ensure KPIs (Total Inventory Value) update accordingly.
Downloads Verification: Request CSV exports, open generated files and inspect the structure matches exactly definitions request.
Email Configuration: Ensure test SMTP setup (Mailtrap or locally hosted dev server) reliably receives test outbound message and attached data table report.
