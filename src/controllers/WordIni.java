package controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import models.AbstractWord;
import models.Word;
import models.WordType;

public class WordIni {
	public HashSet<AbstractWord> wordIni(String csvFileName)
			throws FileNotFoundException {
		HashSet<AbstractWord> Words = new HashSet<AbstractWord>();

		BufferedReader reader = new BufferedReader(new FileReader(csvFileName));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String[] str = line.split(",");
				WordType w = transfer(str[1]);
				Words.add(new Word(str[0], w));
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Words;
	}
	
	public WordType transfer(String wordType) {
		for (WordType w : WordType.values()) {
	        if (w.toString().equalsIgnoreCase(wordType)) {
	          return w;
	        }
	    }
		return null;
	}
	
}
