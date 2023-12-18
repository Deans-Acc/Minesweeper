package com.example.minesweeper.Peer;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.net.InetAddress;

public class PortScanner {
    private static final int TARGET_PORT = 1222;
    private static final int TIMEOUT = 100;

    public static List<String> findIPsWaitingOnPort(String subnet, int startHost, int endHost) {
        List<String> activeIPs = new ArrayList<>();

        if (startHost > endHost) {
            System.out.println("Ungültiger Host-Bereich.");
            return activeIPs;
        }

        for (int host = startHost; host <= endHost; host++) {
            String ipAddress = subnet + "." + host;
            if (isPortActive(ipAddress, TARGET_PORT)) {
                activeIPs.add(ipAddress);
            }
        }
        return activeIPs;
    }
    private static <InetAddress> boolean isPortActive(String host, int port) {
        try {
            Socket socket = new Socket();
            socket.connect(new java.net.InetSocketAddress(host, port), TIMEOUT);
            socket.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
