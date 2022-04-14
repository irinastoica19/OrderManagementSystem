package org.example.presentation;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.bll.ClientBLL;
import org.example.model.Client;

/**
 * This class is the Controller for the client view. It contains the functions which are called
 * when clicking the buttons of the interface.
 *
 * @author Stoica Irina
 * @since Apr 12, 2022
 *
 */
public class ClientController {
    @FXML
    private TableView<Client> clientTableView;
    @FXML
    private TextField addNameTextField;
    @FXML
    private TextField addAddressTextField;
    @FXML
    private TextField addEmailTextField;
    @FXML
    private TextField deleteIdTextField;
    @FXML
    private TextField updateIdTextField;
    @FXML
    private TextField updateNameTextField;
    @FXML
    private TextField updateAddressTextField;
    @FXML
    private TextField updateEmailTextField;

    private final ClientBLL clientBLL = new ClientBLL();
    private final TableGenerator<Client> clientTableGenerator = new TableGenerator<>();

    /**
     * Changes to the starting window when called
     *
     * @throws IOException Thrown if the name fxml file to be loaded is not found
     */
    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("view");
    }

    /**
     * Called automatically when the application starts and loads the clients from the database to
     * the TableView.
     */
    @FXML
    private void initialize(){
        try {
            List<Client> list = clientBLL.findAll();
            clientTableGenerator.generateTableHeader(list, clientTableView);
        } catch(NullPointerException e){
            PopUpWindow.displayError(e.getMessage());
        }
    }

    /**
     * Tries to insert the given client fields in the database and shows a confirmation message.
     * If the insertion fails, a pop up window displays the error message.
     */
    @FXML
    private void addClient(){
        try {
            clientBLL.insertClient(addNameTextField.getText(), addAddressTextField.getText(), addEmailTextField.getText());
            refreshTable();
            PopUpWindow.displayError("Client successfully added.");
        } catch(IllegalArgumentException e){
            PopUpWindow.displayError(e.getMessage());
        }
    }

    /**
     * Tries to delete the client with the given id from the database and shows a confirmation
     * message. If this fails, a pop up window displays the error message.
     */
    @FXML
    private void deleteClient(){
        try {
            Client client = clientBLL.findClientById(deleteIdTextField.getText());
            clientBLL.deleteClient(client.getId());
            clientTableView.refresh();
            refreshTable();
            PopUpWindow.displayError("Client successfully deleted.");
        } catch(NumberFormatException | NoSuchElementException e){
            PopUpWindow.displayError(e.getMessage());
        }
    }

    /**
     * Tries to update the client with the given fields from the database and shows a confirmation
     * message. If this fails, a pop up window displays the error message.
     */
    @FXML
    private void updateClient(){
        try{
            Client client = clientBLL.findClientById(updateIdTextField.getText());
            clientBLL.updateClient(updateIdTextField.getText(), updateNameTextField.getText(), updateAddressTextField.getText(), updateEmailTextField.getText());
            clientTableView.refresh();
            refreshTable();
            PopUpWindow.displayError("Client successfully updated.");
        } catch(IllegalArgumentException | NoSuchElementException e){
            PopUpWindow.displayError(e.getMessage());
        }
    }

    /**
     * Reloads the content of the Table from the database and displays an error message if the
     * table is empty.
     */
    private void refreshTable(){
        try {
            List<Client> clientList = clientBLL.findAll();
            clientTableGenerator.reloadTableValues(clientList, clientTableView);
        } catch(NullPointerException e){
            PopUpWindow.displayError(e.getMessage());
        }
    }

}