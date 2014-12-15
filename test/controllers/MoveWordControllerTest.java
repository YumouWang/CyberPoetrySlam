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

import common.Constants;
import views.MainView;
import views.PoemView;
import views.RowView;
import views.WordView;

public class MoveWordControllerTest {
	GameState gameState;
	MainView mainView;
	MoveWordController moveWordController;
	Word wordOne;
	Word wordTwo;
	Word wordThree;
	Word wordFour;
	WordView wordViewOne;
	WordView wordViewTwo;
	WordView wordViewThree;
	WordView wordViewFour;
	Row rowOne;
	RowView rowViewOne;
	Poem poemOne;
	PoemView poemViewOne;

	Position protectedAreaPosition;
	Position unprotectedAreaPosition;
	Position crossLinePosition;
	UnprotectedMemento un = null;
	ProtectedMemento p = null;

	@Before
	public void initialize() {
		gameState = new GameState(null);
		gameState.getProtectedArea().getAbstractWordCollection().clear();
		gameState.getUnprotectedArea().getAbstractWordCollection().clear();
		mainView = new MainView(gameState, null);
		// Create all the word
		wordOne = new Word("Word1", WordType.NOUN);
		wordTwo = new Word("Word2", WordType.NOUN);
		wordThree = new Word("Word3", WordType.NOUN);
		wordFour = new Word("Word4", WordType.NOUN);
		wordViewOne = new WordView(wordOne, new Position(10, 10));
		wordViewTwo = new WordView(wordTwo, new Position(50, 50));
		wordViewThree = new WordView(wordThree, new Position(50, 70));
		wordViewFour = new WordView(wordFour, new Position(10, 500));
		// Add them to the MainView and GameState
		mainView.addProtectedAbstractWordView(wordViewOne);
		gameState.getProtectedArea().addAbstractWord(wordOne);
		mainView.addProtectedAbstractWordView(wordViewTwo);
		gameState.getProtectedArea().addAbstractWord(wordTwo);
		mainView.addProtectedAbstractWordView(wordViewThree);
		gameState.getProtectedArea().addAbstractWord(wordThree);
		mainView.addUnprotectedAbstractWordView(wordViewFour);
		gameState.getUnprotectedArea().addAbstractWord(wordFour);
		// Create three position, one is in protected area, one is in
		// unprotected area, the crossLinePosition is the position near the line
		protectedAreaPosition = new Position(10, 20);
		unprotectedAreaPosition = new Position(30, 500);
		crossLinePosition = new Position(30,
				Constants.PROTECTED_AREA_HEIGHT - 10);
		// Create row
		rowOne = new Row(wordOne);
		rowOne.connect(wordTwo);
		// Create rowView
		rowViewOne = new RowView(rowOne, protectedAreaPosition, mainView);
		// Add rowView to MainView, add row to GameState
		mainView.addProtectedAbstractWordView(rowViewOne);
		gameState.getProtectedArea().addAbstractWord(rowOne);
		// Create poem
		Row rowThree = new Row(wordOne);
		poemOne = new Poem(rowThree);
		poemOne.connect(new Row(wordTwo));
		// Create poemView
		poemViewOne = new PoemView(poemOne, protectedAreaPosition, mainView);
		// Add poem to GameState, add poemView to MainView
		mainView.addProtectedAbstractWordView(poemViewOne);
		gameState.getProtectedArea().addAbstractWord(poemOne);
		moveWordController = new MoveWordController(mainView, gameState);
	}

