/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer;

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
public class SerwerWorker extends Thread {

    Socket in = null;
    BufferedReader r = null;
    BufferedWriter w = null;

    public SerwerWorker(Socket in) throws IOException {
        this.in = in; //przypisanie socketu do obiektu workera
        this.r = new BufferedReader(new InputStreamReader(this.in.getInputStream(), "utf-8")); //otwarcie strumienia do odczytu
        this.w = new BufferedWriter(new OutputStreamWriter(this.in.getOutputStream())); //otwarcie strumienia do zapisu
    }

    @Override
    public void run() {
        Process p; //utworzenie nowego procesu
        BufferedReader b = null; //czytacz dla procesu
        try {
            String cmd = this.r.readLine(); //pobranie od uzytkownika co chce uruchomic
            System.out.println(cmd); //wypisanie na serwerze co przyszlo
            p = Runtime.getRuntime().exec(cmd); //uruchomienie polecenia
            p.waitFor(); //poczekanie na odpowiedz

            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(p.getInputStream())); //stworzenie buforowanego czytacza z procesu

            String line = "";
            while ((line = reader.readLine()) != null) { //czytanie wyjscia procesu
                System.out.println(line); //wypisanie na serwerze wyniku polecenia
                this.w.write(line); //wyslanie do uzytkownika wyniku polecenia
                this.w.flush(); //wytczyszczenie buffora i przeslanie do uzytkownika
            }
            
        } catch (IOException ex) { //lapanie wyjatkow
            System.out.println(ex);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        } finally {
            try {
                this.r.close(); //zamkniecie czytacza
            } catch (IOException ex) {
                Logger.getLogger(SerwerWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                this.w.close(); //zamkniecie pisacza
            } catch (IOException ex) {
                Logger.getLogger(SerwerWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                this.in.close(); //zamkniecie socketu
            } catch (IOException ex) {
                Logger.getLogger(SerwerWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
