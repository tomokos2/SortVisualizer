package application.SortingAlgs;

import javafx.scene.layout.HBox;

public class SelectionSort extends Algorithm {
	
	public SelectionSort(int[] array, HBox graphArea) {
		super(array, graphArea);
	}
	
	@Override
	void sort() {
		System.out.println("Sorting selection");
		
	}

	public static String[] getDescription() {
		String[] runTimes = {"O(n^2)", "O(n^2)", "O(n^2)", "O(1)"};
		return runTimes;
	}
}
