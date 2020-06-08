package application.SortingAlgs;

import javafx.scene.layout.HBox;

public class QuickSort extends Algorithm {

	public QuickSort(int[] array, HBox graphArea) {
		super(array, graphArea);
	}
	
	
	public static String[] getDescription() {
		String[] runTimes = {"O(nlog(n))", "O(nlog(n))", "O(n^2)", "O(log(n))"};
		return runTimes;
	}


	// Last element pivot
	// Use LR pointers
	@Override
	void sort() {
		System.out.println("Sorting quick");
		
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + ", ");
		}
		System.out.println();
		
		quickSort(0, arr.length - 1);
		
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + ", ");
		}
		System.out.println();
	}
	
	private void quickSort(int left, int right) {
		if (left >= right) return;
		
		int part = partition(left, right);
		
		// Sort the parts of the array around the partition
		quickSort(left, part - 1);
		quickSort(part + 1, right);
	}
	
	private int partition(int left, int right) {

		// Partition based on last element
		// This is the number upon which we will base our comparisons
		int pivotVal = arr[right];
		
		// Pointer to the first index of the array
		int pLeft = left;
		// Pointer to the last index of the array
		int pRight = right;
		
		// Until the left and right pointers meet, continue partitioning
		while (pLeft < pRight) {	
			
			// Find the left-most unsorted element
			while (pLeft < right && arr[pLeft] < pivotVal) {
				pLeft++;				
			}

			// Find the right-most unsorted element
			while (pRight > left && arr[pRight] >= pivotVal) {			
				pRight--;
			}
			
			// Swap as long as the two pivots have not yet met
			if (pLeft < pRight) {
				// swap pLeft and pRight
				int temp = arr[pRight];
				arr[pRight] = arr[pLeft];
				arr[pLeft] = temp;

			}
		}
		
		// Update the pivot variable by replacing it with the beginning of that section
		// Since pLeft >= pRight, use pLeft as the location of the partition.
		arr[right] = arr[pLeft];
		arr[pLeft] = pivotVal;
		
		return pLeft;
	}
}
