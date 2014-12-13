package controllers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import views.AbstractWordView;
import views.MainView;
import views.WordView;
import models.GameState;
import models.Position;
import models.UnprotectedMemento;
import models.ProtectedMemento;
import models.Word;
import models.WordType;

public class UndoConnectAbstractWordTest {
	GameState gameState;
	MainView mainView;
	ProtectedMemento protectedMemento;
	UnprotectedMemento unprotectedMemento;
	Word wordOne;
	Word wordTwo;
	AbstractWordView wordViewOne;
	AbstractWordView wordViewTwo;
	UndoConnectAbstractWord undoConnectAbstractWord;
	
	@Before
	public void initialize() {
		unprotectedMemento = null;
		protectedMemento = null;
		gameState = new GameState(unprotectedMemento, protectedMemento);
		gameState.getProtectedArea().getAbstractWordCollection().clear();
		gameState.getUnprotectedArea().getAbstractWordCollection().clear();
		mainView = new MainView(gameState, unprotectedMemento,
				protectedMemento);
		// create two words
		Position p = new Position(0, 0);
		wordOne = new Word("wordOne", WordType.NOUN);
		wordViewOne = new WordView(wordOne, p);
		Position q = new Position(wordViewOne.getWidth(),0);
		assertEquals(wordViewOne.getWidth(), 70);
		Position f = new Position(wordViewOne.getWidth()+70,0);
		wordTwo = new Word("wordOne", WordType.NOUN);
		wordViewTwo = new WordView(wordTwo,q);
		mainView.addProtectedAbstractWordView(wordViewOne);
		gameState.getProtectedArea().addAbstractWord(wordOne);
		mainView.addProtectedAbstractWordView(wordViewTwo);
		gameState.getProtectedArea().addAbstractWord(wordTwo);
		ConnectController connectController = new ConnectController(mainView, gameState);
		connectController.connect(wordViewOne, wordViewTwo);
		undoConnectAbstractWord = new UndoConnectAbstractWord(p, f, q, 
				wordViewOne, wordViewTwo, mainView, gameState);
	}
	
	@Test
	public void testUndo(){
		assertTrue(undoConnectAbstractWord.undo());
		assertEquals(wordViewTwo.getPosition().getX(), wordViewOne.getWidth());
	}
	
	@Test
	public void testRedo() {
		assertTrue(undoConnectAbstractWord.undo());
		assertTrue(undoConnectAbstractWord.execute());
		assertEquals(wordViewTwo.getPosition().getX(), wordViewOne.getWidth());
	}
}
