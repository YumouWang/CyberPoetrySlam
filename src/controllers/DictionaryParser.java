package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Set;
/**
 * Dictionary parser for word initialize
 * 
 * Created by Yumou on 10/3/2014.
 */
public class DictionaryParser {
	String csvFile;
	BufferedReader br;
	String line;
	String cvsSplitBy = ",";
	Hashtable<String, String> hashTable = new Hashtable<String, String>();
	
	public DictionaryParser(String FileName) {
		csvFile = FileName;
	}
	
	public String getFileName() {
		return this.csvFile;
	}
	
	public Hashtable<String, String> parse() {
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] s = line.split(cvsSplitBy);
				hashTable.put(s[0], s[1]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return hashTable;
	}
}
