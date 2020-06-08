package application.SortingAlgs;

import javafx.scene.layout.HBox;

public class SelectionSort extends Algorithm {
	
	public SelectionSort(int[] array, HBox graphArea) {
		super(array, graphArea);
	}
	
	@Override
	void sort() {

		// Move the pivot up until the second to last bar
		for (int i = 0; i < arr.length - 1; i++) {
			
			// Pivot represents the end of the sorted region
			int pivot = i - 1;
			if (i > 0) paintBar(pivot, PIVOT_BAR_COLOR);
			
			
			// Find the minimum value in the unsorted region
			int minIndex = i;
			int minValue = arr[i];
			paintBar(minIndex, SELECTED_BAR_COLOR);
			
			for (int j = i + 1; j < arr.length; j++) {
			
				// Paint the comparisons
				paintBar(j, COMPARED_BAR_COLOR);
				sleep();
				
				// Check for user interruption
				if (isPaused) pause();
				if (wasStopRequested) return;
				
				resetBarColor(j);
				
				// If the current bar is smaller than the min, update min
				if (arr[j] < minValue) {
					colorMin(minIndex, j);
					minIndex = j;
					minValue = arr[j];
				}
			}
			
			// Swap the min with the bar in the beginning of the unsorted region (one after pivot)
			swapBars(minIndex, i);
			arr[minIndex] = arr[i];
			arr[i] = minValue;
			
			sleep();
			
			// Check for user interruption
			if (isPaused) pause();
			if (wasStopRequested) return;
			
			resetBarColor(pivot);
		}
		
		// Make sure that the last bar goes back to being white
		resetBarColor(arr.length - 2);
	}
	
	/**
	 * Resets the color of the previous min, and paints the color of the current min
	 * @param lastMin index of the previous min
	 * @param currMin index of the current min
	 */
	public void colorMin(int lastMin, int currMin) {
		resetBarColor(lastMin);
		paintBar(currMin, SELECTED_BAR_COLOR);
	}

	public static String[] getDescription() {
		String[] runTimes = {"O(n^2)", "O(n^2)", "O(n^2)", "O(1)"};
		return runTimes;
	}
}
