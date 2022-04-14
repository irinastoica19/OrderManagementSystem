package org.example.bll.validators;

import org.example.model.Product;

/**
 * The class implements the Validator interface with Object Product as the parameter. It
 * implements the validate function.
 * @author Stoica Irina
 * @since Apr 12, 2022
 */
public class NumberValidator implements Validator<Product>{

    /**
     * If the price or quantity fields are negative, it will throw an IllegalArgumentException
     *
     * @param p Product to validate
     */
    @Override
    public void validate(Product p){
        if(p.getPrice() < 0){
            throw new IllegalArgumentException("Price cannot have a negative value!");
        }
        if(p.getQuantity() < 0){
            throw new IllegalArgumentException("Quantity cannot have a negative value!");
        }
    }

}
