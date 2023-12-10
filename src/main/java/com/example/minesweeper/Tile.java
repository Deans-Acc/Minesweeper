package com.example.minesweeper;

import javafx.scene.control.Button;

public class Tile extends Button {
    public boolean istMine;
    public int nachbarn;
    public boolean revealed = false;

    Tile(boolean istMine) {
        super();
        this.istMine = istMine;
    }

    public void reveal() {
        revealed = true;
        if (istMine) setText("M");
        else setText(String.valueOf(nachbarn));
    }
}
