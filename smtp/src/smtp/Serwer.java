/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smtp;

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
public class Serwer extends Thread {

    Socket in = null;
    BufferedReader r = null;
    BufferedWriter w = null;

    public Serwer(Socket in) throws IOException {
        this.in = in;
        this.setup();
    }

    @Override
    public void run() {
        try {
            w.write("220 Connected\r\n");
            w.flush();
            Boolean helloed = false;
            Email email = new Email();
            while (true) {
                String message = null;
                message = this.r.readLine(); //czytamy to co uzytkownik nam podal
                if(message == null){
                    continue;
                }
                if(message.toUpperCase().contains("HELO")){
                    this.w.write(email.hello());
                    this.w.flush();
                    helloed = true;
                    continue;
                }
                
                if(message.toUpperCase().startsWith("RCPT TO:")){
                    this.w.write(email.setTo(message.toUpperCase().replace("RCPT TO:", "")));
                    this.w.flush();
                    continue;
                }
                
                if(message.toUpperCase().startsWith("MAIL FROM:")){
                    this.w.write(email.setFrom(message.toUpperCase().replace("MAIL FROM:", "")));
                    this.w.flush();
                    continue;
                }
                
                if(message.toUpperCase().startsWith("DATA")){
                    String data = "";
                    String chunk = this.r.readLine();
                    while(!chunk.trim().equals(".")){
                        data += chunk+"\r\n";
                        chunk = this.r.readLine();
                    }
                    this.w.write(email.setMessage(data));
                    this.w.flush();
                    break;
                }
                
            }
        } catch (IOException ex) {
            Logger.getLogger(Serwer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.tearDown();
            } catch (IOException ex) {
                Logger.getLogger(Serwer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * ustawi strumienie do pisania i czytania z socketu
     */
    protected void setup() throws IOException {
        this.r = new BufferedReader(new InputStreamReader(this.in.getInputStream(), "utf-8"));
        this.w = new BufferedWriter(new OutputStreamWriter(this.in.getOutputStream()));
    }

    /**
     * zamkniecie strumieni i socketu
     */
    protected void tearDown() throws IOException {
        this.r.close();
        this.w.close();
        this.in.close();
    }

}
