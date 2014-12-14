package controllers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import models.AbstractWord;
import models.GameState;
import models.Position;
import models.ProtectedMemento;
import models.UnprotectedMemento;
import models.Word;
import models.WordType;
import views.MainView;
import views.WordView;

public class UndoControllerTest {
	GameState gameState;
	MainView mainView;
	ProtectedMemento protectedMemento;
	UnprotectedMemento unprotectedMemento;
	Word wordOne;
	WordView wordViewOne;
	UndoController undoController;
	UndoMoveAbstractWord undoMoveAbstractWord;
	
	@Before
	public void initialize() throws Exception{
		unprotectedMemento = null;
		protectedMemento = null;
		gameState = new GameState(unprotectedMemento, protectedMemento);
		gameState.getProtectedArea().getAbstractWordCollection().clear();
		gameState.getUnprotectedArea().getAbstractWordCollection().clear();
		mainView = new MainView(gameState, unprotectedMemento,
				protectedMemento);
		wordOne = new Word("wordOne", WordType.NOUN);
		wordViewOne = new WordView(wordOne, new Position(50, 50));
		mainView.addProtectedAbstractWordView(wordViewOne);
		gameState.getProtectedArea().addAbstractWord(wordOne);
		undoController = new UndoController(mainView,gameState);
		MoveWordController moveWordController = new MoveWordController(mainView, gameState);
		moveWordController.moveWord(wordViewOne, new Position(50, 50), new Position(70, 70));
		undoMoveAbstractWord = new UndoMoveAbstractWord(wordViewOne, 50, 50, 70, 70,
				mainView, gameState);
		mainView.recordUndoMove(undoMoveAbstractWord);
	}
	
	@Test
	public void testConstruct() {
		assertNotNull(undoController);
	}
	
	@Test
	public void testProcess(){
		undoController.process();
		assertEquals(mainView.getUndoMoves().size(),0);
		assertFalse(mainView.getUndoButton().isEnabled());
		assertTrue(mainView.getRedoButton().isEnabled());
	}
}
