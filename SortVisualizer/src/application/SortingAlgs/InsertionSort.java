package application.SortingAlgs;

import javafx.scene.layout.HBox;

public class InsertionSort extends Algorithm {

	public InsertionSort(int[] array, HBox graphArea) {
		super(array, graphArea);
	}

	@Override
	void sort() {
		
		// Sorts asynchronously: the graph is visually updated so that the
		// bars are swapped with every comparison, but the sorting algorithm
		// does not swap in the key until the pivot reaches its final position
		// in order to have highest efficiency
		// So, the graph does not actually represent the array until the end
		// of each inner step j
		
		System.out.println("Sorting insertion");

		for (int i = 1; i < arr.length; i++) {
			
			int key = arr[i];

			for (int j = i - 1; j >= -1; j--) {

				int current = j;
				int nextBar = j+1;
				
				// Paint the swapping bars
				paintBars(current, nextBar);
				
				// Check for user disruption
				if (isPaused) pause();
				if (wasStopRequested) break;
				
				if (current >= 0 && key < arr[current]) {
					
					// Swap
					swapBars(current, nextBar);
					arr[nextBar] = arr[current];
					
					
				} else {

					arr[nextBar] = key;
					
					resetBarColor(current);
					resetBarColor(nextBar);
					break;
				}
				
				resetBarColor(current);
				resetBarColor(nextBar);
			}
			
			
			// Check for user disruption
			if (isPaused) pause();
			if (wasStopRequested) break;
			
		}
		
	}
	
	public void paintBars(int current, int nextBar) {
		paintBar(nextBar, SELECTED_BAR_COLOR);
		paintBar(current, COMPARED_BAR_COLOR);
		sleep();
	}
	

	
	public static String[] getDescription() {
		String[] runTimes = {"O(n)", "O(n^2)", "O(n^2)", "O(1)"};
		return runTimes;
	}

}
