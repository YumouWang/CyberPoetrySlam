package controllers;

import static org.junit.Assert.*;

import java.util.Collection;

import models.AbstractWord;
import models.GameState;
import models.Word;
import models.WordType;

import org.junit.Test;

import views.MainGUI;

public class SearchTest {

	@Test
	public void test() {
		GameState gameState = new GameState();
		MainGUI mainGUI = new MainGUI(gameState);
		Search search = new Search(mainGUI, gameState);
		search.updateWordTable();
		
		Collection<AbstractWord> result = search.search("Moon", "");
		assertNotNull(search.wordtable);
	    assertNotNull(result);
		String word = result.iterator().next().getValue().toString();
		String wordType = result.iterator().next().getType().toString();
		assertEquals(word, "Moon");
		assertEquals(wordType, "NOUN");

		Collection<AbstractWord> result1 = search.search("SUN", "NOUN");
		assertNotNull(search.wordtable);
	    assertNotNull(result1);
		String word1 = result1.iterator().next().getValue().toString();
		String wordType1 = result1.iterator().next().getType().toString();
		assertEquals(word1, "Sun");
		assertEquals(wordType1, "NOUN");
		
		Collection<AbstractWord> result2 = search.search("", "");
		assertNotNull(search.wordtable);
	    assertNotNull(result2);
	    assertEquals(search.wordtable.size(), result2.size());
	    assertTrue(result2.containsAll(search.wordtable));
	    
	    Collection<AbstractWord> result3 = search.search("", "NOUN");
		assertNotNull(search.wordtable);
	    assertNotNull(result3);
	    String wordType3 = result3.iterator().next().getType().toString();
	    assertEquals(wordType3, "NOUN");
		
		
		
		
	}

}
