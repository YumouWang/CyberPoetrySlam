package views;

import models.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class MainViewTest {

    GameState gameState;
    Collection<AbstractWord> protectedWords;

    @Before
    public void initialize() {
        gameState = new GameState();
        Area protectedArea = gameState.getProtectedArea();
        protectedWords = protectedArea.getAbstractWordCollection();
        protectedWords.clear();
    }

    @Test
    public void testRefresh() throws Exception {
        Word wordOne = new Word("TestWord", WordType.ADJECTIVE);
        protectedWords.add(wordOne);
        MainView mainView = new MainView(gameState);
        // Test that the mainView has the correct number of words represented
        assertEquals(1, mainView.words.size());
        assertEquals(1, mainView.getWords().size());
        // Test that the only word in the mainView is the correct one
        boolean containsWordOne = false;
        boolean containsWordOneLabel = false;
        for(AbstractWordView wordView : mainView.words) {
            if(wordOne.equals(wordView.getWord())) {
                containsWordOne = true;
                // Test that we added the component to the contentPane
                if(Arrays.asList(mainView.contentPane.getComponents()).contains(wordView.label)) {
                    containsWordOneLabel = true;
                }
            }
        }
        assertTrue(containsWordOne);
        assertTrue(containsWordOneLabel);

        // Test that refresh doesn't modify the state of the mainView
        mainView.refresh();
        assertEquals(1, mainView.getWords().size());
        containsWordOne = false;
        containsWordOneLabel = false;
        for(AbstractWordView wordView : mainView.words) {
            if(wordOne.equals(wordView.getWord())) {
                containsWordOne = true;
                // Test that we added the component to the contentPane
                if(Arrays.asList(mainView.contentPane.getComponents()).contains(wordView.label)) {
                    containsWordOneLabel = true;
                }
            }
        }
        assertTrue(containsWordOne);
        assertTrue(containsWordOneLabel);
    }

    @Test
    public void testAddAbstractWordView() throws Exception {
        Word wordOne = new Word("TestWord", WordType.ADJECTIVE);
        AbstractWordView wordOneView = new AbstractWordView(wordOne, new Position(0, 0));
        MainView mainView = new MainView(gameState);
        assertEquals(0, mainView.getWords().size());
        mainView.addAbstractWordView(wordOneView);
        assertEquals(1, mainView.getWords().size());
        assertTrue(mainView.words.contains(wordOneView));
        assertTrue(Arrays.asList(mainView.contentPane.getComponents()).contains(wordOneView.label));
    }

    @Test
    public void testRemoveAbstractWordView() throws Exception {
        Word wordOne = new Word("TestWord", WordType.ADJECTIVE);
        AbstractWordView wordOneView = new AbstractWordView(wordOne, new Position(0, 0));
        MainView mainView = new MainView(gameState);
        assertEquals(0, mainView.getWords().size());
        mainView.addAbstractWordView(wordOneView);
        assertEquals(1, mainView.getWords().size());
        assertTrue(mainView.words.contains(wordOneView));
        assertTrue(Arrays.asList(mainView.contentPane.getComponents()).contains(wordOneView.label));
        mainView.removeAbstractWordView(wordOneView);
        assertEquals(0, mainView.getWords().size());
        assertFalse(mainView.words.contains(wordOneView));
        assertFalse(Arrays.asList(mainView.contentPane.getComponents()).contains(wordOneView.label));

    }
}