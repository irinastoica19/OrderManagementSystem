package org.example.presentation;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.bll.ProductBLL;
import org.example.model.Product;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * This class is the Controller for the product view. It contains the functions which are called
 * when clicking the buttons of the interface.
 *
 * @author Stoica Irina
 * @since Apr 12, 2022
 *
 */

public class ProductController {
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private  TextField addNameTextField;
    @FXML
    private TextField addPriceTextField;
    @FXML
    private TextField addQuantityTextField;
    @FXML
    private TextField deleteIdTextField;
    @FXML
    private TextField updateIdTextField;
    @FXML
    private TextField updateNameTextField;
    @FXML
    private TextField updatePriceTextField;
    @FXML
    private TextField updateQuantityTextField;

    private final ProductBLL productBLL = new ProductBLL();
    private final TableGenerator<Product> productTableGenerator = new TableGenerator<>();

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
     * Called automatically when the application starts and loads the products from the database to
     * the TableView.
     */
    @FXML
    private void initialize(){
        try {
            List<Product> list = productBLL.findAll();
            productTableGenerator.generateTableHeader(list, productTableView);
        } catch(NullPointerException e){
            PopUpWindow.displayError(e.getMessage());
        }
    }

    /**
     * Tries to insert the given product fields in the database and shows a confirmation message.
     * If the insertion fails, a pop up window displays the error message.
     */
    @FXML
    private void addProduct(){
        try {
            productBLL.insertProduct(addNameTextField.getText(), addPriceTextField.getText(), addQuantityTextField.getText());
            refreshTable();
            PopUpWindow.displayError("Product successfully added.");
        } catch(IllegalArgumentException e){
            PopUpWindow.displayError(e.getMessage());
        }

    }

    /**
     * Tries to delete the product with the given id from the database and shows a confirmation
     * message. If this fails, a pop up window displays the error message.
     */
    @FXML
    private void deleteProduct(){
        try {
            Product product = productBLL.findProductById(deleteIdTextField.getText());
            productBLL.deleteProduct(product.getId());
            refreshTable();
            PopUpWindow.displayError("Product successfully deleted.");
        } catch(NumberFormatException | NoSuchElementException e){
            PopUpWindow.displayError(e.getMessage());
        }
    }

    /**
     * Tries to update the product with the given fields from the database and shows a confirmation
     * message. If this fails, a pop up window displays the error message.
     */
    @FXML
    private void updateProduct(){
        try{
            Product product = productBLL.findProductById(updateIdTextField.getText());
            productBLL.updateProduct(updateIdTextField.getText(), updateNameTextField.getText(), updatePriceTextField.getText(), updateQuantityTextField.getText());
            refreshTable();
            PopUpWindow.displayError("Product successfully updated.");
        } catch(NumberFormatException | NoSuchElementException e ){
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
            productTableGenerator.reloadTableValues(productList, productTableView);
        } catch(NullPointerException e){
            PopUpWindow.displayError(e.getMessage());
        }
    }

}
