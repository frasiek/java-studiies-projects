/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author frasiek
 */
public class ClientsWorker extends Thread {

    protected Socket soc;
    BufferedReader r = null;
    BufferedWriter w = null;
    
    public ClientsWorker(Socket s) {
        this.soc = s;
    }

    public void run() {
        try {
            r = new BufferedReader(new InputStreamReader(this.soc.getInputStream(), "utf-8"));
            w = new BufferedWriter(new OutputStreamWriter(this.soc.getOutputStream()));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    protected String command(String command) {
        String response = "";
        try {
            w.write(command+"\r\n");
            String line;
            while ((line = r.readLine()) != "--end--") {
                response += line;
            }
            return response;
        } catch (IOException ex) {
            Logger.getLogger(ClientsWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}