package application;


import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import application.SortingAlgs.Algorithm;
import application.SortingAlgs.BubbleSort;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MainController implements Initializable {
	
	final int[] speeds = {1000, 500, 50, 20, 10};
	
	int[] arr;
	
	Algorithm alg;
	
	@FXML
	private HBox graphArea;

	@FXML
	private Slider speedSlide;
	
	@FXML
	private Button sortBtn;
	
	@FXML
	private Button stopBtn;

	@FXML
	ToggleGroup sizeSelection;
	
	@FXML
	ToggleGroup sortSelection;

	@FXML
	private void createGraph(ActionEvent e) {
		// Clear current graph
		graphArea.getChildren().clear();
		
		// Get graph dimensions
		double width = graphArea.getWidth();
		double height = graphArea.getHeight();
		
		
		// Get the id of the selected toggle button
		String id = ((Node) sizeSelection.getSelectedToggle()).getId();
		
		// Parse it into the number
		int numBars = Integer.parseInt(id.replace("size", ""));
		
		// Calculate bar dimensions accordingly
		double barWidth = width / numBars;

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
		
		alg.setSleepTime(speeds[(int) speedSlide.getValue()]); 
		stopBtn.setDisable(false);
		
		alg.beginSortProcess();

	}
	
	@FXML
	private void stopSort() {
		System.out.println("clicked");
		alg.shutDown();
		stopBtn.setDisable(true);
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		speedSlide.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue <? extends Number> observable, Number oldValue, Number newValue) {
				if (alg != null) {
					// Get the corresponding speed
					int choice = speeds[newValue.intValue()];
					System.out.println(choice);

					alg.setSleepTime(choice);
				}
			}
		});
		
	}
	
	
}
