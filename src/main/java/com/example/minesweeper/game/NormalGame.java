package com.example.minesweeper.game;

import com.example.minesweeper.util.Random;
import io.vavr.control.Option;

import java.util.Arrays;

public class NormalGame extends AbstractGame {

    private boolean placedMines = false;

    public NormalGame(int width, int height, int mine_amount) throws IllegalArgumentException {
        super(width, height, mine_amount);
    }

    @Override
    protected void preRevealTile(int x, int y) {
        if (!placedMines) {
            placedMines = true;
            place_mines(x * height + y, mine_amount);
            calculate_tiles();
        }
    }

    private void place_mines(int xy, int mine_amount) throws IllegalArgumentException {
        var mines = Random.distinctSet(mine_amount, 0, width * height - 1);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                boolean istMine;
                if (x * height + y < xy) {
                    istMine = mines.contains(x * height + y);
                } else if (x * height + y == xy) {
                    istMine = false;
                } else {
                    istMine = mines.contains(x * height + y - 1);
                }
                field[x][y].istMine = Option.of(istMine);
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
