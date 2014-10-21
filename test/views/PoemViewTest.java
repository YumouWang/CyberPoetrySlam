package views;

import controllers.DisconnectVisitor;
import models.*;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class PoemViewTest {

    Poem poem;
    PoemView poemView;
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
        Row rowOne = new Row(wordOne);
        Row rowTwo = new Row(wordTwo);
        poem = new Poem(rowOne);
        poem.connect(rowTwo);
        gameState.getProtectedArea().addAbstractWord(poem);

        Position positionOne = new Position(0,0);
        wordViewOne = new WordView(wordOne, positionOne);
        mainView.addProtectedAbstractWordView(wordViewOne);

        Position positionTwo = new Position(10,5);
        wordViewTwo = new WordView(wordTwo, positionTwo);
        mainView.addProtectedAbstractWordView(wordViewTwo);

        poemView = new PoemView(poem, positionOne, mainView);
        mainView.addProtectedAbstractWordView(poemView);
    }

    @Test
    public void testMoveTo() throws Exception {
        assertEquals(0, poemView.getPosition().getX());
        assertEquals(0, poemView.getPosition().getY());
        assertEquals(0, wordViewOne.getPosition().getX());
        assertEquals(0, wordViewOne.getPosition().getY());
        assertEquals(0, wordViewTwo.getPosition().getX());
        assertEquals(wordViewOne.height, wordViewTwo.getPosition().getY());

        Position newPosition = new Position(20, 30);
        poemView.moveTo(newPosition);

        assertEquals(20, poemView.getPosition().getX());
        assertEquals(30, poemView.getPosition().getY());
        assertEquals(20, wordViewOne.getPosition().getX());
        assertEquals(30, wordViewOne.getPosition().getY());
        assertEquals(20, wordViewTwo.getPosition().getX());
        assertEquals(30 + wordViewOne.height, wordViewTwo.getPosition().getY());
    }

    @Test
    public void testSetBackground() throws Exception {
        poemView.setBackground(Color.BLACK);
        assertEquals(Color.BLACK, wordViewOne.label.getBackground());
        assertEquals(Color.BLACK, wordViewTwo.label.getBackground());

        poemView.setBackground(Color.RED);
        assertEquals(Color.RED, wordViewOne.label.getBackground());
        assertEquals(Color.RED, wordViewTwo.label.getBackground());
    }

    @Test
    public void testAddRow() throws Exception {
        Word wordThree = new Word("WordThing", WordType.ADVERB);
        Row rowThree = new Row(wordThree);
        WordView wordViewThree = new WordView(wordThree, new Position(50, 50));
        mainView.addProtectedAbstractWordView(wordViewThree);
        RowView rowViewThree = new RowView(rowThree, new Position(40, 40), mainView);

        assertFalse(poemView.contains(wordViewThree));
        assertFalse(poemView.contains(rowViewThree));

        poemView.addRow(rowViewThree);

        assertTrue(poemView.contains(wordViewThree));
        assertTrue(poemView.contains(rowViewThree));
        assertEquals(poemView.getPosition().getX(), rowViewThree.getPosition().getX());
        assertEquals(poemView.getPosition().getY() + wordViewOne.height + wordViewTwo.height, rowViewThree.getPosition().getY());
    }

    @Test
    public void testAddRowToTop() throws Exception {
        Word wordThree = new Word("WordThing", WordType.ADVERB);
        Row rowThree = new Row(wordThree);
        WordView wordViewThree = new WordView(wordThree, new Position(50, 50));
        mainView.addProtectedAbstractWordView(wordViewThree);
        RowView rowViewThree = new RowView(rowThree, new Position(40, 40), mainView);

        assertFalse(poemView.contains(wordViewThree));
        assertFalse(poemView.contains(rowViewThree));

        poemView.addRowToTop(rowViewThree);

        assertTrue(poemView.contains(wordViewThree));
        assertTrue(poemView.contains(rowViewThree));
        assertEquals(poemView.getPosition().getX(), rowViewThree.getPosition().getX());
        assertEquals(poemView.getPosition().getY(), rowViewThree.getPosition().getY());
    }

    @Test
    public void testAddPoem() throws Exception {
        Word wordThree = new Word("WordThing", WordType.ADVERB);
        Row rowThree = new Row(wordThree);
        Poem poemThree = new Poem(rowThree);
        WordView wordViewThree = new WordView(wordThree, new Position(50, 50));
        mainView.addProtectedAbstractWordView(wordViewThree);
        PoemView poemViewThree = new PoemView(poemThree, new Position(40, 40), mainView);

        assertFalse(poemView.contains(wordViewThree));
        assertFalse(poemView.contains(poemViewThree));

        poemView.addPoem(poemViewThree);

        assertTrue(poemView.contains(wordViewThree));
        assertEquals(poemView.getPosition().getX(), wordViewThree.getPosition().getX());
        assertEquals(poemView.getPosition().getY() + wordViewOne.height + wordViewTwo.height, wordViewThree.getPosition().getY());
    }

    @Test
    public void testGetRowViews() throws Exception {
        assertEquals(poemView.rowViews, poemView.getRowViews());
    }

    @Test
    public void testGetSelectedElementNotSelected() throws Exception {
        ConnectionBox box = new ConnectionBox(new Position(-10, -10), 5, 5);
        assertNull(poemView.getSelectedElement(box));
    }

    @Test
    public void testGetSelectedElementOneWord() throws Exception {
        ConnectionBox box = new ConnectionBox(new Position(10, 10), 5, 5);
        AbstractWordView selectedElement = poemView.getSelectedElement(box);

        assertEquals(wordViewOne, selectedElement);
    }

    @Test
    public void testGetSelectedElementOneRowMultipleWords() throws Exception {
        ConnectionBox box = new ConnectionBox(new Position(10, 10), 100, 5);

        Word wordThree = new Word("WordThing", WordType.ADVERB);
        WordView wordViewThree = new WordView(wordThree, new Position(50, 50));

        RowView rowView = poemView.getRowViews().get(0);
        rowView.addWord(wordViewThree);

        AbstractWordView selectedElement = poemView.getSelectedElement(box);

        assertEquals(rowView, selectedElement);
    }

    @Test
    public void testGetSelectedElementMultipleRows() throws Exception {
        ConnectionBox box = new ConnectionBox(new Position(10, 10), 5, 30);
        AbstractWordView selectedElement = poemView.getSelectedElement(box);

        assertEquals(poemView, selectedElement);
    }

    @Test
    public void testGetWord() throws Exception {
        assertEquals(poem, poemView.getWord());
    }

    @Test
    public void testContains() throws Exception {
        Word wordThree = new Word("WordThing", WordType.ADVERB);
        WordView wordViewThree = new WordView(wordThree, new Position(20, 20));

        assertTrue(poemView.contains(poemView));
        assertTrue(poemView.contains(poemView.getRowViews().get(0)));
        assertTrue(poemView.contains(wordViewOne));
        assertTrue(poemView.contains(wordViewTwo));
        assertFalse(poemView.contains(wordViewThree));
    }

    @Test
    public void testRemoveEdgeWordViewFromEndOfRow() throws Exception {
        Word wordThree = new Word("WordThing", WordType.ADVERB);
        WordView wordViewThree = new WordView(wordThree, new Position(20, 20));
        poemView.getRowViews().get(1).addWord(wordViewThree);

        assertTrue(poemView.contains(wordViewOne));
        assertTrue(poemView.contains(wordViewTwo));
        assertTrue(poemView.contains(wordViewThree));

        assertTrue(poemView.removeEdgeWordView(wordViewThree));

        assertTrue(poemView.contains(wordViewOne));
        assertTrue(poemView.contains(wordViewTwo));
        assertFalse(poemView.contains(wordViewThree));
    }

    @Test
    public void testRemoveEdgeWordViewFromBeginningOfRow() throws Exception {
        Word wordThree = new Word("WordThing", WordType.ADVERB);
        WordView wordViewThree = new WordView(wordThree, new Position(20, 20));
        poemView.getRowViews().get(1).addWordToFront(wordViewThree);

        assertTrue(poemView.contains(wordViewOne));
        assertTrue(poemView.contains(wordViewTwo));
        assertTrue(poemView.contains(wordViewThree));

        assertTrue(poemView.removeEdgeWordView(wordViewThree));

        assertTrue(poemView.contains(wordViewOne));
        assertTrue(poemView.contains(wordViewTwo));
        assertFalse(poemView.contains(wordViewThree));
    }

    @Test
    public void testRemoveEdgeWordViewDoesntExistInPoem() throws Exception {
        Word wordThree = new Word("WordThing", WordType.ADVERB);
        WordView wordViewThree = new WordView(wordThree, new Position(20, 20));

        assertTrue(poemView.contains(wordViewOne));
        assertTrue(poemView.contains(wordViewTwo));
        assertFalse(poemView.contains(wordViewThree));

        assertFalse(poemView.removeEdgeWordView(wordViewThree));

        assertTrue(poemView.contains(wordViewOne));
        assertTrue(poemView.contains(wordViewTwo));
        assertFalse(poemView.contains(wordViewThree));
    }

    @Test
    public void testRemoveEdgeWordViewOnlyWordInRow() throws Exception {
        assertTrue(poemView.contains(wordViewOne));
        assertTrue(poemView.contains(wordViewTwo));

        assertTrue(poemView.removeEdgeWordView(wordViewTwo));

        assertTrue(poemView.contains(wordViewOne));
        assertFalse(poemView.contains(wordViewTwo));
    }

    @Test
    public void testRemoveRowViewEnd() throws Exception {
        Word wordThree = new Word("WordThing", WordType.ADVERB);
        Row rowThree = new Row(wordThree);
        WordView wordViewThree = new WordView(wordThree, new Position(20, 20));
        mainView.addProtectedAbstractWordView(wordViewThree);
        RowView rowViewThree = new RowView(rowThree, new Position(20, 20), mainView);
        poemView.addRow(rowViewThree);

        assertTrue(poemView.contains(wordViewOne));
        assertTrue(poemView.contains(wordViewTwo));
        assertTrue(poemView.contains(wordViewThree));

        assertTrue(poemView.removeRowView(rowViewThree));

        assertTrue(poemView.contains(wordViewOne));
        assertTrue(poemView.contains(wordViewTwo));
        assertFalse(poemView.contains(wordViewThree));
    }

    @Test
    public void testRemoveRowViewStart() throws Exception {
        Word wordThree = new Word("WordThing", WordType.ADVERB);
        Row rowThree = new Row(wordThree);
        WordView wordViewThree = new WordView(wordThree, new Position(20, 20));
        mainView.addProtectedAbstractWordView(wordViewThree);
        RowView rowViewThree = new RowView(rowThree, new Position(20, 20), mainView);
        poemView.addRowToTop(rowViewThree);

        assertTrue(poemView.contains(wordViewOne));
        assertTrue(poemView.contains(wordViewTwo));
        assertTrue(poemView.contains(wordViewThree));

        assertTrue(poemView.removeRowView(rowViewThree));

        assertTrue(poemView.contains(wordViewOne));
        assertTrue(poemView.contains(wordViewTwo));
        assertFalse(poemView.contains(wordViewThree));
    }

    @Test
    public void testRemoveRowViewMiddle() throws Exception {
        Word wordThree = new Word("WordThing", WordType.ADVERB);
        Row rowThree = new Row(wordThree);
        WordView wordViewThree = new WordView(wordThree, new Position(20, 20));
        mainView.addProtectedAbstractWordView(wordViewThree);
        RowView rowViewThree = new RowView(rowThree, new Position(20, 20), mainView);
        poemView.addRow(rowViewThree);

        assertTrue(poemView.contains(wordViewOne));
        assertTrue(poemView.contains(wordViewTwo));
        assertTrue(poemView.contains(wordViewThree));

        assertFalse(poemView.removeRowView(poemView.getRowViews().get(1)));

        assertTrue(poemView.contains(wordViewOne));
        assertTrue(poemView.contains(wordViewTwo));
        assertTrue(poemView.contains(wordViewThree));
    }

    @Test
    public void testGetEmptyRowView() throws Exception {
        Word wordThree = new Word("WordThing", WordType.ADVERB);
        Row rowThree = new Row(wordThree);
        WordView wordViewThree = new WordView(wordThree, new Position(20, 20));
        mainView.addProtectedAbstractWordView(wordViewThree);
        RowView rowViewThree = new RowView(rowThree, new Position(20, 20), mainView);
        poemView.addRow(rowViewThree);

        assertTrue(poemView.contains(wordViewOne));
        assertTrue(poemView.contains(wordViewTwo));
        assertTrue(poemView.contains(wordViewThree));
        assertTrue(poemView.removeEdgeWordView(wordViewThree));

        assertEquals(rowViewThree, poemView.getEmptyRowView());
    }

    @Test
    public void testVisitorPatternAbstractPoem() throws Exception {
        DisconnectVisitor disconnector = new DisconnectVisitor(mainView, gameState);
        AbstractWordView abstractWordViewOne = new PoemView(poem, new Position(0,0), mainView);
        assertFalse(poemView.acceptVisitor(disconnector, abstractWordViewOne));
    }

    @Test
    public void testVisitorPatternRow() throws Exception {
        DisconnectVisitor disconnector = new DisconnectVisitor(mainView, gameState);
        assertFalse(poemView.acceptVisitor(disconnector, poemView.getRowViews().get(0)));
    }

    @Test
    public void testVisitorPatternWord() throws Exception {
        DisconnectVisitor disconnector = new DisconnectVisitor(mainView, gameState);
        assertFalse(poemView.acceptVisitor(disconnector, wordViewOne));
    }
}