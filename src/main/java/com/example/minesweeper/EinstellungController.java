package com.example.minesweeper;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class EinstellungController {
    public TextField Breite;
    public TextField Hoehe;
    public TextField Minen;

    public void apply(ActionEvent event) {
        Minesweeper.manager.wight = Integer.parseInt(Breite.getText());
        Minesweeper.manager.height = Integer.parseInt(Hoehe.getText());
        Minesweeper.manager.MineAmount = Integer.parseInt(Minen.getText());
    }
}
