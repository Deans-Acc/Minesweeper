package com.example.minesweeper;

import com.example.minesweeper.Peer.Client;
import com.example.minesweeper.Peer.Server;
import com.example.minesweeper.game.NormalGame;

public class GameManager {

    public int wight = 10;
    public int height = 10;
    public int MineAmount = 20;

    public Server server;
    public Client client;

    public NormalGame game;

    public NormalGame GetNormalGame(){
        game = new NormalGame(wight, height, MineAmount);
        return game;
    }
}
