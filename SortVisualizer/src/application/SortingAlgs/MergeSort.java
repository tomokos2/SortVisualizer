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
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + ", ");
		}
		
		System.out.println();
		
		mergeSort(arr, 0, arr.length);
		
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + ", ");
		}
		
		System.out.println();
	}
	
	private void mergeSort(int[] currArray, int left, int right) {
		
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
	
	
	// Merges arr[left:mid] and arr[mid:right] with assumption that those sections are both sorted
	private void merge(int[] currentArray, int left, int mid, int right) {
		// TODO: possibly eliminate currentArray and use arr instead
		
		double[] updatedHeights = new double[right - left];
		
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
		
		System.out.println("a");
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + ", ");
		}
		System.out.println();
		System.out.println("b");
		for (int i = 0; i < b.length; i++) {
			System.out.print(b[i] + ", ");
		}
		System.out.println();
	
		aIndex = 0;
		bIndex= 0;
		
		for (int i = left; i < right; i++) {
			System.out.println("left " + left + " " + "mid " + mid + " " + "right " + right + " bindex " + bIndex + " aindex " + aIndex);
			
			paintCurrentComparison(left, mid, aIndex, a.length, bIndex, b.length);
			
			if (isPaused) pause();
			if (wasStopRequested) return;
			
			unPaintCurrentComparison(left, mid, aIndex, a.length, bIndex, b.length);
			
			if (aIndex == a.length) {
				// No more elements in a, so fill the rest with b
				
				updatedHeights[i - left] = ((Rectangle) graphArea.getChildren().get(bIndex + mid)).getHeight();
				currentArray[i] = b[bIndex];
				bIndex++;
				
			} else if (bIndex == b.length) {
				// No more elements in b, so fill the rest with a
				
				updatedHeights[i - left] = ((Rectangle) graphArea.getChildren().get(aIndex + left)).getHeight();
				currentArray[i] = a[aIndex];
				aIndex++;
				
			} else if (a[aIndex] <= b[bIndex]) {
				// Fill with minimum of a and b
				
				updatedHeights[i - left] = ((Rectangle) graphArea.getChildren().get(aIndex + left)).getHeight();
				currentArray[i] = a[aIndex];
				aIndex++;
				
			} else {	
				
				updatedHeights[i - left] = ((Rectangle) graphArea.getChildren().get(bIndex + mid)).getHeight();
				currentArray[i] = b[bIndex];
				bIndex++;	
			}

		}
		
		System.out.println("heights");
		for (int i = 0; i < updatedHeights.length; i++) {

			System.out.print(updatedHeights[i] + ", ");
		}
//		for (int i = left; i < right; i++) {
//			((Rectangle) graphArea.getChildren().get(i)).setHeight(updatedHeights[left + i]);
//		}
		
		
		
	}
	
	private void paintCurrentComparison(int left, int mid, int aIndex, int aLen, int bIndex, int bLen) {
		if (aIndex != aLen) {
			paintBar(aIndex + left, SELECTED_BAR_COLOR);
		}
		
		if (bIndex != bLen) {
			paintBar(bIndex + mid, COMPARED_BAR_COLOR);
		}
		
		sleep();
	}
	
	private void unPaintCurrentComparison(int left, int mid, int aIndex, int aLen, int bIndex, int bLen) {
		if (aIndex != aLen) {
			resetBarColor(aIndex + left);
		}
		
		if (bIndex != bLen) {
			resetBarColor(bIndex + mid);
		}
	}

	public static String[] getDescription() {
		String[] runTimes = {"O(nlogn)", "O(nlogn)", "O(nlogn)", "O(n)"};
		return runTimes;
	}

}
