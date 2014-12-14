package watki1;

public class Counter {
	 private int counter = 0;
	 
	 public synchronized int getValue() {
		 return counter;
	 }
	 public synchronized void incValue() {
		 counter++;
	 }
	 public synchronized void decValue() {
		 counter--;
	 }
}
