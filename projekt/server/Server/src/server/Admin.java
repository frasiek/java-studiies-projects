/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author frasiek
 */
public class Admin extends Thread {

    protected static Integer listenClientPort = 8081;
    protected Clients clients;
    
    public Admin(Clients clientsObj){
        this.clients = clientsObj;
    }
    
    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(Admin.listenClientPort);
            while(true){
                AdminWorker sw = new AdminWorker(this, this.clients, server.accept());
                sw.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
