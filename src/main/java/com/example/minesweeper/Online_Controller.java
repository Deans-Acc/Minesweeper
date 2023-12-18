package com.example.minesweeper;

import com.example.minesweeper.Peer.Client;
import com.example.minesweeper.Peer.Server;
import javafx.event.ActionEvent;
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
        //TODO: Run background Join reques
        Server server = new Server(GameMode.vs);
    }

    public void JoinAVS(ActionEvent event) {
        //TODO: Join
        Client client = new Client("127.0.0.1",1222,GameMode.vs);
    }
}
