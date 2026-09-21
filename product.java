============================================================
EXERCISE 8 - MAVEN HIBERNATE POSTGRESQL
PRODUCT INVENTORY SYSTEM
============================================================


============================================================
FILE 1: pom.xml
============================================================

<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.inventory</groupId>
    <artifactId>Exercise8-HibernateInventory</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <dependencies>

        <dependency>
            <groupId>org.hibernate.orm</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>6.6.5.Final</version>
        </dependency>

        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.4</version>
        </dependency>

        <dependency>
            <groupId>jakarta.persistence</groupId>
            <artifactId>jakarta.persistence-api</artifactId>
            <version>3.1.0</version>
        </dependency>

    </dependencies>

</project>


============================================================
FILE 2: src/main/resources/hibernate.cfg.xml
============================================================

<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "https://hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>

    <session-factory>

        <property name="hibernate.connection.driver_class">
            org.postgresql.Driver
        </property>

        <property name="hibernate.connection.url">
            jdbc:postgresql://localhost:5432/inventorydb
        </property>

        <property name="hibernate.connection.username">
            postgres
        </property>

        <!-- CHANGE THIS TO YOUR POSTGRESQL PASSWORD -->
        <property name="hibernate.connection.password">
            YOUR_PASSWORD
        </property>

        <property name="hibernate.show_sql">
            true
        </property>

        <property name="hibernate.format_sql">
            true
        </property>

        <property name="hibernate.hbm2ddl.auto">
            update
        </property>

        <mapping class="example.Product"/>

    </session-factory>

</hibernate-configuration>


============================================================
FILE 3: src/main/java/example/Product.java
============================================================

package example;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String category;
    private double price;
    private int quantity;

    public Product() {
    }

    public Product(String name, String category, double price, int quantity) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product ID: " + id
                + " | Name: " + name
                + " | Category: " + category
                + " | Price: " + price
                + " | Quantity: " + quantity;
    }
}


============================================================
FILE 4: src/main/java/example/HibernateUtil.java
============================================================

package example;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static {
        try {

            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();

            System.out.println("Hibernate connected successfully!");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public static SessionFactory getSessionFactory() {

        return sessionFactory;

    }
}


============================================================
FILE 5: src/main/java/example/ProductDAO.java
============================================================

package example;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class ProductDAO {


    // ============================
    // ADD PRODUCT
    // ============================

    public void addProduct(Product product) {

        Transaction transaction = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.persist(product);

            transaction.commit();

            System.out.println("Product added successfully!");

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }


    // ============================
    // VIEW PRODUCTS
    // ============================

    public void viewProducts() {

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            List<Product> products =
                    session.createQuery(
                            "from Product",
                            Product.class
                    ).list();

            System.out.println();
            System.out.println("========== PRODUCT INVENTORY ==========");

            if (products.isEmpty()) {

                System.out.println("No products found.");

            } else {

                for (Product product : products) {

                    System.out.println(product);

                }
            }

            System.out.println("=======================================");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }


    // ============================
    // UPDATE PRODUCT
    // ============================

    public void updateProduct(
            int id,
            String name,
            String category,
            double price,
            int quantity) {

        Transaction transaction = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Product product =
                    session.get(Product.class, id);

            if (product != null) {

                product.setName(name);
                product.setCategory(category);
                product.setPrice(price);
                product.setQuantity(quantity);

                session.merge(product);

                transaction.commit();

                System.out.println(
                        "Product updated successfully!"
                );

            } else {

                System.out.println("Product not found!");

                transaction.rollback();
            }

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }


    // ============================
    // DELETE PRODUCT
    // ============================

    public void deleteProduct(int id) {

        Transaction transaction = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Product product =
                    session.get(Product.class, id);

            if (product != null) {

                session.remove(product);

                transaction.commit();

                System.out.println(
                        "Product deleted successfully!"
                );

            } else {

                System.out.println("Product not found!");

                transaction.rollback();
            }

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }
}


============================================================
FILE 6: src/main/java/example/MainProgram.java
============================================================

package example;

import java.util.Scanner;

public class MainProgram {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ProductDAO dao = new ProductDAO();

        int choice;

        do {

            System.out.println();
            System.out.println(
                    "========== PRODUCT INVENTORY SYSTEM =========="
            );

            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Exit");

            System.out.println(
                    "=============================================="
            );

            System.out.print("Enter your choice: ");

            choice = sc.nextInt();


            // ============================
            // ADD
            // ============================

            switch (choice) {

                case 1:

                    sc.nextLine();

                    System.out.print(
                            "Enter Product Name: "
                    );

                    String name = sc.nextLine();

                    System.out.print(
                            "Enter Category: "
                    );

                    String category = sc.nextLine();

                    System.out.print(
                            "Enter Price: "
                    );

                    double price = sc.nextDouble();

                    System.out.print(
                            "Enter Quantity: "
                    );

                    int quantity = sc.nextInt();

                    Product product =
                            new Product(
                                    name,
                                    category,
                                    price,
                                    quantity
                            );

                    dao.addProduct(product);

                    break;


                // ============================
                // VIEW
                // ============================

                case 2:

                    dao.viewProducts();

                    break;


                // ============================
                // UPDATE
                // ============================

                case 3:

                    System.out.print(
                            "Enter Product ID to update: "
                    );

                    int updateId = sc.nextInt();

                    sc.nextLine();

                    System.out.print(
                            "Enter New Product Name: "
                    );

                    String newName = sc.nextLine();

                    System.out.print(
                            "Enter New Category: "
                    );

                    String newCategory = sc.nextLine();

                    System.out.print(
                            "Enter New Price: "
                    );

                    double newPrice = sc.nextDouble();

                    System.out.print(
                            "Enter New Quantity: "
                    );

                    int newQuantity = sc.nextInt();

                    dao.updateProduct(
                            updateId,
                            newName,
                            newCategory,
                            newPrice,
                            newQuantity
                    );

                    break;


                // ============================
                // DELETE
                // ============================

                case 4:

                    System.out.print(
                            "Enter Product ID to delete: "
                    );

                    int deleteId = sc.nextInt();

                    dao.deleteProduct(deleteId);

                    break;


                // ============================
                // EXIT
                // ============================

                case 5:

                    System.out.println(
                            "Exiting Product Inventory System..."
                    );

                    break;


                default:

                    System.out.println(
                            "Invalid choice! Please try again."
                    );
            }

        } while (choice != 5);

        sc.close();
    }
}


============================================================
POSTGRESQL SQL
============================================================

CREATE DATABASE inventorydb;


After creating inventorydb, open Query Tool for inventorydb
and execute:

CREATE TABLE IF NOT EXISTS product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(100),
    price DOUBLE PRECISION,
    quantity INTEGER
);

