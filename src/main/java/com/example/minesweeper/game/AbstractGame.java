package com.example.minesweeper.game;

import com.example.minesweeper.Tile;
import com.example.minesweeper.TileState;
import com.example.minesweeper.util.Array;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;

import java.io.IOException;

public abstract class AbstractGame {
    protected Tile[][] field;
    public final int width;
    public final int height;
    public final int mine_amount;
    public GameController controller;
    public boolean gameOver = false;

    /**
     * @throws IllegalArgumentException width <= 0 || height <= 0 || width * height <= mine_amount
     */
    AbstractGame(int width, int height, int mine_amount) throws IllegalArgumentException {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }
        if (mine_amount >= width * height) {
            throw new IllegalArgumentException();
        }
        this.width = width;
        this.height = height;
        this.mine_amount = mine_amount;

        var fxml = new FXMLLoader(getClass().getResource("game_view.fxml"));
        try {
            fxml.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        controller = fxml.getController();

        field = new Tile[width][height];
        initField();
    }

    abstract protected void preRevealTile(int x, int y);


    protected EventHandler<? super MouseEvent> tileOnClick(int x, int y) {
        return event -> {
            if (gameOver) return;
            if (event.getButton() == MouseButton.PRIMARY && field[x][y].state == TileState.Unknown) {
                reveal(x, y);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                field[x][y].mark();
            }
        };
    }

    protected void initField() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                field[x][y] = new Tile();
                field[x][y].setOnMouseClicked(tileOnClick(x, y));
                controller.grid.add(field[x][y], x, y);
            }
        }
    }

    protected Tile[] neighbours(int x, int y) {
        return Array.neighbours(x, y, width - 1, height - 1).stream().map(xy -> field[xy._1][xy._2]).toArray(Tile[]::new);
    }

    protected void reveal(int x, int y) {
        if (field[x][y].state == TileState.Revealed) return;
        preRevealTile(x, y);
        field[x][y].reveal();
        if (gameOver) return;
        if (field[x][y].istMine.get()) {
            gameOver();
            return;
        }
        if (field[x][y].nachbarn.get() == 0) {
            for (var xy : Array.neighbours(x, y, width - 1, height - 1)) {
                reveal(xy._1, xy._2);
            }
        }
    }

    protected void gameOver() {
        gameOver = true;
        new AudioClip(this.getClass().getResource("Explosion.wav").toExternalForm()).play();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                reveal(x, y);
            }
        }
    }
}
