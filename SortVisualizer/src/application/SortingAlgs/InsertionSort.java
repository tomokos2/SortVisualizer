package application.SortingAlgs;

import javafx.scene.layout.HBox;

public class InsertionSort extends Algorithm {

	public InsertionSort(int[] array, HBox graphArea) {
		super(array, graphArea);
	}

	@Override
	void sort() {
		System.out.println("Sorting insertion");
		// TODO Auto-generated method stub
		
	}
	
	public static String[] getDescription() {
		String[] runTimes = {"O(n)", "O(n^2)", "O(n^2)", "O(1)"};
		return runTimes;
	}

}
