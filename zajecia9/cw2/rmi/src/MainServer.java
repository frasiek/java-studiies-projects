
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainServer implements Incrementator {

    private int value = 0;
    // TODO

    @Override
    public int incValue() throws RemoteException {
        return ++value;
    }

    public static void main(String[] args) {
        try {
            MainServer obj = new MainServer();
            Incrementator stub = (Incrementator) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Incrementator", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
