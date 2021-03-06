package models;

import controllers.UndoWithMemento;
import org.junit.Before;
import org.junit.Test;
import views.MainView;

import static org.junit.Assert.*;

public class GameStateTest {

    GameState gameState;

    @Before
    public void initialize() {
        gameState = new GameState(null);
        gameState.getUnprotectedArea().getAbstractWordCollection().clear();
        gameState.getProtectedArea().getAbstractWordCollection().clear();
    }

    @Test
    public void restore() {
        gameState.getProtectedArea().addAbstractWord(new Word("Dog", WordType.NOUN));
        gameState.getUnprotectedArea().addAbstractWord(new Word("Dog", WordType.NOUN));

        UndoWithMemento memento = new UndoWithMemento(new MainView(gameState, null));
        gameState = new GameState(memento);
        new MainView(gameState, memento);
        assertNotNull(gameState);
        assertEquals(1, gameState.getProtectedArea()
                .getAbstractWordCollection().size());
        assertEquals(1, gameState.getUnprotectedArea()
                .getAbstractWordCollection().size());
    }

    @Test
    public void testConstructor() throws Exception {
        assertNotNull(gameState);
        assertNotNull(gameState.protectedArea);
        assertNotNull(gameState.unprotectedArea);
        assertNotNull(gameState.getProtectedArea());
        assertNotNull(gameState.getUnprotectedArea());
    }

    @Test
    public void testProtect() throws Exception {
        AbstractWord word = new Word("Bed", WordType.INTERJECTION);
        gameState.getUnprotectedArea().addAbstractWord(word);
        gameState.protect(word);
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
                .contains(word));
        assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
                .contains(word));
    }

    @Test
    public void testProtectUnhappy() throws Exception {
        AbstractWord wordOne = new Word("Bed", WordType.INTERJECTION);
        AbstractWord wordTwo = new Word("Desk", WordType.NOUN);
        gameState.getUnprotectedArea().addAbstractWord(wordOne);
        gameState.getProtectedArea().addAbstractWord(wordTwo);
        gameState.protect(wordTwo);
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
                .contains(wordTwo));
        assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
                .contains(wordTwo));
        assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection()
                .contains(wordOne));
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection()
                .contains(wordOne));
    }

    @Test
    public void testUnprotect() throws Exception {
        AbstractWord word = new Word("Bed", WordType.INTERJECTION);
        gameState.getProtectedArea().addAbstractWord(word);
        gameState.unprotect(word);
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection()
                .contains(word));
        assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection()
                .contains(word));
    }

    @Test
    public void testUnprotectUnhappy() throws Exception {
        AbstractWord wordOne = new Word("Bed", WordType.INTERJECTION);
        AbstractWord wordTwo = new Word("Desk", WordType.NOUN);
        gameState.getUnprotectedArea().addAbstractWord(wordOne);
        gameState.getProtectedArea().addAbstractWord(wordTwo);
        gameState.unprotect(wordOne);
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
                .contains(wordTwo));
        assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
                .contains(wordTwo));
        assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection()
                .contains(wordOne));
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection()
                .contains(wordOne));
    }
}