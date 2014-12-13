package controllers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QDecoderStream;

import views.AbstractWordView;
import views.MainView;
import views.WordView;
import views.WordViewTest;
import models.GameState;
import models.Position;
import models.ProtectedMemento;
import models.UnprotectedMemento;
import models.Word;
import models.WordType;

public class UndoDisconnectAbstractWordTest {
	GameState gameState;
	MainView mainView;
	ProtectedMemento protectedMemento;
	UnprotectedMemento unprotectedMemento;
	Word wordOne;
	Word wordTwo;
	AbstractWordView wordViewOne;
	AbstractWordView wordViewTwo;
	UndoDisconnectAbstractWord undoDisconnectAbstractWord;
	
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
		AbstractWordView result = null;
		for(AbstractWordView abstractWordView: mainView.getProtectedWordView()){
			if(abstractWordView.contains(wordViewTwo)){
				result = abstractWordView;
				break;
			}
		}
		DisconnectController disconnectController = new DisconnectController(mainView, gameState);
		disconnectController.disconnect(result, wordViewTwo);
		MoveWordController moveWordController = new MoveWordController(mainView, gameState);
		moveWordController.moveWord(wordViewTwo, q, f);
		undoDisconnectAbstractWord = new UndoDisconnectAbstractWord(p,p,f,
				wordViewTwo,wordViewOne,mainView,gameState);
		undoDisconnectAbstractWord.connectTarget = wordViewOne;
	}
	
	@Test
	public void testConstructor() {
		assertNotNull(undoDisconnectAbstractWord);
	}
	
	@Test
	public void testUndo() {
		assertTrue(undoDisconnectAbstractWord.undo());
		assertEquals(wordViewTwo.getPosition().getX(), wordViewOne.getWidth() + 70);
	}
	
	@Test
	public void testRedo() {
		assertTrue(undoDisconnectAbstractWord.undo());
		undoDisconnectAbstractWord.connectTarget = wordViewOne;
		assertTrue(undoDisconnectAbstractWord.execute());
		assertEquals(wordViewTwo.getPosition().getX(), wordViewOne.getWidth() + 70);
	}
}
