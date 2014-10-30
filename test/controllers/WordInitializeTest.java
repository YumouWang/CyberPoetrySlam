package controllers;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Hashtable;

import models.Word;
import models.WordType;

import org.junit.Test;

public class WordInitializeTest {

	@Test
	public void testGetInitialWordFromFile() {
		WordInitialize wordInitialize = new WordInitialize();
		Collection<Word> wordList = wordInitialize.getInitialWordFromFile("Dictionary/WordDictionary.csv");
		Hashtable<String, WordType> hashTable = new Hashtable<String, WordType>();
		for(Word word : wordList) {
			hashTable.put(word.getValue(), word.getType());
		}
		assertNotNull(wordList);
		//assertEquals(wordList.size(), 18);
		assertEquals(hashTable.get("Mouse"), WordType.NOUN);	
	}
	
	@Test
	public void testStringToWordType() {
		WordInitialize wordInitialize = new WordInitialize();
		String str1 = "noun";
		String str2 = "pronoun";
		String str3 = "n";
		assertEquals(wordInitialize.stringToWordType(str1), WordType.NOUN);
		assertEquals(wordInitialize.stringToWordType(str2), WordType.PRONOUN);
		assertEquals(wordInitialize.stringToWordType(str3), null);
	}
}
