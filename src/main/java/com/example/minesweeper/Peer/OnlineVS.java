package com.example.minesweeper.Peer;

import com.example.minesweeper.GameMode;
import com.example.minesweeper.game.AbstractGame;
import com.example.minesweeper.game.NormalGame;

import java.net.ServerSocket;

public class OnlineVS {

    public MinesweeperPeer otherPlayer;
    public boolean host;

    public OnlineVS(String otherplayeradress, int port, boolean ishost){
        this.host = ishost;
        this.otherPlayer = new MinesweeperPeer(otherplayeradress, port, GameMode.vs);
    }

    public void hostAGame(){
        NormalGame game = new NormalGame(10, 10, 15);
        otherPlayer.sendBoard(game.field);
    }

    public void ConnectAsGuest(String otherPlayerIP, int port){
        this.host = false;
        this.otherPlayer = new MinesweeperPeer(otherPlayerIP, port, GameMode.vs);

    }

}
