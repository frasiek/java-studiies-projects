package zadanie4;

public class Main {

	public static void main(String[] args) {
		Neighbor n1 = new Neighbor("Sasiad1");
		Neighbor n2 = new Neighbor("Sasiad2");
		n1.setNeighbor(n2);
		n2.setNeighbor(n1);
		Thread t1 = new Thread(n1);
		Thread t2 = new Thread(n2);
		t1.start();
		t2.start();
	}

}
