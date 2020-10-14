package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage)throws Exception{
		
		Button btnSource = new Button("Choose Source");
		Label lblSource = new Label("");
		HBox hboxSource = new HBox(btnSource,lblSource );
		hboxSource.setSpacing(10);
		TextArea txtR = new TextArea();
				
		btnSource.setOnAction(e -> { 
		
			FileChooser  file = new FileChooser();
			file.setTitle("Open File");		
			selectedFile = file.showOpenDialog(stage); 
			lblSource.setText(selectedFile.getName()); 
			
		});
		
		Button btnDOMParser = new Button("XML Parsing");
		HBox hboxParsing = new HBox(btnDOMParser);
		hboxParsing.setSpacing(10);
		
		btnDOMParser.setOnAction(e -> { 
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder docBuilder = null;
			try {
				
				docBuilder = factory.newDocumentBuilder();
				
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				Document doc = docBuilder.parse(selectedFile.getPath());
				
				doc.getDocumentElement().normalize();
				txtR.setText("");
				txtR.setText(txtR.getText() + "Root element :" + doc.getDocumentElement().getNodeName() + "\n" );
		        NodeList nList = doc.getElementsByTagName("movie");
		        txtR.setText(txtR.getText() + "----------------------------------" + "\n");
		        
		        for (int temp = 0; temp < nList.getLength(); temp++) {
		            Node nNode = nList.item(temp);
		            txtR.setText(txtR.getText() + "\nCurrent Element :" + nNode.getNodeName() + "\n");
		            
		            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		               Element eElement = (Element) nNode;
		                
		               txtR.setText(txtR.getText() + "Title : " + eElement.getElementsByTagName("title").item(0).getTextContent() + "\n");
		             
		               txtR.setText(txtR.getText() + "Year : " + eElement.getElementsByTagName("year").item(0).getTextContent() + "\n");
		              
		               txtR.setText(txtR.getText() + "Rating : " + eElement.getElementsByTagName("rating").item(0).getTextContent() + "\n");
		               
		            	NodeList directorsList = eElement.getElementsByTagName("director");
		            	for (int count = 0; count < directorsList.getLength(); count++) {
		            		   
		            	    Node nodeDirector = directorsList.item(count); 
		            	    if (nodeDirector.getNodeType() == nodeDirector.ELEMENT_NODE) {
		                         Element director = (Element) nodeDirector;
   	                            
		                         txtR.setText(txtR.getText() + "Director Name : " + director.getElementsByTagName("name").item(0).getTextContent() + "\n");
		            	    }   
		            	}
		               
		               NodeList genres = eElement.getElementsByTagName("item");
		                           
		            	   for (int count = 0; count < genres.getLength(); count++) {
		            		   
		            		   Node nodeItem = genres.item(count); 
		            		   if (nodeItem.getNodeType() == nodeItem.ELEMENT_NODE) {
		                           Element item = (Element) nodeItem;
		                           txtR.setText(txtR.getText() + "Genre : " + item.getTextContent() + "\n");
		                           
		            		   }   
		            	   }
				         
		            }
		         }
				
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			});
		
		txtR.setText(selectedFile.getName());
		GridPane grdPane = new GridPane();
		
		Label searchSource = new Label("Search for the Keyword ");
		TextField text= new TextField();
		Button keywordSearch = new Button("Search");
		keywordSearch.setMinWidth(100);
		HBox hboxSearch = new HBox(searchSource, text, keywordSearch);
		
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
		
		grdPane.setPadding(new Insets(10,10,10,10));
		grdPane.setMinSize(10, 10);
		grdPane.setHgap(10);
		grdPane.setVgap(10);
		grdPane.setAlignment(Pos.CENTER);
		
		grdPane.addRow(0,hboxSource);
		grdPane.addRow(1,hboxParsing);
		grdPane.addRow(2, txtR);
		grdPane.addRow(3, hboxSearch);
		grdPane.addRow(4,Tgroup1);
		grdPane.addRow(5,Tgroup2);
		grdPane.addRow(6, hboxChart);
		
		Scene scene = new Scene(grdPane,500,600);
		stage.setScene(scene);
		stage.setTitle("Keyword Search System");
		stage.show();
	}
}
