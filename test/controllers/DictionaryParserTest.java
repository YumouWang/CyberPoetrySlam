package controllers;

import org.junit.Test;

import java.util.Hashtable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DictionaryParserTest {
	String FileName = "Dictionary/WordDictionary.csv";
	
	@Test
	public void testDictionaryParser() {
		DictionaryParser parse = new DictionaryParser(FileName);
		assertNotNull(parse);
		assertEquals(parse.getFileName(),FileName);
		
		Hashtable<String, String> hashTable = parse.parse();
		System.out.println(hashTable);
		assertEquals(hashTable.get("Mouse"),"noun");
		assertEquals(hashTable.get("Bear"),"noun");
		assertEquals(hashTable.get("Cat"),"noun");
		assertEquals(hashTable.get("Tiger"),"noun");
		assertEquals(hashTable.get("Lion"),"noun");
		assertEquals(hashTable.get("Dog"),"noun");
		assertEquals(hashTable.get("Tree"),"noun");
		assertEquals(hashTable.get("Lake"),"noun");
		assertEquals(hashTable.get("River"),"noun");
		assertEquals(hashTable.get("Sun"),"noun");
		assertEquals(hashTable.get("Sea"),"noun");
	}

}
