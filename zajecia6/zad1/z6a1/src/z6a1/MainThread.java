/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package z6a1;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import static java.lang.Thread.sleep;

public class MainThread {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: java MainThread src_port");
            System.exit(0);
        }

        ServerSocket server = new ServerSocket(Integer.parseInt(args[0]));
        Socket srv = server.accept();
        System.out.print("Connection accepted");
        try (
                BufferedReader r = new BufferedReader(new InputStreamReader(srv.getInputStream(), "utf-8"));
                BufferedWriter w = new BufferedWriter(new OutputStreamWriter(srv.getOutputStream()));) {
            String line;
            while ((line = r.readLine()) != null) {
                System.out.println("Line red "+line);
                w.write(line+"\r\n");
                w.flush();
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        } finally {
            System.out.print("Connection closed");
            srv.close();
        }
    }
}
