package org.example.bll;

import org.example.bll.validators.NumberValidator;
import org.example.bll.validators.Validator;
import org.example.dao.ProductDAO;
import org.example.model.Product;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Contains the business logic part of the application that has to do with the product.
 *
 * @author Stoica Irina
 * @since Apr 12, 2022
 */

public class ProductBLL {
    private final Validator<Product> validator;
    private final ProductDAO productDAO;

    public ProductBLL(){
        validator = new NumberValidator();
        productDAO = new ProductDAO();
    }

    /**
     * This function creates a list of products and calls the ProductDAO class function which
     * gets all entries of the product table.
     *
     * @return A list containing all products in the table
     */
    public List<Product> findAll() {
        List<Product> list = productDAO.findAll();
        if(list == null){
            throw new NullPointerException("The database table is empty");
        }
        return list;
    }

    /**
     * This function receives the fields entered by the user and tries to create a Product and call
     * the database insert function. If this fails, it throws an exception
     *
     * @param name Name of product
     * @param priceString Product price
     * @param quantityString Product quantity
     * @throws IllegalArgumentException Exception thrown if any of the fields are empty
     */
    public void insertProduct(String name, String priceString, String quantityString) throws IllegalArgumentException {
        if(name.equals("") || priceString.equals("") || quantityString.equals("")){
            throw new IllegalArgumentException("Cannot add product.\nPlease fill in all the fields");
        }
        int price = Integer.parseInt(priceString);
        int quantity = Integer.parseInt(quantityString);
        Product product = new Product(name, price, quantity);
        validator.validate(product);
        productDAO.insert(product);
    }

    /**
     * This function receives the value of the field of the id. If the field is empty, it throws
     * an IllegalArgumentException. It searches in the database for the entry with the id and
     * if no entry with that id is found, it throws an NoSuchElementException
     *
     * @param idString The id to be searched by, in string form
     * @return The object of Type product with integer form of the id entered
     * @throws NoSuchElementException Thrown when the query fails to find a matching entry
     * @throws IllegalArgumentException Thrown when the idString is empty
     */
    public Product findProductById(String idString) throws NoSuchElementException, NumberFormatException {
        if(idString.equals("")){
            throw new NumberFormatException("Please fill in the id field.");
        }
        try {
            int id = Integer.parseInt(idString);
            Product product = productDAO.findById(id);
            if (product == null) {
                throw new NoSuchElementException("The product with id = " + id + " was not found!");
            }
            return product;
        } catch(NumberFormatException e){
            throw new NumberFormatException("Please enter a valid id.");
        }
    }

    /**
     * Calls the database layer method for deleting a table entry
     *
     * @param id Id in integer form
     */
    public void deleteProduct(Integer id){
        productDAO.delete(id);
    }

    /**
     * Receives the id of the product to be updated and its new name, price and quantity in
     * String form. It then tries to create an object with these values and to modify it in
     * the database.
     *
     * @param idString The id for the product to be identified by
     * @param name New product name
     * @param priceString New product price
     * @param quantityString New product quantity
     * @throws IllegalArgumentException thrown when the the fields are empty or when price or
     * quantity cannot be converted to strings
     */
    public void updateProduct(String idString, String name, String priceString, String quantityString) throws IllegalArgumentException {
        if(name.equals("") || priceString.equals("") || quantityString.equals("")){
            throw new IllegalArgumentException("Cannot update client.\nPlease fill in all the fields");
        }
        try {
            int id = Integer.parseInt(idString);
            int price = Integer.parseInt(priceString);
            int quantity = Integer.parseInt(quantityString);
            Product product = new Product(id, name, price, quantity);
            validator.validate(product);
            productDAO.update(product);
        } catch(NumberFormatException e){
            throw new IllegalArgumentException("Invalid data fields");
        }
    }

}
