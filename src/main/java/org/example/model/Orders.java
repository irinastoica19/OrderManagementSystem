package org.example.model;

/**
 * This class contains the fields related to the orders and getters and setters for them. These
 * are necessary for the database operations. This class name and field names correspond to the
 * table and table columns present in the database.
 *
 * @author Stoica Irina
 * @since Apr 12, 2022
 */

public class Orders {
    private Integer id;
    private Integer clientId;
    private Integer productId;
    private Integer quantity;

    public Orders() { }

    public Orders(Integer clientId, Integer productId, Integer quantity) {
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Orders(Integer id, Integer clientId, Integer productId, Integer quantity) {
        this.id = id;
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
