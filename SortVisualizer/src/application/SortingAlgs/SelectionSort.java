package application.SortingAlgs;

import javafx.scene.layout.HBox;

public class SelectionSort extends Algorithm {
	
	public SelectionSort(int[] array, HBox graphArea) {
		super(array, graphArea);
	}
	
	@Override
	void sort() {

		
		for (int i = 0; i < arr.length - 1; i++) {
			
			int pivot = i - 1;
			if (i > 0) paintBar(pivot, PIVOT_BAR_COLOR);
			
			int minIndex = i;
			int minValue = arr[i];
			
			paintBar(minIndex, SELECTED_BAR_COLOR);
			
			for (int j = i + 1; j < arr.length; j++) {
				
				paintBar(j, COMPARED_BAR_COLOR);
				sleep();
				
				if (isPaused) pause();
				if (wasStopRequested) return;
				
				resetBarColor(j);
				
				if (arr[j] < minValue) {
					
					colorMin(minIndex, j);
					minIndex = j;
					minValue = arr[j];
				}
			}
			
			swapBars(minIndex, i);
			sleep();
			
			if (isPaused) pause();
			if (wasStopRequested) return;
			
			arr[minIndex] = arr[i];
			arr[i] = minValue;
			
			resetBarColor(pivot);
		}
		
		resetBarColor(arr.length - 2);
	}
	
	public void colorMin(int lastMin, int currMin) {
		resetBarColor(lastMin);
		paintBar(currMin, SELECTED_BAR_COLOR);
	}

	public static String[] getDescription() {
		String[] runTimes = {"O(n^2)", "O(n^2)", "O(n^2)", "O(1)"};
		return runTimes;
	}
}
