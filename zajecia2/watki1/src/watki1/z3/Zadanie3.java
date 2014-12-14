package watki1;

public class Zadanie3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread t1 = new Thread(new ThreadRunn2());
		Thread t2 = new Thread(new ThreadRunn2());
		
		t1.start();
		t2.start();
	}

}
