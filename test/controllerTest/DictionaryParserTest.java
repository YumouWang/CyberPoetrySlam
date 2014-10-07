package controllerTest;

import static org.junit.Assert.*;

import java.util.Hashtable;

import org.junit.Test;

import controllers.DictionaryParser;

public class DictionaryParserTest {
	String FileName = "Dictionary/WordDictionary.csv";
	
	@Test
	public void testDictionaryParser() {
		DictionaryParser parse = new DictionaryParser(FileName);
		assertNotNull(parse);
		assertEquals(parse.getFileName(),FileName);
		
		Hashtable<String, String> hashTable = parse.parse();
		assertEquals(hashTable.get("that"),"pronoun");
		assertEquals(hashTable.get("you"),"pronoun");
		assertEquals(hashTable.get("cat"),"noun");
		assertEquals(hashTable.get("all"),"adjective");
		assertEquals(hashTable.get("they"),"pronoun");
		
	}

}
