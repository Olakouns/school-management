package com.esmt.sn.schoolmanagement.front.admin.classes;

import com.esmt.sn.schoolmanagement.models.Classe;
import com.esmt.sn.schoolmanagement.payload.RubricPayload;
import com.esmt.sn.schoolmanagement.utils.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClassItemController implements Initializable {

    public Label className;
    public TableView<RubricPayload> tableView;
    public TableColumn<RubricPayload, String> rubricsNameColumn;
    public TableColumn<RubricPayload, String> amountColumn;

    private Classe classe;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.initiateTable();
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
        this.populateData();
    }

    private void populateData() {
        this.className.setText(this.classe.getName());

        tableView.getItems().clear();
        ObservableList<RubricPayload> rubricPayloads = FXCollections.observableArrayList();
        this.classe.getRubriques().forEach(rubrique -> {
            rubricPayloads.add(new RubricPayload(String.valueOf(rubrique.getLabel()), Constants.formatAmount(rubrique.getAmount().doubleValue())+ "F CFA"));
        });
        tableView.setItems(rubricPayloads);
    }

    private void initiateTable() {
        tableView.setSelectionModel(null);
        rubricsNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameStringProperty());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().getAmountStringProperty());
    }

    public void onEdit(ActionEvent actionEvent) {
    }

    public void onAddRubrics(ActionEvent actionEvent) {
    }

    public void onDelete(ActionEvent actionEvent) {
    }
}
