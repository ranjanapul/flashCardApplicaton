package com.example.oop;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Alerts {
    public static void display(String title, String message){
        Stage window = new Stage();//Since we are making a new window for alert box, we make a new stage
        window.initModality(Modality.APPLICATION_MODAL);//Block user interaction with other windows till this alert is taken care of
        //eg- we are unable to proceed until we close alerts in apps
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close the window");
        closeButton.setOnAction(e -> window.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);//to center align
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();//Displays this window and to return, it must be closed and since user
        //interaction is blocked, we must close the alert box before doing anything else



    }
}
