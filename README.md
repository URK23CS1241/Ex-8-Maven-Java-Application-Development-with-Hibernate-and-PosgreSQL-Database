Exercise 8 – Maven Java Application with Hibernate and PostgreSQL
Product Inventory System
Aim

To develop a Maven Java application using Hibernate ORM and PostgreSQL to implement a Product Inventory System that performs CRUD operations such as adding, viewing, updating, and deleting product records.

Technologies Used
Java
Maven
Hibernate ORM
PostgreSQL
Jakarta Persistence API
Eclipse / Spring Tool Suite
pgAdmin
Features

The application provides the following operations:

Add Product
View Products
Update Product
Delete Product
Exit

Each product contains:

Product ID
Product Name
Category
Price
Quantity
Project Structure
Exercise8-HibernateInventory/
│
├── pom.xml
│
└── src/
    └── main/
        ├── java/
        │   └── example/
        │       ├── HibernateUtil.java
        │       ├── Product.java
        │       ├── ProductDAO.java
        │       └── MainProgram.java
        │
        └── resources/
            └── hibernate.cfg.xml
Database Configuration

Create a PostgreSQL database named:

CREATE DATABASE inventorydb;

Connect to inventorydb and create the product table:

CREATE TABLE IF NOT EXISTS product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(100),
    price DOUBLE PRECISION,
    quantity INTEGER
);
Hibernate Configuration

The application connects to PostgreSQL using:

Database: inventorydb
Host: localhost
Port: 5432
Username: postgres

Update the PostgreSQL password in:

src/main/resources/hibernate.cfg.xml

Example:

<property name="hibernate.connection.password">
    YOUR_PASSWORD
</property>

Replace YOUR_PASSWORD with your PostgreSQL password.

Java Classes
Product.java

The Product class is the entity class mapped to the product table using Hibernate/Jakarta Persistence annotations.

It contains:

id
name
category
price
quantity
HibernateUtil.java

HibernateUtil creates and manages the Hibernate SessionFactory.

Configuration
    ↓
hibernate.cfg.xml
    ↓
SessionFactory
    ↓
Hibernate Session
ProductDAO.java

ProductDAO performs database operations using Hibernate.

Method	Operation
addProduct()	INSERT
viewProducts()	SELECT
updateProduct()	UPDATE
deleteProduct()	DELETE
MainProgram.java

MainProgram provides a console-based menu:

========== PRODUCT INVENTORY SYSTEM ==========

1. Add Product
2. View Products
3. Update Product
4. Delete Product
5. Exit

==============================================
How to Run
Step 1: Open the Maven Project

Open the project in Eclipse or Spring Tool Suite.

Step 2: Update Maven

Right-click the project:

Maven
→ Update Project
→ OK
Step 3: Configure PostgreSQL

Make sure PostgreSQL is running and the database inventorydb exists.

Step 4: Configure Password

Open:

src/main/resources/hibernate.cfg.xml

Enter your PostgreSQL password.

Step 5: Run the Application

Right-click:

MainProgram.java

Select:

Run As
→ Java Application
Sample Output
Add Product
Enter your choice: 1

Enter Product Name: Laptop
Enter Category: Electronics
Enter Price: 90000
Enter Quantity: 10

Product added successfully!
View Products
Enter your choice: 2

========== PRODUCT INVENTORY ==========

Product ID: 1 | Name: Laptop | Category: Electronics | Price: 90000.0 | Quantity: 10

=======================================
Update Product
Enter your choice: 3

Enter Product ID to update: 1
Enter New Product Name: HP Laptop
Enter New Category: Electronics
Enter New Price: 85000
Enter New Quantity: 15

Product updated successfully!
Delete Product
Enter your choice: 4

Enter Product ID to delete: 1

Product deleted successfully!
CRUD Operations
CREATE
   ↓
Add Product
   ↓
PostgreSQL

READ
   ↓
View Products
   ↓
PostgreSQL

UPDATE
   ↓
Update Product
   ↓
PostgreSQL

DELETE
   ↓
Delete Product
   ↓
PostgreSQL
Result

The Product Inventory System was successfully developed using Maven, Hibernate ORM, and PostgreSQL. CRUD operations such as Add, View, Update, and Delete were successfully performed on product records.
