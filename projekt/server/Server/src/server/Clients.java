/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author frasiek
 */
public class Clients extends Thread {

    protected static Integer listenClientPort = 8080;
    protected static ArrayList<ClientsWorker> clients = new ArrayList<ClientsWorker>();

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(Clients.listenClientPort);
            while (true) {
                ClientsWorker sw = new ClientsWorker(server.accept());
                Clients.clients.add(sw);
                sw.start();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String getClientIds() {
        if (Clients.clients.isEmpty()) {
            return null;
        }
        String l = "";
        for (Integer i = 0; i < Clients.clients.size(); i++) {
            //pingowanie czy wszysscy odpowiadaja
            try {
                String response = this.passCommand("ping", i);
                if (!response.equals("pong")) {
                    Clients.clients.get(i).close();
                    Clients.clients.remove((int) i);
                    i--;
                    return getClientIds();
                }
            } catch (Exception ex) {
                Clients.clients.get(i).close();
                Clients.clients.remove((int) i);
                i--;
                return getClientIds();
            }

            l += i.toString() + ", ";
        }
        return l.substring(0, l.length() - 2);
    }

    public ArrayList<Integer> getClientIdsArr() {
        ArrayList<Integer> l = new ArrayList<Integer>();
        if (!Clients.clients.isEmpty()) {
            for (Integer i = 0; i < Clients.clients.size(); i++) {
                l.add(i);
            }
        }

        return l;
    }

    public String passCommand(String command, Integer clientId) {
        ClientsWorker client = Clients.clients.get(clientId);

        return client.command(command);
    }

}
