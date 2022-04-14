package org.example.model;

/**
 * This class contains the fields related to the product and getters and setters for them. These
 * are necessary for the database operations. This class name and field names correspond to the
 * table and table columns present in the database, but also to the TableView from the interface
 * and its columns.
 *
 * @author Stoica Irina
 * @since Apr 12, 2022
 */

public class Product {

    private Integer id;
    private String name;
    private Integer price;
    private Integer quantity;

    public Product() { }

    public Product(String name, Integer price, Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(Integer id, String name, Integer price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity + "]";
    }
}
