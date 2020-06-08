package application.SortingAlgs;

import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

public class MergeSort extends Algorithm {
	
	public MergeSort(int[] array, HBox graphArea) {
		super(array, graphArea);
	}

	@Override
	void sort() {
		System.out.println("Sorting merge");
		mergeSort(arr, 0, arr.length);
	}
	
	private void mergeSort(int[] currArray, int left, int right) {
		// Periodically check for user interruption
		if (isPaused) pause();
		if (wasStopRequested) return;
		
		// The base case is an array length of 1, so that the array is sorted with one element
		if (right <= left + 1) return;
		
		int mid = (right + left) / 2;
		
		// Recursively sort the array by splitting it in half with each step
		mergeSort(currArray, left, mid);
		
		if (isPaused) pause();
		if (wasStopRequested) return;
		
		mergeSort(currArray, mid, right);
		
		if (isPaused) pause();
		if (wasStopRequested) return;
		
		// And then merging the sorted arrays together
		merge(currArray, left, mid, right);
		
	}
	
	
	/** Merges array[left:mid] and array[mid:right] with assumption that those sections are both sorted
	 * @param currentArray the array to sort
	 * @param left index of the left bound, inclusive
	 * @param mid index of the middle of the array, between the two sorted sections
	 * @param right index of the right bound, inclusive
	 */
	private void merge(int[] currentArray, int left, int mid, int right) {
		
		// Dynamically created array of the new bar heights
		double[] updatedHeights = new double[right - left];
		
		// 
		int[] a = new int[mid - left];
		int[] b = new int[right - mid];
		
		
		int aIndex = 0;
		int bIndex = 0;
		
		// Copy the two array sections
		for (int i = left; i < mid; i++) {
			a[aIndex] = currentArray[i];
			aIndex++;
		}
		
		for (int i = mid; i < right; i++) {
			b[bIndex] = currentArray[i];
			bIndex++;
		}
	
		aIndex = 0;
		bIndex= 0;
		
		for (int i = left; i < right; i++) {
			
			paintCurrentComparison(left, mid, aIndex, a.length, bIndex, b.length);
			
			if (isPaused) pause();
			if (wasStopRequested) return;
			
			unPaintCurrentComparison(left, mid, aIndex, a.length, bIndex, b.length);
			
			if (aIndex == a.length) {
				// No more elements in a, so fill the rest with b
				// Get the height of that element from the graph and store it in the array
				updatedHeights[i - left] = ((Rectangle) graphArea.getChildren().get(bIndex + mid)).getHeight();
				currentArray[i] = b[bIndex];
				bIndex++;
				
			} else if (bIndex == b.length) {
				// No more elements in b, so fill the rest with a
				
				updatedHeights[i - left] = ((Rectangle) graphArea.getChildren().get(aIndex + left)).getHeight();
				currentArray[i] = a[aIndex];
				aIndex++;
				
			} else if (a[aIndex] <= b[bIndex]) {
				// A is smaller, so fill with a
				
				updatedHeights[i - left] = ((Rectangle) graphArea.getChildren().get(aIndex + left)).getHeight();
				currentArray[i] = a[aIndex];
				aIndex++;
				
			} else {	
				// B is smaller, so fill with b
				
				updatedHeights[i - left] = ((Rectangle) graphArea.getChildren().get(bIndex + mid)).getHeight();
				currentArray[i] = b[bIndex];
				bIndex++;	
			}

		}
		
		// Create the newly merged bars on the graph
		updateGraph(updatedHeights, left, right);
		
	}
	
	private void paintCurrentComparison(int left, int mid, int aIndex, int aLen, int bIndex, int bLen) {
		// Only paint if the indices have not yet reached past the array length
		if (aIndex != aLen) {
			paintBar(aIndex + left, SELECTED_BAR_COLOR);
		}
		
		if (bIndex != bLen) {
			paintBar(bIndex + mid, COMPARED_BAR_COLOR);
		}
		
		sleep();
	}
	
	private void unPaintCurrentComparison(int left, int mid, int aIndex, int aLen, int bIndex, int bLen) {
		// Only unpaint if the indices have not yet reached past the array length
		if (aIndex != aLen) {
			resetBarColor(aIndex + left);
		}
		
		if (bIndex != bLen) {
			resetBarColor(bIndex + mid);
		}
	}
	
	/**
	 * Draws each bar on the graph with the height specified in the array
	 * @param updatedHeights array of calculated heights for each bar
	 * @param left is an int of left bound
	 * @param right is an int of right bound
	 */
	private void updateGraph(double[] updatedHeights, int left, int right) {
	
		for (int i = left; i < right; i++) {
			paintBar(i, SELECTED_BAR_COLOR);
			((Rectangle) graphArea.getChildren().get(i)).setHeight(updatedHeights[i - left]);
			
			sleep();
			resetBarColor(i);
		}
	}

	public static String[] getDescription() {
		String[] runTimes = {"O(nlog(n))", "O(nlog(n))", "O(nlog(n))", "O(n)"};
		return runTimes;
	}

}
