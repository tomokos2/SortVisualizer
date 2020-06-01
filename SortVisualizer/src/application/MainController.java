package application;


import java.util.Random;

import application.SortingAlgs.Algorithm;
import application.SortingAlgs.BubbleSort;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainController {
	
	int[] arr;
	
	Algorithm alg;
	
	@FXML
	private HBox graphArea;

	@FXML
	private Slider sizeSlide;
	
	@FXML
	private Button sortBtn;
	
	@FXML
	private Button stopBtn;

	

	@FXML
	private void createGraph(ActionEvent e) {
		// Clear current graph
		graphArea.getChildren().clear();
		
		// Get graph dimensions
		double width = graphArea.getWidth();
		double height = graphArea.getHeight();
		
		// Calculate bar dimensions accordingly
		int numBars = (int) sizeSlide.getValue();
		double barWidth = width / numBars;
		System.out.println(barWidth);
		System.out.println(numBars);
		
		arr = new int[numBars];
		
		Random random = new Random();
		for (int i = 0; i < numBars; i++) {
			arr[i] = random.nextInt(numBars);
			double barHeight = height * (arr[i]+1) / numBars;
			Rectangle bar = new Rectangle(barWidth, barHeight, Color.ALICEBLUE);
			graphArea.getChildren().add(bar);
		}
		
		sortBtn.setDisable(false);
	}
	
	
	
	@FXML
	private void startSort() {
		alg = new BubbleSort(arr, graphArea);
		
		stopBtn.setDisable(false);
		
		alg.beginSortProcess();

	}
	
	@FXML
	private void stopSort() {
		System.out.println("clicked");
		alg.shutDown();
		stopBtn.setDisable(true);
	}
	
}
