package com.esmt.sn.schoolmanagement.front.admin.sections;

import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;
import com.esmt.sn.schoolmanagement.back.services.AdminService;
import com.esmt.sn.schoolmanagement.back.services.AdminServiceImpl;
import com.esmt.sn.schoolmanagement.models.Section;
import com.esmt.sn.schoolmanagement.models.enums.SectionType;
import com.esmt.sn.schoolmanagement.payload.SectionPayload;
import com.esmt.sn.schoolmanagement.utils.Basics;
import com.esmt.sn.schoolmanagement.utils.Constants;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditSectionController implements Initializable {

    public TextField sectionNameField;
    public Label sectionNameError;
    public ProgressIndicator loaderProgress;
    public HBox actionHBoxBtn;
    public ComboBox<SectionPayload> typeCombo;
    public Label typeLabelError;
    private Section section;

    private AdminService adminService;

    private List<SectionPayload> sectionPayloadList = new ArrayList<>(List.of(new SectionPayload(SectionType.MATERNELLE, "Maternelle"),
            new SectionPayload(SectionType.COLLEGE, "College"),
            new SectionPayload(SectionType.ELEMENTAIRE, "Elementaire"),
            new SectionPayload(SectionType.FROID_ET_CLIMATISATION, "Froid et climatisation")));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.adminService = AdminServiceImpl.getInstance();
        typeCombo.getItems().addAll(sectionPayloadList);

    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Section getSection() {
        return section;
    }

    public void setStage(Stage stage) {
        stage.setOnCloseRequest(event -> section = null);
    }

    public void onSaveSection(ActionEvent actionEvent) {
        boolean hasError = false;
        sectionNameError.setVisible(false);
        typeLabelError.setVisible(false);

        if (sectionNameField.getText().trim().isEmpty()) {
            sectionNameError.setText("Champ obligatoire");
            sectionNameError.setVisible(true);
            hasError = true;
        }

        if (typeCombo.getSelectionModel().getSelectedIndex() == -1) {
            hasError = true;
            typeLabelError.setText(Constants.formatText("Veuillez remplir le champ"));
        }

        if (hasError) return;

        loaderProgress.setVisible(true);
        actionHBoxBtn.setVisible(false);
        actionHBoxBtn.managedProperty().bind(actionHBoxBtn.visibleProperty());

        this.section.setNom(sectionNameField.getText().trim());

        String socialStat = String.valueOf(typeCombo.getValue());
        Optional<SectionPayload> socialOptionalS = Optional.empty();
        if (!socialStat.trim().isEmpty()) {
            socialOptionalS = sectionPayloadList.stream().filter(s -> s.getName().equals(socialStat)).findFirst();
        }

        socialOptionalS.ifPresent(s -> {
            this.section.setSectionType(s.getSectionType());
        });

        new Thread(() -> {
            try {
                this.section = this.adminService.addSection(this.section);
                Platform.runLater(() -> Basics.closeStage(actionEvent));
            } catch (DAOException e) {
                e.printStackTrace();
            } finally {
                loaderProgress.setVisible(true);
            }
        }).start();
    }

    public void onCancel(ActionEvent actionEvent) {
        Constants.closeStage(actionEvent);
    }
}
