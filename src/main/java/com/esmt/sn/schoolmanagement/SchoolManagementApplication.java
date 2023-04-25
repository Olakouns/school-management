package com.esmt.sn.schoolmanagement;

import com.esmt.sn.schoolmanagement.back.config.DataLoad;
import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class SchoolManagementApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, DAOException {
        DataLoad.loadAllData();
        FXMLLoader fxmlLoader = new FXMLLoader(SchoolManagementApplication.class.getResource("auth/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        stage.getIcons().add(new Image("/asserts/logo.jpg"));
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}