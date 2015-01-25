/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author frasiek
 */
public class AdminWorker extends Thread {

    protected Socket soc;
    protected Admin parent;
    protected Clients clients;
    protected Integer selectedClient = null;

    public AdminWorker(Admin a, Clients c, Socket s) {
        this.soc = s;
        this.parent = a;
        this.clients = c;
        System.out.println("Admin connected.");
    }

    @Override
    public void run() {
        try (
                BufferedReader r = new BufferedReader(new InputStreamReader(this.soc.getInputStream(), "utf-8"));
                BufferedWriter w = new BufferedWriter(new OutputStreamWriter(this.soc.getOutputStream()));) {
            String line;
            while ((line = r.readLine()) != null) {
                line = line.toLowerCase().trim();
                if (line.startsWith("show clients")) {
                    this.showClients(w);
                    continue;
                }
                if (line.startsWith("use")) {
                    this.useClient(w, r, line);
                    continue;
                }
                if (line.startsWith("quit")) {
                    break;
                }
                w.write("Unknown command: '" + line + "'\r\n");
                w.flush();
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            System.out.print("Admin connection closed\r\n");
            try {
                this.soc.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientsWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    protected void showClients(BufferedWriter w) {
        try {
            String ClientIds = this.clients.getClientIds();
            if (ClientIds == null) {
                w.write("No one is connected.\r\n");
                w.flush();

            } else {
                w.write("Connected clients: " + ClientIds + "\r\n");
                w.write("Select client via 'USE id' command\r\n");
                w.flush();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    protected void useClient(BufferedWriter w, BufferedReader r, String line) {
        this.selectedClient = Integer.parseInt(line.substring(4));
        ArrayList<Integer> allowed = this.clients.getClientIdsArr();
        try {
            allowed.contains(this.selectedClient);
            if (!allowed.contains(this.selectedClient)) {
                this.selectedClient = null;
                w.write("Selected client is not connected\r\n");
                w.flush();
            } else {
                w.write("Selected client: " + this.selectedClient.toString() + "\r\n");
                w.flush();
                this.clientIdle(w, r);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    protected void clientIdle(BufferedWriter w, BufferedReader r) {
        try {
            w.write("Client mode! You'r commands will be sent to client!\r\n");
            w.flush();

            String line;
            while ((line = r.readLine()) != null) {
                if (line.toLowerCase().trim().equals("quit")) {
                    w.write("Client mode stopped.\r\n");
                    w.flush();
                    break;
                }
                String response = this.clients.passCommand(line, this.selectedClient);
                response = this.trySavingFile(response);
                w.write(response);
                w.write("\r\n");
                w.flush();
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            try {
                w.write("Client error: " + ex.getMessage());
            } catch (IOException ex1) {
                System.out.println(ex.getMessage());
            }
        } catch (NullPointerException ex) {
            try {
                System.out.print(ex.toString());
                ex.printStackTrace();
                w.write("Client connection error.\r\n");
                w.write("Client mode stopped.");
                w.write("\r\n");
                w.flush();
            } catch (IOException ex1) {
                Logger.getLogger(AdminWorker.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    private String trySavingFile(String response) {
        if (response.contains("--begin file content--")) {
            try {
                String fileContent = response.substring(response.indexOf("--begin file content--\r\n") + 24, response.indexOf("\r\n--end file content--"));
                response = response.replace("--begin file content--\r\n" + fileContent + "\r\n--end file content--", "");
                byte[] decoded = Base64.decode(fileContent);
                Date d = new Date();

                File theDir = new File("downloads");

                // if the directory does not exist, create it
                if (!theDir.exists()) {
                    try {
                        theDir.mkdir();
                    } catch (SecurityException se) {
                    }
                }

                String filename = System.getProperty("user.dir")+"/downloads/" + System.currentTimeMillis();
                Path out = Paths.get(filename);

                Files.write(out, decoded);
                response += "\r\nFile downloaded: " + filename;
                System.out.println("File downloaded: " + filename);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        return response;
    }

}
