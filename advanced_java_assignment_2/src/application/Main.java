package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// Set title for the stage 
			primaryStage.setTitle("Test"); 
				  
			// Create a label 
			Label name = new Label("Sharjeel");
			Label name2 = new Label("Kenny");
						
			// Create a Stack Pane
			 StackPane sp = new StackPane();
				        
			// Add field
			sp.getChildren().add(name);
			sp.getChildren().add(name2);
			
			BorderPane root = new BorderPane();
			Scene scene = new Scene(sp,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
