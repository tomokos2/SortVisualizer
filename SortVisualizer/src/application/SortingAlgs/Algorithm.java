package application.SortingAlgs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.scene.layout.HBox;

public abstract class Algorithm {
	
	// Default amount of time between each frame, in ms
	protected int sleepTime = 1000;
	
	// Create a new thread for running the sorting animation
	ExecutorService service = Executors.newCachedThreadPool();
	
	// Keep track of when the user stops the sorting
	protected boolean wasStopRequested;
	
	// Keep track of when the user pauses the sorting
	protected boolean isPaused;
	
	HBox graphArea;
	int[] arr;

	public Algorithm(int[] array, HBox graphArea) {
		this.arr = array;
		this.graphArea = graphArea;
		this.isPaused = false;
	}
	
	/**
	 *  Implement differently for each sorting algorithm
	 *  Sorts and draws the array
	 */
	abstract void sort();
	
	// Call to begin sorting
	public void beginSortProcess() {
		wasStopRequested = false;
		service.submit(new Runnable() {

			@Override
			public void run() {
				sort();
			}
			
		});
	}
	
	/**
	 * Stop the current thread from continuing and shut down the service.
	 */
	public void shutDown() {
		wasStopRequested = true;
		service.shutdown();
	}
	
	/**
	 * Clicked to notify the program to start or stop
	 * @param shouldPause pause or resume 
	 */
	public void togglePause(boolean shouldPause) {
		isPaused = shouldPause;
	}
	
	
	/**
	 * Changes the speed of the sorting algorithm
	 * @param sleepTime the number of ms to wait between each step
	 */
	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	
	/** Pause the program momentarily for animation visual 
	 * Can be changed dynamically by user
	 * Called by the algorithms that extend this class
	 */
	protected void sleep() {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/** Stops the animation with the ability to be resumed
	 * Called by the algorithms that extend this class
	 */
	protected void pause() {
		
		isPaused = true;
		try {
			while (isPaused && !wasStopRequested) {
				// Sleep for a couple ms at a time until user clicks resume
				// Break out if they stop the program
				Thread.sleep(10);
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
