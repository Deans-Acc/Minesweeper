package com.example.minesweeper.Peer;

import com.example.minesweeper.GameMode;
import com.example.minesweeper.MainController;
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
                    System.out.println("Guest1: "+guest.isConnected());
                    switch (mode){
                        case vs -> handleForVS(guest);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void handleForVS(Socket s){
       try{
           NormalGame g = new NormalGame(10,10,20);
           sendObjectToClient(s,g.field);
           MainController.OpenNewGame(g);
           receivObjecteFromClient(s);
       }catch (IOException e){ e.printStackTrace();} catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
       }
    }
    private static void sendToClient(Socket clientSocket, String message) throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        outputStream.write(message.getBytes());
        outputStream.flush();
    }
    private static void sendObjectToClient(Socket clientSocket, Object data) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream())) {
            objectOutputStream.writeObject(data);
            objectOutputStream.flush();
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
