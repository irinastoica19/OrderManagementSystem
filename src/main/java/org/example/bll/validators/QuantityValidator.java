package org.example.bll.validators;

import org.example.model.Orders;

/**
 * The class implements the Validator interface with Orders Product as the parameter. It
 * implements the validate function.
 *
 * @author Stoica Irina
 * @since Apr 12, 2022
 */
public class QuantityValidator implements Validator<Orders>{

    /**
     * If the quantity has a negative value, it throws an IllegalArgumentException
     *
     * @param orders Order to be validated
     */
    @Override
    public void validate(Orders orders) {
        if(orders.getQuantity() < 0){
            throw new IllegalArgumentException("Quantity cannot have a negative value!");
        }
    }

}
