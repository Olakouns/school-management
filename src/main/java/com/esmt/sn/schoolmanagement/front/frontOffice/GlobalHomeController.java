package com.esmt.sn.schoolmanagement.front.frontOffice;

import com.esmt.sn.schoolmanagement.SchoolManagementApplication;
import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;
import com.esmt.sn.schoolmanagement.back.services.AdminService;
import com.esmt.sn.schoolmanagement.back.services.AdminServiceImpl;
import com.esmt.sn.schoolmanagement.front.listeners.Loadable;
import com.esmt.sn.schoolmanagement.models.Section;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GlobalHomeController implements Initializable, Loadable {
    public GridPane gridPane;

    private int lastColumn;
    private int lastRow;

    private AdminService adminService;
    private List<Section> sections = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.adminService = AdminServiceImpl.getInstance();
        this.loadData();
    }

    @Override
    public void loadData() {
        lastColumn = 0;
        lastRow = 0;
        gridPane.setHgap(50);
        new Thread(() -> {
            try {
                this.sections = this.adminService.getSections();
                this.sections.forEach(section -> Platform.runLater(() -> this.addItem(section)));
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void addItem(Section section) {
        try {
            FXMLLoader loader = new FXMLLoader(SchoolManagementApplication.class.getResource("front/section-item.fxml"));
            Parent spentNode = loader.load();
            SectionItemFrontController itemController = loader.getController();
            itemController.setSection(section);
            gridPane.add(spentNode, lastColumn, lastRow);
            if (lastColumn < 1) {
                lastColumn++;
            } else {
                lastColumn = 0;
                lastRow += 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
