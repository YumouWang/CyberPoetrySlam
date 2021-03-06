package controllers;

import models.AbstractWord;
import models.GameState;
import models.Word;
import models.WordType;
import org.junit.Test;
import views.MainView;

import java.util.Collection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SearchTest {
    @Test
    public void test() {
        GameState gameState = new GameState(null);
        MainView mainView = new MainView(gameState, null);
        SearchController search = new SearchController(mainView, gameState);
        search.updateWordTable();
        Word word = new Word("a", WordType.NOUN);
        search.wordtable.add(word);

        Collection<AbstractWord> result = search.search("aa", "");
        assertNotNull(search.wordtable);
        assertTrue(result.size() == 0);

        Collection<AbstractWord> result1 = search.search("a", "");
        assertNotNull(search.wordtable);
        assertTrue(result1.contains(word));

        Collection<AbstractWord> result2 = search.search("", "noun");
        assertNotNull(search.wordtable);
        assertTrue(result2.contains(word));

        Collection<AbstractWord> result3 = search.search("a", "noun");
        assertNotNull(search.wordtable);
        assertTrue(result3.contains(word));

    }

}
