package com.example.minesweeper.util;

import io.vavr.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class Array {
    public static int[] convert(Integer[] in) {
        int[] out = new int[in.length];
        for (int i = 0; i < out.length; i++) {
            out[i] = in[i];
        }
        return out;
    }

    public static List<Tuple2<Integer, Integer>> neighbours(int x, int y, int max_x, int max_y) {
        var out = new ArrayList<Tuple2<Integer, Integer>>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (x + dx < 0 || y + dy < 0) continue;
                if (x + dx > max_x || y + dy > max_y) continue;
                if (dx == 0 && dy == 0) continue;
                out.add(new Tuple2<>(x + dx, y + dy));
            }
        }
        return out;
    }
}
