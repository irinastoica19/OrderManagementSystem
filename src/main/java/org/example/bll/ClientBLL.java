package org.example.bll;

import org.example.bll.validators.EmailValidator;
import org.example.bll.validators.Validator;
import org.example.dao.ClientDAO;
import org.example.model.Client;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Contains the business logic part of the application that has to do with the client.
 *
 * @author Stoica Irina
 * @since Apr 12, 2022
 */
public class ClientBLL {
    private final Validator<Client> validator;
    private final ClientDAO clientDAO;

    public ClientBLL(){
        validator = new EmailValidator();
        clientDAO = new ClientDAO();
    }

    /**
     * This function creates a list of clients and calls the ClientDAO class function which
     * gets all entries of the client table.
     *
     * @return A list containing all clients in the table
     */
    public List<Client> findAll() {
         List<Client> list = clientDAO.findAll();
         if(list == null){
             throw new NullPointerException("The database table is empty");
         }
         return list;
    }

    /**
     * This function receives the fields entered by the user and tries to create a Client and call
     * the database insert function. If this fails, it throws an exception
     *
     * @param name Name of client
     * @param address Client address
     * @param email Client email
     * @throws IllegalArgumentException Exception thrown if any of the fields are empty
     */
    public void insertClient(String name, String address, String email) throws IllegalArgumentException {
        if(name.equals("") || address.equals("") || email.equals("")){
            throw new IllegalArgumentException("Cannot add client.\nPlease fill in all the fields");
        }
        Client client = new Client(name, address, email);
        validator.validate(client);
        clientDAO.insert(client);
    }

    /**
     * This function receives the value of the field of the id. If the field is empty, it throws
     * an NumberFormatException. It searches in the database for the entry with the id and
     * if no entry with that id is found, it throws an NoSuchElementException
     *
     * @param idString The id to be searched by, in string form
     * @return The object of Type client with integer form of the id entered
     * @throws NoSuchElementException Thrown when the query fails to find a matching entry
     * @throws NumberFormatException Thrown when the idString is empty
     */
    public Client findClientById(String idString) throws NoSuchElementException, NumberFormatException {
        if(idString.equals("")){
            throw new NumberFormatException("Please fill in the id field.");
        }
        try {
            int id = Integer.parseInt(idString);
            Client client = clientDAO.findById(id);
            if (client == null) {
                throw new NoSuchElementException("The client with id = " + id + " was not found!");
            }
            return client;
        } catch(NumberFormatException e){
            throw new NumberFormatException("Please enter a valid id.");
        }
    }

    /**
     * Calls the database layer method for deleting a table entry
     *
     * @param id Id in integer form
     */
    public void deleteClient(Integer id){
        clientDAO.delete(id);
    }

    /**
     * Receives the id of the client to be updated and its new name, address and email in
     * String form. It then tries to create an object with these values and to modify it in
     * the database.
     *
     * @param idString The id for the client to be identified by
     * @param name New client name
     * @param address New client address
     * @param email New client email
     * @throws IllegalArgumentException thrown when the the fields are empty
     */
    public void updateClient(String idString, String name, String address, String email) throws IllegalArgumentException {
        if(name.equals("") || address.equals("") || email.equals("")){
            throw new IllegalArgumentException("Cannot update client.\nPlease fill in all the fields");
        }
        int id = Integer.parseInt(idString);
        Client client = new Client(id, name, address, email);
        validator.validate(client);
        clientDAO.update(client);
    }

}
