package com.example.minesweeper;

import com.example.minesweeper.util.Array;
import com.example.minesweeper.util.Random;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.Arrays;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Game extends GridPane {
    Tile[][] field;
    int width;
    int height;

    boolean GameOver = false;

    /**
     * @throws IllegalArgumentException width <= 0 || height <= 0 || width * height <= mine_amount
     */
    Game(int width, int height, int mine_amount) throws IllegalArgumentException {
        super();
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }
        field = new Tile[width][height];
        this.width = width;
        this.height = height;
        place_mines(mine_amount);
        calculate_tiles();
        /*
        setScaleX(2); //TODO: Settings
        setScaleY(2); //TODO: Settings
        */
    }

    private void place_mines(int mine_amount) throws IllegalArgumentException {
        var mines = Random.distinctSet(mine_amount, 0, width * height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                field[x][y] = new Tile(mines.contains(x * height + y));

                // JavaTM
                int finalX = x;
                int finalY = y;

                field[x][y].setOnMouseClicked(event -> {
                    if(event.getButton() == MouseButton.PRIMARY && !field[finalX][finalY].istMarkiert && !GameOver){
                        reveal(finalX, finalY);
                    } else if (event.getButton() == MouseButton.SECONDARY && !GameOver) {
                        field[finalX][finalY].Markiere();
                    }
                });
                add(field[x][y], x, y);
            }
        }
    }

    private void calculate_tiles() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (field[x][y].istMine) continue;
                field[x][y].nachbarn = (int) Arrays.stream(neighbours(x, y)).filter(tile -> tile.istMine).count();
            }
        }
    }

    private Tile[] neighbours(int x, int y) {
        return Array.neighbours(x, y, width - 1, height - 1).stream().map(xy -> field[xy._1][xy._2]).toArray(Tile[]::new);
    }

    private void reveal(int x, int y) {
        if(field[x][y].istMarkiert){field[x][y].Markiere();}
        if (field[x][y].revealed) return;
        field[x][y].reveal();
        if (field[x][y].istMine) {
            gameOver();
            return;
        }
        if (field[x][y].nachbarn == 0) {
            for (var xy : Array.neighbours(x, y, width - 1, height - 1)) {
                reveal(xy._1, xy._2);
            }
        }
    }

    private void gameOver() {
        // TODO End the game
        GameOver = true;
        try (InputStream inputStream = getClass().getResourceAsStream("Explosion.wav")) {
            playWav(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RevealAllMines();
    }

    private void RevealAllMines(){
        for (int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                field[x][y].revealIfMine();
            }
        }
    }

    private void playWav(InputStream inputStream) {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
             AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
