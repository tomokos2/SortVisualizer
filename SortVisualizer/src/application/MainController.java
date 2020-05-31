package application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MainController {
	
	@FXML
	private HBox graphArea;

	@FXML
	private Slider sizeSlide;
	
	@FXML
	private Button createGraphBtn;
	
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
		
		for (int i = 1; i <= numBars; i++) {
			double barHeight = height * i / numBars;
			Rectangle bar = new Rectangle(barWidth, barHeight, Color.WHITE);
			graphArea.getChildren().add(bar);
		}
		
	}
}
