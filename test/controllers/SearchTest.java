package controllers;

import models.AbstractWord;
import models.GameState;
import models.Word;
import org.junit.Test;
import views.MainView;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class SearchTest {

	@Test
	public void testSearch() {
		GameState gameState = new GameState();
		MainView mainView = new MainView(gameState);
		Search search = new Search(mainView, gameState);
		search.updateWordTable();
		Collection<AbstractWord> result = search.search("Moon", "");

		String word = result.iterator().next().getValue();
		String wordType = ((Word)result.iterator().next()).getType().toString();
		assertEquals(word, "Moon");
		assertEquals(wordType, "NOUN");

		Collection<AbstractWord> result1 = search.search("SUN", "NOUN");
		String word1 = result1.iterator().next().getValue();
		String wordType1 = ((Word)result1.iterator().next()).getType().toString();
		assertEquals(word1, "Sun");
		assertEquals(wordType1, "NOUN");

	}

}
