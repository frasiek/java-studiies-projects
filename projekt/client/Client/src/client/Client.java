/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.spi.DirectoryManager;

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

    public Client() {
        try {
            InetAddress addr = InetAddress.getByName(Client.host);
            InetAddress addrLocal = InetAddress.getByName(Client.hostLocal);

            this.socket = new Socket(addr, Client.port, addrLocal, Client.portLocal);
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), "utf-8"));
            this.writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            String line;
            while ((line = this.reader.readLine()) != null) {
                switch (line) {
                    case "ping":
                        this.pingMe();
                        continue;
                    case "info":
                        this.info();
                        continue;
                }
                if (line.startsWith("ls")) {
                    this.ls(line);
                    continue;
                }
                if (line.startsWith("cat")) {
                    this.cat(line);
                    continue;
                }

                this.write("Unknown command '" + line + "'");

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                this.socket.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    protected void pingMe() {
        this.write("pong");
    }

    private void info() {
        String info = "";
        info += "Available processors (cores): "
                + Runtime.getRuntime().availableProcessors() + "\r\n";

        info += "Free memory (bytes): "
                + Runtime.getRuntime().freeMemory() + "\r\n";

        long maxMemory = Runtime.getRuntime().maxMemory();
        info += "Maximum memory (bytes): "
                + (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory) + "\r\n";

        info += "Total memory available to JVM (bytes): "
                + Runtime.getRuntime().totalMemory() + "\r\n";

        /* Get a list of all filesystem roots on this system */
        File[] roots = File.listRoots();

        /* For each filesystem root, print some info */
        for (File root : roots) {
            info += "File system root: " + root.getAbsolutePath() + "\r\n";
            info += "Total space (bytes): " + root.getTotalSpace() + "\r\n";
            info += "Free space (bytes): " + root.getFreeSpace() + "\r\n";
            info += "Usable space (bytes): " + root.getUsableSpace();
        }
        this.write(info);
    }

    protected void ls(String line) {
        String startDir = line.replace("ls", "").trim();
        if (startDir.equals("")) {
            startDir = ".";
        }
        String response = "Listing directory " + startDir + "\r\n";
        File f = new File(startDir);
        File[] list = f.listFiles();
        if (list == null) {
            this.write("Empty dir.");
            return;
        }
        response = "";
        for (File file : list) {
            response += file.toString() + "\r\n";
        }
        this.write(response);
    }

    protected void cat(String line) {
        String filePath = line.replace("cat", "").trim();
        if (filePath.equals("")) {
            this.write("File name not provided");
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath), Charset.forName("UTF-8"));) {
            String part;
            String ret = "";
            while ((part = reader.readLine()) != null) {
                ret += part + "\r\n";
            }
            this.write(ret);
        } catch (Exception e) {
            this.write(e.getMessage());
        }
    }

    private void write(String message) {
        try {
            this.writer.write(message + "\r\n");
            this.writer.write("--end--\r\n");
            this.writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Client client = new Client();
    }

}
