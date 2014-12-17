package models;

import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.*;

public class AreaTest {

    @Test
    public void testConstructor() throws Exception {
        Word word = new Word("Horse", WordType.VERB);
        Collection<AbstractWord> words = new HashSet<AbstractWord>();
        words.add(word);
        Area area = new Area(words);
        assertNotNull(area);
        assertEquals(1, area.abstractWordCollection.size());
        assertTrue(area.abstractWordCollection.contains(word));
        assertTrue(area.getAbstractWordCollection().contains(word));
    }

    @Test
    public void testConstructorNull() throws Exception {
        Area area = new Area(null);
        assertNotNull(area);
        assertEquals(0, area.abstractWordCollection.size());
    }

    @Test
    public void testAddAbstractWord() throws Exception {
        AbstractWord word = new Word("Cow", WordType.ADVERB);
        Area area = new Area(null);
        assertEquals(0, area.abstractWordCollection.size());
        area.addAbstractWord(word);
        assertEquals(1, area.abstractWordCollection.size());
        assertTrue(area.abstractWordCollection.contains(word));
    }

    @Test
    public void testRemoveAbstractWord() throws Exception {
        Word word = new Word("Mule", WordType.NOUN);
        Collection<AbstractWord> words = new HashSet<AbstractWord>();
        words.add(word);
        Area area = new Area(words);
        assertTrue(area.abstractWordCollection.contains(word));
        area.removeAbstractWord(word);
        assertFalse(area.abstractWordCollection.contains(word));
    }

}