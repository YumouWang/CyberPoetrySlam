package controllers;

import models.Word;
import models.WordType;

import org.junit.Test;

import java.util.Hashtable;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DictionaryParserTest {
	String FileName = "Dictionary/WordDictionary.csv";
	
	@Test
	public void testDictionaryParser() {
		DictionaryParser parse = new DictionaryParser(FileName);
		assertNotNull(parse);
		assertEquals(parse.getFileName(),FileName);
		
		List<Word> wordList = parse.parse();
		//System.out.println(wordList);
		assertEquals(wordList.size(), 327);
	}
	
	
	@Test
	public void testStringToWordType() {
		DictionaryParser parse = new DictionaryParser(FileName);
		String str1 = "noun";
		String str2 = "pronoun";
		String str3 = "n";
		assertEquals(parse.stringToWordType(str1), WordType.NOUN);
		assertEquals(parse.stringToWordType(str2), WordType.PRONOUN);
		assertEquals(parse.stringToWordType(str3), null);
	}

}
