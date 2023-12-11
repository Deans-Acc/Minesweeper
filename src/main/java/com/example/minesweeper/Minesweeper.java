package com.example.minesweeper;

import com.example.minesweeper.game.NormalGame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Minesweeper extends Application {
    private final NormalGame game = new NormalGame(10, 10, 10);

    @Override
    public void start(Stage stage) throws IOException {
        var fxml = new FXMLLoader(getClass().getResource("main_view.fxml"));
        Parent main = fxml.load();
        ((MainController) fxml.getController()).main.getChildren().add(game.controller.pane);
        Scene scene = new Scene(main, 100, 100);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}