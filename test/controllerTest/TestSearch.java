package controllerTest;

import controllers.Search;
import models.AbstractWord;
import models.GameState;
import models.Word;
import org.junit.Test;
import views.MainGUI;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class TestSearch {

	@Test
	public void testSearch() {
		GameState gameState = new GameState();
		MainGUI mainGUI = new MainGUI(gameState);
		Search search = new Search(mainGUI, gameState);
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
