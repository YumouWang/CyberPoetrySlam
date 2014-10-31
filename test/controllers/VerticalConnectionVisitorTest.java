package controllers;

import models.*;

import org.junit.Before;
import org.junit.Test;

import views.*;
import static org.junit.Assert.*;

public class VerticalConnectionVisitorTest {

	GameState gameState;
	Area protectedArea;
	MainView mainView;
	Word wordOne;
	Word wordTwo;
	WordView wordViewOne;
	WordView wordViewTwo;
	Row rowOne;
	Row rowTwo;
	RowView rowViewOne;
	RowView rowViewTwo;
	Poem poemOne;
	Poem poemTwo;
	PoemView poemViewOne;
	PoemView poemViewTwo;
	VerticalConnectionVisitor verticalConnectionVisitor;
	unprotectedMemento un = null;
	protectedMemento p = null;

	@Before
	public void setUp() throws Exception {
		gameState = new GameState(un, p);
		protectedArea = gameState.getProtectedArea();
        protectedArea.getAbstractWordCollection().clear();
        gameState.getUnprotectedArea().getAbstractWordCollection().clear();
		mainView = new MainView(gameState, un, p);
		// Create all the words separately
		wordOne = new Word("Word", WordType.ADVERB);
		wordTwo = new Word("Word", WordType.ADVERB);
		Word wordThree = new Word("Word", WordType.ADVERB);
		Word wordFour = new Word("Word", WordType.ADVERB);
		Word wordFive = new Word("Word", WordType.ADVERB);
		Word wordSix = new Word("Word", WordType.ADVERB);
		Word wordSeven = new Word("Word", WordType.ADVERB);
		Word wordEight = new Word("Word", WordType.ADVERB);
		Word wordNine = new Word("Word", WordType.ADVERB);
		Word wordTen = new Word("Word", WordType.ADVERB);
		// Add them to the model
		protectedArea.addAbstractWord(wordOne);
		protectedArea.addAbstractWord(wordTwo);
		Position pos = new Position(0, 0);
		// Make views for the words
		wordViewOne = new WordView(wordOne, pos);
		wordViewTwo = new WordView(wordTwo, pos);
		WordView wordViewThree = new WordView(wordThree, pos);
		WordView wordViewFour = new WordView(wordFour, pos);
		WordView wordViewFive = new WordView(wordFive, pos);
		WordView wordViewSix = new WordView(wordSix, pos);
		WordView wordViewSeven = new WordView(wordSeven, pos);
		WordView wordViewEight = new WordView(wordEight, pos);
		WordView wordViewNine = new WordView(wordNine, pos);
		WordView wordViewTen = new WordView(wordTen, pos);
		// Add them to the mainView
		mainView.addProtectedAbstractWordView(wordViewOne);
		mainView.addProtectedAbstractWordView(wordViewTwo);
		mainView.addProtectedAbstractWordView(wordViewThree);
		mainView.addProtectedAbstractWordView(wordViewFour);
		mainView.addProtectedAbstractWordView(wordViewFive);
		mainView.addProtectedAbstractWordView(wordViewSix);
		mainView.addProtectedAbstractWordView(wordViewSeven);
		mainView.addProtectedAbstractWordView(wordViewEight);
		mainView.addProtectedAbstractWordView(wordViewNine);
		mainView.addProtectedAbstractWordView(wordViewTen);
		// Create rows
		rowOne = new Row(wordThree);
		rowOne.connect(wordFour);
		rowTwo = new Row(wordFive);
		rowTwo.connect(wordSix);
		protectedArea.addAbstractWord(rowOne);
		protectedArea.addAbstractWord(rowTwo);
		// Create rowViews
		rowViewOne = new RowView(rowOne, pos, mainView);
		rowViewTwo = new RowView(rowTwo, pos, mainView);
        mainView.removeProtectedAbstractWordView(wordViewThree);
        mainView.removeProtectedAbstractWordView(wordViewFour);
        mainView.removeProtectedAbstractWordView(wordViewFive);
        mainView.removeProtectedAbstractWordView(wordViewSix);
		mainView.addProtectedAbstractWordView(rowViewOne);
		mainView.addProtectedAbstractWordView(rowViewTwo);
		// Create Poems
		Row rowThree = new Row(wordSeven);
		poemOne = new Poem(rowThree);
		poemOne.connect(new Row(wordEight));
		Row rowFour = new Row(wordNine);
		poemTwo = new Poem(rowFour);
		poemTwo.connect(new Row(wordTen));
		protectedArea.addAbstractWord(poemOne);
		protectedArea.addAbstractWord(poemTwo);
		// Create PoemViews
		poemViewOne = new PoemView(poemOne, pos, mainView);
		poemViewTwo = new PoemView(poemTwo, pos, mainView);
        mainView.removeProtectedAbstractWordView(wordViewSeven);
        mainView.removeProtectedAbstractWordView(wordViewEight);
        mainView.removeProtectedAbstractWordView(wordViewNine);
        mainView.removeProtectedAbstractWordView(wordViewTen);
		mainView.addProtectedAbstractWordView(poemViewOne);
		mainView.addProtectedAbstractWordView(poemViewTwo);
		verticalConnectionVisitor = new VerticalConnectionVisitor(mainView,gameState);
	}

