 package application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// Discourage resizing
			primaryStage = new Stage(StageStyle.UTILITY);
			Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Button stopBtn = (Button) scene.lookup("#stopBtn");
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					stopBtn.fire();
				}
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
