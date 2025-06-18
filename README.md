# 🛒 GrocerFlow - Inventory Management System

**GrocerFlow** is a lightweight and modular **Inventory Management System** tailored for small to medium grocery stores. It was built as a semester project for the **Software Design and Architecture (SDA)** course using core Java, JDBC, and MySQL, with an emphasis on **Object-Oriented Programming**, **clean architecture**, and **design principles**.

---

## 📦 Functional Modules

The system is divided into key modules, each representing a core business function:

### 1. Authentication
- Role-based login (Admin, Employee)
- Secure credential validation using MySQL

### 2. Account Management (Admin-only)
- Add new employees
- View, update, and delete employee accounts

### 3. Product Management
- Add new products with name, category, quantity, price
- Delete outdated products
- View full product list

### 4. Inventory Search
- Filter/search items by name or category

### 5. Reporting (Planned)
- Placeholder for future reports on stock summary and trends

---

## 🧱 Architecture & Design

### 🧩 Technologies Used

| Layer            | Technology       |
|------------------|------------------|
| Language         | Java (JDK 17+)   |
| GUI              | Java Swing       |
| Database         | MySQL            |
| DB Connectivity  | JDBC             |
| Build Tool       | (Manual build, no Maven/Gradle) |
| IDE              | IntelliJ / NetBeans compatible |

---

### 🧠 OOP Design Principles

- **Encapsulation**: Separate concerns for models, views, and controllers
- **Abstraction**: UI abstracts internal logic from users
- **Inheritance**: Designed to support future role expansion
- **Polymorphism**: Future roles can override controller behavior

---

### 🏛️ Architectural Patterns

- **MVC Architecture**
  - `Model`: Represents the core domain (e.g., `Product`, `User`)
  - `View`: Swing-based interfaces (e.g., `AddProductView`)
  - `Controller`: Manages logic (`ProductController`, `UserController`)

- **GRASP Patterns**
  - *Controller*: Each functional UI delegates to a controller
  - *Creator*: Controllers instantiate and persist models
  - *Information Expert*: Business logic remains in model classes

- **Single Responsibility Principle**:
  Each class handles only one concern (e.g., `AddProductView` handles only UI logic for product creation)

---

## 🗂️ Project Structure

grocerflow/
├── model/
│ └── Product.java, User.java
├── controller/
│ └── ProductController.java, UserController.java
├── view/
│ └── LoginView.java, AddProductView.java, DashboardView.java
├── db/
│ └── DBConnection.java

## 🔐 Roles & Permissions

| Role     | Access                                             |
|----------|----------------------------------------------------|
| Admin    | Full access: employee management, inventory CRUD   |
| Employee | Limited access: add/view/search inventory          |

---

## 🧪 Testing

- Manual test cases for:
  - Login/Logout flows
  - Add/delete products
  - Search functionality
- Basic exception handling for:
  - SQL errors
  - Invalid input formats
