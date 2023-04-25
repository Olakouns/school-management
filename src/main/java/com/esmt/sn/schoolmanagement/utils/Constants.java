package com.esmt.sn.schoolmanagement.utils;

import com.esmt.sn.schoolmanagement.models.Privilege;
import com.esmt.sn.schoolmanagement.models.Utilisateur;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Constants {

    public static String API_URL = "https://dev-api.artcreativity.io/eges/api/";
    public static String MEDIA_API_URL = "https://dev-api.artcreativity.io/eges/";

    public static final int sizeOfContentPage = 50;
    public static final double discountLimit = 25.0;
    public static List<Privilege> USER_PRIVILEGE = new ArrayList<>();

    public static Utilisateur utilisateur;


    public static String formatAmount(double number) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);
        DecimalFormat decimalFormat = (DecimalFormat) nf;
        return decimalFormat.format(number);
    }

    public static Circle surroundedImage(String url, double radius) {
        Circle circle = new Circle(250, 250, radius);
        Image im = new Image(url, false);
        circle.setFill(new ImagePattern(im));
        return circle;
    }

    public static String formatDateTime(Instant date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return simpleDateFormat.format(Date.from(date));
    }

    public static String formatTime(Instant date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(Date.from(date));
    }

    public static String formatTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String formatDate(Instant date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(Date.from(date));
    }

    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(date);
    }

    public static String formatText(String text) {
        return new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

    public static String formatFullDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy à HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        return simpleDateFormat.format(date);
    }

    public static String formatTextByOtherWay(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD);
    }

    public static void formatNumberInput(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*(\\.)?\\d*")) {
                    textField.setText(oldValue);
                }
            }
        });
    }

    public static String formatDoubleValue(double value) {
        NumberFormat numberFormat = DecimalFormat.getNumberInstance(Locale.FRENCH);
        return numberFormat.format(value);
    }

    public static String formatBigDoubleValue(double value) {
        return String.format("%.02f", value);
    }

    public static void formatDatePicker(DatePicker datePicker) {
        datePicker.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0);
            }
        });
    }

    public static void dateConverterFormat(DatePicker datePicker) {
        datePicker.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });
    }

    public static void hideLoader(ProgressIndicator loaderProgress, Button saveBtn) {
        loaderProgress.setVisible(false);
        loaderProgress.managedProperty().bind(loaderProgress.visibleProperty());
        saveBtn.setVisible(true);
    }

    public static void showLoader(ProgressIndicator loaderProgress, Button saveBtn) {
        loaderProgress.setVisible(true);
        saveBtn.setVisible(false);
        saveBtn.managedProperty().bind(saveBtn.visibleProperty());
    }


    public static void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


}
