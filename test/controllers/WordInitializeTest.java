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
		assertNotNull(wordList);
		//assertEquals(wordList.size(), 18);
		assertEquals(wordList.size(), 100);
		assertFalse(wordList.contains(new Word("run", WordType.NOUN)));
	}
}
