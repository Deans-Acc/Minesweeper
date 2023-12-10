package com.example.minesweeper.util;

import java.util.Arrays;
import java.util.HashSet;

public class Random {
    /**
     * @param n     Amount of numbers
     * @param lower Lower bound (inclusive)
     * @param upper Upper bound (exclusive)
     * @return n distinct random numbers in [lower, upper)
     * @throws IllegalArgumentException (upper - lower) < n
     */
    public static int[] distinct(int n, int lower, int upper) throws IllegalArgumentException {
        var rng = new java.util.Random();
        Integer[] out = new Integer[n];
        for (int i = 0; i < n; i++) {
            int rand = rng.nextInt(0, upper - lower - i);
            int pos = 0;
            int x = lower;
            while (pos <= rand) {
                int finalX = x;
                if (Arrays.stream(out).noneMatch(a -> a == finalX)) {
                    pos++;
                }
                x++;
            }
            out[i] = x - 1;
        }
        return Array.convert(out);
    }

    /**
     * See {@link #distinct(int, int, int)}
     */

    public static HashSet<Integer> distinctSet(int n, int lower, int upper) throws IllegalArgumentException {
        var rng = new java.util.Random();
        HashSet<Integer> out = new HashSet<>();
        for (int i = 0; i < n; i++) {
            int rand = rng.nextInt(0, upper - lower - i);
            int pos = 0;
            int x = lower;
            while (pos <= rand) {
                if (!out.contains(x)) {
                    pos++;
                }
                x++;
            }
            if ((!out.add(x - 1))) throw new AssertionError();
        }
        assert out.size() == n;
        return out;
    }
}
