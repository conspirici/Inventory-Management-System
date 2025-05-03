# 🛒 Inventory Management System - Java + MySQL

This is a **simple Inventory Management System** designed for **grocery stores**, built as a **Software Design and Architecture semester project** by a team of four. It follows proper **Object-Oriented Programming**, **UML modeling**, and **design principles** to support real-world maintainability and scalability.

---

## Key Modules

The system is divided into **7 functional modules**, each mapped to a clear use case and independently implementable:

1. **Login / Logout**
   - Role-based authentication (Admin, Employee)
   - Session management

2. **Account Management**
   - Admin can create, edit, or delete employee accounts
   - Enforces role-based access

3. **Add Item to Inventory**
   - Add new grocery items with attributes like name, quantity, category, price

4. **Delete Item from Inventory**
   - Delete outdated or discontinued items by Admin

5. **Search Item**
   - Search inventory by name, category, or ID

6. **View Inventory**
   - Display complete list of items with filtering options

7. **Generate Reports**
   - Create summary reports based on stock, activity, and item trends

---

## ⚙Tech Stack

| Layer            | Technology       |
|------------------|------------------|
| Language         | Java (JDK 17+)   |
| Database         | MySQL            |
| UI               | Java Swing       |
| Database Access  | JDBC             |
| Design Tools     | Draw.io / Lucidchart for UML |

---

## Design Methodology

### Object-Oriented Programming (OOP)

We used OOP to ensure modularity, reusability, and clean separation of concerns:

- **Encapsulation**: Each module has its own data and behavior
- **Abstraction**: Users interact with high-level operations
- **Inheritance**: Roles (e.g., Admin, Employee) can extend base User
- **Polymorphism**: Future enhancements can allow custom UI actions per role

---

### Software Design Principles & Patterns

- **Single Responsibility Principle (SRP)**: Each class has a well-defined job
- **GRASP Patterns**:
  - *Controller*: Each module has its own controller class
  - *Creator*: DAOs are responsible for creating model objects
  - *Information Expert*: Logic resides in the class that has the data
- **MVC Architecture**:
  - **Model**: `User`, `Item`, `Inventory`
  - **View**: `LoginUI`, `InventoryUI`, etc.
  - **Controller**: `LoginController`, `InventoryController`

---

## UML Diagrams (Created)

For each use case (module), the following were developed:

- **General Use Case Diagram** (with `<<include>>` and `<<extend>>` as needed)
- **Fully Dressed Use Cases** (structured table format)
- **System Sequence Diagrams (SSD)**
- **Communication Diagrams** (based on GRASP)
- **Class Diagrams** (showing structure and relationships)

---

## Roles & Access Control

| Role     | Capabilities                                                                 |
|----------|------------------------------------------------------------------------------|
| Admin    | Full access: account mgmt, inventory CRUD, reports                          |
| Employee | Limited access: login, view/search inventory, add items                     |

---

## Testing

- Manual test cases for each use case
- Valid and invalid login tests
- CRUD tests on inventory table
- SQL exception handling

---

## MySQL Setup

Sample `users` and `items` table created in MySQL with the following schema:

```sql
CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255),
    role VARCHAR(20)
);

CREATE TABLE items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    category VARCHAR(50),
    quantity INT,
    price DECIMAL(10, 2)
);
