/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication15;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author frasiek
 */
public class MainServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException, IOException {
        DatagramSocket ds = new DatagramSocket(8080);
        byte[] buf = new byte[1024];
        DatagramPacket dp = new DatagramPacket(buf, 1024);
        
        try {
            while (true) {
                ds.receive(dp);
                String r = new String(dp.getData(), 0, dp.getLength());
                System.out.println(r);
            }
        } finally {
            ds.close();
        }
    }

}
