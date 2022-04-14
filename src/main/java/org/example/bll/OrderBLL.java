package org.example.bll;

import org.example.bll.validators.QuantityValidator;
import org.example.bll.validators.Validator;
import org.example.dao.OrderDAO;
import org.example.dao.ProductDAO;
import org.example.model.Client;
import org.example.model.Orders;
import org.example.model.Product;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Contains the business logic part of the application that has to do with the orders.
 *
 * @author Stoica Irina
 * @since Apr 12, 2022
 */

public class OrderBLL {

    private final Validator<Orders> validator;
    private final OrderDAO orderDAO;
    private final ProductDAO productDAO;

    public OrderBLL(){
        validator = new QuantityValidator();
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();
    }

    /**
     * Tries to create an order with the given client, product and quantity. If the client or product
     * are null, and IllegalArgumentException is thrown. Otherwise it checks for the quantity given
     * to be smaller than the quantity of the product in the database and throws again an exception
     * if this condition is not met. If the order is generated successfully, the product quantity in
     * the database is updated with the new value
     *
     * @param client Client of the order
     * @param product Product of the order
     * @param quantityString Quantity of Product for the order
     * @throws IllegalArgumentException Thrown when the client or product do not exist.
     */
    public void addOrder(Client client, Product product, String quantityString) throws IllegalArgumentException, NullPointerException {
        if(client == null){
            throw new IllegalArgumentException("Please select a client");
        }
        if(product == null){
            throw new IllegalArgumentException("Please select a product");
        }
        try {
            Integer quantity = Integer.parseInt(quantityString);
            Orders order = new Orders(client.getId(), product.getId(), quantity);
            validator.validate(order);
            validateStock(order, product);
            orderDAO.insert(order);
            Integer updatedQuantity = product.getQuantity() - quantity;
            Product updatedProduct = new Product(product.getId(), product.getName(), product.getPrice(), updatedQuantity);
            productDAO.update(updatedProduct);
            generateBill(client, product, quantity);
        } catch(IllegalArgumentException e){
            throw new NullPointerException("Please enter quantity");
        }
    }

    /**
     * Helper function for checking the order quantity to be smaller than the quantity of the
     * product in the database
     *
     * @param orders Order to be checked
     * @param product Available product in the database
     * @throws IllegalArgumentException Thrown when available quantity of product is not enough
     */
    private void validateStock(Orders orders, Product product) throws IllegalArgumentException{
        if(orders.getQuantity() > product.getQuantity()){
            throw new IllegalArgumentException("Under-stocked item!\nOnly " + product.getQuantity() + " left.");
        }
    }

    /**
     * Receives the client and product of the order and creates the bill in form of a text file
     * with the important information
     *
     * @param client Client that made the order
     * @param product Product ordered by the client
     * @param quantity Quantity desired by the client
     */
    private void generateBill(Client client, Product product, Integer quantity){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm");
        LocalDateTime now = LocalDateTime.now();
        String fileName = dtf.format(now) + "_" + client.getName() + ".txt";
        File file = new File(fileName);
        try {
            FileWriter writer = new FileWriter(file.getAbsolutePath(), true);
            writer.write("Client name: " + client.getName() + "\n");
            writer.write("Address: " + client.getAddress() + "\n");
            writer.write("Email: " + client.getEmail() + "\n\n");
            writer.write("Product: " + product.getName() + "\n");
            writer.write("Quantity: " + quantity + "\n");
            writer.write("Price per unit: " + product.getPrice() + "\n\n");
            writer.write("Total cost: " + (quantity * product.getPrice()) + "\n");
            writer.close();
        } catch( IOException e ){
            e.printStackTrace();
        }
    }

}
