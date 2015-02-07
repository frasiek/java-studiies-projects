import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Incrementator extends Remote {
	public int incValue() throws RemoteException;
}
