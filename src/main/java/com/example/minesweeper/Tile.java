package com.example.minesweeper;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile extends ImageView {
    public boolean istMine;
    public boolean istMarkiert;
    public int nachbarn;
    public boolean revealed = false;

    Image image = new Image(getClass().getResource("None.png").toExternalForm());

    Tile(boolean istMine) {
        super();
        this.istMine = istMine;
        setImage(image);
    }

    public void reveal() {
        revealed = true;
        if (istMine) {
            setImage(new Image(getClass().getResource("Fuck.png").toExternalForm()));
        }
        else {
            setImage(new Image(getClass().getResource(String.valueOf(nachbarn)+".png").toExternalForm()));
        }
    }

    public void revealIfMine(){
        if(istMine && !revealed){
            setImage(new Image(getClass().getResource("Mine.png").toExternalForm()));
        }
    }

    public void Markiere(){
        if(revealed) return;
        istMarkiert = !istMarkiert;
        if(istMarkiert){
            setImage(new Image(getClass().getResource("Flagg.png").toExternalForm()));
        }else {
            setImage(image);
        }
    }
}

