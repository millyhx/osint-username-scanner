package com.milly.osint.ui;

import com.milly.osint.core.ScanResult;
import com.milly.osint.core.ScannerService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;

public class MainController {

    @FXML private TextField usernameField;

    @FXML private TableView<ScanResult> resultsTable;
    @FXML private TableColumn<ScanResult, String> siteColumn;
    @FXML private TableColumn<ScanResult, String> existsColumn;
    @FXML private TableColumn<ScanResult, String> urlColumn;

    @FXML private VBox metadataBox;
    @FXML private Label nameLabel;
    @FXML private Label bioLabel;
    @FXML private Label followersLabel;
    @FXML private Label followingLabel;
    @FXML private Label reposLabel;
    @FXML private ImageView avatarImage;



    private final ScannerService scannerService = new ScannerService();


    @FXML
    private void initialize() {
        siteColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getSiteName()));

        existsColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().exists() ? "Yes" : "No"));

        urlColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getProfileUrl()));

        resultsTable.setRowFactory(table -> new TableRow<>() {
            @Override
            protected void updateItem(ScanResult item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setStyle("");
                    return;
                }

                if (item.exists()) {
                    setStyle("-fx-background-color: #c8f7c5;"); // light green
                } else {
                    setStyle("-fx-background-color: #f7c5c5;"); // light red
                }
            }
        });

    }

    private void clearMetadata() {
        nameLabel.setText("Name:");
        bioLabel.setText("Bio:");
        followersLabel.setText("Followers:");
        followingLabel.setText("Following:");
        reposLabel.setText("Public Repos:");
        avatarImage.setImage(null);
    }



    @FXML
    private void onScanClicked() {
        clearMetadata();
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

        resultsTable.setOnMouseClicked(event -> {
            ScanResult selected = resultsTable.getSelectionModel().getSelectedItem();
            if (selected == null) return;

            Map<String, String> m = selected.getMetadata();

            nameLabel.setText("Name: " + m.getOrDefault("name", ""));
            bioLabel.setText("Bio: " + m.getOrDefault("bio", ""));
            followersLabel.setText("Followers: " + m.getOrDefault("followers", ""));
            followingLabel.setText("Following: " + m.getOrDefault("following", ""));
            reposLabel.setText("Public Repos: " + m.getOrDefault("public_repos", ""));

            // Load avatar if present
            String avatarUrl = m.get("avatar");
            if (avatarUrl != null && !avatarUrl.isEmpty()) {
                avatarImage.setImage(new Image(avatarUrl, true));
            } else {
                avatarImage.setImage(null);
            }
        });


    }

}
