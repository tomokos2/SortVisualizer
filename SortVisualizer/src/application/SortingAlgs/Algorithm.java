package application.SortingAlgs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Algorithm {
	public int sleepTime = 1000;
	ExecutorService service = Executors.newCachedThreadPool();
	protected boolean wasStopRequested;
	
	public void beginSortProcess() {
		wasStopRequested = false;
		service.submit(new Runnable() {

			@Override
			public void run() {
				sort();
			}
			
		});
	}
	
	abstract void sort();
	
	protected void sleep() {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	public void shutDown() {
		System.out.println("shutting down...");
		wasStopRequested = true;
		service.shutdown();
	}
	
}
