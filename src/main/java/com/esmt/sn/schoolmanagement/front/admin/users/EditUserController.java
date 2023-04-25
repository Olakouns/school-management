package com.esmt.sn.schoolmanagement.front.admin.users;

import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;
import com.esmt.sn.schoolmanagement.back.services.AdminService;
import com.esmt.sn.schoolmanagement.back.services.AdminServiceImpl;
import com.esmt.sn.schoolmanagement.front.listeners.Loadable;
import com.esmt.sn.schoolmanagement.models.Privilege;
import com.esmt.sn.schoolmanagement.models.Role;
import com.esmt.sn.schoolmanagement.models.Utilisateur;
import com.esmt.sn.schoolmanagement.models.enums.GenderType;
import com.esmt.sn.schoolmanagement.payload.GenderPayload;
import com.esmt.sn.schoolmanagement.payload.SectionPayload;
import com.esmt.sn.schoolmanagement.utils.Basics;
import com.esmt.sn.schoolmanagement.utils.Constants;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditUserController implements Initializable, Loadable {

    public TextField responseEmailField;
    public Label responseEmailLabelError;
    public TextField firstNameField;
    public Label firstNameLabelError;
    public TextField lastNameField;
    public Label lastNameLabelError;
    public TextField responsePhoneField;
    public Label responsePhoneLabelError;
    public ComboBox<GenderPayload> sexCombo;
    public Label sexLabelError;
    public TextField loginField;
    public Label loginLabelError;
    public TextField addressField;
    public Label addressLabelError;
    public PasswordField passwordField;
    public Label passwordError;
    public ProgressIndicator loaderProgress;
    public HBox actionHBoxBtn;
    public AnchorPane anchorRole;
    public Label roleErrorLabel;

    private Utilisateur utilisateur;
    private List<GenderPayload> genderPayloadList = new ArrayList<>(List.of(new GenderPayload(GenderType.FEMALE, "Feminin"), new GenderPayload(GenderType.MALE, "Masculin")));
    private AdminService adminService;
    private List<Role> roles = new ArrayList<>();

    private final List<Role> rolesList = new ArrayList<>();
    int cols = 2, colCnt = 0, rowCnt = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.adminService = AdminServiceImpl.getInstance();
        this.sexCombo.getItems().addAll(genderPayloadList);
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setStage(Stage stage) {
        stage.setOnCloseRequest(event -> utilisateur = null);
    }

    public void onAddUser(ActionEvent actionEvent) {

        boolean hasError = false;
        responseEmailLabelError.setText("");
        firstNameLabelError.setText("");
        lastNameLabelError.setText("");
        sexLabelError.setText("");
        responsePhoneLabelError.setText("");
        loginLabelError.setText("");
        addressLabelError.setText("");
        passwordError.setText("");


        if (responseEmailField.getText().trim().isEmpty()) {
            hasError = true;
            responseEmailLabelError.setText(Constants.formatText("Veuillez remplir le champ"));
        }

        if (firstNameField.getText().trim().isEmpty()) {
            hasError = true;
            firstNameLabelError.setText(Constants.formatText("Veuillez remplir le champ"));
        }

        if (lastNameField.getText().trim().isEmpty()) {
            hasError = true;
            lastNameLabelError.setText(Constants.formatText("Veuillez remplir le champ"));
        }

        if (sexCombo.getSelectionModel().getSelectedIndex() == -1) {
            hasError = true;
            sexLabelError.setText(Constants.formatText("Veuillez remplir le champ"));
        }

        if (responsePhoneField.getText().trim().isEmpty()) {
            hasError = true;
            responsePhoneLabelError.setText(Constants.formatText("Veuillez remplir le champ"));
        }

        if (loginField.getText().trim().isEmpty()) {
            hasError = true;
            loginLabelError.setText(Constants.formatText("Veuillez remplir le champ"));
        }

        if (addressField.getText().trim().isEmpty()) {
            hasError = true;
            addressLabelError.setText(Constants.formatText("Veuillez remplir le champ"));
        }

        if (passwordField.getText().trim().isEmpty()) {
            hasError = true;
            passwordError.setText(Constants.formatText("Veuillez remplir le champ"));
        }

        if (rolesList.size() == 0) {
            hasError = true;
            roleErrorLabel.setText(Constants.formatText("Veuillez choisir au moins un role"));
        }


        if (hasError) return;

        loaderProgress.setVisible(true);
        actionHBoxBtn.setVisible(false);
        actionHBoxBtn.managedProperty().bind(actionHBoxBtn.visibleProperty());


        String socialStat = String.valueOf(sexCombo.getValue());
        Optional<GenderPayload> socialOptionalS = Optional.empty();
        if (!socialStat.trim().isEmpty()) {
            socialOptionalS = genderPayloadList.stream().filter(s -> s.getName().equals(socialStat)).findFirst();
        }
        socialOptionalS.ifPresent(s -> {
            this.utilisateur.setGenre(s.getGenderType());
        });

        this.utilisateur.setNom(lastNameField.getText().trim());
        this.utilisateur.setPrenom(firstNameField.getText().trim());
        this.utilisateur.setPassword(passwordField.getText().trim());
        this.utilisateur.setEmail(responseEmailField.getText().trim());
        this.utilisateur.setTelephone(responsePhoneField.getText().trim());
        this.utilisateur.setAdresse(addressField.getText().trim());
        this.utilisateur.setLogin(loginField.getText().trim());
        this.utilisateur.setRoles(this.rolesList);

        new Thread(() -> {
            try {
                this.utilisateur = this.adminService.addUser(this.utilisateur);
                Platform.runLater(() -> Basics.closeStage(actionEvent));
            } catch (DAOException e) {
                e.printStackTrace();
            } finally {
                loaderProgress.setVisible(true);
            }
        }).start();
    }

    public void onCancel(ActionEvent actionEvent) {
    }

    @Override
    public void loadData() {
        try {
            this.roles = this.adminService.getRoles();
            this.loadRoleItem();

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private void loadRoleItem() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 5, 5, 5));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
//        AnchorPane anchorPane = new AnchorPane();
        cols = 1;
        colCnt = 0;
        rowCnt = 0;
        this.roles.forEach(role -> {
            CheckBox checkBox = new CheckBox(role.getName());
            checkBox.setPrefWidth(250);
            if ((utilisateur.getId() != 0) && utilisateur.getRoles().stream().anyMatch(privilege1 -> privilege1.getId() == role.getId())) {
                checkBox.setSelected(true);
                rolesList.add(role);
            }
            gridPane.add(checkBox, colCnt, rowCnt);
            colCnt++;
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    rolesList.add(role);
                } else {
                    rolesList.remove(role);
                }
            });
            if (colCnt > cols) {
                rowCnt++;
                colCnt = 0;
            }
        });
        anchorRole.getChildren().add(gridPane);
    }
}
