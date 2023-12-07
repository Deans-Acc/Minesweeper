package com.example.minesweeper;

import javafx.scene.control.Button;

public class Tile extends Button {
    private boolean istMine;
    Tile(boolean istMine) {
        super();
        this.istMine = istMine;
    }
}
