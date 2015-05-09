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
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author frasiek
 */
public class Email {

    String from;
    String to;
    String message;

    Socket smtp = null;
    BufferedReader in = null;
    BufferedWriter out = null;

    String line = "";

    public Email() {
        try {
            smtp = new Socket("localhost", 25);
            smtp.setSoTimeout(2000);
            in = new BufferedReader(new InputStreamReader(smtp.getInputStream(), "utf-8"));
            out = new BufferedWriter(new OutputStreamWriter(smtp.getOutputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected String hello() {
        try {
            out.write("HELO srv\r\n");
            out.flush();
            String line = null;
            line = in.readLine();
            if (!line.startsWith("250")) {
                System.out.println("Server error: " + line);
                return line;
            }
        } catch (IOException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "250 OK";
    }

    public String getFrom() {
        return from;
    }

    public String setFrom(String from) {
        try {
            this.from = from;

            out.write("MAIL FROM:" + this.from + "\r\n");
            out.flush();

            line = in.readLine();
            return line;
        } catch (IOException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "500 Error";
    }

    public String getTo() {
        return to;
    }

    public String setTo(String to) {
        try {
            this.to = to;
            out.write("RCPT TO:" + this.to + "\r\n");
            out.flush();

            line = in.readLine();
            return line;
        } catch (IOException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "500 Error";
    }

    public String getMessage() {
        return message;
    }

    public String setMessage(String message) {
        try {
            this.message = message;
            System.out.println("Data started");
            out.write(this.message + "\r\n");
            out.write(".\r\n");
            out.flush();

            line = in.readLine();
            closeConnections();
            return line;
        } catch (IOException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "500 Error";
    }
    
    protected void closeConnections(){
        try {
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            smtp.close();
        } catch (IOException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
