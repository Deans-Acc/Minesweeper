package com.example.minesweeper.Peer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkThread extends Thread{
    private ServerSocket serverSocket;

    public NetworkThread(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run(){
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                handleConnection(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleConnection(Socket s){
        try(
                InputStream inputStream = s.getInputStream();
                OutputStream outputStream = s.getOutputStream();
                ){

        }catch (IOException e){}
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