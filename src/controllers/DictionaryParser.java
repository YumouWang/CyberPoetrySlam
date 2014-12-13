package controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import models.Word;
import models.WordType;

/**
 * Dictionary parser to get a word list from a CSV file
 * 
 * @author Yumou
 * @version 10/3/2014
 */
public class DictionaryParser {
	String csvFile;
	BufferedReader br;
	String line;
	String cvsSplitBy = ",";
	List<Word> wordList = new ArrayList<Word>();

	public DictionaryParser(String FileName) {
		csvFile = FileName;
	}

	public String getFileName() {
		return this.csvFile;
	}

	public List<Word> parse() {
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] s = line.split(cvsSplitBy);
				String wordValue = s[0].trim();
				WordType wordType = stringToWordType(s[1].trim());
				if (wordType != null) {
					Word word = new Word(wordValue, wordType);
					wordList.add(word);
				}
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
		return wordList;
	}

	/**
	 * transfer a string to an enum word type
	 * 
	 * @param wordType
	 * @return
	 */
	public WordType stringToWordType(String wordType) {
		for (WordType w : WordType.values()) {
			if (w.toString().equalsIgnoreCase(wordType)) {
				return w;
			}
		}
		return null;
	}

}
