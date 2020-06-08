package application;


import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import application.SortingAlgs.Algorithm;
import application.SortingAlgs.BubbleSort;
import application.SortingAlgs.InsertionSort;
import application.SortingAlgs.MergeSort;
import application.SortingAlgs.QuickSort;
import application.SortingAlgs.SelectionSort;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

public class MainController implements Initializable {
	
	// Styles
	private final static String HOVER_CREATE_BTN_STYLE = "-fx-background-color:  #b294bb;";
	private final static String IDLE_CREATE_BTN_STYLE = "-fx-background-color: #5d486f;";
	private final static String SORT_BTN_SHOW_STYLE = "-fx-background-color: #81a2be;";
	private final static String SORT_BTN_HIDE_STYLE = "-fx-background-color: transparent;";
	private final static String PAUSE_BTN_STYLE = "-fx-background-color: #de935f; -fx-background-radius: 0;";
	private final static String RESUME_BTN_STYLE = "-fx-background-color: #8abeb7; -fx-background-radius: 0;"; 
	private final static String PAUSE_LABEL = "Pause";
	private final static String RESUME_LABEL = "Resume";
	
	// Speed selection possibilities in ms
	final int[] SPEEDS = {1000, 500, 50, 20, 7};
	
	// Graph array
	int[] arr;
	
	// Current algorithm
	Algorithm alg;
	
	// Pause/Resume button tracker
	boolean isPaused;
	
	@FXML
	private AnchorPane anchor;
	
	// Wrapper for bar graph
	@FXML
	private HBox graphArea;

	// Slide bar to choose speed of sorting execution
	@FXML
	private Slider speedSlide;
	
	// Appears after graph is created, click to begin sort
	@FXML
	private Button sortBtn;
	
	// Create a new graph according to the size choice
	@FXML
	private Button createGraphBtn;
	
	// Terminate the sorting sequence
	@FXML
	private Button stopBtn;
	
	// Pauses or resumes the sorting sequence
	@FXML
	private Button pauseToggleBtn;

	// Skips the sorting to the end
	@FXML
	private Button skipBtn;
	
	// Radio button group to choose number of bars in graph
	@FXML
	ToggleGroup sizeSelection;
	
	// Radio button group to choose the sorting algorithm
	@FXML
	ToggleGroup sortSelection;
	
	// Labels for algorithm description
	@FXML
	Label averageRun;
	
	@FXML
	Label worstRun;
	
	@FXML
	Label bestRun;
	
	@FXML
	Label spaceComp;
	
/////////////////////////////////////////////////////////////////////////////////////////////////////
/*
 * 	Algorithm description managers
 */
////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	@FXML
	private void describeBubble(ActionEvent e) {
		setDescription(BubbleSort.getDescription());
	}
	
	@FXML
	private void describeInsertion(ActionEvent e) {
		setDescription(InsertionSort.getDescription());
	}
	
	@FXML
	private void describeMerge(ActionEvent e) {
		setDescription(MergeSort.getDescription());
	}
	
	@FXML
	private void describeSelection(ActionEvent e) {
		setDescription(SelectionSort.getDescription());
	}
	
	@FXML
	private void describeQuick(ActionEvent e) {
		setDescription(QuickSort.getDescription());
	}
	
	private void setDescription(String[] description) {
		// First tag in description is best case runtime
		bestRun.setText(description[0]);
		
		// Second tag in description is average case runtime
		averageRun.setText(description[1]);
		
		// Third tag in description is worst case runtime
		worstRun.setText(description[2]);
		
		// Fourth tag in description is space complexity
		spaceComp.setText(description[3]);
	}

	
/////////////////////////////////////////////////////////////////////////////////////////////////////
/*
* 	Graph initialization / setup
*/
////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
			// Get a random number in the range of the number of bars
			arr[i] = random.nextInt(numBars);
			
			// Bar height is proportional to the random number value
			double barHeight = height * (arr[i]+1) / numBars;
			Rectangle bar = new Rectangle(barWidth, barHeight, Algorithm.BASE_BAR_COLOR);
			graphArea.getChildren().add(bar);
		}
		
		// Now, the user can sort.
		showSort();
	}
	
	private void reset() {
		// Stop, clear, reset pause button
		stopSort();
		graphArea.getChildren().clear();		
		if (!isPaused) togglePause(new ActionEvent());	
	}


/////////////////////////////////////////////////////////////////////////////////////////////////////
/*
* 	Sorting runtime button handlers
*/
////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@FXML
	private void startSort() {
		hideSort();
		alg = getAlgorithm();
		
		alg.setSleepTime(SPEEDS[(int) speedSlide.getValue()]); 
		setDisablControlBar(false);
		
		Thread thread = new Thread(alg);
		thread.start();
	}
	
	@FXML
	private void stopSort() {
		if (alg != null) {
			alg.shutDown();
		}
		setDisablControlBar(true);
	}
	
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
	private void skip(ActionEvent e) {
		if (alg != null) {
			alg.setSleepTime(0);
		}
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////
/*
* 	Other listeners/helper functions
*/
////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		isPaused = true;
		hideSort();
		
		// Listen for user changing the speed of the algorithm
		speedSlide.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue <? extends Number> observable, Number oldValue, Number newValue) {
				if (alg != null) {
					// Get the corresponding speed
					int choice = SPEEDS[newValue.intValue()];
					alg.setSleepTime(choice);
				}
			}
		});
			
		// Styling things
		createGraphBtn.setStyle(IDLE_CREATE_BTN_STYLE);
		createGraphBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent e) {
				createGraphBtn.setStyle(HOVER_CREATE_BTN_STYLE);
				anchor.setCursor(Cursor.HAND);
			}
			
		});
		createGraphBtn.setOnMouseExited(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent e) {
				createGraphBtn.setStyle(IDLE_CREATE_BTN_STYLE);
				anchor.setCursor(Cursor.DEFAULT);
			}
			
		});
	}
	
	private void hideSort() {
		sortBtn.setStyle(SORT_BTN_HIDE_STYLE);
		sortBtn.setDisable(true);
	}
	
	private void showSort() {
		sortBtn.setStyle(SORT_BTN_SHOW_STYLE);
		sortBtn.setDisable(false);
	}
	
	private void setDisablControlBar(boolean shouldDisable) {
		stopBtn.setDisable(shouldDisable);
		pauseToggleBtn.setDisable(shouldDisable);
		skipBtn.setDisable(shouldDisable);
	}
	
	private Algorithm getAlgorithm() {
		String selection = ((Node) sortSelection.getSelectedToggle()).getId();
		Algorithm selectedAlg;
		
		switch (selection) {
		case "bubble":
			selectedAlg = new BubbleSort(arr, graphArea);
			break;
		case "insertion":
			selectedAlg = new InsertionSort(arr, graphArea);
			break;
		case "selection":
			selectedAlg = new SelectionSort(arr, graphArea);
			break;
		case "merge":
			selectedAlg = new MergeSort(arr, graphArea);
			break;
		case "quick":
			selectedAlg = new QuickSort(arr, graphArea);
			break;
		default:
			selectedAlg = new BubbleSort(arr, graphArea);
			System.err.println("Selection toggle error");
		}
		
		return selectedAlg;
	}
}
