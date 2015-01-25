/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author frasiek
 */
public class Client {
    
    private Socket socket = null;
    private BufferedReader reader;
    private BufferedWriter writer;
    private final static String host = "localhost";
    private final static String hostLocal = "localhost";
    private final static Integer port = 8080;
    private final static Integer portLocal = 8082;
    
    
    public Client(){
        try {
            InetAddress addr = InetAddress.getByName(Client.host);
            InetAddress addrLocal = InetAddress.getByName(Client.hostLocal);
            
            this.socket = new Socket(addr, Client.port, addrLocal, Client.portLocal);
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), "utf-8"));
            this.writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
