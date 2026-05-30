package com.milly.osint;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // TODO: Load JavaFx UI
        stage.setTitle("OSINT Username Scanner");
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}
