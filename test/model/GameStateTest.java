package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameStateTest {

    @Test
    public void testConstructor() throws Exception {
        GameState gameState = new GameState();
        assertNotNull(gameState);
        assertNotNull(gameState.protectedArea);
        assertNotNull(gameState.unprotectedArea);
        assertNotNull(gameState.getProtectedArea());
        assertNotNull(gameState.getUnprotectedArea());
    }

    @Test
    public void testProtect() throws Exception {
        GameState gameState = new GameState();
        AbstractWord word = new Word("Bed", WordType.INTERJECTION);
        gameState.getUnprotectedArea().addAbstractWord(word);
        gameState.protect(word);
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(word));
        assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection().contains(word));
    }

    @Test
    public void testProtectUnhappy() throws Exception {
        GameState gameState = new GameState();
        AbstractWord wordOne = new Word("Bed", WordType.INTERJECTION);
        AbstractWord wordTwo = new Word("Desk", WordType.NOUN);
        gameState.getUnprotectedArea().addAbstractWord(wordOne);
        gameState.getProtectedArea().addAbstractWord(wordTwo);
        gameState.protect(wordTwo);
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(wordTwo));
        assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection().contains(wordTwo));
        assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection().contains(wordOne));
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(wordOne));
    }

    @Test
    public void testUnprotect() throws Exception {
        GameState gameState = new GameState();
        AbstractWord word = new Word("Bed", WordType.INTERJECTION);
        gameState.getProtectedArea().addAbstractWord(word);
        gameState.unprotect(word);
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(word));
        assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection().contains(word));
    }

    @Test
    public void testUnprotectUnhappy() throws Exception {
        GameState gameState = new GameState();
        AbstractWord wordOne = new Word("Bed", WordType.INTERJECTION);
        AbstractWord wordTwo = new Word("Desk", WordType.NOUN);
        gameState.getUnprotectedArea().addAbstractWord(wordOne);
        gameState.getProtectedArea().addAbstractWord(wordTwo);
        gameState.unprotect(wordOne);
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(wordTwo));
        assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection().contains(wordTwo));
        assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection().contains(wordOne));
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(wordOne));
    }
}