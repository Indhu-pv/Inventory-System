# Inventory Monitoring and Reporting System

This project implements an **Inventory Monitoring and Reporting System** built using **Spring Boot, Hibernate, MySQL, Thymeleaf, and Spring Security**.  
It allows administrators to manage products, monitor stock levels, generate reports, and maintain stock activity logs.

---

## Architecture & Technology Stack

### Backend
- **Framework:** Spring Boot 3.x
- **Language:** Java 17
- **ORM:** Hibernate
- **Persistence:** Spring Data JPA

### Database
- **Database:** MySQL Community Server
- **Schema Management:** JPA + Hibernate

### Security
- **Framework:** Spring Security
- **Authentication:** Form Login
- **Authorization:** Role-Based Access Control

### Frontend
- **Template Engine:** Thymeleaf
- **UI Technologies:** HTML, CSS, JavaScript (Vanilla)

### Reporting Utilities
- **Email Notifications:** `spring-boot-starter-mail`
- **CSV Export:** Java IO / Standard CSV utilities

---

## User Roles

### USER
- Access limited to:
  - `/dashboard`
- Attempts to access restricted pages will return a **403 Access Denied** response.

### ADMIN
Full system access including:

- `/dashboard`
- `/products`
- `/low-stock`
- `/stock-logs`
- Report generation
- CSV export
- Email reporting

---

## System Features

- Product inventory management
- Stock quantity updates
- Low stock monitoring
- Stock activity logging with timestamps
- Inventory dashboard with KPIs
- CSV report exports
- Email report delivery
- Role-based authentication and authorization

---

## Verification Plan

### Manual Verification

#### 1. Database Connection

Verify database connection using the configuration in `application.properties`.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

Ensure the application connects successfully to **MySQL**.

---

#### 2. Access Control Testing

1. Login with **USER credentials**
2. Attempt to visit:

```
/products
```

Expected result:

```
403 Access Denied
```

3. Login with **ADMIN credentials**

Expected result:

- Access to all protected routes is allowed.

---

#### 3. Core Function Workflow

1. Insert a product with stock.
2. Modify product quantity incrementally.
3. Navigate to:

```
/stock-logs
```

Verify:

- A valid **timestamped log entry** is created.

4. Navigate to:

```
/dashboard
```

Verify:

- **KPIs update correctly**, such as **Total Inventory Value**.

---

#### 4. CSV Download Verification

1. Request a CSV export from the reporting module.
2. Download the generated file.
3. Open it in Excel or a text editor.

Verify:

- Correct **column structure**
- Accurate **data values**
- Format matches the defined report structure.

---

#### 5. Email Configuration Verification

Use a test SMTP environment.

Recommended tools:
- Mailtrap
- Local SMTP development server

Steps:

1. Trigger the email report feature.
2. Confirm the email is delivered successfully.
3. Verify:

- Email body renders correctly
- Attached data table report is accurate.

---

## Project Structure

```
src
 ├── main
 │   ├── java
 │   │   └── com.inventory
 │   │       ├── controller
 │   │       ├── service
 │   │       ├── repository
 │   │       ├── model
 │   │       └── security
 │   └── resources
 │       ├── templates
 │       ├── static
 │       └── application.properties
```

---

## Future Improvements

- REST API integration
- Analytics dashboard with charts
- Automated scheduled reports
- Docker containerization
- Pagination and advanced search features

---

## Author

Inventory Monitoring and Reporting System Project
