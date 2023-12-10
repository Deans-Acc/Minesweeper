package com.example.minesweeper;

import javafx.scene.control.Button;

public class Tile extends Button {
    public boolean istMine;
    public  boolean istMarkiert;
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

    public void Markiere(){
        istMarkiert = !istMarkiert;
        if(istMarkiert){
            //TODO: Fahnenen graphic
        /*
        ImageView image = new ImageView(getClass().getResource("resources/com/example/minesweeper/Fahne.png").toExternalForm());
        setGraphic(image);
        */
            setText("F");
        }else {
            setText("");
        }
    }
}
