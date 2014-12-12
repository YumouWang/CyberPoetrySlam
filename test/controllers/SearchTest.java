package controllers;

import static org.junit.Assert.*;

import java.util.Collection;

import models.AbstractWord;
import models.GameState;
import models.Word;
import models.WordType;
import models.ProtectedMemento;
import models.UnprotectedMemento;

import org.junit.Test;

import views.MainView;

public class SearchTest {
	UnprotectedMemento un = null;
	ProtectedMemento p = null;

	@Test
	public void test() {
		GameState gameState = new GameState(un, p);
		MainView mainView = new MainView(gameState, un, p);
		SearchController search = new SearchController(mainView, gameState);
		search.updateWordTable();

		Collection<AbstractWord> result = search.search("aa", "");
		assertNotNull(search.wordtable);
		assertTrue(result.size() == 0);


	}

}
