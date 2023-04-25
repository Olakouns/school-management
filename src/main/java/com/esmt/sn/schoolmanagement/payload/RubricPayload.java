package com.esmt.sn.schoolmanagement.payload;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RubricPayload {
    private StringProperty name;
    private StringProperty amount;

    public RubricPayload() {
    }

    public RubricPayload(String name, String amount) {
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleStringProperty(amount);
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

    public String getAmount() {
        return amount.get();
    }

    public StringProperty getAmountStringProperty() {
        return amount;
    }

    public StringProperty amountProperty() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount.set(amount);
    }
}
