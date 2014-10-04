package models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

public class RowTest {

    @Test
    public void testConstructor() throws Exception {
        Word word = new Word("Porch", WordType.PRONOUN);
        List<Word> words = new ArrayList<Word>();
        words.add(word);
        Row row = new Row(words);
        assertNotNull(row);
        assertNotEquals(0, row.id);
        assertEquals(words.size(), row.words.size());
        assertTrue(row.words.contains(word));
    }

    @Test
    public void testConstructorNull() throws Exception {
        Row row = new Row(null);
        assertNotNull(row);
        assertEquals(0, row.words.size());
    }

    @Test
    public void testGetValue() throws Exception {
        Word wordOne = new Word("Dog", WordType.NOUN);
        Word wordTwo = new Word("Cat", WordType.ADVERB);
        Word wordThree = new Word("Mouse", WordType.ANY);
        List<Word> words = new ArrayList<Word>();
        words.add(wordOne);
        words.add(wordTwo);
        words.add(wordThree);

        Row row = new Row(words);
        assertEquals("Dog Cat Mouse", row.getValue());
    }

    @Test
    public void testConnect() throws Exception {
        Word wordOne = new Word("Chair", WordType.NOUN);
        Word wordTwo = new Word("Table", WordType.ADVERB);
        Word wordThree = new Word("House", WordType.ANY);
        List<Word> words = new ArrayList<Word>();
        words.add(wordOne);

        Row row = new Row(words);
        assertEquals("Chair", row.getValue());
        row.connect(wordTwo);
        assertEquals("Chair Table", row.getValue());
        row.connect(wordThree);
        assertEquals("Chair Table House", row.getValue());
    }

    @Test
    public void testConnectToFront() throws Exception {
        Word wordOne = new Word("Chair", WordType.NOUN);
        Word wordTwo = new Word("Table", WordType.ADVERB);
        Word wordThree = new Word("House", WordType.ANY);
        List<Word> words = new ArrayList<Word>();
        words.add(wordOne);

        Row row = new Row(words);
        assertEquals("Chair", row.getValue());
        row.connectToFront(wordTwo);
        assertEquals("Table Chair", row.getValue());
        row.connectToFront(wordThree);
        assertEquals("House Table Chair", row.getValue());
    }

    @Test
    public void testDisconnectEnd() throws Exception {
        Word wordOne = new Word("Chair", WordType.NOUN);
        Word wordTwo = new Word("Table", WordType.ADVERB);
        Word wordThree = new Word("House", WordType.ANY);
        List<Word> words = new ArrayList<Word>();
        words.add(wordOne);
        words.add(wordTwo);
        words.add(wordThree);

        Row row = new Row(words);
        assertEquals("Chair Table House", row.getValue());
        row.disconnect(wordThree);
        assertEquals("Chair Table", row.getValue());
    }

    @Test
    public void testDisconnectStart() throws Exception {
        Word wordOne = new Word("Chair", WordType.NOUN);
        Word wordTwo = new Word("Table", WordType.ADVERB);
        Word wordThree = new Word("House", WordType.ANY);
        List<Word> words = new ArrayList<Word>();
        words.add(wordOne);
        words.add(wordTwo);
        words.add(wordThree);

        Row row = new Row(words);
        assertEquals("Chair Table House", row.getValue());
        row.disconnect(wordOne);
        assertEquals("Table House", row.getValue());
    }

    @Test
    public void testDisconnectMiddle() throws Exception {
        Word wordOne = new Word("Chair", WordType.NOUN);
        Word wordTwo = new Word("Table", WordType.ADVERB);
        Word wordThree = new Word("House", WordType.ANY);
        List<Word> words = new ArrayList<Word>();
        words.add(wordOne);
        words.add(wordTwo);
        words.add(wordThree);

        Row row = new Row(words);
        assertEquals("Chair Table House", row.getValue());
        try {
            row.disconnect(wordTwo);
        } catch(Exception e) {
            assertEquals(e.getMessage(), "Invalid disconnect");
        }
        assertEquals("Chair Table House", row.getValue());
    }
}