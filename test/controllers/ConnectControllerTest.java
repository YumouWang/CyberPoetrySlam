package controllers;

import models.GameState;
import models.Position;
import models.Word;
import models.WordType;
import models.protectedMemento;
import models.protectedMementoTest;
import models.unprotectedMemento;

import org.junit.Before;
import org.junit.Test;

import views.MainView;
import views.WordView;
import static org.junit.Assert.*;

public class ConnectControllerTest {
	
	GameState gameState;
	MainView mainView;
	WordView viewOne;
	WordView viewTwo;
	ConnectController connectController;
	protectedMemento p = null;
	unprotectedMemento un = null;

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
		assertTrue(connectController.connect(viewOne, viewTwo));
	}

	@Test
	public void testConnectHorizontal2() throws Exception {
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