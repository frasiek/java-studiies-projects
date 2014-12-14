

public class Fork {
	private boolean busy = false;
	
	public synchronized void get(){
		while(busy){
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		busy = true;
	}
	
	public synchronized void put(){
		busy = false;
		notifyAll();
	}
}
