
public class Main {

	public static void main(String[] args) {
		Warehouse data = new Warehouse();

		for (int i = 0; i < 2; i++) {
			(new Thread(new Consumer(i, data))).start();
			(new Thread(new Producer(i, data))).start();
		}
	}

}
