package com.esmt.sn.schoolmanagement.front.admin.roles;

import com.esmt.sn.schoolmanagement.SchoolManagementApplication;
import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;
import com.esmt.sn.schoolmanagement.back.services.AdminService;
import com.esmt.sn.schoolmanagement.back.services.AdminServiceImpl;
import com.esmt.sn.schoolmanagement.front.admin.sections.SectionItemController;
import com.esmt.sn.schoolmanagement.front.listeners.Loadable;
import com.esmt.sn.schoolmanagement.models.Classe;
import com.esmt.sn.schoolmanagement.models.Role;
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

public class RoleManagerController implements Initializable , Loadable {
    public ProgressIndicator loaderProgress;
    public GridPane gridPane;
    private AdminService adminService;
    private List<Role> roles = new ArrayList<>();

    private int lastColumn ;
    private int lastRow ;

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
                this.roles = this.adminService.getRoles();
                this.roles.forEach(role -> Platform.runLater(() -> this.addItem(role)));
            } catch (DAOException e) {
                e.printStackTrace();
            } finally {
                loaderProgress.setVisible(false);
            }
        }).start();
    }

    private void addItem(Role role) {
        try {
            FXMLLoader loader = new FXMLLoader(SchoolManagementApplication.class.getResource("admin/role-manager/role-item.fxml"));
            Parent spentNode = loader.load();
            RoleItemController itemController = loader.getController();
            itemController.setRole(role);
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

    public void onAddRole(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SchoolManagementApplication.class.getResource("admin/role-manager/edit-role.fxml"));
            Parent parent = fxmlLoader.load();
            Role role = new Role();
            EditRoleController dialogController = fxmlLoader.getController();
            dialogController.setRole(role);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajouter un role");
            stage.getIcons().add(new Image("/asserts/logo.jpg"));
            stage.setResizable(false);
            stage.setScene(scene);
            dialogController.setStage(stage);
            stage.showAndWait();
            if(dialogController.getRole()!=null){
                loadData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
