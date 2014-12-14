package watki1;

public class ThreadExt extends Thread {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		System.out.println("TX: ThreadExt created!");
		System.out.println("TX: Getting sleepy...");
		MyDate.printCurrentDate();
		try {
			sleep(3000);
		} catch (InterruptedException e) {

		} finally {
			System.out.println("TX: Wow i'm awaken!");
			MyDate.printCurrentDate();
		}
	}

	
	
}
