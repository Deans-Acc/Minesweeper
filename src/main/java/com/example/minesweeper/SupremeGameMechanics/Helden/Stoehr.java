package com.example.minesweeper.SupremeGameMechanics.Helden;

import com.example.minesweeper.SupremeGameMechanics.Held;
import com.example.minesweeper.util.Random;

public class Stoehr extends Held {

    public Stoehr(){
        super("Stöhr");
    }
    @Override
    public void faehigkeit() {
        //TODO: Notengebung -> Füge 1-6 Minen dem gegner im Nächsten Spiel hinzu
        var a = Random.distinct(1,1,6);
        int addAmount = a[0];
    }
}
