import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BoxChanger extends Remote {
	public Box change(Box box) throws RemoteException;
}
