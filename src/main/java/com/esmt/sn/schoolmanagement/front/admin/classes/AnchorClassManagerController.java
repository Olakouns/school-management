package com.esmt.sn.schoolmanagement.front.admin.classes;

import com.esmt.sn.schoolmanagement.SchoolManagementApplication;
import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;
import com.esmt.sn.schoolmanagement.back.services.AdminService;
import com.esmt.sn.schoolmanagement.back.services.AdminServiceImpl;
import com.esmt.sn.schoolmanagement.front.admin.users.UserItemController;
import com.esmt.sn.schoolmanagement.front.listeners.Loadable;
import com.esmt.sn.schoolmanagement.models.Classe;
import com.esmt.sn.schoolmanagement.models.Section;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AnchorClassManagerController implements Initializable, Loadable {

    public Button btnAddClass;
    public GridPane gridPane;
    public ProgressIndicator loaderProgress;
    public VBox vBox;
    private Section section;

    private AdminService adminService;
    private List<Classe> classes = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.adminService = AdminServiceImpl.getInstance();
    }

    @Override
    public void loadData() {
        loaderProgress.setVisible(true);
        vBox.getChildren().clear();
        new Thread(() -> {
            try {
                this.classes = this.adminService.getClassList(this.section.getNom());
                this.classes.forEach(classe -> Platform.runLater(() -> this.addItem(classe)));
            } catch (DAOException e) {
                e.printStackTrace();
            } finally {
                loaderProgress.setVisible(false);
            }
        }).start();
    }

    private void addItem(Classe classe) {
        try {
            FXMLLoader loader = new FXMLLoader(SchoolManagementApplication.class.getResource("admin/classes/classe-item.fxml"));
            Parent userNode = loader.load();
            ClassItemController itemController = loader.getController();
            itemController.setClasse(classe);
            vBox.getChildren().add(userNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSection(Section section) {
        this.section = section;
        this.btnAddClass.setText("Ajouter une classe " + this.section.getNom());
    }


    public void onAddNewClass(ActionEvent actionEvent) {

    }
}
