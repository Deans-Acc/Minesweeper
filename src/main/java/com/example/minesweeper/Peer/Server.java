package com.example.minesweeper.Peer;

import com.example.minesweeper.MainController;
import com.example.minesweeper.Minesweeper;
import com.example.minesweeper.Tile;
import com.example.minesweeper.game.NormalGame;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    int Port = 1222;
    public boolean isRunning = true;
    public GameMode mode;
    public ServerSocket socket;
    public Socket guest;
    public Server(GameMode m){
        this.mode = m;
        try{
            socket = new ServerSocket(Port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.start();
    }

    @Override
    public void run() {
        while (isRunning){
            try {
                if(guest == null){
                    guest = socket.accept();
                    switch (mode){
                        case vs -> handleForVS(guest);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public  void handleForCoop(Socket s){
        //TODO: Coop
        try{
            NormalGame game = Minesweeper.manager.GetNormalGame();
            sendObjectToClient(s,game.field);
            MainController.OpenNewGame(game);
            Minesweeper.manager.game.field = (Tile[][])receivObjecteFromClient(s);
        }catch (IOException e){}catch (ClassNotFoundException e){}
    }

    public void handleForVS(Socket s){
       try{
           NormalGame g = new NormalGame(10,10,20);
           sendObjectToClient(s,g.field);
           MainController.OpenNewGame(g);
           synchronized(s){
                s.wait();
           }
           receivObjecteFromClient(s);
       }catch (IOException e){ e.printStackTrace();} catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }
    }
    private static void sendToClient(Socket clientSocket, String message) throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        outputStream.write(message.getBytes());
    }
    private static void sendObjectToClient(Socket clientSocket, Object data) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream())) {
            objectOutputStream.writeObject(data);
        }
    }
    private static void receiveFromClient(Socket clientSocket) throws IOException {
        InputStream inputStream = clientSocket.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            String receivedData = new String(buffer, 0, bytesRead);
            System.out.println("Empfangene Daten: " + receivedData);
        }
    }
    private static Object receivObjecteFromClient(Socket clientSocket) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream())) {
            return objectInputStream.readObject();
        }
    }
}
