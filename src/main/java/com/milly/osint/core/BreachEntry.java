package com.milly.osint.core;

import javafx.beans.property.SimpleStringProperty;

public class BreachEntry {
    private final SimpleStringProperty source;
    private final SimpleStringProperty email;
    private final SimpleStringProperty password;

    public BreachEntry(String source, String email, String password) {
        this.source = new SimpleStringProperty(source);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
    }

    public SimpleStringProperty sourceProperty() { return source; }
    public SimpleStringProperty emailProperty() { return email; }
    public SimpleStringProperty passwordProperty() { return password; }

    public String getSource() { return source.get(); }
    public String getEmail() { return email.get(); }
    public String getPassword() { return password.get(); }
}
