package application.SortingAlgs;



import javafx.scene.layout.HBox;

public class BubbleSort extends Algorithm {

	public BubbleSort(int[] array, HBox graphArea) {
		super(array, graphArea);
	}
	
	// Swap adjacent if they are in the wrong order
	// Needs one extra pass in order to confirm sorted	
	@Override
	public void sort() {
		System.out.println("Sorting bubble");
		
		// Comparing index i to the index next to it, i+1, so need to stop at arr.length - 1
		for (int i = 0; i < arr.length - 1; i++) {
		
			// Keep track of whether it has finished sorting or not
			boolean isSorted = true;
			
			// With every pass, the last unsorted element is the only one guaranteed to be sorted
			for (int j = 0; j < arr.length - i - 1; j++) {
				
				int current = j;
				int comparedIndex = j+1;
				
				paintCurrentComparison(current, comparedIndex);

				if (arr[current] > arr[comparedIndex]) {
					isSorted = false;
					swap(current, comparedIndex);
				}
				
				// Check for any user disruptions
				if (isPaused) pause();
				if (wasStopRequested) break;
				
				resetBarColor(current);
				resetBarColor(comparedIndex);
			}
			
			// Check again for any user disruptions
			if (isPaused) pause();
			if (wasStopRequested) break;
			
			if (isSorted) {
				break;
			}
		}
		
	}
	
	
	private void swap(int current, int comparedIndex) {
		// Swap the bars
		swapBars(current, comparedIndex);
		
		// Update the array
		int temp = arr[current];
		arr[current] = arr[comparedIndex];
		arr[comparedIndex] = temp;
	}
	
	
	public static String[] getDescription() {
		String[] runTimes = {"O(n)", "O(n^2)", "O(n^2)", "O(1)"};
		return runTimes;
	}
	
}


