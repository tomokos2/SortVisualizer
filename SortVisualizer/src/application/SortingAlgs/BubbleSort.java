package application.SortingAlgs;



import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class BubbleSort extends Algorithm {
	HBox graphArea;
	int[] arr;

	public BubbleSort(int[] array, HBox graphArea) {
		this.arr = array;
		this.graphArea = graphArea;
	}
	
	
	// Swap adjacent if they are in the wrong order
	// Needs one extra pass in order to confirm sorted	
	@Override
	public void sort() {
		// Comparing index i to the index next to it, i+1, so need to stop at arr.length - 1
		for (int i = 0; i < arr.length - 1; i++) {
		
			// Keep track of whether it has finished sorting or not
			boolean isSorted = true;
			
			// With every pass, the last unsorted element is the only one guaranteed to be sorted
			for (int j = 0; j < arr.length - i - 1; j++) {
				
				if (wasStopRequested) break;
				
				int current = j;
				int comparedIndex = j+1;
				
				paintCurrentComparison(current, comparedIndex);

				if (arr[current] > arr[comparedIndex]) {
					isSorted = false;
					swap(current, comparedIndex);
				}
			}
			
			if (wasStopRequested) break;
			
			if (isSorted) {
				break;
			}
		}
	}
	
	
	private void paintCurrentComparison(int current, int comparedIndex) {
		
		Rectangle bar1 = (Rectangle) graphArea.getChildren().get(current);
		bar1.setFill(Color.web("#CC6666"));
		
		Rectangle bar2 = (Rectangle) graphArea.getChildren().get(comparedIndex);
		bar2.setFill(Color.web("#DE935F"));
		
		sleep();
		
		bar1.setFill(Color.ALICEBLUE);
		bar2.setFill(Color.ALICEBLUE);
	}
	
	private void swap(int current, int indexToSwap) {
		// Swap

		
		Rectangle tempBar = (Rectangle) graphArea.getChildren().get(current);
		
		Paint tempFill = tempBar.getFill();
		double tempHeight = tempBar.getHeight();
		
		Rectangle barToSwap = (Rectangle) graphArea.getChildren().get(indexToSwap);
		
		tempBar.setHeight(barToSwap.getHeight());
		tempBar.setFill(barToSwap.getFill());
		
		barToSwap.setHeight(tempHeight);
		barToSwap.setFill(tempFill);
		
		int temp = arr[current];
		arr[current] = arr[indexToSwap];
		
		arr[indexToSwap] = temp;

		
	}
	
	
}


