package com.example.minesweeper.Peer;
import java.net.*;
import java.io.*;

public class MinesweeperPeer {
    private String Address;
    private int Port;

    public MinesweeperPeer(String ip, int port){
        this.Address = ip;
        this.Port = port;
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

    private Object receiveMessage(ObjectInputStream in) throws IOException {
        try {
            return in.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
