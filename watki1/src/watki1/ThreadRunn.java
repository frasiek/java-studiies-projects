package watki1;

public class ThreadRunn extends MessagePrinter implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.printMessage("TR: ThreadRunn created");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}
