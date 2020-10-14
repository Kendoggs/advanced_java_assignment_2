package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class Main extends Application {

	File selectedFile = new File(""); 
	ArrayList<String> arr = new ArrayList<>(); 
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage)throws Exception{
		
		Button btnSource = new Button("Choose Source");
		Label lblSource = new Label("");
		HBox hboxSource = new HBox(btnSource,lblSource );
		
		Button btnSearch = new Button("Load Text");
		btnSearch.setMinWidth(100);
		HBox hbox = new HBox(btnSearch);
		//hbox.setAlignment(Pos.CENTER); 
		
		btnSource.setOnAction(e -> { 
			FileChooser  file = new FileChooser();
			file.setTitle("Open File");		
			selectedFile = file.showOpenDialog(stage); 
			lblSource.setText(" "+selectedFile.getName()); 
			});
			
		TextArea txtR = new TextArea();
		txtR.setText(selectedFile.getName());
		GridPane grdPane = new GridPane();
		
		Label searchSource = new Label("Search for the Keyword ");
		TextField text= new TextField();
		Button keywordSearch = new Button("Search");
		keywordSearch.setMinWidth(100);
		HBox hboxSearch = new HBox(searchSource, text, keywordSearch);
		//HBox search = new HBox(keywordSearch);
		//search.setAlignment(Pos.CENTER);
		
	
		
		ToggleGroup Tgroup = new ToggleGroup();
		RadioButton button1 = new RadioButton("Top-3 correlated keywords");
		RadioButton button2 = new RadioButton("Top-5 correlated keywords");
		RadioButton button3 = new RadioButton("Top-8 correlated keywords");
		RadioButton button4 = new RadioButton("Top-10 correlated keywords");
		button1.setToggleGroup(Tgroup);  button2.setToggleGroup(Tgroup);
		button3.setToggleGroup(Tgroup);  button4.setToggleGroup(Tgroup);
		HBox Tgroup1 = new HBox(button1,button2);
		HBox Tgroup2 = new HBox(button3,button4);
		
		
		
		Button btnBarChart = new Button("Bar Chart");
		btnBarChart.setMinWidth(100);
		Button btnPieChart = new Button("Pie Chart");
		btnPieChart.setMinWidth(100);
		
		HBox hboxChart = new HBox(10, btnBarChart, btnPieChart);
		//hboxChart.setAlignment(Pos.CENTER); 
		
		
		grdPane.setPadding(new Insets(10,10,10,10));
		grdPane.setMinSize(10, 10);
		grdPane.setHgap(10);
		grdPane.setVgap(10);
		grdPane.setAlignment(Pos.CENTER);
		
		grdPane.addRow(0,hboxSource);
		grdPane.addRow(1,hbox);
		grdPane.addRow(2, txtR);
		grdPane.addRow(3, hboxSearch);
		//grdPane.addRow(4, search);
		grdPane.addRow(4,Tgroup1);
		grdPane.addRow(5,Tgroup2);
		grdPane.addRow(6, hboxChart);
		
		btnSearch.setOnAction(e -> {
			BufferedReader reader = null;
			try
			{
				txtR.setText("");
				reader = new BufferedReader(new FileReader(selectedFile.getPath()));
				String line = reader.readLine();
				
				while(line != null ) {					
					arr.add(line.split(",")[4]);
					txtR.setText(txtR.getText() + line + "\n");	
					line = reader.readLine();
				}
				reader.close();
			}catch(Exception er)
			{
				er.printStackTrace();				
			}
			
		});
		
		Scene scene = new Scene(grdPane,500,600);
		stage.setScene(scene);
		stage.setTitle("Open File System");
		stage.show();
	}
}