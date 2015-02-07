package jdbc;

import java.io.IOException;
import java.net.ServerSocket;

public class Jdbc {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(Integer.parseInt(args[1]));
        while (true) {
            Baza baza = new Baza(args[0], server.accept());
            baza.start();
        }
    }

}
