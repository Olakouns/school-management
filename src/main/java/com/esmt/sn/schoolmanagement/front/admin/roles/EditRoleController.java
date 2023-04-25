package com.esmt.sn.schoolmanagement.front.admin.roles;

import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;
import com.esmt.sn.schoolmanagement.back.services.AdminService;
import com.esmt.sn.schoolmanagement.back.services.AdminServiceImpl;
import com.esmt.sn.schoolmanagement.models.Privilege;
import com.esmt.sn.schoolmanagement.models.Role;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditRoleController implements Initializable {

    public TextField roleField;
    public Label roleLabelError;
    public Accordion accordion;
    public ProgressIndicator loaderProgress;
    public HBox actionHBoxBtn;


    private Role role;
    private AdminService adminService;
    private final List<String> categoryList = new ArrayList<>();
    private List<List<Privilege>> lists = new ArrayList<>();

    private List<Privilege> privileges;
    private final List<Privilege> privilegeList = new ArrayList<>();
    int cols = 2, colCnt = 0, rowCnt = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.adminService = AdminServiceImpl.getInstance();

        this.loadRole();
    }

    private void loadRole() {
        new Thread(() -> {
            try {
                List<Privilege> privileges = adminService.getPrivileges();
                orderByCategory(privileges);
                loadData();
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void loadData() {
        lists.forEach(secondList -> {
            TitledPane titledPane = new TitledPane();
            titledPane.setText(secondList.get(0).getCategory());
            GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(10, 5, 5, 5));
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            AnchorPane anchorPane = new AnchorPane();
            cols = 1;
            colCnt = 0;
            rowCnt = 0;
            secondList.forEach(privilege -> {
                CheckBox checkBox = new CheckBox(privilege.getDescription());
                checkBox.setPrefWidth(250);
                if ((role.getId() != 0) && role.getPrivileges().stream().anyMatch(privilege1 -> privilege1.getId() == privilege.getId())) {
                    checkBox.setSelected(true);
                    privilegeList.add(privilege);
                }
                gridPane.add(checkBox, colCnt, rowCnt);
                colCnt++;
                checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        privilegeList.add(privilege);
                    } else {
                        privilegeList.remove(privilege);
                    }
                });
                if (colCnt > cols) {
                    rowCnt++;
                    colCnt = 0;
                }
            });
            anchorPane.getChildren().add(gridPane);
            titledPane.setContent(anchorPane);
            Platform.runLater(() -> {
                accordion.getPanes().add(titledPane);
            });
        });
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
        if (role.getId() != 0) {
            roleField.setText(role.getName());
        }
    }

    private void orderByCategory(List<Privilege> privileges) {
        privileges.forEach(privilege -> {
            if (!categoryList.contains(privilege.getCategory())) {
                categoryList.add(privilege.getCategory());
            }
        });
        lists = new ArrayList<>();
        categoryList.forEach(s -> {
            List<Privilege> subList = new ArrayList<>();
            privileges.forEach(privilege -> {
                if (s.equals(privilege.getCategory())) {
                    subList.add(privilege);
                }
            });
            lists.add(subList);
        });
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setStage(Stage stage) {
        stage.setOnCloseRequest(event -> role = null);
    }

    public void onSave(ActionEvent actionEvent) {
        boolean hasError = false;
        roleLabelError.setText("");
        if (roleField.getText().trim().isEmpty()) {
            hasError = true;
            roleLabelError.setText("Champ obligatoire");
        }
        
        if (hasError) {
            return;
        }

        loaderProgress.setVisible(true);
        actionHBoxBtn.setVisible(false);

        role.setName(roleField.getText());
        role.setPrivileges(privilegeList);

        new Thread(() -> {
            try {
                role = this.adminService.addRole(role);
                Platform.runLater(() -> closeStage(actionEvent));
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void onCancel(ActionEvent actionEvent) {
        role = null;
        closeStage(actionEvent);
    }
}
