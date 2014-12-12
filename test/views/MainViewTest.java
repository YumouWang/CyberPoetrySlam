package views;

import controllers.MouseInputController;
import models.*;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.*;

public class MainViewTest {

	GameState gameState;
	Collection<AbstractWord> protectedWords;
	Collection<AbstractWord> unprotectedWords;
	UnprotectedMemento un = null;
	ProtectedMemento p = null;

	@Before
	public void initialize() {
		gameState = new GameState(un, p);
		Area protectedArea = gameState.getProtectedArea();
		Area unprotectedArea = gameState.getUnprotectedArea();
		protectedWords = protectedArea.getAbstractWordCollection();
		protectedWords.clear();
		unprotectedWords = unprotectedArea.getAbstractWordCollection();
		unprotectedWords.clear();
	}

	@Before
	public void reStore() {
		Collection<AbstractWordView> absProtect = new HashSet<AbstractWordView>();
		absProtect.add(new WordView(new Word("Dog", WordType.NOUN),
				new Position(0, 0)));
		ProtectedMemento pp = new ProtectedMemento(absProtect);
		Collection<AbstractWordView> absUnprotect = new HashSet<AbstractWordView>();
		absUnprotect.add(new WordView(new Word("Dog", WordType.NOUN),
				new Position(0, 0)));
		UnprotectedMemento unun = new UnprotectedMemento(absUnprotect);
		GameState gamestate = new GameState(unun, pp);
		MainView mainview = new MainView(gamestate, unun, pp);
		ProtectedMemento ppp = mainview.getProtectedState();
		UnprotectedMemento ununun = mainview.getUnprotectedState();
		absProtect = mainview.getProtectedWordView();
		absUnprotect = mainview.getUnprotectedWordView();
		assertNotNull(ppp);
		assertNotNull(ununun);
		assertNotNull(absProtect);
		assertNotNull(absUnprotect);
		assertNotNull(mainview);
	}

	@Test
	public void testRefresh() throws Exception {
		Word wordOne = new Word("TestWord", WordType.ADJECTIVE);
		protectedWords.add(wordOne);
		MainView mainView = new MainView(gameState, un, p);
		// Test that the mainView has the correct number of words represented
		assertEquals(1, mainView.protectedAreaWords.size());
		assertEquals(1, mainView.getProtectedAreaWords().size());

		// Test that refresh doesn't modify the state of the mainView
		mainView.refresh();
		assertEquals(1, mainView.getProtectedAreaWords().size());
	}

	@Test
	public void testAddProtectedAbstractWordView() throws Exception {
		Word wordOne = new Word("TestWord", WordType.ADJECTIVE);
		WordView wordOneView = new WordView(wordOne, new Position(0, 0));
		MainView mainView = new MainView(gameState, un, p);
		assertEquals(0, mainView.getProtectedAreaWords().size());
		mainView.addProtectedAbstractWordView(wordOneView);
		assertEquals(1, mainView.getProtectedAreaWords().size());
		assertTrue(mainView.getProtectedAreaWords().contains(wordOneView));
	}

	@Test
	public void testAddUnprotectedAbstractWordView() throws Exception {
		Word wordOne = new Word("TestWord", WordType.ADJECTIVE);
		WordView wordOneView = new WordView(wordOne, new Position(0, 0));
		MainView mainView = new MainView(gameState, un, p);
		assertEquals(0, mainView.getUnprotectedAreaWords().size());
		mainView.addUnprotectedAbstractWordView(wordOneView);
		assertEquals(1, mainView.getUnprotectedAreaWords().size());
		assertTrue(mainView.getUnprotectedAreaWords().contains(wordOneView));
	}

	@Test
	public void testRemoveAbstractWordView() throws Exception {
		Word wordOne = new Word("TestWord", WordType.ADJECTIVE);
		Word wordTwo = new Word("TestWord", WordType.ADJECTIVE);
		WordView wordOneView = new WordView(wordOne, new Position(0, 0));
		WordView wordTwoView = new WordView(wordTwo, new Position(0, 300));
		MainView mainView = new MainView(gameState, un, p);
		assertEquals(0, mainView.getProtectedAreaWords().size());
		mainView.addProtectedAbstractWordView(wordOneView);
		assertEquals(1, mainView.getProtectedAreaWords().size());
		assertTrue(mainView.getProtectedAreaWords().contains(wordOneView));
		mainView.removeProtectedAbstractWordView(wordOneView);
		assertEquals(0, mainView.getProtectedAreaWords().size());
		assertFalse(mainView.getProtectedAreaWords().contains(wordOneView));

		assertEquals(0, mainView.getUnprotectedAreaWords().size());
		mainView.addUnprotectedAbstractWordView(wordTwoView);
		assertEquals(1, mainView.getUnprotectedAreaWords().size());
		assertTrue(mainView.getUnprotectedAreaWords().contains(wordTwoView));
		mainView.removeUnprotectedAbstractWordView(wordTwoView);
		assertEquals(0, mainView.getUnprotectedAreaWords().size());
		assertFalse(mainView.getUnprotectedAreaWords().contains(wordOneView));

	}

