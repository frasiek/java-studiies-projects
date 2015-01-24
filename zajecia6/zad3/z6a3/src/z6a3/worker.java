/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package z6a3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.security.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author frasiek
 */
public class worker extends Thread {

    protected Socket s;
    protected Long ts;

    public worker(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        System.out.print("Connection accepted");
        try (
                BufferedReader r = new BufferedReader(new InputStreamReader(this.s.getInputStream(), "utf-8"));
                BufferedWriter w = new BufferedWriter(new OutputStreamWriter(this.s.getOutputStream()));) {
            String line;
            while ((line = r.readLine()) != null) {
                System.out.println("Line red " + line);
                line = line.toLowerCase().trim();
                if (line.startsWith("czesc")) {
                    w.write("Welcome to " + InetAddress.getLocalHost().getHostName());
                    w.flush();
                    Date date = new Date();
                    this.ts = date.getTime();
                }
                if (line.startsWith("zegnaj")) {
                    Date date = new Date();
                    w.write("Bye after: " + ((date.getTime() - this.ts)/1000));
                    w.flush();
                }
                if (line.startsWith("czas")) {
                    Date d = new Date();
                    w.write("Current time: " + d.toString());
                    w.flush();
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        } finally {
            System.out.print("Connection closed");
            try {
                this.s.close();
            } catch (IOException ex) {
                Logger.getLogger(worker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
