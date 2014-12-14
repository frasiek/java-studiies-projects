
public class Butler {
	
	private static Butler b;
	private int monkCount = 0; 
	
	public static Butler getInstance(){
		if(b == null){
			b = new Butler();
		}
		return b;
	}
	
	public synchronized void enter(){
		while(monkCount >= 4){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		monkCount += 1;
	}
	
	public synchronized void leave(){
		monkCount -= 1;
		notifyAll();
	}
	
	
}
