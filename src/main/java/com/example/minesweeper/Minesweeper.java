package com.example.minesweeper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Minesweeper extends Application {
    private GridPane view = new GridPane();

    @Override
    public void start(Stage stage) throws IOException {
        Tile test = new Tile(false);

        view.add(test, 0, 0);

        Scene scene = new Scene(view, 100, 100);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}