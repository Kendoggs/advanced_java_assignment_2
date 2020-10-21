package application;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class View {
	
	private GridPane view;
	private Button btnSource;
	private Label lblSource;
	private HBox hboxSource;
	private TextArea txtR;
	
	private Button btnDOMParser;
	private HBox hboxParsing;
	
	private Label searchSource;
	private TextField text;
	private Button keywordSearch;
	private HBox hboxSearch;
	
	private ToggleGroup TGroup;
	private RadioButton button1;
	private RadioButton button2;
	private RadioButton button3;
	private RadioButton button4;
	private HBox Tgroup1;
	private HBox Tgroup2;
	private Button btnBarChart;
	private Button btnPieChart;
	private HBox hboxChart;
	
	public View() {
		
		createAndConfigurePane();
		createAndLayoutControls();
		
	}
	
	public Parent asParent() {
		
		return view;
		
	}
	
	private void createAndConfigurePane() {
		
		view = new GridPane();
		view.setPadding(new Insets(10,10,10,10));
		view.setMinSize(10, 10);
		view.setHgap(10);
		view.setVgap(10);
		view.setAlignment(Pos.CENTER);
		
	}
	
	private void createAndLayoutControls() {
		
		btnSource = new Button("Choose Source");
		lblSource = new Label("");
		hboxSource = new HBox(btnSource,lblSource);
		hboxSource.setSpacing(10);
		txtR = new TextArea();
		
		btnDOMParser = new Button("XML Parsing");
		hboxParsing = new HBox(btnDOMParser);
		hboxParsing.setSpacing(10);
		
		searchSource = new Label("Search for the Keyword ");
		text = new TextField();
		keywordSearch = new Button("Search");
		keywordSearch.setMinWidth(100);
		hboxSearch = new HBox(searchSource, text, keywordSearch);
		
		TGroup = new ToggleGroup();
		button1 = new RadioButton("Top-3 correlated keywords");
		button2 = new RadioButton("Top-5 correlated keywords");
		button3 = new RadioButton("Top-8 correlated keywords");
		button4 = new RadioButton("Top-10 correlated keywords");
		button1.setToggleGroup(TGroup);  button2.setToggleGroup(TGroup);
		button3.setToggleGroup(TGroup);  button4.setToggleGroup(TGroup);
		Tgroup1 = new HBox(button1,button2);
		Tgroup2 = new HBox(button3,button4);
		btnBarChart = new Button("Bar Chart");
		btnBarChart.setMinWidth(100);
		btnPieChart = new Button("Pie Chart");
		btnPieChart.setMinWidth(100);
		hboxChart = new HBox(10, btnBarChart, btnPieChart);
		
		view.addRow(0,hboxSource);
		view.addRow(1,hboxParsing);
		view.addRow(2, txtR);
		view.addRow(3, hboxSearch);
		view.addRow(4,Tgroup1);
		view.addRow(5,Tgroup2);
		view.addRow(6, hboxChart);
		
	}
	
	public void setlblSource(String txt) {
		
		lblSource.setText(txt);
		
	}
	
	public void setTxtR(String txt) {
		
		txtR.setText(txt);
		
	}
	
	public String getTxtR() {
		
		return txtR.getText();
		
	}
	
	public void setText(String txt) {
		
		text.setText(txt);
		
	}
	
	public String getText() {
		
		return text.getText();
		
	}
	
	public ToggleGroup getTGroup() {
		
		return TGroup;
		
	}
	
	public void btnSourceListener(EventHandler<ActionEvent> listener) {
		
		btnSource.setOnAction(listener);
		
	}
	
	public void btnDOMParserListener(EventHandler<ActionEvent> listener) {
		
		btnDOMParser.setOnAction(listener);
		
	}
	
	public void btnKeywordSearchListener(EventHandler<ActionEvent> listener) {
		
		keywordSearch.setOnAction(listener);
		
	}
	
	public void btnBarChartListener(EventHandler<ActionEvent> listener) {
		
		btnBarChart.setOnAction(listener);
		
	}
	
	public void btnPieChartListener(EventHandler<ActionEvent> listener) {
		
		btnPieChart.setOnAction(listener);
		
	}
	
}
