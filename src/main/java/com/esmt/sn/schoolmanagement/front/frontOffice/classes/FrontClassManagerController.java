package com.esmt.sn.schoolmanagement.front.frontOffice.classes;

import com.esmt.sn.schoolmanagement.SchoolManagementApplication;
import com.esmt.sn.schoolmanagement.back.exceptions.DAOException;
import com.esmt.sn.schoolmanagement.back.services.AdminService;
import com.esmt.sn.schoolmanagement.back.services.AdminServiceImpl;
import com.esmt.sn.schoolmanagement.front.admin.classes.ClassItemController;
import com.esmt.sn.schoolmanagement.front.listeners.CallBack;
import com.esmt.sn.schoolmanagement.front.listeners.Loadable;
import com.esmt.sn.schoolmanagement.front.listeners.SelectListener;
import com.esmt.sn.schoolmanagement.models.Classe;
import com.esmt.sn.schoolmanagement.payload.ClassPayload;
import com.esmt.sn.schoolmanagement.payload.RubricPayload;
import com.esmt.sn.schoolmanagement.utils.Constants;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.util.Callback;

public class FrontClassManagerController implements Initializable, Loadable, SelectListener<FrontClassItemController, Classe>, CallBack {

    public ProgressIndicator loaderProgress;
    //    public VBox vBox;
    public AnchorPane contentFirst;
    public BorderPane contentBorder;
    public TableView<ClassPayload> tableView;
    public TableColumn<ClassPayload, String> classNameColumn;
    public TableColumn<ClassPayload, String> nbrRubricsNameColumn;
    public TableColumn<ClassPayload, Void> actionColumn;

    private AdminService adminService;
    private List<Classe> classes = new ArrayList<>();
    private ClassDetailsController classDetailsController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.adminService = AdminServiceImpl.getInstance();
        this.initiateTable();
    }

    @Override
    public void loadData() {
        new Thread(() -> {
            try {
                this.classes = this.adminService.getClassList(Constants.CURRENT_SECTION.getNom());
                this.setDataIntoTable();
            } catch (DAOException e) {
                e.printStackTrace();
            } finally {
                loaderProgress.setVisible(false);
            }
        }).start();
    }

    private void initiateTable() {
        tableView.setSelectionModel(null);
        classNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameStringProperty());
        nbrRubricsNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNbrRubricsStringProperty());
    }

    private void setDataIntoTable() {
        tableView.getItems().clear();
        ObservableList<ClassPayload> classPayloads = FXCollections.observableArrayList();
        this.classes.forEach(classe -> {
            classPayloads.add(new ClassPayload(String.valueOf(classe.getName()), String.valueOf(classe.getRubriques().size())));
        });
        tableView.setItems(classPayloads);
        this.addButtonToTable();
    }

    private void addButtonToTable() {
        Callback<TableColumn<ClassPayload, Void>, TableCell<ClassPayload, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<ClassPayload, Void> call(final TableColumn<ClassPayload, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Voir les détails");
                    {
                        btn.setCursor(Cursor.HAND);
                        btn.setOnAction((ActionEvent event) -> {
                            ClassPayload data = getTableView().getItems().get(getIndex());
                            Optional<Classe> optionalClasse = classes.stream().filter(classe1 -> classe1.getName().equals(data.getName())).findFirst();
                            optionalClasse.ifPresent(FrontClassManagerController.this::goToDetails);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };

        actionColumn.setCellFactory(cellFactory);
//        table.getColumns().add(colBtn);
    }


    @Override
    public void onCall() {
        loadData();
        contentFirst.setVisible(true);
        contentBorder.setVisible(false);
    }

    private void goToDetails(Classe classe) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SchoolManagementApplication.class.getResource("front/classes/class-details.fxml"));
            Parent parent = fxmlLoader.load();
            classDetailsController = fxmlLoader.getController();
            classDetailsController.setCurrentClass(classe);
            classDetailsController.setCallBack(this);
            contentBorder.setCenter(parent);
            contentFirst.setVisible(false);
            contentBorder.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSelect(FrontClassItemController child, Classe data) {

    }
}
