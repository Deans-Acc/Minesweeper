package com.example.minesweeper;

import com.example.minesweeper.Peer.Client;
import com.example.minesweeper.Peer.GameMode;
import com.example.minesweeper.Peer.Server;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Online_Controller {
    public VBox main;
    public Label HostLabel;
    public TextField iptextfield;
    public Label status;

    public void HostAVS(ActionEvent event) {
        //TODO: Run background Join reques
        Server server = new Server(GameMode.vs);
        Minesweeper.manager.server = server;
    }

    public void JoinAVS(ActionEvent event) {
        //TODO: Join
        Client client = new Client(iptextfield.getText(),1222,GameMode.vs);
        Minesweeper.manager.client = client;

    }

    public void HostACoop(ActionEvent event) {
        Server server = new Server(GameMode.coop);
        Minesweeper.manager.server = server;
    }

    public void JoinACoop(ActionEvent event) {
        Client client = new Client(iptextfield.getText(),1222,GameMode.coop);
        Minesweeper.manager.client = client;
    }

    public void HostAHelden(ActionEvent event) {
        Server server = new Server(GameMode.hero);
        Minesweeper.manager.server = server;
    }

    public void JoinAHelden(ActionEvent event) {
        Client client = new Client(iptextfield.getText(),1222,GameMode.coop);
        Minesweeper.manager.client = client;
    }
}
