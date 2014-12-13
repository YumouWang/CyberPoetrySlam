package controllers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import models.GameState;
import models.Position;
import models.ProtectedMemento;
import models.UnprotectedMemento;
import models.Word;
import models.WordType;
import views.MainView;
import views.WordView;

public class RedoControllerTest {
	GameState gameState;
	MainView mainView;
	ProtectedMemento protectedMemento;
	UnprotectedMemento unprotectedMemento;
	Word wordOne;
	WordView wordViewOne;
	RedoController redoController;
	//UndoController undoController;
	UndoMoveAbstractWord undoMoveAbstractWord;
	
	@Before
	public void initialize() {
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
		redoController = new RedoController(mainView);
		//undoController = new UndoController(mainView);
		MoveWordController moveWordController = new MoveWordController(mainView, gameState);
		moveWordController.moveWord(wordViewOne, new Position(50, 50), new Position(70, 70));
		undoMoveAbstractWord = new UndoMoveAbstractWord(wordViewOne, 50, 50, 70, 70,
				mainView, gameState);
		mainView.recordRedoMove(undoMoveAbstractWord);
	}
	
	@Test
	public void testConstruct() {
		assertNotNull(redoController);
	}
	
	@Test
	public void testProcess() {
		redoController.process();
		assertEquals(mainView.getRedoMoves().size(),0);
		assertFalse(mainView.getRedoButton().isEnabled());
		assertTrue(mainView.getUndoButton().isEnabled());
	}
}
