package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

public class Model {
	private File selectedFile;
	// Storing keywords
	private ArrayList<String> arr;
	// Storing keywords along with their occurrence (frequency)
	private Hashtable<String, Integer> frequencyTable;
	// Storing frequency for each keyword
	private int kwFrequency = 0;
	
	public void setSelectedFile() {
		
		selectedFile = new File("");
		
	}
	
	public void setSelectedFile(File file) {
		
		selectedFile = file;
		
	}
	
	public File getSelectedFile() {
		
		return selectedFile;
		
	}
	
	public void setArr() {
		
		arr = new ArrayList<>();
		
	}
	
	public void setArr(ArrayList<String> arrayList) {
		
		arr = arrayList;
		
	}
	
	public ArrayList<String> getArr() {
		
		return arr;
		
	}
	
	public void setFrequencyTable() {
		frequencyTable = new Hashtable<String, Integer>();
	}
	
	public void setFrequencyTable(Hashtable<String, Integer> hMap) {
		frequencyTable = hMap;
	}
	
	public Hashtable<String, Integer> getFrequencyTable() {
		
		return frequencyTable;
		
	}
	
	public void setKwFrequency() {
		
		kwFrequency = 0;
		
	}
	
	public void setKwFrequency(int frequency) {
		
		kwFrequency = frequency;
		
	}
	
	public int getKwFrequency() {
		
		return kwFrequency;
		
	}
	
}
