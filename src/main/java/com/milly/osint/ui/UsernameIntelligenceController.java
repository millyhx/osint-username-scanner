package com.milly.osint.ui;

import com.milly.osint.core.UsernameIntelligenceResult;
import com.milly.osint.core.UsernameIntelligenceService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class UsernameIntelligenceController {

    @FXML private TextField usernameField;
    @FXML private ListView<String> resultsList;

    private final UsernameIntelligenceService service = new UsernameIntelligenceService();

    @FXML
    private void onAnalyseClicked() {
        String username = usernameField.getText().trim();

        if (username.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter a username.").showAndWait();
            return;
        }

        resultsList.getItems().clear();

        List<UsernameIntelligenceResult> results = service.analyse(username);

        if (results.isEmpty()) {
            resultsList.getItems().add("No intelligence findings for this username.");
            return;
        }

        for (UsernameIntelligenceResult r : results) {
            resultsList.getItems().add(r.getCategory() + ": " + r.getDetail());
        }
    }
}
