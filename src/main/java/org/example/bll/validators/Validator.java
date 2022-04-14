package org.example.bll.validators;

/**
 * @author  Technical University of Cluj-Napoca, Romania Distributed Systems
 *          Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @since  Apr 03, 2017
 */
public interface Validator<T> {

	void validate(T t);

}
