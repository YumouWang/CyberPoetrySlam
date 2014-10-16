package views;

import models.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

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

//    @Test
//    public void testRefresh() throws Exception {
//        Word wordOne = new Word("TestWord", WordType.ADJECTIVE);
//        protectedWords.add(wordOne);
//        MainView mainView = new MainView(gameState);
//        // Test that the mainView has the correct number of words represented
//        assertEquals(1, mainView.protectedAreaWords.size());
//        assertEquals(1, mainView.getProtectedAreaWords().size());
//        // Test that the only word in the mainView is the correct one
//        boolean containsWordOne = false;
//        boolean containsWordOneLabel = false;
//        for(AbstractWordView wordView : mainView.getProtectedAreaWords()) {
//            if(wordOne.equals(wordView.getWord())) {
//                containsWordOne = true;
//                // Test that we added the component to the contentPane
//                if(Arrays.asList(mainView.contentPane.getComponents()).contains(((WordView)wordView).label)) {
//                    containsWordOneLabel = true;
//                }
//            }
//        }
//        assertTrue(containsWordOne);
//        assertTrue(containsWordOneLabel);
//
//        // Test that refresh doesn't modify the state of the mainView
//        mainView.refresh();
//        assertEquals(1, mainView.getProtectedAreaWords().size());
//        containsWordOne = false;
//        containsWordOneLabel = false;
//        for(AbstractWordView wordView : mainView.getProtectedAreaWords()) {
//            if(wordOne.equals(wordView.getWord())) {
//                containsWordOne = true;
//                // Test that we added the component to the contentPane
//                if(Arrays.asList(mainView.contentPane.getComponents()).contains(((WordView)wordView).label)) {
//                    containsWordOneLabel = true;
//                }
//            }
//        }
//        assertTrue(containsWordOne);
//        assertTrue(containsWordOneLabel);
//    }

    @Test
    public void testAddAbstractWordView() throws Exception {
        Word wordOne = new Word("TestWord", WordType.ADJECTIVE);
        WordView wordOneView = new WordView(wordOne, new Position(0, 0));
        MainView mainView = new MainView(gameState);
        assertEquals(0, mainView.getProtectedAreaWords().size());
        mainView.addProtectedAbstractWordView(wordOneView);
        assertEquals(1, mainView.getProtectedAreaWords().size());
        assertTrue(mainView.getProtectedAreaWords().contains(wordOneView));
    }

    @Test
    public void testRemoveAbstractWordView() throws Exception {
        Word wordOne = new Word("TestWord", WordType.ADJECTIVE);
        WordView wordOneView = new WordView(wordOne, new Position(0, 0));
        MainView mainView = new MainView(gameState);
        assertEquals(0, mainView.getProtectedAreaWords().size());
        mainView.addProtectedAbstractWordView(wordOneView);
        assertEquals(1, mainView.getProtectedAreaWords().size());
        assertTrue(mainView.getProtectedAreaWords().contains(wordOneView));
        mainView.removeProtectedAbstractWordView(wordOneView);
        assertEquals(0, mainView.getProtectedAreaWords().size());
        assertFalse(mainView.getProtectedAreaWords().contains(wordOneView));

    }
}