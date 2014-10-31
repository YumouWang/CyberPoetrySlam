package models;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import views.AbstractWordView;
import views.WordView;
import static org.junit.Assert.*;

public class GameStateTest {

	GameState gameState;
	unprotectedMemento un = null;
	protectedMemento p = null;

	@Before
	public void initialize() {
		gameState = new GameState(un, p);
	}

	@Before
	public void restore() {
		Collection<AbstractWordView> absProtect = new HashSet<AbstractWordView>();
		absProtect.add(new WordView(new Word("Dog", WordType.NOUN),
				new Position(0, 0)));
		p = new protectedMemento(absProtect);
		Collection<AbstractWordView> absUnprotect = new HashSet<AbstractWordView>();
		absUnprotect.add(new WordView(new Word("Dog", WordType.NOUN),
				new Position(0, 0)));
		un = new unprotectedMemento(absUnprotect);
		gameState = new GameState(un, p);
		assertEquals(1, gameState.getProtectedArea()
				.getAbstractWordCollection().size());
		assertEquals(1, gameState.getUnprotectedArea()
				.getAbstractWordCollection().size());
		assertNotNull(gameState);
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