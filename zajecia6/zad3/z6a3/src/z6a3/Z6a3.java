/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package z6a3;

import java.net.ServerSocket;

/**
 *
 * @author frasiek
 */
public class Z6a3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: java MainThread src_port");
            System.exit(0);
        }

        ServerSocket server = new ServerSocket(Integer.parseInt(args[0]));
        while(true){
            worker w = new worker(server.accept());
            w.start();
        }
        
    }
    
}
