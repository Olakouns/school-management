package com.esmt.sn.schoolmanagement.utils;

import javafx.stage.FileChooser;

import java.io.File;

public class ConfigUtils {
    public static void configureFileChooser(FileChooser fileChooser, String type) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        if(type.equals("images"))
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("JPG, PNG, JPEG", "*.jpg", "*.png", "*.jpeg")
            );

        if(type.equals("images+pdf"))
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Tous", "*.pdf", "*.jpg", "*.png", "*.jpeg"),
                    new FileChooser.ExtensionFilter("JPG, PNG, JPEG", "*.jpg", "*.png", "*.jpeg"),
                    new FileChooser.ExtensionFilter("PDF", "*.pdf")
            );
    }

    public static void loadExcelFile(FileChooser fileChooser){
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("All EXCELS", "*.xlsx")
        );
    }
}
