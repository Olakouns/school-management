package com.esmt.sn.schoolmanagement.front.auth;

import com.esmt.sn.schoolmanagement.SchoolManagementApplication;
import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;
import com.esmt.sn.schoolmanagement.back.services.AuthService;
import com.esmt.sn.schoolmanagement.back.services.AuthServiceImpl;
import com.esmt.sn.schoolmanagement.models.Privilege;
import com.esmt.sn.schoolmanagement.models.enums.TypePrivilege;
import com.esmt.sn.schoolmanagement.payload.ApiResponse;
import com.esmt.sn.schoolmanagement.utils.Basics;
import com.esmt.sn.schoolmanagement.utils.Constants;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class LoginController implements Initializable {

    public AnchorPane parentContent;
    public TextField emailField;
    public Label emailError;
    public PasswordField passwordField;
    public Label passwordError;
    public Label errorLabel;
    public Button submitBtn;
    public ProgressIndicator loaderProgress;

    private AuthService authService;
    public final static Logger logger = LogManager.getLogger(LoginController.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.authService = AuthServiceImpl.getInstance();
        emailField.setText("supperadmin@esmt.sn");
    }

    public void onHelp(ActionEvent actionEvent) {
    }

    public void onPasswordForgot(ActionEvent actionEvent) {
    }

    public void onConnect(ActionEvent actionEvent) {
        boolean hasError = false;
        emailError.setVisible(false);
        passwordError.setVisible(false);
        errorLabel.setVisible(false);

        if (emailField.getText().trim().isEmpty()) {
            emailError.setText("Champ obligatoire");
            emailError.setVisible(true);
            hasError = true;
        }
        if (passwordField.getText().trim().isEmpty()) {
            passwordError.setText("Champ obligatoire");
            passwordError.setVisible(true);
            hasError = true;
        }
        if (hasError) return;

        loaderProgress.setVisible(true);
        submitBtn.setVisible(false);
        submitBtn.managedProperty().bind(submitBtn.visibleProperty());

        new Thread(() -> {
            try {
                ApiResponse apiResponse = this.authService.loginUser(emailField.getText().trim(), passwordField.getText().trim());
                if (apiResponse.isSuccess()) {
                    logger.info(apiResponse.getMessage());
                    this.loadPrivileges();

                    Platform.runLater(() -> {
                        Basics.closeStage(actionEvent);
                        if (Constants.USER_PRIVILEGE.contains(TypePrivilege.CREATE_ROLE)) {
                            this.openGlobalAdminHome();
                        } else {
                            this.openUserHome();
                        }
                    });


                } else {
                    this.showError(apiResponse.getMessage());
                }
            } catch (DAOException e) {
                e.printStackTrace();
                this.showError("Erreur de connexion");
            }
        }).start();
    }

    private void loadPrivileges() {
        Constants.utilisateur.getRoles().forEach(role -> {
            Constants.USER_PRIVILEGE.addAll(role.getPrivileges().stream().map(Privilege::getName).collect(Collectors.toList()));
        });
    }

    private void openGlobalAdminHome() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SchoolManagementApplication.class.getResource("home.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 871, 686);
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/asserts/logo.jpg"));
            stage.setTitle("Administrateur-Home");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openUserHome() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SchoolManagementApplication.class.getResource("front/global-home.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 871, 686);
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/asserts/logo.jpg"));
            stage.setTitle("User-Home");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        Platform.runLater(() -> {
            errorLabel.setVisible(true);
            errorLabel.setText(message);
            loaderProgress.setVisible(false);
            submitBtn.setVisible(true);
        });
    }
}
