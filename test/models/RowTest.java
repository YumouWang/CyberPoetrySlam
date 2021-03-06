package models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RowTest {

    @Test
    public void testConstructorList() throws Exception {
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
    public void testConstructorWord() throws Exception {
        Word word = new Word("Porch", WordType.PRONOUN);
        Row row = new Row(word);
        assertNotNull(row);
        assertNotEquals(0, row.id);
        assertEquals(1, row.words.size());
        assertTrue(row.words.contains(word));
    }

    @Test
    public void testConstructorNull() throws Exception {
        Word word = null;
        Row row1 = new Row(word);
        List<Word> words = null;
        Row row2 = new Row(words);
        assertNotNull(row1);
        assertNotNull(row2);
        assertEquals(0, row1.words.size());
        assertEquals(0, row2.words.size());
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
    public void testConnectRow() throws Exception {
        Word wordOne = new Word("Chair", WordType.NOUN);
        Word wordTwo = new Word("Table", WordType.ADVERB);
        Word wordThree = new Word("House", WordType.ANY);
        Word wordFour = new Word("City", WordType.ANY);
        List<Word> wordsOne = new ArrayList<Word>();
        wordsOne.add(wordOne);
        wordsOne.add(wordTwo);
        List<Word> wordsTwo = new ArrayList<Word>();
        wordsTwo.add(wordThree);
        wordsTwo.add(wordFour);

        Row rowOne = new Row(wordsOne);
        Row rowTwo = new Row(wordsTwo);
        assertEquals("Chair Table", rowOne.getValue());
        rowOne.connect(rowTwo);
        assertEquals("Chair Table House City", rowOne.getValue());
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
        assertFalse(row.disconnect(wordTwo));
        assertEquals("Chair Table House", row.getValue());
    }

    @Test
    public void testSplitRowAt() throws Exception {
        Word wordOne = new Word("Chair", WordType.NOUN);
        Word wordTwo = new Word("Table", WordType.ADVERB);
        Word wordThree = new Word("House", WordType.ANY);
        List<Word> words = new ArrayList<Word>();
        words.add(wordOne);
        words.add(wordTwo);
        words.add(wordThree);

        Row row = new Row(words);
        assertEquals("Chair Table House", row.getValue());
        assertFalse(row.disconnect(wordTwo));
        Row result = row.splitRowAt(wordTwo);
        assertEquals("House", result.getValue());
        assertEquals("Chair Table", row.getValue());
    }

    @Test
    public void testSplitRowAtRow() throws Exception {
        Word wordOne = new Word("Chair", WordType.NOUN);
        Word wordTwo = new Word("Table", WordType.ADVERB);
        Word wordThree = new Word("House", WordType.ANY);
        List<Word> words = new ArrayList<Word>();
        words.add(wordOne);
        words.add(wordTwo);
        words.add(wordThree);

        Row row = new Row(words);
        assertEquals("Chair Table House", row.getValue());
        Row result = row.splitRowAt(wordOne);
        assertEquals("Chair", row.getValue());
        assertEquals("Table House", result.getValue());
    }

    @Test
    public void testContains() throws Exception {
        Word wordOne = new Word("MyWord", WordType.ANY);
        Word wordTwo = new Word("MyOtherWord", WordType.ADJECTIVE);
        Row rowOne = new Row(wordOne);
        assertTrue(rowOne.contains(wordOne));
        assertTrue(rowOne.contains(rowOne));
        assertFalse(rowOne.contains(wordTwo));
    }
}