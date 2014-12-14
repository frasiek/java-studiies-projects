package zadanie6;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Fork {
	private boolean busy = false;
	private ReentrantLock lock = new ReentrantLock();
	private Condition cnd;
	
	public Fork() {
		cnd = lock.newCondition();
	}
	
	public void get(){
		lock.lock();
		try{
			while(busy){
				try {
					cnd.await();
				} catch (InterruptedException e) {}
			}
			busy = true;
		} finally {
			lock.unlock();
		}
	}
	
	public void put(){
		lock.lock();
		try{
			busy = false;
			cnd.signal();
		} finally {
			lock.unlock();
		}	
	}
}

