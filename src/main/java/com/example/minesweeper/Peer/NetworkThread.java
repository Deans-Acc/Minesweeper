package com.example.minesweeper.Peer;

import com.example.minesweeper.GameMode;
import com.example.minesweeper.MainController;
import com.example.minesweeper.Tile;
import com.example.minesweeper.game.NormalGame;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkThread extends Thread{
    private ServerSocket serverSocket;
    public GameMode mode;

    public NetworkThread(int port, GameMode m) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.mode = m;
    }

    @Override
    public void run(){
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Conneced");

                try{
                    System.out.println("Start 1");
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    System.out.println("Done");

                    NormalGame g = new NormalGame(10,10,20);
                    System.out.println("Starte Senden");
                    SendMessage(outputStream, g.field);
                    System.out.println(" gesendet");
                    MainController.OpenNewGame(g);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleConnection(Socket s){
        try{
            s.getOutputStream().flush();
            System.out.println("Start 1");
            ObjectOutputStream outputStream = new ObjectOutputStream(s.getOutputStream());
            System.out.println("Start 2");
            ObjectInputStream inputStream = new ObjectInputStream(s.getInputStream());
            System.out.println("Done");

            NormalGame g = new NormalGame(10,10,20);
            System.out.println("Starte Senden");
            SendMessage(outputStream, g.field);
            System.out.println(" gesendet");
            MainController.OpenNewGame(g);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void SendMessage(ObjectOutputStream out, Object message) throws IOException{
        out.writeObject(message);
        out.flush();
    }
}

/*
Vs Modus:
So schnell wie möglich lösen

Co-op
Probiere das Rätels gemaisam zu lösen

Core vs:
Definere Punkte
Andere bekommen minen fals der andere Punkte bekommt
Felder werden immer größer -> Setzte einen caps für maximal größe

Battelpass:
Neuere Texturen
Skins
Mehr sounds
Items
Charakters -> bestimmte fähigkeiten

Charaktere:
Trump -> Baut eine Mauer über welche du nich schauen kannst sondern drum rum gehen musst
Stöhr -> Notengebung -> Fügt 1 bis 6 Minen hinzu
Obama -> Dohnen Aufklärung -> 1 Mine deaktiveren
Dean(Deanosauros) -> Rosten -> Der Gegner wird die Ganze zeit verbal gerostet
Nike -> verwirdheit -> für 5 sekunden ist der Input gegners auf random Butten der Tastatur gesetzt

skilltree

Prestige Modus

Lootboxen
Normal / Rare / Legendary

Items:
AddMines -> Fügt im vs oder Core mehr Minen
Blind -> gegner wird für 3 sekunden blind
RemoveMine -> Reveal ein oder mehrere Minen
Reverser -> Dreht das Spielfeld des Gegners
 */