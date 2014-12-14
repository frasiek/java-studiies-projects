
public class Consumer implements Runnable {

	private Warehouse data;
	private Integer number;

	Consumer(Integer number, Warehouse data) {
		this.data = data;
		this.number = number;
	}

	@Override
	public void run() {
		for (;;) {
			consume();
		}
	}

	public void consume() {
		Integer elements = data.get();
		System.out.println("Konsument " + number + " - konsumuje, magazyn zawiera " + elements + " elementow");
	}

}
