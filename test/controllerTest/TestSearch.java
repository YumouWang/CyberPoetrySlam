package controllerTest;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import models.AbstractWord;
import models.GameState;

import org.junit.Test;

import views.MainGUI;
import controllers.Search;

public class TestSearch {

	@Test
	public void testSearch() {
		GameState gameState = new GameState();
		MainGUI mainGUI = new MainGUI(gameState);
		Search search = new Search(mainGUI, gameState);
		search.updateWordTable();
		Collection<AbstractWord> result = search.search("Moon", "");

		String word = result.iterator().next().getValue().toString();
		String wordType = result.iterator().next().getType().toString();
		assertEquals(word, "Moon");
		assertEquals(wordType, "NOUN");

		Collection<AbstractWord> result1 = search.search("SUN", "NOUN");
		String word1 = result1.iterator().next().getValue().toString();
		String wordType1 = result1.iterator().next().getType().toString();
		assertEquals(word1, "Sun");
		assertEquals(wordType1, "NOUN");

	}

}
