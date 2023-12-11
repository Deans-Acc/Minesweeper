package com.example.minesweeper.game;

import com.example.minesweeper.util.Random;
import io.vavr.control.Option;

import java.util.Arrays;

public class NormalGame extends AbstractGame {


    public NormalGame(int width, int height, int mine_amount) throws IllegalArgumentException {
        super(width, height, mine_amount);
        place_mines(mine_amount);
        calculate_tiles();
    }

    @Override
    protected void preRevealTile(int x, int y) {
    }

    private void place_mines(int mine_amount) throws IllegalArgumentException {
        var mines = Random.distinctSet(mine_amount, 0, width * height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                field[x][y].istMine = Option.of(mines.contains(x * height + y));
            }
        }
    }

    private void calculate_tiles() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (field[x][y].istMine.get()) continue;
                field[x][y].nachbarn = Option.of((int) Arrays.stream(neighbours(x, y)).filter(tile -> tile.istMine.get()).count());
            }
        }
    }
}
