package views;

import models.*;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class RowViewTest {

    Row row;
    RowView rowView;
    WordView wordViewOne;
    WordView wordViewTwo;
    GameState gameState;
    MainView mainView;

    @Before
    public void setUp() throws Exception {
        gameState = new GameState();
        mainView = new MainView(gameState);

        Word wordOne = new Word("WordOne", WordType.VERB);
        Word wordTwo = new Word("WordTwo", WordType.ADVERB);
        row = new Row(wordOne);
        row.connect(wordTwo);
        gameState.getProtectedArea().addAbstractWord(row);

        Position positionOne = new Position(0,0);
        wordViewOne = new WordView(wordOne, positionOne);
        mainView.addProtectedAbstractWordView(wordViewOne);

        Position positionTwo = new Position(10,5);
        wordViewTwo = new WordView(wordTwo, positionTwo);
        mainView.addProtectedAbstractWordView(wordViewTwo);

        rowView = new RowView(row, positionOne, mainView);
        mainView.addProtectedAbstractWordView(rowView);
    }

    @Test
    public void testMoveTo() throws Exception {
        assertEquals(0, rowView.getPosition().getX());
        assertEquals(0, rowView.getPosition().getY());
        assertEquals(0, wordViewOne.getPosition().getX());
        assertEquals(0, wordViewOne.getPosition().getY());
        assertEquals(wordViewOne.width, wordViewTwo.getPosition().getX());
        assertEquals(0, wordViewTwo.getPosition().getY());

        Position newPosition = new Position(20, 30);
        rowView.moveTo(newPosition);

        assertEquals(20, rowView.getPosition().getX());
        assertEquals(30, rowView.getPosition().getY());
        assertEquals(20, wordViewOne.getPosition().getX());
        assertEquals(30, wordViewOne.getPosition().getY());
        assertEquals(20 + wordViewOne.width, wordViewTwo.getPosition().getX());
        assertEquals(30, wordViewTwo.getPosition().getY());
    }

    @Test
    public void testSetBackground() throws Exception {
        rowView.setBackground(Color.BLACK);
        assertEquals(Color.BLACK, wordViewOne.label.getBackground());
        assertEquals(Color.BLACK, wordViewTwo.label.getBackground());

        rowView.setBackground(Color.RED);
        assertEquals(Color.RED, wordViewOne.label.getBackground());
        assertEquals(Color.RED, wordViewTwo.label.getBackground());
    }

    @Test
    public void testAddWord() throws Exception {
        Word wordThree = new Word("WordThing", WordType.ADVERB);
        WordView wordViewThree = new WordView(wordThree, new Position(50, 50));

        assertFalse(rowView.contains(wordViewThree));

        rowView.addWord(wordViewThree);

        assertTrue(rowView.contains(wordViewThree));
        assertEquals(rowView.getPosition().getX() +  wordViewOne.width + wordViewTwo.width, wordViewThree.getPosition().getX());
        assertEquals(rowView.getPosition().getY(), wordViewThree.getPosition().getY());
    }

    @Test
    public void testAddWordToFront() throws Exception {
        Word wordThree = new Word("WordThing", WordType.ADVERB);
        WordView wordViewThree = new WordView(wordThree, new Position(50, 50));

        assertFalse(rowView.contains(wordViewThree));

        rowView.addWordToFront(wordViewThree);

        assertTrue(rowView.contains(wordViewThree));
        assertEquals(rowView.getPosition().getX(), wordViewThree.getPosition().getX());
        assertEquals(rowView.getPosition().getY(), wordViewThree.getPosition().getY());
    }

    @Test
    public void testAddRow() throws Exception {
        Word wordThree = new Word("WordThing", WordType.ADVERB);
        Word wordFour = new Word("WordFour", WordType.ADVERB);
        gameState.getProtectedArea().addAbstractWord(wordThree);
        gameState.getProtectedArea().addAbstractWord(wordFour);
        WordView wordViewThree = new WordView(wordThree, new Position(20, 20));
        WordView wordViewFour = new WordView(wordFour, new Position(20, 20));
        mainView.addProtectedAbstractWordView(wordViewThree);
        mainView.addProtectedAbstractWordView(wordViewFour);
        Row rowTwo = new Row(wordThree);
        rowTwo.connect(wordFour);
        RowView rowViewTwo = new RowView(rowTwo, new Position(50, 50), mainView);

        assertFalse(rowView.contains(wordViewThree));
        assertFalse(rowView.contains(wordViewFour));

        rowView.addRow(rowViewTwo);

        assertTrue(rowView.contains(wordViewThree));
        assertTrue(rowView.contains(wordViewFour));
        assertEquals(rowView.getPosition().getX() +  wordViewOne.width + wordViewTwo.width, wordViewThree.getPosition().getX());
        assertEquals(rowView.getPosition().getX() +  wordViewOne.width + wordViewTwo.width + wordViewThree.width, wordViewFour.getPosition().getX());
        assertEquals(rowView.getPosition().getY(), wordViewThree.getPosition().getY());
        assertEquals(rowView.getPosition().getY(), wordViewFour.getPosition().getY());
    }

    @Test
    public void testGetWordViews() throws Exception {
        assertEquals(rowView.wordViews, rowView.getWordViews());
    }

    @Test
    public void testContains() throws Exception {
        Word wordThree = new Word("WordThing", WordType.ADVERB);
        WordView wordViewThree = new WordView(wordThree, new Position(20, 20));

        assertTrue(rowView.contains(rowView));
        assertTrue(rowView.contains(wordViewOne));
        assertTrue(rowView.contains(wordViewTwo));
        assertFalse(rowView.contains(wordViewThree));
    }

    @Test
    public void testRemoveWordViewStart() throws Exception {
        Word wordThree = new Word("WordThing", WordType.ADVERB);
        gameState.getProtectedArea().addAbstractWord(wordThree);
        row.connect(wordThree);
        WordView wordViewThree = new WordView(wordThree, new Position(20, 20));
        mainView.addProtectedAbstractWordView(wordViewThree);
        rowView.addWord(wordViewThree);

        assertTrue(rowView.contains(wordViewThree));
        assertTrue(rowView.contains(wordViewOne));
        assertTrue(rowView.contains(wordViewTwo));

        assertTrue(rowView.removeWordView(wordViewOne));

        assertTrue(rowView.contains(wordViewThree));
        assertTrue(rowView.contains(wordViewTwo));
        assertFalse(rowView.contains(wordViewOne));
        assertEquals(wordViewTwo.getPosition().getX(), rowView.getPosition().getX());
        assertEquals(wordViewTwo.getPosition().getY(), rowView.getPosition().getY());
        assertEquals(wordViewTwo.width + wordViewThree.width, rowView.width);
    }

    @Test
    public void testRemoveWordViewEnd() throws Exception {
        Word wordThree = new Word("WordThing", WordType.ADVERB);
        gameState.getProtectedArea().addAbstractWord(wordThree);
        row.connect(wordThree);
        WordView wordViewThree = new WordView(wordThree, new Position(20, 20));
        mainView.addProtectedAbstractWordView(wordViewThree);
        rowView.addWord(wordViewThree);

        assertTrue(rowView.contains(wordViewThree));
        assertTrue(rowView.contains(wordViewOne));
        assertTrue(rowView.contains(wordViewTwo));

        assertTrue(rowView.removeWordView(wordViewThree));

        assertTrue(rowView.contains(wordViewOne));
        assertTrue(rowView.contains(wordViewTwo));
        assertFalse(rowView.contains(wordViewThree));
        assertEquals(wordViewOne.getPosition().getX(), rowView.getPosition().getX());
        assertEquals(wordViewOne.getPosition().getY(), rowView.getPosition().getY());
        assertEquals(wordViewTwo.width + wordViewOne.width, rowView.width);
    }

    @Test
    public void testRemoveWordViewFail() throws Exception {
        Word wordThree = new Word("WordThing", WordType.ADVERB);
        gameState.getProtectedArea().addAbstractWord(wordThree);
        row.connect(wordThree);
        WordView wordViewThree = new WordView(wordThree, new Position(20, 20));
        mainView.addProtectedAbstractWordView(wordViewThree);
        rowView.addWord(wordViewThree);

        assertTrue(rowView.contains(wordViewThree));
        assertTrue(rowView.contains(wordViewOne));
        assertTrue(rowView.contains(wordViewTwo));

        assertFalse(rowView.removeWordView(wordViewTwo));

        assertTrue(rowView.contains(wordViewThree));
        assertTrue(rowView.contains(wordViewOne));
        assertTrue(rowView.contains(wordViewTwo));
        assertEquals(wordViewOne.getPosition().getX(), rowView.getPosition().getX());
        assertEquals(wordViewOne.getPosition().getY(), rowView.getPosition().getY());
        assertEquals(wordViewTwo.width + wordViewOne.width + wordViewThree.width, rowView.width);
    }

    @Test
    public void testGetWord() throws Exception {
        assertEquals(row, rowView.getWord());
    }

    @Test
    public void testGetSelectedElementNotSelected() throws Exception {
        ConnectionBox box = new ConnectionBox(new Position(-10, -10), 5, 5);
        assertNull(rowView.getSelectedElement(box));
    }

    @Test
    public void testGetSelectedElementOneElement() throws Exception {
        ConnectionBox box = new ConnectionBox(new Position(10, 10), 5, 5);
        AbstractWordView selectedElement = rowView.getSelectedElement(box);

        assertEquals(wordViewOne, selectedElement);
    }

    @Test
    public void testGetSelectedElementMultipleElements() throws Exception {
        ConnectionBox box = new ConnectionBox(new Position(10, 10), 50, 5);
        AbstractWordView selectedElement = rowView.getSelectedElement(box);

        assertEquals(rowView, selectedElement);
    }
}