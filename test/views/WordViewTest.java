package views;

import common.Constants;
import controllers.AbstractWordViewVisitor;
import controllers.DisconnectVisitor;
import models.*;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class WordViewTest {

    UnprotectedMemento un = null;
    ProtectedMemento p = null;

    @Test
    public void testConstructor() throws Exception {
        Word word = new Word("View", WordType.VERB);
        Position position = new Position(0, 0);
        WordView view = new WordView(word, position);
        view.setBackground(Color.BLACK);
        assertNotNull(view);
        assertEquals(position, view.position);
        assertEquals(position, view.getPosition());
        assertEquals(word, view.word);
        assertEquals(word, view.getWord());
        assertEquals(word.getValue(), view.label.getText());
        assertEquals(position.getX(), view.label.getX());
        assertEquals(position.getY(), view.label.getY());
    }

    @Test
    public void testMoveTo() throws Exception {
        Word word = new Word("ViewTwo", WordType.PREFIX);
        Position position = new Position(0, 0);
        WordView view = new WordView(word, position);
        assertEquals(position, view.getPosition());
        Position newPosition = new Position(100, 100);
        view.moveTo(newPosition);
        assertEquals(newPosition, view.getPosition());
        assertEquals(newPosition.getX(), view.label.getX());
        assertEquals(newPosition.getY(), view.label.getY());
    }

    @Test
    public void testIsOverlappingYes1() throws Exception {
        Word wordOne = new Word("Object", WordType.NOUN);
        Word wordTwo = new Word("Other1", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        WordView viewOne = new WordView(wordOne, positionOne);
        Position positionTwo = new Position(positionOne.getX() + viewOne.width
                / 2, positionOne.getY() + viewOne.height / 2);
        WordView viewTwo = new WordView(wordTwo, positionTwo);
        boolean result = viewOne.isOverlapping(viewTwo);
        assertTrue(result);
    }

    @Test
    public void testIsOverlappingYes2() throws Exception {
        Word wordOne = new Word("Object", WordType.NOUN);
        Word wordTwo = new Word("Other1", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        WordView viewOne = new WordView(wordOne, positionOne);
        Position positionTwo = new Position(positionOne.getX() + viewOne.width
                / 2, positionOne.getY() - viewOne.height / 2);
        WordView viewTwo = new WordView(wordTwo, positionTwo);
        boolean result = viewOne.isOverlapping(viewTwo);
        assertTrue(result);
    }

    @Test
    public void testIsOverlappingYes3() throws Exception {
        Word wordOne = new Word("Object", WordType.NOUN);
        Word wordTwo = new Word("Other1", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        WordView viewOne = new WordView(wordOne, positionOne);
        Position positionTwo = new Position(positionOne.getX() - viewOne.width
                / 2, positionOne.getY() - viewOne.height / 2);
        WordView viewTwo = new WordView(wordTwo, positionTwo);
        boolean result = viewOne.isOverlapping(viewTwo);
        assertTrue(result);
    }

    @Test
    public void testIsOverlappingYes4() throws Exception {
        Word wordOne = new Word("Object", WordType.NOUN);
        Word wordTwo = new Word("Other1", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        WordView viewOne = new WordView(wordOne, positionOne);
        Position positionTwo = new Position(positionOne.getX() - viewOne.width
                / 2, positionOne.getY() + viewOne.height / 2);
        WordView viewTwo = new WordView(wordTwo, positionTwo);
        boolean result = viewOne.isOverlapping(viewTwo);
        assertTrue(result);
    }

    @Test
    public void testIsOverlappingNo1() throws Exception {
        Word wordOne = new Word("Object", WordType.NOUN);
        Word wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        WordView viewOne = new WordView(wordOne, positionOne);
        Position positionTwo = new Position(positionOne.getX() + viewOne.width
                / 2, positionOne.getY() + viewOne.height * 2);
        WordView viewTwo = new WordView(wordTwo, positionTwo);
        boolean result = viewOne.isOverlapping(viewTwo);
        assertFalse(result);
    }

    @Test
    public void testIsOverlappingNo2() throws Exception {
        Word wordOne = new Word("Object", WordType.NOUN);
        Word wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        WordView viewOne = new WordView(wordOne, positionOne);
        Position positionTwo = new Position(positionOne.getX() + viewOne.width
                * 2, positionOne.getY() + viewOne.height / 2);
        WordView viewTwo = new WordView(wordTwo, positionTwo);
        boolean result = viewOne.isOverlapping(viewTwo);
        assertFalse(result);
    }

    @Test
    public void testIsOverlappingNo3() throws Exception {
        Word wordOne = new Word("Object", WordType.NOUN);
        Word wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        WordView viewOne = new WordView(wordOne, positionOne);
        Position positionTwo = new Position(positionOne.getX() - viewOne.width
                * 2, positionOne.getY() + viewOne.height / 2);
        WordView viewTwo = new WordView(wordTwo, positionTwo);
        boolean result = viewOne.isOverlapping(viewTwo);
        assertFalse(result);
    }

    @Test
    public void testIsOverlappingNo4() throws Exception {
        Word wordOne = new Word("Object", WordType.NOUN);
        Word wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        WordView viewOne = new WordView(wordOne, positionOne);
        Position positionTwo = new Position(positionOne.getX() + viewOne.width
                / 2, positionOne.getY() - viewOne.height * 2);
        WordView viewTwo = new WordView(wordTwo, positionTwo);
        boolean result = viewOne.isOverlapping(viewTwo);
        assertFalse(result);
    }

    @Test
    public void testIsOverlappingTopLeft() throws Exception {
        Word wordOne = new Word("Object", WordType.NOUN);
        Word wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        Position positionTwo = new Position(-1, -1);
        WordView viewOne = new WordView(wordOne, positionOne);
        WordView viewTwo = new WordView(wordTwo, positionTwo);
        viewTwo.width = 2;
        viewTwo.height = 2;
        boolean result = viewOne.isOverlapping(viewTwo);
        assertTrue(result);
    }

    @Test
    public void testIsOverlappingTopRight() throws Exception {
        Word wordOne = new Word("Object", WordType.NOUN);
        Word wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        Position positionTwo = new Position(-1, 9);
        WordView viewOne = new WordView(wordOne, positionOne);
        WordView viewTwo = new WordView(wordTwo, positionTwo);
        viewOne.width = 10;
        viewTwo.width = 2;
        viewTwo.height = 2;
        boolean result = viewOne.isOverlapping(viewTwo);
        assertTrue(result);
    }

    @Test
    public void testIsOverlappingBottomRight() throws Exception {
        Word wordOne = new Word("Object", WordType.NOUN);
        Word wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        Position positionTwo = new Position(9, 9);
        WordView viewOne = new WordView(wordOne, positionOne);
        WordView viewTwo = new WordView(wordTwo, positionTwo);
        viewOne.width = 10;
        viewOne.height = 10;
        viewTwo.width = 2;
        viewTwo.height = 2;
        boolean result = viewOne.isOverlapping(viewTwo);
        assertTrue(result);
    }

    @Test
    public void testIsOverlappingBottomLeft() throws Exception {
        Word wordOne = new Word("Object", WordType.NOUN);
        Word wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        Position positionTwo = new Position(9, -1);
        WordView viewOne = new WordView(wordOne, positionOne);
        WordView viewTwo = new WordView(wordTwo, positionTwo);
        viewOne.height = 10;
        viewTwo.width = 2;
        viewTwo.height = 2;
        boolean result = viewOne.isOverlapping(viewTwo);
        assertTrue(result);
    }

    @Test
    public void testIsAdjacentToAbove() throws Exception {
        Word wordOne = new Word("Object", WordType.NOUN);
        Word wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        WordView viewOne = new WordView(wordOne, positionOne);
        viewOne.width = 100;
        viewOne.height = 100;

        Position positionTwo = new Position(1,
                100 + Constants.CONNECT_DISTANCE / 2);
        WordView viewTwo = new WordView(wordTwo, positionTwo);
        viewTwo.width = 100;
        viewTwo.height = 100;
        AdjacencyType result = viewOne.isAdjacentTo(viewTwo);
        assertEquals(AdjacencyType.ABOVE, result);
    }

    @Test
    public void testIsAdjacentToBelow() throws Exception {
        Word wordOne = new Word("Object", WordType.NOUN);
        Word wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        WordView viewOne = new WordView(wordOne, positionOne);
        viewOne.width = 100;
        viewOne.height = 100;

        Position positionTwo = new Position(1,
                100 + Constants.CONNECT_DISTANCE / 2);
        WordView viewTwo = new WordView(wordTwo, positionTwo);
        viewTwo.width = 100;
        viewTwo.height = 100;
        AdjacencyType result = viewTwo.isAdjacentTo(viewOne);
        assertEquals(AdjacencyType.BELOW, result);
    }

    @Test
    public void testIsAdjacentToLeft() throws Exception {
        Word wordOne = new Word("Object", WordType.NOUN);
        Word wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        WordView viewOne = new WordView(wordOne, positionOne);
        viewOne.width = 100;
        viewOne.height = 100;

        Position positionTwo = new Position(
                100 + Constants.CONNECT_DISTANCE / 2, 1);
        WordView viewTwo = new WordView(wordTwo, positionTwo);
        viewTwo.width = 100;
        viewTwo.height = 100;
        AdjacencyType result = viewOne.isAdjacentTo(viewTwo);
        assertEquals(AdjacencyType.LEFT, result);
    }

    @Test
    public void testIsAdjacentToRight() throws Exception {
        Word wordOne = new Word("Object", WordType.NOUN);
        Word wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        WordView viewOne = new WordView(wordOne, positionOne);
        viewOne.width = 100;
        viewOne.height = 100;

        Position positionTwo = new Position(
                100 + Constants.CONNECT_DISTANCE / 2, 1);
        WordView viewTwo = new WordView(wordTwo, positionTwo);
        viewTwo.width = 100;
        viewTwo.height = 100;
        AdjacencyType result = viewTwo.isAdjacentTo(viewOne);
        assertEquals(AdjacencyType.RIGHT, result);
    }

    @Test
    public void testIsAdjacentToNotAdjacent() throws Exception {
        Word wordOne = new Word("Object", WordType.NOUN);
        Word wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        Position positionTwo = new Position(200, 200);
        WordView viewOne = new WordView(wordOne, positionOne);
        WordView viewTwo = new WordView(wordTwo, positionTwo);
        viewOne.width = 100;
        viewOne.height = 100;
        viewTwo.width = 100;
        viewTwo.height = 100;
        AdjacencyType result = viewOne.isAdjacentTo(viewTwo);
        assertEquals(AdjacencyType.NOT_ADJACENT, result);
    }

    @Test
    public void testIsClickedYes() throws Exception {
        Word word = new Word("View", WordType.PREFIX);
        Position position = new Position(0, 0);
        WordView view = new WordView(word, position);
        boolean result = view.isClicked(new Position(1, 1));
        assertTrue(result);
    }

    @Test
    public void testIsClickedNo() throws Exception {
        Word word = new Word("View", WordType.PREFIX);
        Position position = new Position(0, 0);
        WordView view = new WordView(word, position);
        boolean result = view.isClicked(new Position(-1, -1));
        assertFalse(result);
    }

    @Test
    public void testSetSize() throws Exception {
        Word word = new Word("View", WordType.PREFIX);
        Position position = new Position(0, 0);
        WordView view = new WordView(word, position);
        view.setSize(9, 11);
        assertEquals(9, view.width);
        assertEquals(11, view.height);
    }

    @Test
    public void testGetAbstractWord() throws Exception {
        // All of our AbstractWordView implementations overload the getWord()
        // method,
        // So to test that method we create an implementation of the
        // AbstractWordView
        // class and call getWord() on it
        class TestAbstractWordView extends AbstractWordView {
            public TestAbstractWordView(AbstractWord word, Position position) {
                super(word, position);
            }

            @Override
            public boolean moveTo(Position toPosition) {
                return false;
            }

            @Override
            public AbstractWordView getSelectedElement(ConnectionBox box) {
                return null;
            }

            @Override
            public boolean contains(AbstractWordView otherWord) {
                return false;
            }

            @Override
            public void setBackground(Color color) {
            }

            @Override
            public boolean acceptVisitor(AbstractWordViewVisitor visitor,
                                         AbstractWordView otherView) {
                return false;
            }

            @Override
            public boolean acceptVisitor(AbstractWordViewVisitor visitor,
                                         WordView wordView) {
                return false;
            }

            @Override
            public boolean acceptVisitor(AbstractWordViewVisitor visitor,
                                         RowView rowView) {
                return false;
            }

            @Override
            public boolean acceptVisitor(AbstractWordViewVisitor visitor,
                                         PoemView poemView) {
                return false;
            }
        }

        Word word = new Word("View", WordType.PREFIX);
        Position position = new Position(0, 0);
        AbstractWordView view = new TestAbstractWordView(word, position);
        assertEquals(word.getId(), view.getWord().getId());
    }

    @Test
    public void testContains() throws Exception {
        Word word = new Word("View", WordType.PREFIX);
        Position position = new Position(0, 0);
        WordView view = new WordView(word, position);
        assertTrue(view.contains(view));
    }

    @Test
    public void testGetSelectedElementNull() throws Exception {
        Word word = new Word("View", WordType.PREFIX);
        Position position = new Position(10, 10);
        WordView view = new WordView(word, position);
        ConnectionBox box = new ConnectionBox(new Position(0, 0), 0, 0);
        assertNull(view.getSelectedElement(box));
    }

    @Test
    public void testGetSelectedElement() throws Exception {
        Word word = new Word("View", WordType.PREFIX);
        Position position = new Position(0, 0);
        WordView view = new WordView(word, position);
        ConnectionBox box = new ConnectionBox(new Position(5, 5), 0, 0);
        assertEquals(view, view.getSelectedElement(box));
    }

    @Test
    public void testVisitorPatternAbstractWord() throws Exception {
        GameState gameState = new GameState(null);
        MainView mainView = new MainView(gameState, null);
        DisconnectVisitor disconnector = new DisconnectVisitor(mainView,
                gameState);

        Word word = new Word("View", WordType.PREFIX);
        Position position = new Position(0, 0);
        WordView view = new WordView(word, position);

        Word wordTwo = new Word("View", WordType.PREFIX);
        AbstractWordView abstractWordViewOne = new WordView(wordTwo,
                new Position(0, 0));
        assertFalse(view.acceptVisitor(disconnector, abstractWordViewOne));
    }

    @Test
    public void testVisitorPatternPoem() throws Exception {
        GameState gameState = new GameState(null);
        MainView mainView = new MainView(gameState, null);
        DisconnectVisitor disconnector = new DisconnectVisitor(mainView,
                gameState);

        Word wordOne = new Word("Happy", WordType.ADJECTIVE);
        Word wordTwo = new Word("Happy", WordType.ADJECTIVE);
        Row rowOne = new Row(wordOne);
        Row rowTwo = new Row(wordTwo);
        Poem poemOne = new Poem(rowOne);
        poemOne.connect(rowTwo);
        WordView wordViewOne = new WordView(wordOne, new Position(0, 0));
        WordView wordViewTwo = new WordView(wordTwo, new Position(0, 0));
        mainView.addProtectedAbstractWordView(wordViewOne);
        mainView.addProtectedAbstractWordView(wordViewTwo);
        PoemView poemView = new PoemView(poemOne, new Position(2, 2), mainView);
        mainView.addProtectedAbstractWordView(poemView);
        assertTrue(wordViewOne.acceptVisitor(disconnector, poemView));
    }

    @Test
    public void testVisitorPatternRow() throws Exception {
        GameState gameState = new GameState(null);
        MainView mainView = new MainView(gameState, null);
        DisconnectVisitor disconnector = new DisconnectVisitor(mainView,
                gameState);

        Word wordOne = new Word("Happy", WordType.ADJECTIVE);
        Word wordTwo = new Word("Happy", WordType.ADJECTIVE);
        Row rowOne = new Row(wordOne);
        rowOne.connect(wordTwo);
        WordView wordViewOne = new WordView(wordOne, new Position(0, 0));
        WordView wordViewTwo = new WordView(wordTwo, new Position(0, 0));
        mainView.addProtectedAbstractWordView(wordViewOne);
        mainView.addProtectedAbstractWordView(wordViewTwo);
        RowView rowView = new RowView(rowOne, new Position(2, 2), mainView);
        mainView.addProtectedAbstractWordView(rowView);
        assertTrue(wordViewOne.acceptVisitor(disconnector, rowView));
    }
}