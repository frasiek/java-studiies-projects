
public class Producer implements Runnable {

	private Warehouse data;
	private Integer number;

	Producer(Integer number, Warehouse data) {
		this.data = data;
		this.number = number;
	}

	@Override
	public void run() {
		for (;;) {
			produce();
		}
	}

	public void produce() {
		Integer elements = data.put();
		System.out.println("Producent " + number + " - produkuje, magazyn zawiera " + elements + " elementow");
	}

}
