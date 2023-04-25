package com.esmt.sn.schoolmanagement.utils;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Basics {
    public static void closeStage(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stageClose = (Stage) source.getScene().getWindow();
        stageClose.close();
    }
}
