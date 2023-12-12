package com.example.minesweeper.game;


import com.example.minesweeper.TileState;
import com.example.minesweeper.util.Array;
import com.microsoft.z3.*;
import com.microsoft.z3.enumerations.Z3_lbool;
import io.vavr.control.Option;

import java.util.Arrays;

public class FairGame extends AbstractGame {
    Context ctx = new Context();

    public FairGame(int width, int height, int mine_amount) throws IllegalArgumentException {
        super(width, height, mine_amount);
    }

    private void mkBase(Solver s, Expr<BoolSort>[] vars) {
        for (int i = 0; i < width * height; i++) {
            vars[i] = ctx.mkConst(String.valueOf(i), ctx.mkBoolSort());
        }
        int[] coeffs = new int[width * height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int n = x * height + y;
                var tile = field[x][y];
                if (tile.istMine.isDefined()) {
                    s.add(ctx.mkEq(vars[n], ctx.mkBool(tile.istMine.get())));
                }
                if (tile.nachbarn.isDefined()) {
                    var m = Array.neighbours(x, y, width - 1, height - 1);
                    Expr<BoolSort>[] neighbours = new Expr[m.size()];
                    for (int i = 0; i < m.size(); i++) {
                        var a = m.get(i);
                        neighbours[i] = vars[a._1 * height + a._2];
                    }
                    int[] c = new int[neighbours.length];
                    for (int i = 0; i < neighbours.length; i++) {
                        c[i] = 1;
                    }
                    s.add(ctx.mkPBEq(c, neighbours, tile.nachbarn.get()));
                }
                coeffs[n] = 1;
            }
        }
        s.add(ctx.mkPBEq(coeffs, vars, mine_amount));
    }

    private boolean safeExists(Solver s, Expr<BoolSort>[] vars) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pos = x * height + y;
                if (field[x][y].state == TileState.Revealed) continue;
                if (s.check(ctx.mkEq(vars[pos], ctx.mkBool(true))) == Status.UNSATISFIABLE) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void setMine(int x, int y) {
        field[x][y].istMine = Option.of(true);
    }

    protected void setSafe(Solver s, Expr<BoolSort>[] vars, int x, int y) {
        int pos = x * height + y;
        field[x][y].istMine = Option.of(false);
        if (s.check(ctx.mkEq(vars[pos], ctx.mkBool(false))) != Status.SATISFIABLE) {
            throw new RuntimeException();
        }

        int n = (int) Array.neighbours(x, y, width - 1, height - 1).stream().
                map(tile -> s.getModel().eval(vars[tile._1 * height + tile._2], false).getBoolValue()).
                filter(val -> val == Z3_lbool.Z3_L_TRUE).
                count();
        field[x][y].nachbarn = Option.of(n);
    }

    @Override
    protected void preRevealTile(int x, int y) {
        if (field[x][y].istMine.isDefined()) return;

        // on first click set around 1/4 of mines randomly

        Solver s = ctx.mkSimpleSolver();
        Expr<BoolSort>[] vars = new Expr[width * height];

        mkBase(s, vars);

        int pos = x * height + y;

        if (s.check(ctx.mkEq(vars[pos], ctx.mkBool(false))) == Status.UNSATISFIABLE) {
            // Must be a mine
            setMine(x, y);
            return;
        }
        if (s.check(ctx.mkEq(vars[pos], ctx.mkBool(true))) == Status.UNSATISFIABLE) {
            // Can't be a mine
            setSafe(s, vars, x, y);
            return;
        }
        if (safeExists(s, vars)) {
            setMine(x, y);
            return;
        }
        setSafe(s, vars, x, y);
    }
}
