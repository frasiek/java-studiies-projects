
public class Telnet {
	
	public static void main(String[] args) {
		Worker w = new Worker(args);
		w.start();
		try {
			w.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
