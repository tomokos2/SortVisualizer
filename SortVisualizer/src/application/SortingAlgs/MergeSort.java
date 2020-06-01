package application.SortingAlgs;

import javafx.scene.layout.HBox;

public class MergeSort extends Algorithm {

	public MergeSort(int[] array, HBox graphArea) {
		super(array, graphArea);
	}

	@Override
	void sort() {
		System.out.println("Sorting merge");
		
	}

	public static String[] getDescription() {
		String[] runTimes = {"O(nlogn)", "O(nlogn)", "O(nlogn)", "O(n)"};
		return runTimes;
	}

}
