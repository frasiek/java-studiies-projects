
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainClient {

    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            BoxChanger stub = (BoxChanger) registry.lookup("BoxChanger");
            Box box = new Box();
            box = stub.change(box);
            box = stub.change(box);
            System.out.println(box.getValue());
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
