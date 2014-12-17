package models;

import org.junit.Test;

import static org.junit.Assert.*;

//import static org.junit.Assert.assertNotEquals;

public class WordTest {

    @Test
    public void testConstructor() throws Exception {
        Word word = new Word("Dog", WordType.NOUN);
        assertNotNull(word);
        assertNotEquals(0, word.id);
        assertNotEquals(0, word.getId());
        assertEquals("Dog", word.value);
        assertEquals(WordType.NOUN, word.type);
    }

    @Test
    public void testGetValue() throws Exception {
        Word word = new Word("Chair", WordType.NOUN);
        assertEquals("Chair", word.getValue());
    }

    @Test
    public void testConnectWord() throws Exception {
        Word wordOne = new Word("Chair", WordType.NOUN);
        Word wordTwo = new Word("House", WordType.ADJECTIVE);

        Row row = wordOne.connect(wordTwo);
        assertEquals("Chair House", row.getValue());
    }

    @Test
    public void testConnectRow() throws Exception {
        Word wordOne = new Word("Chair", WordType.NOUN);
        Word wordTwo = new Word("Table", WordType.ADVERB);
        Word wordThree = new Word("House", WordType.ANY);

        Row row = wordOne.connect(wordTwo);

        assertEquals("Chair Table", row.getValue());
        wordThree.connect(row);
        assertEquals("House Chair Table", row.getValue());
    }

    @Test
    public void testContains() throws Exception {
        Word wordOne = new Word("MyWord", WordType.ANY);
        Word wordTwo = new Word("MyOtherWord", WordType.ADJECTIVE);
        assertTrue(wordOne.contains(wordOne));
        assertFalse(wordOne.contains(wordTwo));
    }
}