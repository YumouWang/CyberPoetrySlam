package controllers;

import static org.junit.Assert.*;
import models.GameState;
import models.Poem;
import models.Position;
import models.Row;
import models.Word;
import models.WordType;
import models.ProtectedMemento;
import models.UnprotectedMemento;

import org.junit.Before;
import org.junit.Test;

import views.MainView;
import views.PoemView;
import views.WordView;

public class ShiftRowControllerTestTest {
	GameState gameState;
	MainView mainView;
	WordView viewOne;
	WordView viewTwo;
	WordView viewThree;
	WordView viewFour;
	WordView viewFive;
	PoemView poemView;
	ShiftRowController shiftRowController;
	ProtectedMemento p = null;
	UnprotectedMemento un = null;

	@Before
	public void setUp() throws Exception {
		gameState = new GameState(null);
		gameState.getProtectedArea().getAbstractWordCollection().clear();
        gameState.getUnprotectedArea().getAbstractWordCollection().clear();
		mainView = new MainView(gameState, null);

		Word wordOne = new Word("MyWord", WordType.ANY);
		Word wordTwo = new Word("MyOtherWord", WordType.ADJECTIVE);
		Word wordThree = new Word("MyThirdWord", WordType.NOUN);
		Word wordFour = new Word("Fourth", WordType.ANY);
		Word wordFive = new Word("Fifth", WordType.ANY);
		Poem poem = new Poem(new Row(wordOne));
		poem.connect(new Row(wordTwo));
		poem.connect(new Row(wordThree));
		poem.connect(new Row(wordFour));
		viewOne = new WordView(wordOne, new Position(1, 71));
		viewTwo = new WordView(wordTwo, new Position(1, 110));
		viewThree = new WordView(wordThree, new Position(1, 150));
		viewFour = new WordView(wordFour, new Position(1, 200));
		viewFive = new WordView(wordFive, new Position(250, 220));

		mainView.addProtectedAbstractWordView(viewOne);
		mainView.addProtectedAbstractWordView(viewTwo);
		mainView.addProtectedAbstractWordView(viewThree);
		mainView.addProtectedAbstractWordView(viewFour);
		mainView.addProtectedAbstractWordView(viewFive);
		poemView = new PoemView(poem, new Position(100, 210), mainView);
		mainView.removeProtectedAbstractWordView(viewTwo);
		mainView.removeProtectedAbstractWordView(viewOne);
		mainView.removeProtectedAbstractWordView(viewThree);
		mainView.removeProtectedAbstractWordView(viewFour);
		mainView.addProtectedAbstractWordView(poemView);

		shiftRowController = new ShiftRowController(mainView, gameState);
	}

	@Test
	public void testOverlappingWord() throws Exception {
		shiftRowController.shiftRow(poemView, poemView.getRowViews().get(1),
				new Position(260, 34), new Position(10, 23));
		boolean WordOverlapping = poemView.getRowViews().get(1)
				.isOverlapping(viewFive);
		assertTrue(!WordOverlapping);

		/*shiftRowController.shiftRow(poemView, poemView.getRowViews().get(1),
				new Position(10, 34), new Position(210, 23));
		boolean isAdjacent = false;
		if (poemView.getRowViews().get(1).isAdjacentTo(viewFour) != AdjacencyType.NOT_ADJACENT) {
			isAdjacent = true;
		}
		assertTrue(isAdjacent);*/
	}
	
	@Test
	public void test() throws Exception {
		shiftRowController.shiftRow(poemView, poemView.getRowViews().get(0),
				new Position(5, 34), new Position(3, 23));
		Position pos = poemView.getRowViews().get(0).getPosition();
		assertEquals(102, pos.getX());
		assertEquals(210, pos.getY());

		shiftRowController.shiftRow(poemView, poemView.getRowViews().get(0),
				new Position(10, 34), new Position(150, 23));
		boolean PoemConnected = poemView.getRowViews().get(0)
				.isOverlapping(poemView.getRowViews().get(1));
		assertTrue(PoemConnected);

		shiftRowController.shiftRow(poemView, poemView.getRowViews().get(1),
				new Position(210, 34), new Position(10, 23));
		boolean PoemConnected1 = (poemView.getRowViews().get(1)
				.isOverlapping(poemView.getRowViews().get(0)))
				&& (poemView.getRowViews().get(1).isOverlapping(poemView
						.getRowViews().get(2)));
		assertTrue(PoemConnected1);

		shiftRowController.shiftRow(poemView, poemView.getRowViews().get(3),
				new Position(210, 34), new Position(10, 23));
		boolean PoemConnected2 = poemView.getRowViews().get(3)
				.isOverlapping(poemView.getRowViews().get(2));
		assertTrue(PoemConnected2);

		shiftRowController.shiftRow(poemView, poemView.getRowViews().get(2),
				new Position(10, 34), new Position(210, 23));
		boolean isOutOfBounds = mainView.isMoveOutOfBounds(poemView
				.getRowViews().get(2), poemView.getRowViews().get(2)
				.getPosition());
		assertTrue(!isOutOfBounds);

	}

	
}