	@Test
	public void testMoveWord() {
		// test move word from protected area to protected area
		assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertTrue(mainView.getProtectedAreaWords().contains(wordViewOne));
		assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertFalse(mainView.getUnprotectedAreaWords().contains(wordViewOne));
		moveWordController.moveWord(wordViewOne, wordViewOne.getPosition(),
				protectedAreaPosition);
		assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertTrue(mainView.getProtectedAreaWords().contains(wordViewOne));
		assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertFalse(mainView.getUnprotectedAreaWords().contains(wordViewOne));

		// test move word from unprotected area to unprotected area
		assertFalse(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(wordFour));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewFour));
		assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(wordFour));
		assertTrue(mainView.getUnprotectedAreaWords().contains(wordViewFour));
		moveWordController.moveWord(wordViewFour, wordViewFour.getPosition(),
				unprotectedAreaPosition);
		assertFalse(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(wordFour));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewFour));
		assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(wordFour));
		assertTrue(mainView.getUnprotectedAreaWords().contains(wordViewFour));

		// test move word from unprotected area to protected area
		assertFalse(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(wordFour));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewFour));
		assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(wordFour));
		assertTrue(mainView.getUnprotectedAreaWords().contains(wordViewFour));
		moveWordController.moveWord(wordViewFour, wordViewFour.getPosition(),
				protectedAreaPosition);
		assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(wordFour));
		assertTrue(mainView.getProtectedAreaWords().contains(wordViewFour));
		assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(wordFour));
		assertFalse(mainView.getUnprotectedAreaWords().contains(wordViewFour));

		// test move word from protected area to unprotected area
		assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertTrue(mainView.getProtectedAreaWords().contains(wordViewOne));
		assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertFalse(mainView.getUnprotectedAreaWords().contains(wordViewOne));
		moveWordController.moveWord(wordViewOne, wordViewOne.getPosition(),
				unprotectedAreaPosition);
		assertFalse(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewOne));
		assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertTrue(mainView.getUnprotectedAreaWords().contains(wordViewOne));

		// test move row from protected area to protected area
		assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(rowOne));
		assertTrue(mainView.getProtectedAreaWords().contains(rowViewOne));
		assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(rowOne));
		assertFalse(mainView.getUnprotectedAreaWords().contains(rowViewOne));
		moveWordController.moveWord(rowViewOne, rowViewOne.getPosition(),
				protectedAreaPosition);
		assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(rowOne));
		assertTrue(mainView.getProtectedAreaWords().contains(rowViewOne));
		assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(rowOne));
		assertFalse(mainView.getUnprotectedAreaWords().contains(rowViewOne));

		// test move row from protected area to unprotected area
		assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(rowOne));
		assertTrue(mainView.getProtectedAreaWords().contains(rowViewOne));
		assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(rowOne));
		assertFalse(mainView.getUnprotectedAreaWords().contains(rowViewOne));
		moveWordController.moveWord(rowViewOne, rowViewOne.getPosition(),
				unprotectedAreaPosition);
		assertFalse(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewOne));
		assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertTrue(mainView.getUnprotectedAreaWords().contains(wordViewOne));

		// test move poem from protected area to protected area
		assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(poemOne));
		assertTrue(mainView.getProtectedAreaWords().contains(poemViewOne));
		assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(poemOne));
		assertFalse(mainView.getUnprotectedAreaWords().contains(poemViewOne));
		moveWordController.moveWord(poemViewOne, poemViewOne.getPosition(),
				protectedAreaPosition);
		assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(poemOne));
		assertTrue(mainView.getProtectedAreaWords().contains(poemViewOne));
		assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(poemOne));
		assertFalse(mainView.getUnprotectedAreaWords().contains(poemViewOne));

		// test move poem from protected area to unprotected area
		assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(poemOne));
		assertTrue(mainView.getProtectedAreaWords().contains(poemViewOne));
		assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(poemOne));
		assertFalse(mainView.getUnprotectedAreaWords().contains(poemViewOne));
		moveWordController.moveWord(poemViewOne, poemViewOne.getPosition(),
				unprotectedAreaPosition);
		assertFalse(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewOne));
		assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertTrue(mainView.getUnprotectedAreaWords().contains(wordViewOne));

	}

	@Test
	public void testProtectWord() {
		assertFalse(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(wordFour));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewFour));
		assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(wordFour));
		assertTrue(mainView.getUnprotectedAreaWords().contains(wordViewFour));
		moveWordController.protectWord(wordViewFour);
		assertNotNull(moveWordController);
		assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(wordFour));
		assertTrue(mainView.getProtectedAreaWords().contains(wordViewFour));
		assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(wordFour));
		assertFalse(mainView.getUnprotectedAreaWords().contains(wordViewFour));
	}

	@Test
	public void testUnprotectWord() {
		assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertTrue(mainView.getProtectedAreaWords().contains(wordViewOne));
		assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertFalse(mainView.getUnprotectedAreaWords().contains(wordViewOne));
		moveWordController.unprotectWord(wordViewOne);
		assertNotNull(moveWordController);
		assertFalse(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewOne));
		assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertTrue(mainView.getUnprotectedAreaWords().contains(wordViewOne));
	}

	@Test
	public void testReleaseRow() {
		assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(rowOne));
		assertTrue(mainView.getProtectedAreaWords().contains(rowViewOne));
		assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(rowOne));
		assertFalse(mainView.getUnprotectedAreaWords().contains(rowViewOne));
		moveWordController.relaseRow(rowViewOne);
		assertFalse(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(rowOne));
		assertFalse(mainView.getProtectedAreaWords().contains(rowViewOne));
		assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertTrue(mainView.getUnprotectedAreaWords().contains(wordViewOne));
	}

	@Test
	public void testReleasePoem() {
		assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(poemOne));
		assertTrue(mainView.getProtectedAreaWords().contains(poemViewOne));
		assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(poemOne));
		assertFalse(mainView.getUnprotectedAreaWords().contains(poemViewOne));
		moveWordController.relasePoem(poemViewOne);
		assertFalse(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(poemOne));
		assertFalse(mainView.getProtectedAreaWords().contains(poemViewOne));
		assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertTrue(mainView.getUnprotectedAreaWords().contains(wordViewOne));
	}

	@Test
	public void testCrossLine() {
		// move a protected word to cross the line
		assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertTrue(mainView.getProtectedAreaWords().contains(wordViewOne));
		assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertFalse(mainView.getUnprotectedAreaWords().contains(wordViewOne));
		moveWordController.moveWord(wordViewOne, wordViewOne.getPosition(),
				crossLinePosition);
		assertFalse(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewOne));
		assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertTrue(mainView.getUnprotectedAreaWords().contains(wordViewOne));
		assertEquals(wordViewOne.getPosition().getX(), crossLinePosition.getX());
		assertEquals(wordViewOne.getPosition().getY(),
				Constants.PROTECTED_AREA_HEIGHT);

		// move a unprotected word to cross the line
		assertFalse(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(wordFour));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewFour));
		assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(wordFour));
		assertTrue(mainView.getUnprotectedAreaWords().contains(wordViewFour));
		moveWordController.moveWord(wordViewOne, wordViewOne.getPosition(),
				crossLinePosition);
		assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertTrue(mainView.getProtectedAreaWords().contains(wordViewOne));
		assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
				.contains(wordOne));
		assertFalse(mainView.getUnprotectedAreaWords().contains(wordViewOne));
		assertEquals(wordViewOne.getPosition().getX(), crossLinePosition.getX());
		assertEquals(wordViewOne.getPosition().getY(),
				Constants.PROTECTED_AREA_HEIGHT - 20);
	}
}
