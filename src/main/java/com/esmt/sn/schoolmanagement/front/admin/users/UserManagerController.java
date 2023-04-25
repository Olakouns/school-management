package com.esmt.sn.schoolmanagement.front.admin.users;

import com.esmt.sn.schoolmanagement.SchoolManagementApplication;
import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;
import com.esmt.sn.schoolmanagement.back.services.AdminService;
import com.esmt.sn.schoolmanagement.back.services.AdminServiceImpl;
import com.esmt.sn.schoolmanagement.front.admin.roles.RoleItemController;
import com.esmt.sn.schoolmanagement.front.admin.sections.EditSectionController;
import com.esmt.sn.schoolmanagement.front.listeners.Loadable;
import com.esmt.sn.schoolmanagement.models.Section;
import com.esmt.sn.schoolmanagement.models.Utilisateur;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserManagerController implements Initializable, Loadable {

    public ProgressIndicator loaderProgress;
    public VBox vBox;

    List<Utilisateur> users =  new ArrayList<>();

    private AdminService adminService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.adminService = AdminServiceImpl.getInstance();
    }

    @Override
    public void loadData() {
        vBox.getChildren().clear();
        loaderProgress.setVisible(true);
        new Thread(() -> {
            try {
                this.users = this.adminService.getUsers();
                this.users.forEach(user -> Platform.runLater(() -> this.addItem(user)));
            } catch (DAOException e) {
                e.printStackTrace();
            } finally {
                loaderProgress.setVisible(false);
            }
        }).start();
    }

    private void addItem(Utilisateur user) {
        try {
            FXMLLoader loader = new FXMLLoader(SchoolManagementApplication.class.getResource("admin/user-manager/user-item.fxml"));
            Parent userNode = loader.load();
            UserItemController itemController = loader.getController();
            itemController.setUtilisateur(user);
            vBox.getChildren().add(userNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAddUser(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SchoolManagementApplication.class.getResource("admin/user-manager/edit-user.fxml"));
            Parent parent = fxmlLoader.load();
            Utilisateur utilisateur = new Utilisateur();
            EditUserController dialogController = fxmlLoader.getController();
            dialogController.setUtilisateur(utilisateur);
            dialogController.loadData();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Ajout d'utilisateur");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("/asserts/logo.jpg"));
            stage.setResizable(false);
            stage.setScene(scene);
            dialogController.setStage(stage);
            stage.showAndWait();
            if(dialogController.getUtilisateur() !=null ){
                loadData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
