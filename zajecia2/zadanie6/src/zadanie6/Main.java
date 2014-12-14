package zadanie6;

public class Main {

	public static void main(String[] args) {
		Fork f1 = new Fork();
		Fork f2 = new Fork();
		Fork f3 = new Fork();
		Fork f4 = new Fork();
		Fork f5 = new Fork();
		
		Monk m1 = new Monk(1, f5, f1);
		Monk m2 = new Monk(2, f1, f2);
		Monk m3 = new Monk(3, f2, f3);
		Monk m4 = new Monk(4, f3, f4);
		Monk m5 = new Monk(5, f4, f5);
		
		m1.start();
		m2.start();
		m3.start();
		m4.start();
		m5.start();
	}

}
