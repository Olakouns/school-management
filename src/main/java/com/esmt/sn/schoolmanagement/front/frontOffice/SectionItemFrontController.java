package com.esmt.sn.schoolmanagement.front.frontOffice;

import com.esmt.sn.schoolmanagement.SchoolManagementApplication;
import com.esmt.sn.schoolmanagement.models.Section;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SectionItemFrontController {

    public Button btnLabel;

    private Section section;

    public void setSection(Section section) {
        this.section = section;
        this.btnLabel.setText(this.section.getNom());
    }


    public void onGoGlobalHome(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SchoolManagementApplication.class.getResource("front/global-front.fxml"));
            Parent parent = fxmlLoader.load();
            GlobalFrontController dialogController = fxmlLoader.getController();
            dialogController.setSection(section);
            Stage stage = new Stage();
            Scene scene = new Scene(parent, 1500, 900);
            stage.setTitle("Dashbord");
            stage.getIcons().add(new Image("/asserts/logo.jpg"));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