	@Test
	public void testVisitWordWord() throws Exception {
        wordViewOne.moveTo(new Position(10, 100));
		assertTrue(verticalConnectionVisitor.visit(wordViewOne, wordViewTwo));
		assertFalse(protectedArea.getAbstractWordCollection().contains(wordOne));
		assertFalse(protectedArea.getAbstractWordCollection().contains(wordTwo));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewOne));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewTwo));

		boolean foundResult = false;
		for (AbstractWord word : protectedArea.getAbstractWordCollection()) {
			if (word.contains(wordOne) && word.contains(wordTwo)) {
				foundResult = true;
			}
		}
		assertTrue(foundResult);

		boolean foundResultView = false;
		for (AbstractWordView view : mainView.getProtectedAreaWords()) {
			if (view.contains(wordViewOne) && view.contains(wordViewTwo)) {
				foundResultView = true;
			}
		}
		assertTrue(foundResultView);
	}

	@Test
	public void testVisitWordRow() throws Exception {
        wordViewOne.moveTo(new Position(10, 100));
		assertTrue(verticalConnectionVisitor.visit(wordViewOne, rowViewTwo));
		assertFalse(protectedArea.getAbstractWordCollection().contains(wordOne));
		assertFalse(protectedArea.getAbstractWordCollection().contains(rowTwo));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewOne));
		assertFalse(mainView.getProtectedAreaWords().contains(rowViewTwo));

		boolean foundResult = false;
		for (AbstractWord word : protectedArea.getAbstractWordCollection()) {
			if (word.contains(wordOne) && word.contains(rowTwo)) {
				foundResult = true;
			}
		}
		assertTrue(foundResult);

		boolean foundResultView = false;
		for (AbstractWordView view : mainView.getProtectedAreaWords()) {
			if (view.contains(wordViewOne) && view.contains(rowViewTwo)) {
				foundResultView = true;
			}
		}
		assertTrue(foundResultView);
	}

	@Test
	public void testVisitWordPoem() throws Exception {
        wordViewOne.moveTo(new Position(10, 100));
		assertTrue(verticalConnectionVisitor.visit(wordViewOne, poemViewOne));
		assertFalse(protectedArea.getAbstractWordCollection().contains(wordOne));
		assertTrue(protectedArea.getAbstractWordCollection().contains(poemOne));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewOne));
		assertTrue(mainView.getProtectedAreaWords().contains(poemViewOne));
		assertTrue(poemOne.contains(wordOne));
		assertTrue(poemViewOne.contains(wordViewOne));
	}

	@Test
	public void testVisitRowWord() throws Exception {
        rowViewOne.moveTo(new Position(10, 100));
		assertTrue(verticalConnectionVisitor.visit(rowViewOne, wordViewOne));
		assertFalse(protectedArea.getAbstractWordCollection().contains(rowOne));
		assertFalse(protectedArea.getAbstractWordCollection().contains(wordOne));
		assertFalse(mainView.getProtectedAreaWords().contains(rowViewOne));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewOne));

		boolean foundResult = false;
		for (AbstractWord word : protectedArea.getAbstractWordCollection()) {
			if (word.contains(rowOne) && word.contains(wordOne)) {
				foundResult = true;
			}
		}
		assertTrue(foundResult);

		boolean foundResultView = false;
		for (AbstractWordView view : mainView.getProtectedAreaWords()) {
			if (view.contains(rowViewOne) && view.contains(wordViewOne)) {
				foundResultView = true;
			}
		}
		assertTrue(foundResultView);
	}

	@Test
	public void testVisitRowRow() throws Exception {
        rowViewOne.moveTo(new Position(10, 100));
		assertTrue(verticalConnectionVisitor.visit(rowViewOne, rowViewTwo));
		assertFalse(protectedArea.getAbstractWordCollection().contains(rowOne));
		assertFalse(protectedArea.getAbstractWordCollection().contains(rowTwo));
		assertFalse(mainView.getProtectedAreaWords().contains(rowViewOne));
		assertFalse(mainView.getProtectedAreaWords().contains(rowViewTwo));

		boolean foundResult = false;
		for (AbstractWord word : protectedArea.getAbstractWordCollection()) {
			if (word.contains(rowOne) && word.contains(rowTwo)) {
				foundResult = true;
			}
		}
		assertTrue(foundResult);

		boolean foundResultView = false;
		for (AbstractWordView view : mainView.getProtectedAreaWords()) {
			if (view.contains(rowViewOne) && view.contains(rowViewTwo)) {
				foundResultView = true;
			}
		}
		assertTrue(foundResultView);
	}

	@Test
	public void testVisitRowPoem() throws Exception {
        rowViewOne.moveTo(new Position(10, 100));
		assertTrue(verticalConnectionVisitor.visit(rowViewOne, poemViewOne));
		assertFalse(protectedArea.getAbstractWordCollection().contains(rowOne));
		assertTrue(protectedArea.getAbstractWordCollection().contains(poemOne));
		assertFalse(mainView.getProtectedAreaWords().contains(rowViewOne));
		assertTrue(mainView.getProtectedAreaWords().contains(poemViewOne));
		assertTrue(poemOne.contains(rowOne));
		assertTrue(poemViewOne.contains(rowViewOne));
	}

	@Test
	public void testVisitPoemWord() throws Exception {
        poemViewOne.moveTo(new Position(10, 100));
		assertTrue(verticalConnectionVisitor.visit(poemViewOne, wordViewOne));
		assertTrue(protectedArea.getAbstractWordCollection().contains(poemOne));
		assertFalse(protectedArea.getAbstractWordCollection().contains(wordOne));
		assertTrue(mainView.getProtectedAreaWords().contains(poemViewOne));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewOne));
		assertTrue(poemOne.contains(wordOne));
		assertTrue(poemViewOne.contains(wordViewOne));
	}

	@Test
	public void testVisitPoemRow() throws Exception {
        poemViewOne.moveTo(new Position(10, 100));
		assertTrue(verticalConnectionVisitor.visit(poemViewOne, rowViewOne));
		assertTrue(protectedArea.getAbstractWordCollection().contains(poemOne));
		assertFalse(protectedArea.getAbstractWordCollection().contains(rowOne));
		assertTrue(mainView.getProtectedAreaWords().contains(poemViewOne));
		assertFalse(mainView.getProtectedAreaWords().contains(rowViewOne));
		assertTrue(poemOne.contains(rowOne));
		assertTrue(poemViewOne.contains(rowViewOne));
	}

	@Test
	public void testVisitPoemPoem() throws Exception {
        poemViewOne.moveTo(new Position(10, 100));
		assertTrue(verticalConnectionVisitor.visit(poemViewOne, poemViewTwo));
		assertTrue(protectedArea.getAbstractWordCollection().contains(poemOne));
		assertFalse(protectedArea.getAbstractWordCollection().contains(poemTwo));
		assertTrue(mainView.getProtectedAreaWords().contains(poemViewOne));
		assertFalse(mainView.getProtectedAreaWords().contains(poemViewTwo));
		assertTrue(poemOne.contains(poemTwo.getRows().get(0)));
		assertTrue(poemViewOne.contains(poemViewTwo.getRowViews().get(0)));
	}

}