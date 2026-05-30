package com.milly.osint.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainController {

    @FXML private TextField usernameField;
    @FXML private TableView<?> resultsTable;
    @FXML private TableColumn<?, ?> siteColumn;
    @FXML private TableColumn<?, ?> existsColumn;
    @FXML private TableColumn<?, ?> urlColumn;

    @FXML
    private void onScanClicked() {
        System.out.println("Scan clicked! Username: " + usernameField.getText());
    }
}
