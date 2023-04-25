package com.esmt.sn.schoolmanagement.front.admin.sections;

import com.esmt.sn.schoolmanagement.SchoolManagementApplication;
import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;
import com.esmt.sn.schoolmanagement.back.services.AdminService;
import com.esmt.sn.schoolmanagement.back.services.AdminServiceImpl;
import com.esmt.sn.schoolmanagement.front.listeners.Loadable;
import com.esmt.sn.schoolmanagement.models.Section;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SectionManagerController implements Initializable, Loadable {
    public GridPane gridPane;
    public ProgressIndicator loaderProgress;

    private int lastColumn ;
    private int lastRow ;

    private AdminService adminService;
    private List<Section> sections = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.adminService = AdminServiceImpl.getInstance();
    }

    public void loadData() {
        loaderProgress.setVisible(true);
        lastColumn = 0;
        lastRow = 0;
        new Thread(() -> {
            try {
                this.sections = this.adminService.getSections();
                this.sections.forEach(section -> Platform.runLater(() -> this.addItem(section)));
            } catch (DAOException e) {
                e.printStackTrace();
            } finally {
                loaderProgress.setVisible(false);
            }
        }).start();
    }

    public void onAddSection(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SchoolManagementApplication.class.getResource("admin/sections/edit-section.fxml"));
            Parent parent = fxmlLoader.load();
            Section section = new Section();
            EditSectionController dialogController = fxmlLoader.getController();
            dialogController.setSection(section);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Ajout de section");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("/asserts/logo.jpg"));
            stage.setResizable(false);
            stage.setScene(scene);
            dialogController.setStage(stage);
            stage.showAndWait();
            if(dialogController.getSection()!=null){
                loadData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addItem(Section section) {
        try {
            FXMLLoader loader = new FXMLLoader(SchoolManagementApplication.class.getResource("admin/sections/section-item.fxml"));
            Parent spentNode = loader.load();
            SectionItemController itemController = loader.getController();
            itemController.setSection(section);
            gridPane.add(spentNode,lastColumn,lastRow);
            if(lastColumn<2){
                lastColumn++;
            }
            else{
                lastColumn = 0;
                lastRow+=1;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
