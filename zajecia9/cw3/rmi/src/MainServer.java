
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainServer implements BoxChanger {

    private int value = 0;

    public Box change(Box box) throws RemoteException {
        box.setValue(value++);
        return box;
    }

    public static void main(String[] args) {
        try {
            MainServer obj = new MainServer();
            Box b = new Box();
            BoxChanger stub = (BoxChanger) UnicastRemoteObject.exportObject(obj, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("BoxChanger", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
