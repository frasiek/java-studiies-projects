/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smtp;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author frasiek
 */
public class Smtp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(24); //stworzenie nowego obiektu socket
        while(true){
            Serwer srv = new Serwer(server.accept()); //zaczynamy nasluchiwac polaczenia, jak tylko ktos sie podlaczy tworzymy nowy obiek obslugujacy zadanie
            srv.start(); //uruchomienie watki
        }
    }
    
}
