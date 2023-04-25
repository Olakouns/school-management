package com.esmt.sn.schoolmanagement.front.admin.users;

import com.esmt.sn.schoolmanagement.models.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class UserItemController implements Initializable {


    public Label lastNameLabel;
    public Label firstnameLabel;
    public Label emailLabel;
    public Label phoneLabel;
    public Label addressLabel;
    private Utilisateur utilisateur;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        this.populateData();
    }

    private void populateData() {
        this.lastNameLabel.setText(this.utilisateur.getNom());
        this.firstnameLabel.setText(this.utilisateur.getPrenom());
        this.emailLabel.setText(this.utilisateur.getEmail());
        this.phoneLabel.setText(this.utilisateur.getTelephone());
        this.addressLabel.setText(this.utilisateur.getAdresse());
    }

    public void onEdit(ActionEvent actionEvent) {
    }

    public void onDelete(ActionEvent actionEvent) {
    }
}
