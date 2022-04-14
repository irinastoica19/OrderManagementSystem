package org.example.presentation;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * This class is the controller of the main window from which we can enter all the other windows
 * and come back to
 *
 * @author Stoica Irina
 * @since Apr 12, 2022
 *
 */

public class ViewController {
    @FXML
    private Button closeButton;

    /**
     * Changes to the client window when called
     *
     * @throws IOException Thrown if the name fxml file to be loaded is not found
     */
    @FXML
    private void switchToClientView() throws IOException {
        App.setRoot("clientview");
    }

    /**
     * Changes to the product window when called
     *
     * @throws IOException Thrown if the name fxml file to be loaded is not found
     */
    @FXML
    private void switchToProductView() throws IOException {
        App.setRoot("productview");
    }

    /**
     * Changes to the order window when called
     *
     * @throws IOException Thrown if the name fxml file to be loaded is not found
     */
    @FXML
    private void switchToOrderView() throws IOException {
        App.setRoot("orderview");
    }

    /**
     * Closes the window and terminates the application
     */
    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
