package views;

import models.Position;
import models.Word;
import models.WordType;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.*;

public class SelectionBoxTest {

    @Test
    public void testStartNewSelection() throws Exception {
        SelectionBox box = new SelectionBox();

        assertNull(box.startLocation);
        assertNull(box.endLocation);

        Position start = new Position(5, 5);
        box.startNewSelection(start);

        assertEquals(start, box.startLocation);
        assertNull(box.endLocation);
    }

    @Test
    public void testMoveSelection() throws Exception {
        SelectionBox box = new SelectionBox();

        assertNull(box.startLocation);
        assertNull(box.endLocation);

        Position move = new Position(5, 5);
        box.moveSelection(move);

        assertEquals(move, box.endLocation);
        assertNull(box.startLocation);
    }

    @Test
    public void testGetSelectedItems() throws Exception {
        SelectionBox box = new SelectionBox();

        box.startLocation = new Position(5, 5);
        box.endLocation = new Position(10, 10);

        Word word = new Word("myWord", WordType.ADJECTIVE);
        Word word2 = new Word("myOtherWord", WordType.ADJECTIVE);
        Collection<AbstractWordView> words = new HashSet<AbstractWordView>();
        WordView viewOne = new WordView(word, new Position(0, 0));
        WordView viewTwo = new WordView(word2, new Position(40, 40));
        words.add(viewOne);
        words.add(viewTwo);

        Collection<AbstractWordView> selectedWords = box.getSelectedItems(words);

        assertTrue(selectedWords.contains(viewOne));
        assertFalse(selectedWords.contains(viewTwo));
    }

    @Test
    public void testClearBox() throws Exception {
        SelectionBox box = new SelectionBox();

        box.startLocation = new Position(5, 5);
        box.endLocation = new Position(10, 10);

        box.clearBox();

        assertNull(box.endLocation);
        assertNull(box.startLocation);
    }

    @Test
    public void testPaintComponentWithNullLocations() throws Exception {
        // Just checking to make sure it doesn't throw an exception.
        // Mostly for extra code coverage
        SelectionBox box = new SelectionBox();

        // Use bufferedImage to get a valid graphics object
        BufferedImage bi = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        box.paintComponent(g);
        g.dispose();
    }

    @Test
    public void testPaintComponent() throws Exception {
        // Just checking to make sure it doesn't throw an exception.
        // Mostly for extra code coverage
        SelectionBox box = new SelectionBox();
        box.startLocation = new Position(5, 5);
        box.endLocation = new Position(10, 10);

        // Use bufferedImage to get a valid graphics object
        BufferedImage bi = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        box.paintComponent(g);
        g.dispose();
    }
}