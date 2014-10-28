package controllers;

import models.WordType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

import models.Word;

public class WordInitialize {
	Collection<Word> wordList;

	/**
	 * get initial word from a file
	 * 
	 * @param FileName
	 * @return
	 */
	public Collection<Word> getInitialWordFromFile(String FileName) {
		DictionaryParser parse = new DictionaryParser(FileName);
		Hashtable<String, String> hashTable = parse.parse();
		wordList = new HashSet<Word>();
		for (Iterator itr = hashTable.keySet().iterator(); itr.hasNext();) {
			String wordValue = (String) itr.next();
			String wordType = (String) hashTable.get(wordValue);
			if (stringToWordType(wordType) != null) {
				Word word = new Word(wordValue, stringToWordType(wordType));
				wordList.add(word);
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
