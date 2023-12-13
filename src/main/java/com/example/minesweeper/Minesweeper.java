package com.example.minesweeper;

import com.example.minesweeper.game.NormalGame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Minesweeper extends Application {
    private final NormalGame game = new NormalGame(10, 10, 99);
    private static final Radio radio = new Radio();

    @Override
    public void start(Stage stage) throws IOException {
        var fxml = new FXMLLoader(getClass().getResource("main_view.fxml"));
        Parent main = fxml.load();
        GridPane gamePane = ((MainController) fxml.getController()).gamePane;
        gamePane.getChildren().add(game.controller.pane);
        Scene scene = new Scene(main, 1000, 1000);
        stage.setScene(scene);
        stage.show();

        radio.start();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void addTitle(String title) {
        radio.addTitle(title);
    }
}

