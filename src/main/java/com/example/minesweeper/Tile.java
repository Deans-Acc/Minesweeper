package com.example.minesweeper;

import io.vavr.control.Option;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile extends ImageView {
    public Option<Boolean> istMine = Option.none();
    public Option<Integer> nachbarn = Option.none();

    public TileState state = TileState.Unknown;

    private final Image blank = new Image(getClass().getResource("None.png").toExternalForm());

    public Tile() {
        super();
        setImage(blank);
    }

    public void reveal() {
        state = TileState.Revealed;
        if (istMine.get()) setImage(new Image(getClass().getResource("Mine.png").toExternalForm()));
        else setImage(new Image(getClass().getResource(nachbarn.get()+".png").toExternalForm()));
    }

    public void mark() {
        switch (state) {
            case Unknown -> {
                state = TileState.Marked;
                setImage(new Image(getClass().getResource("Flagg.png").toExternalForm()));
            }
            case Marked -> {
                state = TileState.Unknown;
                setImage(blank);
            }
            case Revealed -> {
            }
        }
    }
}
