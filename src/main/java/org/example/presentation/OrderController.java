package org.example.presentation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.bll.ClientBLL;
import org.example.bll.OrderBLL;
import org.example.bll.ProductBLL;
import org.example.model.Client;
import org.example.model.Product;

import java.io.IOException;
import java.util.List;

/**
 *
 * This class is the Controller for the order view. It contains the functions which are called
 * when clicking the buttons of the interface.
 *
 * @author Stoica Irina
 * @since Apr 12, 2022
 *
 */

public class OrderController {

    @FXML
    private TableView<Client> clientTableView;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private Label clientLabel;
    @FXML
    private Label productLabel;
    @FXML
    private TextField quantityTextField;

    private final ProductBLL productBLL = new ProductBLL();
    private final ClientBLL clientBLL = new ClientBLL();
    private final OrderBLL orderBLL = new OrderBLL();
    private Product product = null;
    private Client client = null;
    private final TableGenerator<Product> productTableGenerator = new TableGenerator<>();
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
     * Called automatically when the application starts and loads the clients and the products
     * from the database to the TableView.
     */
    @FXML
    private void initialize(){
        try {
            List<Product> productList = productBLL.findAll();
            List<Client> clientList = clientBLL.findAll();
            productTableGenerator.generateTableHeader(productList, productTableView);
            clientTableGenerator.generateTableHeader(clientList, clientTableView);
        } catch(NullPointerException e){
            PopUpWindow.displayError(e.getMessage());
        }
    }

    /**
     * Gets the client selected in the TableView and displays it on the label
     */
    @FXML
    private void showSelectedClient(){
        try {
            client = clientTableGenerator.showSelectedRow(clientTableView);
            clientLabel.setText(client.toString());
        } catch(NullPointerException e){
            PopUpWindow.displayError(e.getMessage() + "client");
        }
    }

    /**
     * Gets the product selected in the TableView and displays it on the label
     */
    @FXML
    private void showSelectedProduct(){
        try {
            product = productTableGenerator.showSelectedRow(productTableView);
            productLabel.setText(product.toString());
        } catch(NullPointerException e){
            PopUpWindow.displayError(e.getMessage() + "product");
        }
    }

    /**
     * Tries to add the order in the database through the orderBLL and reloads the values of the
     * TableView when done. If this fails, a pop up window displays the error message.
     */
    @FXML
    private void makeOrder(){
        try{
            orderBLL.addOrder(client, product, quantityTextField.getText());
            refreshTable();
            PopUpWindow.displayError("Order successfully generated.");
        } catch(IllegalArgumentException | NullPointerException e){
            PopUpWindow.displayError(e.getMessage());
        }
    }

    /**
     * Reloads the content of the Table from the database and displays an error message if the
     * table is empty.
     */
    private void refreshTable(){
        try {
            List<Product> productList = productBLL.findAll();
            List<Client> clientList = clientBLL.findAll();
            productTableGenerator.reloadTableValues(productList, productTableView);
            clientTableGenerator.reloadTableValues(clientList, clientTableView);
        } catch(NullPointerException e){
            PopUpWindow.displayError(e.getMessage());
        }
    }

}
