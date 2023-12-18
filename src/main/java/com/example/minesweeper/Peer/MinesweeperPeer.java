package com.example.minesweeper.Peer;
import com.example.minesweeper.GameMode;
import com.example.minesweeper.MainController;
import com.example.minesweeper.Tile;
import com.example.minesweeper.game.NormalGame;

import java.net.*;
import java.io.*;

public class MinesweeperPeer {
    private String Address;
    private int Port;
    public Socket s = null;
    public GameMode mode;

    public MinesweeperPeer(String ip, int port, GameMode m){
        this.Address = ip;
        this.Port = port;
        this.mode = m;
        try {
            s = new Socket(ip, port);
            System.out.println("here:" + s.isConnected());
            if(mode == GameMode.vs){
                NormalGame g = new NormalGame(10,10,20);
                System.out.println("Bevor");
                g.field = reveiveBoard();
                System.out.println("after");
                MainController.OpenNewGame(g);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void StartPeer(){
        try(
                Socket socket = new Socket(Address, Port);
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ){
            SendMessage(outputStream, "Test");
            String receivedMessage = (String) receiveMessage(inputStream);
            System.out.println(receivedMessage);
        }catch (IOException e){}
    }

    private void SendMessage(ObjectOutputStream out, Object message) throws IOException{
        out.writeObject(message);
        out.flush();
    }

    public void sendBoard(Tile[][] field){
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(s.getOutputStream());
            outputStream.writeObject(field);
            outputStream.flush();
        }catch (IOException e){}
    }

    public Tile[][] reveiveBoard(){
        try{
            ObjectInputStream inputStream = new ObjectInputStream(s.getInputStream());
            Tile[][] a = (Tile[][]) inputStream.readObject();
            return a;
        }catch (IOException e){} catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private Object receiveMessage(ObjectInputStream in) throws IOException {
        try {
            return in.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
