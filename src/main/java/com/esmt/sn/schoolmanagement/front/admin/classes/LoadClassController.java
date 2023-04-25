package com.esmt.sn.schoolmanagement.front.admin.classes;

import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;
import com.esmt.sn.schoolmanagement.back.services.AdminService;
import com.esmt.sn.schoolmanagement.back.services.AdminServiceImpl;
import com.esmt.sn.schoolmanagement.models.Classe;
import com.esmt.sn.schoolmanagement.models.Rubrique;
import com.esmt.sn.schoolmanagement.models.Section;
import com.esmt.sn.schoolmanagement.models.enums.SectionType;
import com.esmt.sn.schoolmanagement.utils.Basics;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

public class LoadClassController implements Initializable {
    public ProgressIndicator loaderProgress;
    public Label labelText;
    public Button btnClose;

    private File file;
    private List<Classe> classes = new ArrayList<>();
    private List<Section> sections;

    private AdminService adminService;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnClose.setVisible(false);
        btnClose.managedProperty().bind(btnClose.visibleProperty());
        labelText.setVisible(false);
        labelText.managedProperty().bind(labelText.visibleProperty());
        this.adminService = AdminServiceImpl.getInstance();
    }

    public void onClose(ActionEvent actionEvent) {
        Basics.closeStage(actionEvent);
    }


    public void setFile(File file) {
        this.file = file;
        new Thread(this::launchAction).start();
    }

    private void launchAction() {
        try {
            FileInputStream fis = new FileInputStream(this.file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object

            int start = 1;
            Classe classe = new Classe();
            Classe tmpClass = null;
            String lastClassName = "";
            for (Row row : sheet) {
                if (start != 1) {
                    if (!lastClassName.equals(row.getCell(0).getStringCellValue())) {
                        if (tmpClass != null) {
                            this.addData(classe.getName(), classe);
                        }
                        classe = new Classe();
                        classe.setRubriques(new ArrayList<>());
                        classe.setName(row.getCell(0).getStringCellValue());
                        lastClassName = classe.getName();
                        classe.setSection(this.findSection(row.getCell(1).getStringCellValue()));
                        if (classe.getSection() == null) {
                            Platform.runLater(() -> this.showMessage(false));
                            return;
                        }
                    }

                    if (this.findSection(row.getCell(1).getStringCellValue()) == null) {
                        Platform.runLater(() -> this.showMessage(false));
                        return;
                    }

                    Rubrique rubrique = new Rubrique(row.getCell(2).getStringCellValue(), BigDecimal.valueOf(row.getCell(3).getNumericCellValue()));
                    rubrique.setSection(classe.getSection());

                    classe.addRubrique(rubrique);
                    tmpClass = classe;
                }

                if (start == sheet.getPhysicalNumberOfRows()) {
                    this.addData(classe.getName(), classe);
                    this.launchSaveData();
                }
                start++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addData(String className, Classe classe) {
        for (String s : className.split("/")) {
            Classe tmp = new Classe();
            tmp.setName(s);
            tmp.setSection(classe.getSection());

            for (Rubrique rubrique : classe.getRubriques()) {
                Rubrique rubrique1 = new Rubrique(rubrique.getLabel(), rubrique.getAmount());
                rubrique1.setSection(rubrique.getSection());
                tmp.addRubrique(rubrique1);
            }
            this.classes.add(tmp);
        }

    }

    private void launchSaveData() {
        for (Classe aClass : this.classes) {
            try {
                this.adminService.addClass(aClass.getSection().getSection_id(), aClass);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }

        Platform.runLater(() -> this.showMessage(true));
    }

    private void showMessage(boolean success) {
        loaderProgress.setVisible(false);
        loaderProgress.managedProperty().bind(loaderProgress.visibleProperty());
        labelText.setVisible(true);
        labelText.setText(success ? "Chargement effectue" : "Une erreur s'est produite! Verifiez le format de votre fichier");
        labelText.getStyleClass().add(success ? "k-text-color" : "error-color");
        btnClose.setVisible(true);
    }

    public boolean getResult() {
        return true;
    }

    public void setSectionList(List<Section> sections) {
        this.sections = sections;
    }

    private Section findSection(String sectionType) {
        Optional<Section> sectionOptional = this.sections.stream().filter(section -> section.getSectionType().toString().equals((sectionType))).findFirst();
        return sectionOptional.orElse(null);
    }
}
