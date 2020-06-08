package application.SortingAlgs;

import javafx.scene.layout.HBox;

public class SelectionSort extends Algorithm {
	
	public SelectionSort(int[] array, HBox graphArea) {
		super(array, graphArea);
	}
	
	@Override
	void sort() {
		System.out.println("Sorting selection");
		
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + ", ");
		}
		System.out.println();

		
		for (int i = 0; i < arr.length - 1; i++) {
			
			int minIndex = i;
			int minValue = arr[i];
			for (int j = i + 1; j < arr.length; j++) {
				
				if (arr[j] < minValue) {
					minIndex = j;
					minValue = arr[j];
				}
			}
			
			arr[minIndex] = arr[i];
			arr[i] = minValue;
		}
		
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + ", ");
		}
		System.out.println();
	}

	public static String[] getDescription() {
		String[] runTimes = {"O(n^2)", "O(n^2)", "O(n^2)", "O(1)"};
		return runTimes;
	}
}
