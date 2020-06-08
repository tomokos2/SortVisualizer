package application.SortingAlgs;


import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public abstract class Algorithm implements Runnable {
	
	public static final Color BASE_BAR_COLOR = Color.ALICEBLUE;
	protected static final Color SELECTED_BAR_COLOR = Color.web("#CC6666");
	protected static final Color COMPARED_BAR_COLOR = Color.web("#DE935F");
	protected static final Color PIVOT_BAR_COLOR = Color.web("#659F65");
	
	// Default amount of time between each frame, in ms
	protected int sleepTime = 1000;
	
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
	
	/**
	 * Call to begin sorting
	 * Creates a new Thread
	 */
	@Override
	public void run() {
		wasStopRequested = false;
		sort();
	}
	
	/**
	 * Stop the current thread from continuing and shut down the service.
	 */
	public void shutDown() {
		wasStopRequested = true;
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
	
	/**
	 * Paints one bar of the graph
	 * Returns on invalid indices
	 * @param index the index of the bar
	 * @param color the color to paint it
	 */
	protected void paintBar(int index, Color color) {
		if (index < 0 || index > arr.length) return;
		Rectangle bar1 = (Rectangle) graphArea.getChildren().get(index);
		bar1.setFill(color);
		
	}
	
	protected void paintCurrentComparison(int current, int comparedIndex) {
		
		paintBar(current, SELECTED_BAR_COLOR);
		paintBar(comparedIndex, COMPARED_BAR_COLOR);
		
		sleep();
	}
	
	/**
	 * Takes two bars and swaps their visual components
	 * @param current the first index
	 * @param indexToSwap the second index
	 */
	protected void swapBars(int current, int indexToSwap) {

		Rectangle currBar = (Rectangle) graphArea.getChildren().get(current);
		
		// Save the current bar's attributes
		Paint tempFill = currBar.getFill();
		double tempHeight = currBar.getHeight();
		
		Rectangle barToSwap = (Rectangle) graphArea.getChildren().get(indexToSwap);
		
		// Swap the heights and colors of the bars
		currBar.setHeight(barToSwap.getHeight());
		currBar.setFill(barToSwap.getFill());
		
		barToSwap.setHeight(tempHeight);
		barToSwap.setFill(tempFill);
		
		// Always sleep when a change occurs
		sleep();
		
	}
	
	/**
	 * Return the bar to the base color
	 * Returns on invalid indices
	 * @param index index of the bar
	 */
	protected void resetBarColor(int index) {
		if (index < 0 || index > arr.length) return;
		
		Rectangle bar = (Rectangle) graphArea.getChildren().get(index);
		bar.setFill(BASE_BAR_COLOR);
	}
	
}
