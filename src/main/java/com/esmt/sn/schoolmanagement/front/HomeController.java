package com.esmt.sn.schoolmanagement.front;

import com.esmt.sn.schoolmanagement.SchoolManagementApplication;
import com.esmt.sn.schoolmanagement.utils.Basics;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public Button btnHome;
    public Button btnOther;
    public Button btnConfig;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onGoHome(ActionEvent actionEvent) {
    }

    public void onGoOtherScreen(ActionEvent actionEvent) {
    }

    public void onLoadConfiguration(ActionEvent actionEvent) {
        try {
            Basics.closeStage(actionEvent);
            FXMLLoader fxmlLoader = new FXMLLoader(SchoolManagementApplication.class.getResource("global.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1500, 900);
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/asserts/logo.jpg"));
            stage.setTitle("Administrateur-Configuration");
//            stage.setMaximized(true);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
