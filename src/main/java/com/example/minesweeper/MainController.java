package com.example.minesweeper;

import com.example.minesweeper.game.NormalGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    public VBox main;

    public void ButtenSpielen(ActionEvent event) {
        NormalGame game = new NormalGame(10,10,20);
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameFrame.fxml"));
            Parent main = fxmlLoader.load();
            GameFrame gameFrameController = fxmlLoader.getController();
            GridPane gamePane = gameFrameController.gamePane;
            gamePane.getChildren().add(game.controller.pane);
            Scene scene = new Scene(main, 600, 400);
            stage.setScene(scene);
            game.controller.Minecounter.setText("Mines: 20");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ButtenOnline(ActionEvent event) {
    }

    public void ButtenEinstellungen(ActionEvent event) {
    }
}