package com.milly.osint.ui;

import com.milly.osint.core.ScanResult;
import com.milly.osint.core.ScannerService;
import com.milly.osint.core.SiteDefinition;
import com.milly.osint.core.SiteLoader;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML private TextField usernameField;

    @FXML private TableView<ScanResult> resultsTable;
    @FXML private TableColumn<ScanResult, String> siteColumn;
    @FXML private TableColumn<ScanResult, String> existsColumn;
    @FXML private TableColumn<ScanResult, String> urlColumn;

    @FXML private ProgressBar progressBar;

    @FXML private Button openProfileButton;

    @FXML private VBox webViewContainer;
    @FXML private WebView webView;

    private final ScannerService scannerService = new ScannerService();


    @FXML
    private void initialize() {

        // Table column bindings
        siteColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getSiteName()));

        existsColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().exists() ? "Yes" : "No"));

        urlColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getProfileUrl()));

        // Row colouring
        resultsTable.setRowFactory(table -> new TableRow<>() {
            @Override
            protected void updateItem(ScanResult item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setStyle("");
                    return;
                }

                if (item.exists()) {
                    setStyle("-fx-background-color: #c8f7c5;");
                } else {
                    setStyle("-fx-background-color: #f7c5c5;");
                }
            }
        });

        // Disable open button until a valid row is selected
        resultsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            openProfileButton.setDisable(newVal == null || !newVal.exists());
        });

        // Hide WebView panel initially
        webViewContainer.setPrefHeight(0);
        webViewContainer.setMaxHeight(0);

        // ---------------------------------------------------------
        // Keyboard Shortcuts
        // ---------------------------------------------------------

        usernameField.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                    switch (event.getCode()) {
                        case ENTER -> onScanClicked();
                        case O -> {
                            if (event.isControlDown()) onOpenProfileClicked();
                        }
                        case L -> {
                            if (event.isControlDown()) clearResults();
                        }
                        case ESCAPE -> collapseWebView();
                        case F1 -> showHelp();
                    }
                });
            }
        });

    }

    private void clearResults() {
        resultsTable.getItems().clear();
    }

    // ---------------------------------------------------------
    // Help Pane
    // ---------------------------------------------------------
    @FXML
    private void showHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help & Shortcuts");
        alert.setHeaderText("How to use the OSINT Username Scanner");

        String helpText =
                "• Enter a username and press Enter to scan\n" +
                        "• Select a site and press Ctrl+O to open the profile\n" +
                        "• Press Esc to close the WebView panel\n\n" +
                        "Keyboard Shortcuts:\n" +
                        "Enter → Scan\n" +
                        "Ctrl + O → Open Profile\n" +
                        "Ctrl + L → Clear Results\n" +
                        "Esc → Close WebView\n" +
                        "F1 → Help";

        alert.setContentText(helpText);
        alert.showAndWait();
    }


    @FXML
    private void onScanClicked() {

        // Collapse WebView when scanning again
        collapseWebView();

        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            System.out.println("No username entered");
            return;
        }

        resultsTable.getItems().clear();

        Task<List<ScanResult>> task = new Task<>() {
            @Override
            protected List<ScanResult> call() throws Exception {
                List<SiteDefinition> sites = SiteLoader.loadSites();
                List<ScanResult> results = new ArrayList<>();

                int total = sites.size();
                int count = 0;

                for (SiteDefinition site : sites) {
                    ScanResult result = scannerService.scanSite(site, username);
                    results.add(result);

                    count++;
                    updateProgress(count, total);
                }

                return results;
            }
        };

        task.setOnSucceeded(e -> {
            resultsTable.getItems().addAll(task.getValue());
            progressBar.progressProperty().unbind();
            progressBar.setProgress(0);
        });

        progressBar.progressProperty().bind(task.progressProperty());

        new Thread(task).start();
    }




    // ---------------------------------------------------------
    // WebView: Open Profile
    // ---------------------------------------------------------

    @FXML
    private void onOpenProfileClicked() {
        ScanResult selected = resultsTable.getSelectionModel().getSelectedItem();
        if (selected == null || !selected.exists()) return;

        WebEngine engine = webView.getEngine();
        engine.load(selected.getProfileUrl());

        expandWebView();
    }


    // ---------------------------------------------------------
    // Slide Animation (300ms smooth)
    // ---------------------------------------------------------

    private void expandWebView() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(webViewContainer.prefHeightProperty(), 400),
                        new KeyValue(webViewContainer.maxHeightProperty(), 400))
        );
        timeline.play();
    }

    private void collapseWebView() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(webViewContainer.prefHeightProperty(), 0),
                        new KeyValue(webViewContainer.maxHeightProperty(), 0))
        );
        timeline.play();
    }


    // ---------------------------------------------------------
    // Tools Menu (placeholders for now)
    // ---------------------------------------------------------

    @FXML
    private void onUsernameIntelligence() {
        System.out.println("Username Intelligence tool clicked");
    }

    @FXML
    private void onUsernameSuggestions() {
        System.out.println("Username Suggestions tool clicked");
    }

    @FXML
    private void onBreachLookup() {
        System.out.println("Breach Lookup tool clicked");
    }

}
