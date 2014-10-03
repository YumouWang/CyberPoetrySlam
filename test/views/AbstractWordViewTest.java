package views;

import model.AbstractWord;
import model.Position;
import model.Word;
import model.WordType;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AbstractWordViewTest {

    @Test
    public void testConstructor() throws Exception {
        AbstractWord word = new Word("View", WordType.VERB);
        Position position = new Position(0,0);
        AbstractWordView view = new AbstractWordView(word, position);
        assertNotNull(view);
        assertEquals(position, view.position);
        assertEquals(position, view.getPosition());
        assertEquals(word, view.word);
        assertEquals(word, view.getWord());
        assertEquals(word.getValue(), view.label.getText());
        assertEquals(word.getValue(), view.getLabel().getText());
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
    public void testIsOverlapping() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0,0);
        Position positionTwo = new Position(5,5);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        boolean result = viewOne.isOverlapping(viewTwo);
        assertFalse(result);
    }

    @Test
    public void testIsAdjacentToAbove() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0,0);
        Position positionTwo = new Position(0,25);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        AdjacencyType result = viewOne.isAdjacentTo(viewTwo);
        assertEquals(AdjacencyType.ABOVE, result);
    }

    @Test
    public void testIsAdjacentToBelow() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0,0);
        Position positionTwo = new Position(0,25);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        AdjacencyType result = viewTwo.isAdjacentTo(viewOne);
        assertEquals(AdjacencyType.BELOW, result);
    }

    @Test
    public void testIsAdjacentToLeft() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0,0);
        Position positionTwo = new Position(85,0);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        AdjacencyType result = viewOne.isAdjacentTo(viewTwo);
        assertEquals(AdjacencyType.LEFT, result);
    }

    @Test
    public void testIsAdjacentToRight() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0,0);
        Position positionTwo = new Position(85,0);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        AdjacencyType result = viewTwo.isAdjacentTo(viewOne);
        assertEquals(AdjacencyType.RIGHT, result);
    }

    @Test
    public void testIsAdjacentToNotAdjacent() throws Exception {
        AbstractWord wordOne = new Word("Object", WordType.NOUN);
        AbstractWord wordTwo = new Word("Other", WordType.PRONOUN);
        Position positionOne = new Position(0,0);
        Position positionTwo = new Position(200,200);
        AbstractWordView viewOne = new AbstractWordView(wordOne, positionOne);
        AbstractWordView viewTwo = new AbstractWordView(wordTwo, positionTwo);
        AdjacencyType result = viewOne.isAdjacentTo(viewTwo);
        assertEquals(AdjacencyType.NOT_ADJACENT, result);
    }

    @Test
    public void testIsClickedYes() throws Exception {
        AbstractWord word = new Word("View", WordType.PREFIX);
        Position position = new Position(0,0);
        AbstractWordView view = new AbstractWordView(word, position);
        boolean result = view.isClicked(new Position(1, 1));
        assertTrue(result);
    }

    @Test
    public void testIsClickedNo() throws Exception {
        AbstractWord word = new Word("View", WordType.PREFIX);
        Position position = new Position(0,0);
        AbstractWordView view = new AbstractWordView(word, position);
        boolean result = view.isClicked(new Position(-1, -1));
        assertFalse(result);
    }
}