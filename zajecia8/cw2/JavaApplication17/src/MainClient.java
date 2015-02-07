/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author frasiek
 */
public class MainClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException, IOException {
        if(args.length < 2){
            System.out.println("Podaj 2 parametry adres i port");
            System.exit(0);
        }
        
        DatagramSocket ds = new DatagramSocket(Integer.parseInt(args[1])+1);
        InetAddress address = InetAddress.getByName(args[0]);
        
        byte[] buf = new byte[1024];
        while(true){
            System.in.read(buf);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, Integer.parseInt(args[1]));
            ds.send(packet);
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            ds.receive(dp);
            String r = new String(dp.getData(), 0, dp.getLength());
            System.out.println(r);
        }
    }

}