	@Test
	public void testGetProtectedAbstractWordById() throws Exception {
		Word wordOne = new Word("TestWord", WordType.ADJECTIVE);
		WordView wordOneView = new WordView(wordOne, new Position(0, 0));
		Word wordTwo = new Word("TestWord2", WordType.ADJECTIVE);
		Word wordThree = new Word("TestWord3", WordType.ADJECTIVE);
		WordView wordTwoView = new WordView(wordTwo, new Position(0, 0));
		MainView mainView = new MainView(gameState, un, p);
		mainView.addProtectedAbstractWordView(wordOneView);
		mainView.addProtectedAbstractWordView(wordTwoView);
		assertEquals(wordTwoView,
				mainView.getProtectedAbstractWordById(wordTwo.getId()));
		assertEquals(wordOneView,
				mainView.getProtectedAbstractWordById(wordOne.getId()));
		assertEquals(null,
				mainView.getProtectedAbstractWordById(wordThree.getId()));
	}

	@Test
	public void testGetUnprotectedAbstractWordById() throws Exception {
		Word wordOne = new Word("TestWord", WordType.ADJECTIVE);
		WordView wordOneView = new WordView(wordOne, new Position(0, 0));
		Word wordTwo = new Word("TestWord2", WordType.ADJECTIVE);
		Word wordThree = new Word("TestWord3", WordType.ADJECTIVE);
		WordView wordTwoView = new WordView(wordTwo, new Position(0, 0));
		MainView mainView = new MainView(gameState, un, p);
		mainView.addUnprotectedAbstractWordView(wordOneView);
		mainView.addUnprotectedAbstractWordView(wordTwoView);
		assertEquals(wordTwoView,
				mainView.getUnprotectedAbstractWordById(wordTwo.getId()));
		assertEquals(wordOneView,
				mainView.getUnprotectedAbstractWordById(wordOne.getId()));
		assertEquals(null,
				mainView.getProtectedAbstractWordById(wordThree.getId()));
	}

	@Test
	public void testAddMouseInputController() throws Exception {
		// Just checking to make sure it doesn't throw an exception.
		// Mostly for extra code coverage
		MainView mainView = new MainView(gameState, un, p);
		mainView.addMouseInputController(new MouseInputController(mainView,
				gameState));
	}

	@Test
	public void testPaint() throws Exception {
		// Just checking to make sure it doesn't throw an exception.
		// Mostly for extra code coverage
		MainView mainView = new MainView(gameState, un, p);

		// Use bufferedImage to get a valid graphics object
		BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bi.createGraphics();
		mainView.paint(g2);
		g2.dispose();
	}

	@Test
	public void testIsInProtectedArea() throws Exception {
		MainView mainView = new MainView(gameState, un, p);
		Position positionOne = new Position(0, 200);
		Position positionTwo = new Position(0, 500);
		assertTrue(mainView.isInProtectedArea(positionOne));
		assertFalse(mainView.isInProtectedArea(positionTwo));
	}

	@Test
	public void testIsMoveOutOfBounds() throws Exception {
		MainView mainView = new MainView(gameState, un, p);
		Word word = new Word("wordOne", WordType.NOUN);
		WordView wordView = new WordView(word, new Position(0, 0));
		Position positionOne = new Position(-10, 200);
		Position positionTwo = new Position(0, -10);
		Position positionThree = new Position(1000, 100);
		Position positionFour = new Position(0, 1000);
		Position positionFive = new Position(10, 100);

		assertTrue(mainView.isMoveOutOfBounds(wordView, positionOne));
		assertTrue(mainView.isMoveOutOfBounds(wordView, positionTwo));
		assertTrue(mainView.isMoveOutOfBounds(wordView, positionThree));
		assertTrue(mainView.isMoveOutOfBounds(wordView, positionFour));
		assertFalse(mainView.isMoveOutOfBounds(wordView, positionFive));
	}
}