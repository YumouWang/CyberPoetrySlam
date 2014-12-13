package controllers;

import static org.junit.Assert.assertTrue;
import models.GameState;
import models.Position;
import models.ProtectedMemento;
import models.UnprotectedMemento;
import models.Word;
import models.WordType;

import org.junit.Before;
import org.junit.Test;

import views.MainView;
import views.WordView;

public class ConnectControllerTest {
	
	GameState gameState;
	MainView mainView;
	WordView viewOne;
	WordView viewTwo;
	ConnectController connectController;
	ProtectedMemento p = null;
	UnprotectedMemento un = null;

	@Before
	public void setUp()
			throws Exception {
		gameState = new GameState(un, p);
		mainView = new MainView(gameState, un, p);

		Word wordOne = new Word("MyWord", WordType.ANY);
		Word wordTwo = new Word("MyOtherWord", WordType.ADJECTIVE);
		viewOne = new WordView(wordOne, new Position(0, 0));
		viewTwo = new WordView(wordTwo, new Position(0, 23));
		mainView.addProtectedAbstractWordView(viewOne);
		mainView.addProtectedAbstractWordView(viewTwo);

		connectController = new ConnectController(mainView, gameState);
	}

	@Test
	public void testConnectHorizontal() throws Exception {
		viewTwo.moveTo(new Position(viewOne.getWidth() + 1, 0));
		assertTrue(connectController.connect(viewOne, viewTwo));
	}

	@Test
	public void testConnectHorizontal2() throws Exception {
		viewTwo.moveTo(new Position(viewOne.getWidth() + 1, 0));
		assertTrue(connectController.connect(viewTwo, viewOne));
	}

	@Test
	public void testConnectVertical() throws Exception {
		assertTrue(connectController.connect(viewOne, viewTwo));
	}

	@Test
	public void testConnectVertical2() throws Exception {
		assertTrue(connectController.connect(viewTwo, viewOne));
	}
}