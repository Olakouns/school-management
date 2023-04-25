package com.esmt.sn.schoolmanagement.front.admin.roles;

import com.esmt.sn.schoolmanagement.models.Role;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RoleItemController implements Initializable {

    public Label roleNameLabel;
    public Label privilegeNbr;
    private Role role;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
        this.roleNameLabel.setText(this.role.getName());
        this.privilegeNbr.setText(this.role.getPrivileges().size() + " Privilege"+( this.role.getPrivileges().size() > 1 ? "s" : ""));
    }

    public void onEdit(ActionEvent actionEvent) {
    }

    public void onDelete(ActionEvent actionEvent) {
    }
}
