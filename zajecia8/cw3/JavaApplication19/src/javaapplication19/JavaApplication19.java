/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication19;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;

/**
 *
 * @author frasiek
 */
public class JavaApplication19 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        MulticastSocket multicastSocket = new MulticastSocket(1234);
        multicastSocket.joinGroup(InetAddress.getByName("233.0.113.1"));
        
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        while(true){
            multicastSocket.receive(packet);
            String r = new String(packet.getData(), 0, packet.getLength());
            System.out.println(r);
        }
    }
    
}
