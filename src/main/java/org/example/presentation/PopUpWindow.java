package org.example.presentation;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.*;

/**
 *
 * This class generates a pop up window which contains an important message for the user and
 * has to be closed in order to continue the flow of the application.
 *
 * @author  Stoica Irina
 * @since Apr 12, 2022
 *
 */

public class PopUpWindow {

    /**
     * Creates a new stage with a label which contains the message to be shared with the user and
     * a button for closing this window. When shown, the window cannot be ignored. The user must
     * close it before doing anything else.
     *
     * @param error The message to be displayed on the window
     */
    public static void displayError(String error){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinWidth(250);
        stage.setMinHeight(250);

        Label label = new Label();
        label.setText(error);
        label.setFont(new Font("Arial", 16));
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);

        Button close = new Button("Close");
        close.setOnAction( e -> stage.close());

        VBox vBox = new VBox();
        vBox.getChildren().addAll(label, close);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }

}
