package com.esmt.sn.schoolmanagement.payload;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassPayload {
    private StringProperty name;
    private StringProperty nbrRubrics;

    public ClassPayload() {
    }

    public ClassPayload(String name, String nbrRubrics) {
        this.name = new SimpleStringProperty(name);
        this.nbrRubrics = new SimpleStringProperty(nbrRubrics);
    }

    public ClassPayload(StringProperty name, StringProperty nbrRubrics) {
        this.name = name;
        this.nbrRubrics = nbrRubrics;
    }


    public String getName() {
        return name.get();
    }

    public StringProperty getNameStringProperty() {
        return name;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getNbrRubrics() {
        return nbrRubrics.get();
    }

    public StringProperty getNbrRubricsStringProperty() {
        return nbrRubrics;
    }

    public StringProperty nbrRubricsProperty() {
        return nbrRubrics;
    }

    public void setNbrRubrics(String nbrRubrics) {
        this.nbrRubrics.set(nbrRubrics);
    }
}
