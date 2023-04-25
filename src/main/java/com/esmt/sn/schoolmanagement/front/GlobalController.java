package com.esmt.sn.schoolmanagement.front;

import com.esmt.sn.schoolmanagement.SchoolManagementApplication;
import com.esmt.sn.schoolmanagement.front.admin.classes.ClassManagerController;
import com.esmt.sn.schoolmanagement.front.admin.roles.RoleManagerController;
import com.esmt.sn.schoolmanagement.front.admin.sections.SectionManagerController;
import com.esmt.sn.schoolmanagement.front.admin.users.UserManagerController;
import com.esmt.sn.schoolmanagement.front.listeners.Loadable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GlobalController implements Initializable {
    public ToggleButton dashboardBtn;
    public ToggleGroup dashMenu;
    public ToggleButton sectionBtn;
    public ToggleButton ClassBtn;
    public ToggleButton userBtn;
    public ToggleButton roleBtn;
    public Button hepBtn;
    public BorderPane containerPane;
    public AnchorPane navParent;
    public AnchorPane anchorGlobal;
    public Label username;
    public Label navTitle;


    private Parent sectionManger;
    private Parent classManger;
    private Parent roleManger;
    private Parent userManger;


    private SectionManagerController sectionManagerController;
    private ClassManagerController classManagerController;
    private RoleManagerController roleManagerController;
    private UserManagerController userManagerController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dashboardBtn.setSelected(true);
    }


    public void onDashboard(ActionEvent actionEvent) {
    }

    public void onLoadSection(ActionEvent actionEvent) {
        this.loadGlobal(sectionManger, sectionManagerController, "Sections", "admin/sections/section-manager.fxml");
    }

    public void onLoadClass(ActionEvent actionEvent) {
        this.loadGlobal(classManger, classManagerController, "Classes-Rubrique", "admin/classes/class-manager.fxml");
    }

    public void onLoadUser(ActionEvent actionEvent) {
        this.loadGlobal(userManger, userManagerController, "Utilisateurs", "admin/user-manager/user-manager.fxml");
    }

    public void onLoadRole(ActionEvent actionEvent) {
        this.loadGlobal(roleManger, roleManagerController, "Gestion des roles", "admin/role-manager/role-manager.fxml");
    }

    public void onHelp(ActionEvent actionEvent) {
    }

    private <T extends Loadable> void loadGlobal(Parent parent, T controller, String title, String link) {
        this.navTitle.setText(title);
        if (parent == null) {
            try {
                FXMLLoader loader = new FXMLLoader(SchoolManagementApplication.class.getResource(link));
                parent = loader.load();
                controller = loader.getController();
                controller.loadData();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else controller.loadData();
        containerPane.setCenter(parent);
    }
}

