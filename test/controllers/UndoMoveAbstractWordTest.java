package controllers;

import static org.junit.Assert.*;
import models.GameState;
import models.Position;
import models.ProtectedMemento;
import models.UnprotectedMemento;
import models.Word;
import models.WordType;

import org.junit.Before;
import org.junit.Test;

import views.AbstractWordView;
import views.MainView;
import views.WordView;

public class UndoMoveAbstractWordTest {
	GameState gameState;
	MainView mainView;
	ProtectedMemento protectedMemento;
	UnprotectedMemento unprotectedMemento;
	Word wordOne;
	Word wordTwo;
	AbstractWordView wordViewOne;
	UndoMoveAbstractWord undoMoveAbstractWord;
	
	
	@Before
	public void initialize() throws Exception {
		unprotectedMemento = null;
		protectedMemento = null;
		gameState = new GameState(unprotectedMemento, protectedMemento);
		gameState.getProtectedArea().getAbstractWordCollection().clear();
		gameState.getUnprotectedArea().getAbstractWordCollection().clear();
		mainView = new MainView(gameState, unprotectedMemento,
				protectedMemento);
		// create two words
		wordOne = new Word("wordOne", WordType.NOUN);
		wordViewOne = new WordView(wordOne, new Position(50, 50));
		mainView.addProtectedAbstractWordView(wordViewOne);
		gameState.getProtectedArea().addAbstractWord(wordOne);
		undoMoveAbstractWord = new UndoMoveAbstractWord(wordViewOne, 50, 50, 70, 70,
				mainView, gameState);
		MoveWordController moveWordController = new MoveWordController(mainView, gameState);
		moveWordController.moveWord(wordViewOne, new Position(50, 50), new Position(70, 70));
	}
	
	@Test
	public void testConstructor() {
		assertNotNull(undoMoveAbstractWord);
	}
	
	@Test
	public void testUndo() {
		assertTrue(undoMoveAbstractWord.undo());
		assertEquals(wordViewOne.getPosition().getX(),50);
		//assertEquals(wordViewOne.getPosition(), new Position(50,50));
	}
	
	@Test
	public void testExecute() {
		assertTrue(undoMoveAbstractWord.undo());
		assertTrue(undoMoveAbstractWord.execute());
		assertEquals(wordViewOne.getPosition().getX(), 70);
	}
	
	
}
