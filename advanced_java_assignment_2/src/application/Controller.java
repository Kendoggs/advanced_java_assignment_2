package application;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Group;
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

public class Controller {
	
	private View view;
	private Model model;
	
	public Controller(View view, Model model) {
	
		this.view = view;
		this.model = model;
		
		// Initialize Model
		this.model.setSelectedFile();
		this.model.setArr();
		this.model.setFrequencyTable();
		this.model.setKwFrequency();
		
		parse();
		search();
		visualize();
	}
	
	public void parse() {
		
		// Open file chooser on click of "Choose Source"
		this.view.btnSourceListener(e -> { 
			
			FileChooser  file = new FileChooser();
			file.setTitle("Open File");
			// Get current stage
			model.setSelectedFile(file.showOpenDialog(Main.getPrimaryStage())); 
			this.view.setlblSource(model.getSelectedFile().getName()); 
			
		});
		
		// Parse the XML file on click of "XML Parsing"
		this.view.btnDOMParserListener(e -> { 
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder docBuilder = null;
			try {
				
				docBuilder = factory.newDocumentBuilder();
				
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				
				Document doc = docBuilder.parse(this.model.getSelectedFile().getPath());
				
				// Display data from XML file in the text area
				doc.getDocumentElement().normalize();
				this.view.setTxtR("");
				this.view.setTxtR(this.view.getTxtR() + "Root element :" + doc.getDocumentElement().getNodeName() + "\n" );
		        NodeList nList = doc.getElementsByTagName("movie");
		        this.view.setTxtR(this.view.getTxtR() + "----------------------------------" + "\n");
		        
		        for (int temp = 0; temp < nList.getLength(); temp++) {
		        	
		            Node nNode = nList.item(temp);
		            this.view.setTxtR(this.view.getTxtR() + "\nCurrent Element :" + nNode.getNodeName() + "\n");
		            
		            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		            	
		               Element eElement = (Element) nNode; 
		               this.view.setTxtR(this.view.getTxtR() + "Title : " + eElement.getElementsByTagName("title").item(0).getTextContent() + "\n");
		               this.view.setTxtR(this.view.getTxtR() + "Year : " + eElement.getElementsByTagName("year").item(0).getTextContent() + "\n");
		               this.view.setTxtR(this.view.getTxtR() + "Rating : " + eElement.getElementsByTagName("rating").item(0).getTextContent() + "\n");
		               NodeList directorsList = eElement.getElementsByTagName("director");
		               
		               for (int count = 0; count < directorsList.getLength(); count++) {
		            	   
		            	   Node nodeDirector = directorsList.item(count);
		            	   
		            	   if (nodeDirector.getNodeType() == nodeDirector.ELEMENT_NODE) {
		            		   
		            		   Element director = (Element) nodeDirector; 
		            		   this.view.setTxtR(this.view.getTxtR() + "Director Name : " + director.getElementsByTagName("name").item(0).getTextContent() + "\n");
		            	   }
		            	   
		               }
		               
		               NodeList genres = eElement.getElementsByTagName("item");
		                           
	            	   for (int count = 0; count < genres.getLength(); count++) {
	            		   
	            		   Node nodeItem = genres.item(count); 
	            		   
	            		   if (nodeItem.getNodeType() == nodeItem.ELEMENT_NODE) {
	                           Element item = (Element) nodeItem;
	                           this.view.setTxtR(this.view.getTxtR() + "Genre : " + item.getTextContent() + "\n");
	                           
	            		   }
	            		   
	            	   }	
	            	   
	            	   // Store keywords in the array
	            	   NodeList kws = eElement.getElementsByTagName("kw");
			                
	            	   for (int count = 0; count < kws.getLength(); count++) {
	            		   
	            		   Node nodeItem = kws.item(count);
	            		   
	            		   if (nodeItem.getNodeType() == nodeItem.ELEMENT_NODE) {
	            			   
	                           Element item = (Element) nodeItem;
	                           this.model.getArr().add(item.getTextContent());
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
	
	}
	
	public void search() {
		
		// Query the XML file and display the results in the text area
		this.view.btnKeywordSearchListener(e -> { 
			
			String movie_title = this.view.getText();

			this.view.setTxtR("");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder docBuilder = null;
			try {
				
				docBuilder = factory.newDocumentBuilder();
				
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				
				Document doc = docBuilder.parse(this.model.getSelectedFile().getName());
				
				doc.getDocumentElement().normalize();
				
		        NodeList nList = doc.getElementsByTagName("movie");
		        
	        	// Compare titles with the searched keyword (substring query)
		        for (int temp = 0; temp < nList.getLength(); temp++) {
		        	
		            Node nNode = nList.item(temp);

		            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		            	
		               Element eElement = (Element) nNode;
		               String	movie_title1 = eElement.getElementsByTagName("title").item(0).getTextContent();
		               
	            	   // Display matched titles
		               if (movie_title1.contains(movie_title)) {
		               
		            	   this.view.setTxtR(this.view.getTxtR() + "Title : " + eElement.getElementsByTagName("title").item(0).getTextContent() + "\n");
		            	   this.view.setTxtR(this.view.getTxtR() + "\n");
		            	   NodeList kws = eElement.getElementsByTagName("kw");
			               
		            	   for (int count = 0; count < kws.getLength(); count++) {
		            		   
		            		   Node nodeItem = kws.item(count); 
		            		   
		            		   if (nodeItem.getNodeType() == nodeItem.ELEMENT_NODE) {
		                           
		            			   Element item = (Element) nodeItem;
		                           this.model.setKwFrequency(Collections.frequency(this.model.getArr(), item.getTextContent()));	
		                           this.model.getFrequencyTable().put(item.getTextContent(), this.model.getKwFrequency());
		                           
		            		   }   
		            	
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

	}
	
	public void visualize() {
		
		// Display bar chart on the click of "Bar Chart"
		this.view.btnBarChartListener( e-> {
			
			// Store the hash table in a map for easy querying
			Map<String, Integer> sortedMap = this.model.getFrequencyTable()
	                .entrySet()
	                .stream()
	                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
	                .collect(
	                    toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
	                        LinkedHashMap::new));
	        
			try {
			
					CategoryAxis xAxis = new CategoryAxis();
					NumberAxis yAxis = new NumberAxis();
					xAxis.setLabel("Class Name");
					yAxis.setLabel("Class Frequency");
			
					BarChart<String,Integer> bar = new BarChart(xAxis,yAxis);
					bar.setTitle("Class Frequency Bar Chart");
			
					XYChart.Series<String, Integer> series = new XYChart.Series<>();
					RadioButton selectedRadioButton = (RadioButton) this.view.getTGroup().getSelectedToggle();
		            int n = 0;
		            String top_n = selectedRadioButton.getText();
		            // Check which toggle is active
		            switch (top_n) { 
		            case "Top-3 correlated keywords":  
		                n = 3;
		            	break; 
		            case "Top-5 correlated keywords": 
		            	n = 5;
		            	break; 
		            case "Top-8 correlated keywords": 
		            	n = 8;
		            	break; 
		            case "Top-10 correlated keywords": 
		            	n = 10;
		            	break; 
		            default: 
		            	n = 3; 
		                break; 
		            }
		            List<Entry<String, Integer>> top_n_kw = sortedMap.entrySet().stream()
		                    .limit(n)
		                    .collect(toList());
		  	      Map<Object, Object> correlatedFrequency = top_n_kw.stream().collect(
		                  Collectors.toMap(x -> x.getKey(),x -> x.getValue()));
		  	  correlatedFrequency.forEach((k, v) -> {
		  		series.getData().add(new XYChart.Data(k,v));
		        }); 			
		  	  		// Display bar chart in new window
					series.setName("Class Frequency");
					bar.getData().add(series);
					Group root = new Group(bar);
					Scene sc = new Scene(root,500,400);
					Stage stage1 = new Stage();
					stage1.setTitle("Bar Chart");
					stage1.setScene(sc);
					stage1.show();
			
				}catch(Exception e1) {
					e1.printStackTrace();
			}
			
		});
		
		// Display pie chart on the click of "Pie Chart"
		this.view.btnPieChartListener(e -> {
			
			// Store the hash table in a map for easy querying
			Map<String, Integer> sortedMap = this.model.getFrequencyTable()
	                .entrySet()
	                .stream()
	                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
	                .collect(
	                    toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
	                        LinkedHashMap::new));
			
			try {
			
					ObservableList<Data> list = FXCollections.observableArrayList();
					RadioButton selectedRadioButton = (RadioButton) this.view.getTGroup().getSelectedToggle();
		            int n = 0;
		            String top_n = selectedRadioButton.getText();
		            // Check which toggle is active
		            switch (top_n) { 
		            case "Top-3 correlated keywords":  
		                n = 3;
		            	break; 
		            case "Top-5 correlated keywords": 
		            	n = 5;
		            	break; 
		            case "Top-8 correlated keywords": 
		            	n = 8;
		            	break; 
		            case "Top-10 correlated keywords":
		            	n = 10;
		            	break; 
		            default: 
		            	n = 3; 
		                break; 
		            }
		            List<Entry<String, Integer>> top_n_kw = sortedMap.entrySet().stream()
		                    .limit(n)
		                    .collect(toList());
		  	      Map<String, Integer> correlatedFrequency = top_n_kw.stream().collect(
		                  Collectors.toMap(x -> x.getKey(),x -> x.getValue()));
		  	  correlatedFrequency.forEach((k, v) -> { 
		  		list.add(new PieChart.Data(k, v));
		        });
	  	  			// Display pie chart in new window
					PieChart pieChart = new PieChart();
					pieChart.setData(list);
					pieChart.setLegendSide(Side.LEFT);
					pieChart.setTitle("Class Frequency Pie Chart");
					pieChart.setClockwise(false);
			
					Group root = new Group();
					root.getChildren().add(pieChart);
					Scene sc = new Scene(root,500,400);
					Stage stage1 = new Stage();
					stage1.setScene(sc);
					stage1.setTitle("Pie Chart");
					stage1.show();
			}catch(Exception e1) {
				e1.printStackTrace();
			}
			
		});
		
	}
	
}
