package controllers;

import models.*;

import org.junit.Before;
import org.junit.Test;

import views.*;
import static org.junit.Assert.*;

public class DisconnectVisitorTest {

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
	DisconnectVisitor disconnectVisitor;
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
		disconnectVisitor = new DisconnectVisitor(mainView, gameState);
	}

	@Test
	public void testVisitWordWord() throws Exception {
		assertFalse(disconnectVisitor.visit(wordViewOne, wordViewTwo));
	}

	@Test
	public void testVisitWordRow() throws Exception {
		assertFalse(disconnectVisitor.visit(wordViewOne, rowViewOne));
	}

	@Test
	public void testVisitWordPoem() throws Exception {
		assertFalse(disconnectVisitor.visit(wordViewOne, poemViewOne));
	}

	@Test
	public void testVisitRowWordEdgeDisconnect() throws Exception {
		WordView rowWordViewOne = rowViewOne.getWordViews().get(0);
		WordView rowWordViewTwo = rowViewOne.getWordViews().get(1);
		Word rowWordOne = rowOne.getWords().get(0);
		Word rowWordTwo = rowOne.getWords().get(1);
		assertTrue(disconnectVisitor.visit(rowViewOne, rowWordViewOne));

		assertTrue(protectedArea.getAbstractWordCollection().contains(
				rowWordOne));
		assertTrue(protectedArea.getAbstractWordCollection().contains(
				rowWordTwo));
		assertFalse(protectedArea.getAbstractWordCollection().contains(rowOne));
		assertTrue(mainView.getProtectedAreaWords().contains(rowWordViewOne));
		assertTrue(mainView.getProtectedAreaWords().contains(rowWordViewTwo));
		assertFalse(mainView.getProtectedAreaWords().contains(rowViewOne));
		assertFalse(rowOne.contains(rowWordOne));
		assertFalse(rowViewOne.contains(rowWordViewOne));
	}

	@Test
	public void testVisitRowWordMiddleDisconnect() throws Exception {
        rowViewOne.moveTo(new Position(50, 50));
		HorizontalConnectionVisitor connectionVisitor = new HorizontalConnectionVisitor(mainView, gameState);
		assertTrue(connectionVisitor.visit(rowViewOne, wordViewOne));

		WordView rowWordViewOne = rowViewOne.getWordViews().get(0);
		WordView rowWordViewTwo = rowViewOne.getWordViews().get(1);
		WordView rowWordViewThree = rowViewOne.getWordViews().get(2);
		assertEquals(rowWordViewThree, wordViewOne);
		Word rowWordOne = rowOne.getWords().get(0);
		Word rowWordTwo = rowOne.getWords().get(1);
		Word rowWordThree = rowOne.getWords().get(2);
		assertEquals(rowWordThree, wordOne);
		assertTrue(disconnectVisitor.visit(rowViewOne, rowWordViewTwo));

		assertTrue(protectedArea.getAbstractWordCollection().contains(
				rowWordOne));
		assertTrue(protectedArea.getAbstractWordCollection().contains(
				rowWordTwo));
		assertTrue(protectedArea.getAbstractWordCollection().contains(
				rowWordThree));
		assertFalse(protectedArea.getAbstractWordCollection().contains(rowOne));
		assertTrue(mainView.getProtectedAreaWords().contains(rowWordViewOne));
		assertTrue(mainView.getProtectedAreaWords().contains(rowWordViewTwo));
		assertTrue(mainView.getProtectedAreaWords().contains(rowWordViewThree));
		assertFalse(mainView.getProtectedAreaWords().contains(rowViewOne));
		assertFalse(rowOne.contains(rowWordTwo));
		assertFalse(rowViewOne.contains(rowWordViewTwo));
	}

	@Test
	public void testVisitRowRow() throws Exception {
		assertFalse(disconnectVisitor.visit(rowViewOne, rowViewTwo));
	}

	@Test
	public void testVisitRowPoem() throws Exception {
		assertFalse(disconnectVisitor.visit(rowViewOne, poemViewOne));
	}

	@Test
	public void testVisitPoemWordEdgeDisconnect() throws Exception {
		RowView poemRowViewOne = poemViewOne.getRowViews().get(0);
		RowView poemRowViewTwo = poemViewOne.getRowViews().get(1);
		WordView poemWordViewOne = poemRowViewOne.getWordViews().get(0);
		WordView poemWordViewTwo = poemRowViewTwo.getWordViews().get(0);
		Row poemRowOne = poemOne.getRows().get(0);
		Row poemRowTwo = poemOne.getRows().get(1);
		Word poemWordOne = poemRowOne.getWords().get(0);
		Word poemWordTwo = poemRowTwo.getWords().get(0);
		assertTrue(disconnectVisitor.visit(poemViewOne, poemWordViewTwo));

		assertTrue(protectedArea.getAbstractWordCollection().contains(
				poemWordOne));
		assertTrue(protectedArea.getAbstractWordCollection().contains(
				poemWordTwo));
		assertFalse(protectedArea.getAbstractWordCollection().contains(poemOne));
		assertTrue(mainView.getProtectedAreaWords().contains(poemWordViewOne));
		assertTrue(mainView.getProtectedAreaWords().contains(poemWordViewTwo));
		assertFalse(mainView.getProtectedAreaWords().contains(poemViewOne));
		assertFalse(poemOne.contains(poemWordTwo));
		assertFalse(poemViewOne.contains(poemWordViewTwo));
	}

	@Test
	public void testVisitPoemWordMiddleUnsuccessfulDisconnect()
			throws Exception {
		VerticalConnectionVisitor verticalConnectionVisitor = new VerticalConnectionVisitor(
				mainView, gameState);
		HorizontalConnectionVisitor horizontalConnectionVisitor = new HorizontalConnectionVisitor(
				mainView, gameState);
		verticalConnectionVisitor.visit(poemViewOne, rowViewOne);

		RowView poemRowViewTwo = poemViewOne.getRowViews().get(1);

		horizontalConnectionVisitor.visit(poemRowViewTwo, wordViewOne);
		horizontalConnectionVisitor.visit(poemRowViewTwo, wordViewTwo);
		assertFalse(disconnectVisitor.visit(poemViewOne, wordViewOne));
	}

	@Test
	public void testVisitPoemWordMiddleDisconnect() throws Exception {
		VerticalConnectionVisitor connectionVisitor = new VerticalConnectionVisitor(
				mainView, gameState);
		connectionVisitor.visit(poemViewOne, wordViewOne);

		RowView poemRowViewOne = poemViewOne.getRowViews().get(0);
		RowView poemRowViewTwo = poemViewOne.getRowViews().get(1);
		RowView poemRowViewThree = poemViewOne.getRowViews().get(2);
		WordView poemWordViewOne = poemRowViewOne.getWordViews().get(0);
		WordView poemWordViewTwo = poemRowViewTwo.getWordViews().get(0);
		WordView poemWordViewThree = poemRowViewThree.getWordViews().get(0);
		assertEquals(poemWordViewThree, wordViewOne);
		Row poemRowOne = poemOne.getRows().get(0);
		Row poemRowTwo = poemOne.getRows().get(1);
		Row poemRowThree = poemOne.getRows().get(2);
		Word poemWordOne = poemRowOne.getWords().get(0);
		Word poemWordTwo = poemRowTwo.getWords().get(0);
		Word poemWordThree = poemRowThree.getWords().get(0);
		assertEquals(poemWordThree, wordOne);
		assertTrue(disconnectVisitor.visit(poemViewOne, poemWordViewTwo));

		assertTrue(protectedArea.getAbstractWordCollection().contains(
				poemWordOne));
		assertTrue(protectedArea.getAbstractWordCollection().contains(
				poemWordTwo));
		assertTrue(protectedArea.getAbstractWordCollection().contains(
				poemWordThree));
		assertFalse(protectedArea.getAbstractWordCollection().contains(poemOne));
		assertTrue(mainView.getProtectedAreaWords().contains(poemWordViewOne));
		assertTrue(mainView.getProtectedAreaWords().contains(poemWordViewTwo));
		assertTrue(mainView.getProtectedAreaWords().contains(poemWordViewThree));
		assertFalse(mainView.getProtectedAreaWords().contains(poemViewOne));
		assertFalse(poemOne.contains(poemWordTwo));
		assertFalse(poemViewOne.contains(poemWordViewTwo));
	}

	@Test
	public void testVisitPoemRowEdgeDisconnect() throws Exception {
		RowView poemRowViewOne = poemViewOne.getRowViews().get(0);
		RowView poemRowViewTwo = poemViewOne.getRowViews().get(1);
		WordView poemWordViewOne = poemRowViewOne.getWordViews().get(0);
		WordView poemWordViewTwo = poemRowViewTwo.getWordViews().get(0);
		Row poemRowOne = poemOne.getRows().get(0);
		Row poemRowTwo = poemOne.getRows().get(1);
		Word poemWordOne = poemRowOne.getWords().get(0);
		Word poemWordTwo = poemRowTwo.getWords().get(0);
		assertTrue(disconnectVisitor.visit(poemViewOne, poemRowViewOne));

		assertTrue(protectedArea.getAbstractWordCollection().contains(
				poemWordOne));
		assertTrue(protectedArea.getAbstractWordCollection().contains(
				poemWordTwo));
		assertFalse(protectedArea.getAbstractWordCollection().contains(poemOne));
		assertTrue(mainView.getProtectedAreaWords().contains(poemWordViewOne));
		assertTrue(mainView.getProtectedAreaWords().contains(poemWordViewTwo));
		assertFalse(mainView.getProtectedAreaWords().contains(poemViewOne));
		assertFalse(poemOne.contains(poemRowOne));
		assertFalse(poemViewOne.contains(poemRowViewOne));
	}

	@Test
	public void testVisitPoemRowMiddleDisconnect() throws Exception {
		VerticalConnectionVisitor connectionVisitor = new VerticalConnectionVisitor(
				mainView, gameState);
		connectionVisitor.visit(poemViewOne, wordViewOne);

		RowView poemRowViewOne = poemViewOne.getRowViews().get(0);
		RowView poemRowViewTwo = poemViewOne.getRowViews().get(1);
		RowView poemRowViewThree = poemViewOne.getRowViews().get(2);
		WordView poemWordViewOne = poemRowViewOne.getWordViews().get(0);
		WordView poemWordViewTwo = poemRowViewTwo.getWordViews().get(0);
		WordView poemWordViewThree = poemRowViewThree.getWordViews().get(0);
		assertEquals(poemWordViewThree, wordViewOne);
		Row poemRowOne = poemOne.getRows().get(0);
		Row poemRowTwo = poemOne.getRows().get(1);
		Row poemRowThree = poemOne.getRows().get(2);
		Word poemWordOne = poemRowOne.getWords().get(0);
		Word poemWordTwo = poemRowTwo.getWords().get(0);
		Word poemWordThree = poemRowThree.getWords().get(0);
		assertEquals(poemWordThree, wordOne);
		assertTrue(disconnectVisitor.visit(poemViewOne, poemRowViewTwo));

		assertTrue(protectedArea.getAbstractWordCollection().contains(
				poemWordOne));
		assertTrue(protectedArea.getAbstractWordCollection().contains(
				poemWordTwo));
		assertTrue(protectedArea.getAbstractWordCollection().contains(
				poemWordThree));
		assertFalse(protectedArea.getAbstractWordCollection().contains(poemOne));
		assertTrue(mainView.getProtectedAreaWords().contains(poemWordViewOne));
		assertTrue(mainView.getProtectedAreaWords().contains(poemWordViewTwo));
		assertTrue(mainView.getProtectedAreaWords().contains(poemWordViewThree));
		assertFalse(mainView.getProtectedAreaWords().contains(poemViewOne));
		assertFalse(poemOne.contains(poemRowTwo));
		assertFalse(poemViewOne.contains(poemRowViewTwo));
	}

	@Test
	public void testVisitPoemPoem() throws Exception {
		assertFalse(disconnectVisitor.visit(poemViewOne, poemViewTwo));
	}

	@Test
	public void testReducePoemToWordIfPossible() throws Exception {
		Word word = wordViewOne.getWord();
		Row row = new Row(word);
		Poem poem = new Poem(row);
		protectedArea.addAbstractWord(poem);
		disconnectVisitor.reducePoemToWordIfPossible(poem);
		assertFalse(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(poem));
		assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(word));
	}

	@Test
	public void testReduceRowToWordIfPossible() throws Exception {
		Word word = wordViewOne.getWord();
		Row row = new Row(word);
		protectedArea.addAbstractWord(row);
		disconnectVisitor.reduceRowToWordIfPossible(row);
		assertFalse(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(row));
		assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
				.contains(word));
	}

	@Test
	public void testReducePoemViewToWordViewIfPossible() throws Exception {
		Word word = wordViewOne.getWord();
		Row row = new Row(word);
		Poem poem = new Poem(row);
		PoemView poemView = new PoemView(poem, new Position(0, 0), mainView);
		mainView.addProtectedAbstractWordView(poemView);
		disconnectVisitor.reducePoemViewToWordViewIfPossible(poemView);
		assertFalse(mainView.getProtectedAreaWords().contains(poemView));
		assertTrue(mainView.getProtectedAreaWords().contains(wordViewOne));
	}

	@Test
	public void testReduceRowViewToWordViewIfPossible() throws Exception {
		Word word = wordViewOne.getWord();
		Row row = new Row(word);
		RowView rowView = new RowView(row, new Position(0, 0), mainView);
		mainView.addProtectedAbstractWordView(rowView);
		disconnectVisitor.reduceRowViewToWordViewIfPossible(rowView);
		assertFalse(mainView.getProtectedAreaWords().contains(rowView));
		assertTrue(mainView.getProtectedAreaWords().contains(wordViewOne));
	}

}