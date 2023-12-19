package com.example.minesweeper.Peer;

import com.example.minesweeper.MainController;
import com.example.minesweeper.Tile;
import com.example.minesweeper.game.NormalGame;

import java.io.*;
import java.net.Socket;

public class Client extends Thread{

    public Socket socket;
    public GameMode mode;
    public boolean isRunning = true;
    public boolean gestarted = false;
    public Client(String ip, int port, GameMode m){
        this.mode = m;
        try {
            socket = new Socket(ip, port);
        }catch (IOException e){e.printStackTrace();}
        this.start();
    }

    @Override
    public void run() {
        while (isRunning){
            if(!gestarted){
                switch (mode){
                    case vs -> handleVS();
                }
            }
        }
    }

    public void handleVS(){
        try{
            NormalGame g = new NormalGame(10,10,20);
            g.field = (Tile[][]) receiveObjectFromServer(socket);
            MainController.OpenNewGame(g);
            gestarted = true;
            synchronized(socket){
                socket.wait();
            }
            receiveObjectFromServer(socket);
        }catch (ClassNotFoundException e){e.printStackTrace();} catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void sendToServer(Socket socket, String message) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(message.getBytes());
        System.out.println("Daten an Server gesendet: " + message);
    }
    private static void sendToServer(Socket socket, Object data) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            objectOutputStream.writeObject(data);
        }
    }
    private static void receiveFromServer(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            String receivedData = new String(buffer, 0, bytesRead);
            System.out.println("Empfangene Daten vom Server: " + receivedData);
        }
    }
    private static Object receiveObjectFromServer(Socket socket) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
            return objectInputStream.readObject();
        }
    }
}
