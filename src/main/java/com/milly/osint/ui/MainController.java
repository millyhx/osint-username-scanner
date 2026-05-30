package com.milly.osint.ui;

import com.milly.osint.core.ScanResult;
import com.milly.osint.core.ScannerService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class MainController {

    @FXML private TextField usernameField;

    @FXML private TableView<ScanResult> resultsTable;
    @FXML private TableColumn<ScanResult, String> siteColumn;
    @FXML private TableColumn<ScanResult, String> existsColumn;
    @FXML private TableColumn<ScanResult, String> urlColumn;

    private final ScannerService scannerService = new ScannerService();


    @FXML
    private void initialize() {
        siteColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getSiteName()));

        existsColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().exists() ? "Yes" : "No"));

        urlColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getProfileUrl()));
    }


    @FXML
    private void onScanClicked() {
        String username = usernameField.getText().trim();

        if (username.isEmpty()) {
            System.out.println("No username entered");
            return;
        }

        resultsTable.getItems().clear();

        // Run scan
        List<ScanResult> results = scannerService.scanUsername(username);

        // Add results to table
        resultsTable.getItems().addAll(results);
    }

}
