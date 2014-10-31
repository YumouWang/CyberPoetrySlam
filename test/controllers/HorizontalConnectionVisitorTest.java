package controllers;

import models.*;

import org.junit.Before;
import org.junit.Test;

import views.*;
import static org.junit.Assert.*;

public class HorizontalConnectionVisitorTest {

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
	HorizontalConnectionVisitor horizontalConnectionVisitor;
	unprotectedMemento un = null;
	protectedMemento p = null;

	@Before
	public void setUp() throws Exception {
		gameState = new GameState(un, p);
		protectedArea = gameState.getProtectedArea();
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
		mainView.addProtectedAbstractWordView(poemViewOne);
		mainView.addProtectedAbstractWordView(poemViewTwo);
		horizontalConnectionVisitor = new HorizontalConnectionVisitor(mainView,
				gameState);
	}

	@Test
	public void testVisitWordWord() throws Exception {
		assertTrue(horizontalConnectionVisitor.visit(wordViewOne, wordViewTwo));
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
		assertTrue(horizontalConnectionVisitor.visit(wordViewOne, rowViewOne));
		assertFalse(protectedArea.getAbstractWordCollection().contains(wordOne));
		assertTrue(protectedArea.getAbstractWordCollection().contains(rowOne));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewOne));
		assertTrue(mainView.getProtectedAreaWords().contains(rowViewOne));
		assertTrue(rowOne.contains(wordOne));
		assertTrue(rowViewOne.contains(wordViewOne));
	}

	@Test
	public void testVisitWordPoem() throws Exception {
		assertFalse(horizontalConnectionVisitor.visit(wordViewOne, poemViewOne));
	}

	@Test
	public void testVisitRowWord() throws Exception {
		assertTrue(horizontalConnectionVisitor.visit(rowViewOne, wordViewOne));
		assertTrue(protectedArea.getAbstractWordCollection().contains(rowOne));
		assertFalse(protectedArea.getAbstractWordCollection().contains(wordOne));
		assertTrue(mainView.getProtectedAreaWords().contains(rowViewOne));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewOne));
		assertTrue(rowOne.contains(wordOne));
		assertTrue(rowViewOne.contains(wordViewOne));
	}

	@Test
	public void testVisitRowRow() throws Exception {
		assertTrue(horizontalConnectionVisitor.visit(rowViewOne, rowViewTwo));
		assertTrue(protectedArea.getAbstractWordCollection().contains(rowOne));
		assertFalse(protectedArea.getAbstractWordCollection().contains(rowTwo));
		assertTrue(mainView.getProtectedAreaWords().contains(rowViewOne));
		assertFalse(mainView.getProtectedAreaWords().contains(rowViewTwo));
		assertTrue(rowOne.contains(rowTwo.getWords().get(0)));
		assertTrue(rowViewOne.contains(rowViewTwo.getWordViews().get(0)));
	}

	@Test
	public void testVisitRowPoem() throws Exception {
		assertFalse(horizontalConnectionVisitor.visit(rowViewOne, poemViewOne));
	}

	@Test
	public void testVisitPoemWord() throws Exception {
		assertFalse(horizontalConnectionVisitor.visit(poemViewOne, wordViewOne));
	}

	@Test
	public void testVisitPoemRow() throws Exception {
		assertFalse(horizontalConnectionVisitor.visit(poemViewOne, rowViewOne));
	}

	@Test
	public void testVisitPoemPoem() throws Exception {
		assertFalse(horizontalConnectionVisitor.visit(poemViewOne, poemViewTwo));
	}
}