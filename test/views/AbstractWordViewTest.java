package views;

import common.Constants;
import models.AbstractWord;
import models.Position;
import models.Word;
import models.WordType;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class AbstractWordViewTest {

    @Test
    public void testConstructor() throws Exception {
        AbstractWord word = new Word("View", WordType.VERB);
        Position position = new Position(0,0);
        AbstractWordView view = new AbstractWordView(word, position);
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
        AbstractWord word = new Word("ViewTwo", WordType.PREFIX);
        Position position = new Position(0,0);
        AbstractWordView view = new AbstractWordView(word, position);
        assertEquals(position, view.getPosition());
        Position newPosition = new Position(100, 100);
        view.moveTo(newPosition);
        assertEquals(newPosition, view.getPosition());
        assertEquals(newPosition.getX(), view.label.getX());
        assertEquals(newPosition.getY(), view.label.getY());
    }

    @Test
    public void testIsOverlappingYes1() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other1", WordType.PRONOUN);
        Position positionOne = new Position(0,0);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        Position positionTwo = new Position(positionOne.getX() + viewOne.width / 2, positionOne.getY() +  viewOne.height / 2);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        boolean result = viewOne.isOverlapping(viewTwo);
        assertTrue(result);
    }

    @Test
    public void testIsOverlappingYes2() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other1", WordType.PRONOUN);
        Position positionOne = new Position(0,0);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        Position positionTwo = new Position(positionOne.getX() + viewOne.width / 2, positionOne.getY() -  viewOne.height / 2);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        boolean result = viewOne.isOverlapping(viewTwo);
        assertTrue(result);
    }

    @Test
    public void testIsOverlappingYes3() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other1", WordType.PRONOUN);
        Position positionOne = new Position(0,0);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        Position positionTwo = new Position(positionOne.getX() - viewOne.width / 2, positionOne.getY() -  viewOne.height / 2);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        boolean result = viewOne.isOverlapping(viewTwo);
        assertTrue(result);
    }

    @Test
    public void testIsOverlappingYes4() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other1", WordType.PRONOUN);
        Position positionOne = new Position(0,0);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        Position positionTwo = new Position(positionOne.getX() - viewOne.width / 2, positionOne.getY() +  viewOne.height / 2);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        boolean result = viewOne.isOverlapping(viewTwo);
        assertTrue(result);
    }

    @Test
    public void testIsOverlappingNo1() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0,0);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        Position positionTwo = new Position(positionOne.getX() + viewOne.width / 2, positionOne.getY() +  viewOne.height * 2);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        boolean result = viewOne.isOverlapping(viewTwo);
        assertFalse(result);
    }

    @Test
    public void testIsOverlappingNo2() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0,0);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        Position positionTwo = new Position(positionOne.getX() + viewOne.width * 2, positionOne.getY() +  viewOne.height / 2);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        boolean result = viewOne.isOverlapping(viewTwo);
        assertFalse(result);
    }

    @Test
    public void testIsOverlappingNo3() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0,0);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        Position positionTwo = new Position(positionOne.getX() - viewOne.width * 2, positionOne.getY() +  viewOne.height / 2);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        boolean result = viewOne.isOverlapping(viewTwo);
        assertFalse(result);
    }

    @Test
    public void testIsOverlappingNo4() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        Position positionTwo = new Position(positionOne.getX() + viewOne.width / 2, positionOne.getY() -  viewOne.height * 2);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        boolean result = viewOne.isOverlapping(viewTwo);
        assertFalse(result);
    }

    @Test
    public void testIsOverlappingTopLeft() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        Position positionTwo = new Position(-1, -1);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        viewTwo.width = 2;
        viewTwo.height = 2;
        boolean result = viewOne.isOverlapping(viewTwo);
        assertTrue(result);
    }

    @Test
    public void testIsOverlappingTopRight() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        Position positionTwo = new Position(-1, 9);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        viewOne.width = 10;
        viewTwo.width = 2;
        viewTwo.height = 2;
        boolean result = viewOne.isOverlapping(viewTwo);
        assertTrue(result);
    }

    @Test
    public void testIsOverlappingBottomRight() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        Position positionTwo = new Position(9, 9);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        viewOne.width = 10;
        viewOne.height = 10;
        viewTwo.width = 2;
        viewTwo.height = 2;
        boolean result = viewOne.isOverlapping(viewTwo);
        assertTrue(result);
    }

    @Test
    public void testIsOverlappingBottomLeft() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        Position positionTwo = new Position(9, -1);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        viewOne.height = 10;
        viewTwo.width = 2;
        viewTwo.height = 2;
        boolean result = viewOne.isOverlapping(viewTwo);
        assertTrue(result);
    }

    @Test
    public void testIsAdjacentToAbove() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        viewOne.width = 100;
        viewOne.height = 100;

        Position positionTwo = new Position(1, 100 + Constants.CONNECT_DISTANCE / 2);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        viewTwo.width = 100;
        viewTwo.height = 100;
        AdjacencyType result = viewOne.isAdjacentTo(viewTwo);
        assertEquals(AdjacencyType.ABOVE, result);
    }

    @Test
    public void testIsAdjacentToBelow() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        viewOne.width = 100;
        viewOne.height = 100;

        Position positionTwo = new Position(1, 100 + Constants.CONNECT_DISTANCE / 2);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        viewTwo.width = 100;
        viewTwo.height = 100;
        AdjacencyType result = viewTwo.isAdjacentTo(viewOne);
        assertEquals(AdjacencyType.BELOW, result);
    }

    @Test
    public void testIsAdjacentToLeft() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        viewOne.width = 100;
        viewOne.height = 100;

        Position positionTwo = new Position(100 + Constants.CONNECT_DISTANCE / 2, 1);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        viewTwo.width = 100;
        viewTwo.height = 100;
        AdjacencyType result = viewOne.isAdjacentTo(viewTwo);
        assertEquals(AdjacencyType.LEFT, result);
    }

    @Test
    public void testIsAdjacentToRight() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        viewOne.width = 100;
        viewOne.height = 100;

        Position positionTwo = new Position(100 + Constants.CONNECT_DISTANCE / 2, 1);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        viewTwo.width = 100;
        viewTwo.height = 100;
        AdjacencyType result = viewTwo.isAdjacentTo(viewOne);
        assertEquals(AdjacencyType.RIGHT, result);
    }

    @Test
    public void testIsAdjacentToNotAdjacent() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0, 0);
        Position positionTwo = new Position(200, 200);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        viewOne.width = 100;
        viewOne.height = 100;
        viewTwo.width = 100;
        viewTwo.height = 100;
        AdjacencyType result = viewOne.isAdjacentTo(viewTwo);
        assertEquals(AdjacencyType.NOT_ADJACENT, result);
    }

    @Test
    public void testIsClickedYes() throws Exception {
        AbstractWord word = new Word("View", WordType.PREFIX);
        Position position = new Position(0, 0);
        AbstractWordView view = new AbstractWordView(word, position);
        boolean result = view.isClicked(new Position(1, 1));
        assertTrue(result);
    }

    @Test
    public void testIsClickedNo() throws Exception {
        AbstractWord word = new Word("View", WordType.PREFIX);
        Position position = new Position(0, 0);
        AbstractWordView view = new AbstractWordView(word, position);
        boolean result = view.isClicked(new Position(-1, -1));
        assertFalse(result);
    }

    @Test
    public void testSetSize() throws Exception {
        AbstractWord word = new Word("View", WordType.PREFIX);
        Position position = new Position(0, 0);
        AbstractWordView view = new AbstractWordView(word, position);
        view.setSize(9, 11);
        assertEquals(9, view.width);
        assertEquals(11, view.height);
    }
}