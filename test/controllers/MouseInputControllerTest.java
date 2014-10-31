package controllers;

import common.Constants;
import models.*;

import org.junit.Before;
import org.junit.Test;

import views.MainView;
import views.RowView;
import views.WordView;

import java.awt.event.MouseEvent;

import static org.junit.Assert.*;

public class MouseInputControllerTest {

	GameState gameState;
	MainView mainView;
	MouseInputController controller;
	WordView wordViewOne;
	WordView wordViewTwo;
	unprotectedMemento un = null;
	protectedMemento p = null;

	@Before
	public void initialize() {
		gameState = new GameState(un, p);
		gameState.getProtectedArea().getAbstractWordCollection().clear();
		mainView = new MainView(gameState, un, p);
		wordViewOne = new WordView(new Word("Elephant", WordType.ADVERB),
				new Position(10, 10));
		wordViewTwo = new WordView(new Word("Buffalo", WordType.POSTFIX),
				new Position(50, 50));
		mainView.addProtectedAbstractWordView(wordViewOne);
		mainView.addProtectedAbstractWordView(wordViewTwo);
		controller = new MouseInputController(mainView, gameState);
	}

	@Test
	public void testMousePressedNothingSelected() throws Exception {
		MouseEvent event = new MouseEvent(mainView, 0, 0, 0, 9, 9, 1, false);
		assertNull(controller.selectedWord);
		assertNull(controller.mouseDownPosition);

		controller.mousePressed(event);
		assertNull(controller.selectedWord);
		assertEquals(9, controller.mouseDownPosition.getX());
		assertEquals(9, controller.mouseDownPosition.getY());
	}

	@Test
	public void testMousePressedSelectSomething() throws Exception {
		MouseEvent event = new MouseEvent(mainView, 0, 0, 0, 12, 12, 1, false);
		assertNull(controller.selectedWord);
		assertNull(controller.mouseDownPosition);

		controller.mousePressed(event);
		assertEquals(wordViewOne, controller.selectedWord);
		assertEquals(12, controller.mouseDownPosition.getX());
		assertEquals(12, controller.mouseDownPosition.getY());
	}

	@Test
	public void testMouseDraggedNothingSelected() throws Exception {
		MouseEvent event = new MouseEvent(mainView, 0, 0, 0, 9, 9, 1, false);
		assertNull(controller.selectedWord);
		assertNull(controller.mouseDownPosition);

		controller.mouseDragged(event);
		assertNull(controller.selectedWord);
		assertEquals(9, controller.mouseDownPosition.getX());
		assertEquals(9, controller.mouseDownPosition.getY());
	}

	@Test
	public void testMouseDraggedSomethingSelected() throws Exception {
		MouseEvent event = new MouseEvent(mainView, 0, 0, 0, 15, 30, 1, false);
		controller.selectedWord = wordViewOne;
		controller.mouseDownPosition = new Position(11, 11);

		controller.mouseDragged(event);
		assertEquals(wordViewOne, controller.selectedWord);
		assertEquals(15, controller.mouseDownPosition.getX());
		assertEquals(30, controller.mouseDownPosition.getY());
		assertEquals(14, wordViewOne.getPosition().getX());
		assertEquals(29, wordViewOne.getPosition().getY());
	}

	@Test
	public void testMouseReleasedNothingSelected() throws Exception {
		MouseEvent event = new MouseEvent(mainView, 0, 0, 0, 9, 9, 1, false);
		assertNull(controller.selectedWord);
		assertNull(controller.mouseDownPosition);

		controller.mouseReleased(event);
		assertNull(controller.selectedWord);
		assertNull(controller.mouseDownPosition);
	}

	@Test
	public void testMouseReleasedSomethingSelected() throws Exception {
		MouseEvent event = new MouseEvent(mainView, 0, 0, 0, 13, 13, 1, false);
		controller.selectedWord = wordViewOne;
		controller.mouseDownPosition = new Position(11, 11);

		controller.mouseReleased(event);
		assertEquals(wordViewOne, controller.selectedWord);
		assertEquals(11, controller.mouseDownPosition.getX());
		assertEquals(11, controller.mouseDownPosition.getY());
		assertEquals(10, wordViewOne.getPosition().getX());
		assertEquals(10, wordViewOne.getPosition().getY());
	}

	@Test
	public void testMousePressedHandler() throws Exception {
		wordViewTwo.moveTo(new Position(50,
				Constants.PROTECTED_AREA_HEIGHT + 10));
		mainView.removeProtectedAbstractWordView(wordViewTwo);
		mainView.addUnprotectedAbstractWordView(wordViewTwo);
		controller.selectedWord = wordViewOne;
		controller.mousePressedHandler(new Position(52,
				Constants.PROTECTED_AREA_HEIGHT + 12));
		assertEquals(wordViewTwo, controller.selectedWord);
	}

	@Test
	public void testMousePressedHandlerDisconnect() throws Exception {
		Row row = new Row(wordViewOne.getWord());
		row.connect(wordViewTwo.getWord());
		RowView rowViewOne = new RowView(row, new Position(10, 10), mainView);
		mainView.removeProtectedAbstractWordView(wordViewTwo);
		mainView.removeProtectedAbstractWordView(wordViewOne);
		mainView.addProtectedAbstractWordView(rowViewOne);

		controller.selectedWordToDisconnect = wordViewOne;
		controller.mousePressedHandler(new Position(12, 12));
		assertFalse(rowViewOne.contains(wordViewOne));
	}

	@Test
	public void testMousePressedHandlerDisconnect2() throws Exception {
		Row row = new Row(wordViewOne.getWord());
		row.connect(wordViewTwo.getWord());
		RowView rowViewOne = new RowView(row, new Position(10, 10), mainView);
		mainView.removeProtectedAbstractWordView(wordViewTwo);
		mainView.removeProtectedAbstractWordView(wordViewOne);
		mainView.addProtectedAbstractWordView(rowViewOne);

		controller.selectedWordToDisconnect = wordViewTwo;
		controller.mousePressedHandler(new Position(12, 12));
		assertTrue(rowViewOne.contains(wordViewTwo));
	}

	@Test
	public void testMouseDraggedHandler() throws Exception {
		controller.mousePressedHandler(new Position(48, 48));
		controller.mouseDraggedHandler(new Position(55, 55));
		assertEquals(wordViewTwo, controller.selectedWordToDisconnect);
		controller.mouseDraggedHandler(new Position(57, 57));
		assertEquals(wordViewTwo, controller.selectedWordToDisconnect);
	}

	@Test
	public void testMouseReleasedHandlerWordSelected() throws Exception {
		controller.selectedWord = wordViewOne;
		controller.mousePressedHandler(new Position(wordViewOne.getPosition()
				.getX() + 2, wordViewOne.getPosition().getY() + 2));
		controller.mouseDraggedHandler(new Position(50, 50 + wordViewTwo
				.getHeight() + 2));
		controller.mouseReleasedHandler(new Position(50, 50 + wordViewTwo
				.getHeight() + 2));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewOne));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewTwo));

	}

	@Test
	public void testMouseReleasedHandlerSelectionBox() throws Exception {
		controller.mouseReleasedHandler(new Position(48,
				Constants.PROTECTED_AREA_HEIGHT + 10));

		controller.mousePressedHandler(new Position(48, 48));
		controller.mouseDraggedHandler(new Position(55, 55));
		controller.mouseReleasedHandler(new Position(55, 55));
		assertEquals(wordViewTwo, controller.selectedWordToDisconnect);
	}

}