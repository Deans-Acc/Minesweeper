package com.example.minesweeper.game;


import com.example.minesweeper.util.Array;
import com.microsoft.z3.*;
import io.vavr.control.Option;

public class FairGame extends AbstractGame {
    Context ctx = new Context();

    FairGame(int width, int height, int mine_amount) throws IllegalArgumentException {
        super(width, height, mine_amount);
    }

    @Override
    protected void preRevealTile(int x, int y) {
        if (field[x][y].istMine.isDefined()) return;
        // TODO
        Solver s = ctx.mkSolver();

        Expr<BoolSort>[] vars = new Expr[x * y];

        int[] coeffs = new int[x * y];
        for (int nx = 0; nx < width; nx++) {
            for (int ny = 0; ny < height; ny++) {
                int n = nx * height + ny;
                vars[n] = ctx.mkConst(String.valueOf(n), ctx.mkBoolSort());
                var tile = field[nx][ny];
                if (tile.istMine.isDefined()) {
                    s.add(ctx.mkEq(vars[n], ctx.mkBool(tile.istMine.get())));
                }
                if (tile.nachbarn.isDefined()) {
                    int[] c = new int[8];
                    for (int i = 0; i < 8; i++) {
                        c[i] = 1;
                    }
                    Expr<BoolSort>[] neighbours = Array.neighbours(nx, ny, width - 1, height - 1).stream().map(a -> vars[a._1 * height + a._2]).toArray(Expr[]::new);
                    s.add(ctx.mkPBEq(c, neighbours, tile.nachbarn.get()));
                }
                coeffs[n] = 1;
            }
        }
        s.add(ctx.mkPBEq(coeffs, vars, mine_amount));

        if (s.check(ctx.mkEq(vars[1], ctx.mkBool(false))) == Status.UNSATISFIABLE) {
            field[x][y].istMine = Option.of(true);
            return;
        }
        if (s.check(ctx.mkEq(vars[1], ctx.mkBool(true))) == Status.UNSATISFIABLE) {
            field[x][y].istMine = Option.of(false);
            //field[x][y].nachbarn = Option.of(0);
            assert s.check(ctx.mkEq(vars[1], ctx.mkBool(false))) == Status.SATISFIABLE;

            s.getModel().eval(vars[0], false).getBoolValue(); // for all neighbors
            
            return;
        }
        // TODO: Check if safe field exists
    }
}
