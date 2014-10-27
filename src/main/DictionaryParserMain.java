package main;

import java.util.Set;

import controllers.DictionaryParser;
/**
 * Main Launcher for DictionaryParser
 * 
 * Created by Yumou on 10/3/2014.
 */
public class DictionaryParserMain {
	public static void main(String[] args) {
		String FileName = "Dictionary/WordDictionary.csv";
		DictionaryParser parser = new DictionaryParser(FileName);
		System.out.println(parser.getFileName());
		Set<String> keys = parser.parse().keySet();
		for (String key : keys) {
			System.out.println("Word: " + key + ", WordType: " + parser.parse().get(key));
		}
	}
}
