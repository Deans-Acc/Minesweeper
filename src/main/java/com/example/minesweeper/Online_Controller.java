package com.example.minesweeper;

import com.example.minesweeper.Peer.MinesweeperPeer;
import com.example.minesweeper.Peer.NetworkThread;
import com.example.minesweeper.Peer.PortScanner;
import com.example.minesweeper.util.Random;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Online_Controller {
    public VBox main;
    public Label HostLabel;
    public TextField iptextfield;
    public Label status;

    public void HostAVS(ActionEvent event) {
        try{
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println("Your IP " +localHost.getHostAddress());
            NetworkThread n = new NetworkThread(1222,GameMode.vs);
            n.start();
        }catch (UnknownHostException e){} catch (IOException e) {}
        //TODO: Run background Join reques
    }

    public void JoinAVS(ActionEvent event) {
        if(iptextfield.getText().isEmpty()){
            //TODO: Error keine IP adreesse eingegeben
        }else {
            //TODO: Here Join game
            status.setText("Match Macking");
            //var a = PortScanner.findIPsWaitingOnPort("192.168.178",1,255);
            if(/*a.isEmpty()*/false){
                status.setText("No Game found");
            }else{
                //TODO: Schauen wer verfügbar ist, eventuell auch den Spieler entscheiden lassen
                MinesweeperPeer m = new MinesweeperPeer("localhost", 1222, GameMode.vs);
            }
            //MinesweeperPeer m = new MinesweeperPeer("localhost",1222);
        }
    }
}
