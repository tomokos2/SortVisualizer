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
		quickSort(0, arr.length - 1);
	}
	
	/**
	 * Recursively sorts the array by splitting it based on the partition
	 * @param left left bound, inclusive
	 * @param right right bound, inclusive
	 */
	private void quickSort(int left, int right) {
		if (left >= right) return;
		
		int part = partition(left, right);
		
		// Sort the parts of the array around the partition
		quickSort(left, part - 1);
		quickSort(part + 1, right);
	}
	
	/**
	 * Using the last index as a pivot and left and right pointers,
	 * the array moves everything smaller than the pivot to the left,
	 * and everything greater than the pivot to the right
	 * Values equal to the pivot don't really matter
	 * @param left left bound
	 * @param right right bound
	 * @return the location of the partition in the array
	 */
	private int partition(int left, int right) {

		// Partition based on last element
		// This is the number upon which we will base our comparisons
		int pivotVal = arr[right];
		
		// Color the pivot
		paintBar(right, PIVOT_BAR_COLOR);
		
		// Pointer to the first index of the array
		int pLeft = left;
		paintBar(pLeft, SELECTED_BAR_COLOR);
		
		// Pointer to the last index of the array
		// Don't color it to avoid re-coloring the pivot
		int pRight = right;
		
		// Until the left and right pointers meet, continue partitioning
		while (pLeft < pRight) {	
			
			// Find the left-most unsorted element
			while (pLeft < right && arr[pLeft] < pivotVal) {
				// Update the colored bar
				resetBarColor(pLeft);
				
				// Increment the left pointer
				pLeft++;
				paintBar(pLeft, SELECTED_BAR_COLOR);
				
				// Take a nap
				sleep();

			}

			// Find the right-most unsorted element
			while (pRight > left && arr[pRight] >= pivotVal) {	
				// Don't un-color the pivot
				if (pRight != right) resetBarColor(pRight);
				
				// Increment the right pointer
				pRight--;
				paintBar(pRight, COMPARED_BAR_COLOR);
				sleep();
			}
			
			// Swap as long as the two pivots have not yet met
			if (pLeft < pRight) {
				
				swapBars(pLeft, pRight);
				int temp = arr[pRight];
				arr[pRight] = arr[pLeft];
				arr[pLeft] = temp;

			}
			
			// Reset the colors of the pointers
			resetBarColor(pLeft);
			resetBarColor(pRight);
		}
		
		// Paint and swap the pivot and end pointer location visually first
		paintBar(pLeft, SELECTED_BAR_COLOR);
		swapBars(pLeft, right);
		resetBarColor(right);
		resetBarColor(pLeft);
		
		// Update the pivot variable by replacing it with the beginning of that section
		// Since pLeft >= pRight, use pLeft as the location of the partition.
		arr[right] = arr[pLeft];
		arr[pLeft] = pivotVal;
		
		// Location of the partition
		return pLeft;
	}
	
}
