package com.esmt.sn.schoolmanagement.front.admin.classes;

import com.esmt.sn.schoolmanagement.SchoolManagementApplication;
import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;
import com.esmt.sn.schoolmanagement.back.services.AdminService;
import com.esmt.sn.schoolmanagement.back.services.AdminServiceImpl;
import com.esmt.sn.schoolmanagement.front.admin.sections.EditSectionController;
import com.esmt.sn.schoolmanagement.front.admin.sections.SectionItemController;
import com.esmt.sn.schoolmanagement.front.listeners.Loadable;
import com.esmt.sn.schoolmanagement.models.Section;
import com.esmt.sn.schoolmanagement.utils.ConfigUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClassManagerController implements Initializable, Loadable {

    public TabPane tabPane;
    public AnchorPane contentFirst;
    public ProgressIndicator loaderProgress;

    private AdminService adminService;
    private List<Section> sections = new ArrayList<>();

    private final FileChooser fileChooser = new FileChooser();
    private File file;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.adminService = AdminServiceImpl.getInstance();
    }

    @Override
    public void loadData() {
        tabPane.getTabs().clear();
        loaderProgress.setVisible(true);
        new Thread(() -> {
            try {
                this.sections = this.adminService.getSections();
                this.sections.forEach(section -> Platform.runLater(() -> this.addItemSection(section, new Tab(section.getNom()))));
            } catch (DAOException e) {
                e.printStackTrace();
            } finally {
                loaderProgress.setVisible(false);
            }
        }).start();
    }


    public void onLoadFile(ActionEvent actionEvent) {
        ConfigUtils.loadExcelFile(fileChooser);
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            this.openLoaderClass();
        }
    }

    private void openLoaderClass() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SchoolManagementApplication.class.getResource("admin/classes/load-class.fxml"));
            Parent parent = fxmlLoader.load();
            LoadClassController dialogController = fxmlLoader.getController();
            dialogController.setSectionList(this.sections);
            dialogController.setFile(this.file);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Chargement des donnees");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("/asserts/logo.jpg"));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.showAndWait();
            if(dialogController.getResult()){
                loadData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addItemSection(Section section, Tab tab) {
        try {
            FXMLLoader loader = new FXMLLoader(SchoolManagementApplication.class.getResource("admin/classes/anchor-class-manager.fxml"));
            Parent anchorNode = loader.load();
            AnchorClassManagerController itemController = loader.getController();
            itemController.setSection(section);
            itemController.loadData();
            tab.setContent(anchorNode);
            tabPane.getTabs().add(tab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
