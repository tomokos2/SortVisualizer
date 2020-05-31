package application;

public class BubbleSort {
	int[] arr;
	
	public BubbleSort(int[] array) {
		this.arr = array;
	}
	
	public void sort() {
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = 0; j < arr.length - i - 1; j++) {
				if (arr[j] > arr[j + 1]) {
					// Swap
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
	}
}
