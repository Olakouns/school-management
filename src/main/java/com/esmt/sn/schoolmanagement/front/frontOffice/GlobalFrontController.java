package com.esmt.sn.schoolmanagement.front.frontOffice;

import com.esmt.sn.schoolmanagement.SchoolManagementApplication;
import com.esmt.sn.schoolmanagement.front.admin.sections.SectionManagerController;
import com.esmt.sn.schoolmanagement.front.frontOffice.classes.FrontClassManagerController;
import com.esmt.sn.schoolmanagement.front.listeners.Loadable;
import com.esmt.sn.schoolmanagement.models.Section;
import com.esmt.sn.schoolmanagement.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GlobalFrontController implements Initializable {

    public Label username;
    public Label navTitle;
    public BorderPane containerPane;
    public AnchorPane navParent;
    public AnchorPane anchorGlobal;

    private Section currentSection;
    private Parent classManger;

    private FrontClassManagerController frontClassManagerController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.username.setText(Constants.utilisateur.toString());
    }

    public void setSection(Section section) {
        this.currentSection = section;
        Constants.CURRENT_SECTION = this.currentSection;
    }

    public void onHelp(ActionEvent actionEvent) {

    }

    public void onDashboard(ActionEvent actionEvent) {

    }

    public void onLoadClass(ActionEvent actionEvent) {
        this.loadGlobal(classManger, frontClassManagerController, "Les classes", "front/classes/front-class-manager.fxml");
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
