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
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MainController implements Initializable {
	
	private final static String HOVER_CREATE_BTN_STYLE = "-fx-background-color:  #b294bb;";
	private final static String IDLE_CREATE_BTN_STYLE = "-fx-background-color: #5d486f;";
	private final static String SORT_BTN_SHOW_STYLE = "-fx-background-color: #81a2be;";
	private final static String SORT_BTN_HIDE_STYLE = "-fx-background-color: transparent;";
	private final static String PAUSE_BTN_STYLE = "-fx-background-color: #de935f;";
	private final static String RESUME_BTN_STYLE = "-fx-background-color: #8abeb7";
	private final static String PAUSE_LABEL = "Pause";
	private final static String RESUME_LABEL = "Resume";
	
	
	final int[] SPEEDS = {1000, 500, 50, 20, 5};
	
	int[] arr;
	
	Algorithm alg;
	
	boolean isPaused;
	
	@FXML
	private HBox graphArea;

	@FXML
	private Slider speedSlide;
	
	@FXML
	private Button sortBtn;
	
	@FXML
	private Button stopBtn;
	
	@FXML
	private Button createGraphBtn;

	@FXML
	ToggleGroup sizeSelection;
	
	@FXML
	ToggleGroup sortSelection;
	
	@FXML
	Label averageRun;
	
	@FXML
	Label worstRun;
	
	@FXML
	Label bestRun;
	
	
	@FXML
	private void describeBubble(ActionEvent e) {
		setDescription(BubbleSort.getDescription());
	}
	
	@FXML
	private void describeInsertion(ActionEvent e) {
		
	}
	
	@FXML
	private void describeMerge(ActionEvent e) {
		
	}
	
	@FXML
	private void describeSelection(ActionEvent e) {
		
	}
	
	private void setDescription(String[] description) {
		bestRun.setText(description[0]);
		averageRun.setText(description[1]);
		worstRun.setText(description[2]);
	}
	
	@FXML
	private void createGraph(ActionEvent e) {
		reset();
		
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
		
		// Now, the user can sort.
		showSort();
	}
	
	@FXML
	private Button pauseToggleBtn;
	
	@FXML
	private void togglePause(ActionEvent e) {
		if (pauseToggleBtn.getText().equals(PAUSE_LABEL)) {
			pauseToggleBtn.setText(RESUME_LABEL);
			pauseToggleBtn.setStyle(RESUME_BTN_STYLE);
			
			isPaused = false;
			if (alg != null) {
				alg.togglePause(true);
			}
			
		} else {
			pauseToggleBtn.setText(PAUSE_LABEL);
			pauseToggleBtn.setStyle(PAUSE_BTN_STYLE);
			
			isPaused = true;
			if (alg != null) {
				alg.togglePause(false);
			}
		}
	}
	
	
	@FXML
	private void startSort() {
		hideSort();
		alg = new BubbleSort(arr, graphArea);
		
		alg.setSleepTime(SPEEDS[(int) speedSlide.getValue()]); 
		setDisablePauseStop(false);
		
		alg.beginSortProcess();

	}
	
	@FXML
	private void stopSort() {
		if (alg != null) {
			alg.shutDown();
		}
		setDisablePauseStop(true);
	}
	


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		isPaused = true;
		
		speedSlide.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue <? extends Number> observable, Number oldValue, Number newValue) {
				if (alg != null) {
					// Get the corresponding speed
					int choice = SPEEDS[newValue.intValue()];
					alg.setSleepTime(choice);
				}
			}
		});
		
		hideSort();
		
		createGraphBtn.setStyle(IDLE_CREATE_BTN_STYLE);
		createGraphBtn.setOnMouseEntered(e -> createGraphBtn.setStyle(HOVER_CREATE_BTN_STYLE));
		createGraphBtn.setOnMouseExited(e -> createGraphBtn.setStyle(IDLE_CREATE_BTN_STYLE));
		
	}
	
	private void hideSort() {
		sortBtn.setStyle(SORT_BTN_HIDE_STYLE);
		sortBtn.setDisable(true);
	}
	
	private void showSort() {
		sortBtn.setStyle(SORT_BTN_SHOW_STYLE);
		sortBtn.setDisable(false);
	}
	
	private void setDisablePauseStop(boolean shouldDisable) {
		stopBtn.setDisable(shouldDisable);
		pauseToggleBtn.setDisable(shouldDisable);
	}
	
	private void reset() {
		
		stopSort();
		// Clear current graph
		graphArea.getChildren().clear();
		
		if (!isPaused) togglePause(new ActionEvent());
		
	}
}
