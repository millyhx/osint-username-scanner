package com.milly.osint.ui;

import com.milly.osint.core.BreachEntry;
import com.milly.osint.core.BreachLookupService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class BreachLookupController {

    @FXML private TextField queryField;
    @FXML private TableView<BreachEntry> resultsTable;
    @FXML private TableColumn<BreachEntry, String> sourceColumn;
    @FXML private TableColumn<BreachEntry, String> emailColumn;
    @FXML private TableColumn<BreachEntry, String> passwordColumn;

    private final BreachLookupService service = new BreachLookupService();

    @FXML
    private void initialize() {
        sourceColumn.setCellValueFactory(data -> data.getValue().sourceProperty());
        emailColumn.setCellValueFactory(data -> data.getValue().emailProperty());
        passwordColumn.setCellValueFactory(data -> data.getValue().passwordProperty());
    }

    @FXML
    private void onSearchClicked() {
        String query = queryField.getText().trim();
        if (query.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a username or email.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        resultsTable.getItems().clear();

        Task<List<BreachEntry>> task = new Task<>() {
            @Override
            protected List<BreachEntry> call() throws Exception {
                return service.search(query);
            }
        };

        task.setOnSucceeded(e -> resultsTable.getItems().addAll(task.getValue()));
        task.setOnFailed(e -> {
            task.getException().printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to fetch breach data.", ButtonType.OK);
            alert.showAndWait();
        });


        new Thread(task).start();
    }
}
