package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class PoemTest {

    @Test
    public void testConstructor() throws Exception {
        List<Word> words = new ArrayList<Word>();
        Row row = new Row(words);
        List<Row> rows = new ArrayList<Row>();
        rows.add(row);
        Poem poem = new Poem(rows);
        assertNotNull(poem);
        assertNotEquals(0, poem.id);
        assertEquals(rows.size(), poem.rows.size());
        assertTrue(poem.rows.contains(row));
    }

    @Test
    public void testConstructorNull() throws Exception {
        Poem poem = new Poem(null);
        assertNotNull(poem);
        assertEquals(0, poem.rows.size());
    }

    @Test
    public void testGetValue() throws Exception {
        Word wordOne = new Word("Dog", WordType.NOUN);
        Word wordTwo = new Word("Cat", WordType.ADVERB);
        List<Word> wordsOne = new ArrayList<Word>();
        wordsOne.add(wordOne);
        wordsOne.add(wordTwo);
        Row rowOne = new Row(wordsOne);

        Word wordThree = new Word("Mouse", WordType.ANY);
        Word wordFour = new Word("Tiger", WordType.POSTFIX);
        List<Word> wordsTwo = new ArrayList<Word>();
        wordsTwo.add(wordThree);
        wordsTwo.add(wordFour);
        Row rowTwo = new Row(wordsTwo);

        List<Row> rows = new ArrayList<Row>();
        rows.add(rowOne);
        rows.add(rowTwo);

        Poem poem = new Poem(rows);
        assertEquals("Dog Cat\nMouse Tiger", poem.getValue());
    }

    @Test
    public void testConnectRow() throws Exception {
        Word wordOne = new Word("Dog", WordType.NOUN);
        Word wordTwo = new Word("Cat", WordType.ADVERB);
        List<Word> wordsOne = new ArrayList<Word>();
        wordsOne.add(wordOne);
        wordsOne.add(wordTwo);
        Row rowOne = new Row(wordsOne);

        Word wordThree = new Word("Mouse", WordType.ANY);
        Word wordFour = new Word("Tiger", WordType.POSTFIX);
        List<Word> wordsTwo = new ArrayList<Word>();
        wordsTwo.add(wordThree);
        wordsTwo.add(wordFour);
        Row rowTwo = new Row(wordsTwo);

        List<Row> rows = new ArrayList<Row>();
        rows.add(rowOne);

        Poem poem = new Poem(rows);
        assertEquals("Dog Cat", poem.getValue());
        poem.connect(rowTwo);
        assertEquals("Dog Cat\nMouse Tiger", poem.getValue());
    }

    @Test
    public void testConnectPoem() throws Exception {
        Word wordOne = new Word("Dog", WordType.NOUN);
        Word wordTwo = new Word("Cat", WordType.ADVERB);
        List<Word> wordsOne = new ArrayList<Word>();
        wordsOne.add(wordOne);
        wordsOne.add(wordTwo);
        Row rowOne = new Row(wordsOne);

        Word wordThree = new Word("Mouse", WordType.ANY);
        Word wordFour = new Word("Tiger", WordType.POSTFIX);
        List<Word> wordsTwo = new ArrayList<Word>();
        wordsTwo.add(wordThree);
        wordsTwo.add(wordFour);
        Row rowTwo = new Row(wordsTwo);

        List<Row> rowsOne = new ArrayList<Row>();
        rowsOne.add(rowOne);
        List<Row> rowsTwo = new ArrayList<Row>();
        rowsTwo.add(rowTwo);

        Poem poemOne = new Poem(rowsOne);
        assertEquals("Dog Cat", poemOne.getValue());
        Poem poemTwo = new Poem(rowsTwo);
        assertEquals("Mouse Tiger", poemTwo.getValue());
        poemOne.connect(poemTwo);
        assertEquals("Dog Cat\nMouse Tiger", poemOne.getValue());
    }

    @Test
    public void testDisconnectEndRow() throws Exception {
        Word wordOne = new Word("Dog", WordType.NOUN);
        Word wordTwo = new Word("Cat", WordType.ADVERB);
        List<Word> wordsOne = new ArrayList<Word>();
        wordsOne.add(wordOne);
        wordsOne.add(wordTwo);
        Row rowOne = new Row(wordsOne);

        Word wordThree = new Word("Mouse", WordType.ANY);
        Word wordFour = new Word("Tiger", WordType.POSTFIX);
        List<Word> wordsTwo = new ArrayList<Word>();
        wordsTwo.add(wordThree);
        wordsTwo.add(wordFour);
        Row rowTwo = new Row(wordsTwo);

        List<Row> rows = new ArrayList<Row>();
        rows.add(rowOne);
        rows.add(rowTwo);

        Poem poem = new Poem(rows);
        assertEquals("Dog Cat\nMouse Tiger", poem.getValue());
        Poem result = poem.disconnect(rowTwo);
        assertNull(result);
        assertEquals("Dog Cat", poem.getValue());
    }

    @Test
    public void testDisconnectStartRow() throws Exception {
        Word wordOne = new Word("Dog", WordType.NOUN);
        Word wordTwo = new Word("Cat", WordType.ADVERB);
        List<Word> wordsOne = new ArrayList<Word>();
        wordsOne.add(wordOne);
        wordsOne.add(wordTwo);
        Row rowOne = new Row(wordsOne);

        Word wordThree = new Word("Mouse", WordType.ANY);
        Word wordFour = new Word("Tiger", WordType.POSTFIX);
        List<Word> wordsTwo = new ArrayList<Word>();
        wordsTwo.add(wordThree);
        wordsTwo.add(wordFour);
        Row rowTwo = new Row(wordsTwo);

        List<Row> rows = new ArrayList<Row>();
        rows.add(rowOne);
        rows.add(rowTwo);

        Poem poem = new Poem(rows);
        assertEquals("Dog Cat\nMouse Tiger", poem.getValue());
        Poem result = poem.disconnect(rowOne);
        assertNull(result);
        assertEquals("Mouse Tiger", poem.getValue());
    }

    @Test
    public void testDisconnectMiddleRow() throws Exception {
        Word wordOne = new Word("Dog", WordType.NOUN);
        Word wordTwo = new Word("Cat", WordType.ADVERB);
        List<Word> wordsOne = new ArrayList<Word>();
        wordsOne.add(wordOne);
        wordsOne.add(wordTwo);
        Row rowOne = new Row(wordsOne);

        Word wordThree = new Word("Mouse", WordType.ANY);
        Word wordFour = new Word("Tiger", WordType.POSTFIX);
        List<Word> wordsTwo = new ArrayList<Word>();
        wordsTwo.add(wordThree);
        wordsTwo.add(wordFour);
        Row rowTwo = new Row(wordsTwo);

        Word wordFive = new Word("Hawk", WordType.PREFIX);
        Word wordSix = new Word("Fish", WordType.PREPOSITION);
        List<Word> wordsThree = new ArrayList<Word>();
        wordsThree.add(wordFive);
        wordsThree.add(wordSix);
        Row rowThree = new Row(wordsThree);

        List<Row> rows = new ArrayList<Row>();
        rows.add(rowOne);
        rows.add(rowTwo);
        rows.add(rowThree);

        Poem poem = new Poem(rows);
        assertEquals("Dog Cat\nMouse Tiger\nHawk Fish", poem.getValue());
        Poem result = poem.disconnect(rowTwo);
        assertEquals("Dog Cat", poem.getValue());
        assertEquals("Hawk Fish", result.getValue());
    }

    @Test
    public void testDisconnectAMiddleRow() throws Exception {
        Word wordOne = new Word("Dog", WordType.NOUN);
        Word wordTwo = new Word("Cat", WordType.ADVERB);
        List<Word> wordsOne = new ArrayList<Word>();
        wordsOne.add(wordOne);
        wordsOne.add(wordTwo);
        Row rowOne = new Row(wordsOne);

        Word wordThree = new Word("Mouse", WordType.ANY);
        Word wordFour = new Word("Tiger", WordType.POSTFIX);
        List<Word> wordsTwo = new ArrayList<Word>();
        wordsTwo.add(wordThree);
        wordsTwo.add(wordFour);
        Row rowTwo = new Row(wordsTwo);

        Word wordFive = new Word("Hawk", WordType.PREFIX);
        Word wordSix = new Word("Fish", WordType.PREPOSITION);
        List<Word> wordsThree = new ArrayList<Word>();
        wordsThree.add(wordFive);
        wordsThree.add(wordSix);
        Row rowThree = new Row(wordsThree);

        Word wordSeven = new Word("Eye", WordType.POSTFIX);
        List<Word> wordsFour = new ArrayList<Word>();
        wordsFour.add(wordSeven);
        Row rowFour = new Row(wordsFour);

        Word wordEight = new Word("Leg", WordType.PREFIX);
        List<Word> wordsFive = new ArrayList<Word>();
        wordsFive.add(wordEight);
        Row rowFive = new Row(wordsFive);

        List<Row> rows = new ArrayList<Row>();
        rows.add(rowOne);
        rows.add(rowTwo);
        rows.add(rowThree);
        rows.add(rowFour);
        rows.add(rowFive);

        Poem poem = new Poem(rows);
        assertEquals("Dog Cat\nMouse Tiger\nHawk Fish\nEye\nLeg", poem.getValue());
        Poem result = poem.disconnect(rowThree);
        assertEquals("Dog Cat\nMouse Tiger", poem.getValue());
        assertEquals("Eye\nLeg", result.getValue());
    }

    @Test
    public void testDisconnectEdgeWordInRowOne() throws Exception {
        Word wordOne = new Word("Dog", WordType.NOUN);
        Word wordTwo = new Word("Cat", WordType.ADVERB);
        List<Word> wordsOne = new ArrayList<Word>();
        wordsOne.add(wordOne);
        wordsOne.add(wordTwo);
        Row rowOne = new Row(wordsOne);

        Word wordThree = new Word("Mouse", WordType.ANY);
        Word wordFour = new Word("Tiger", WordType.POSTFIX);
        List<Word> wordsTwo = new ArrayList<Word>();
        wordsTwo.add(wordThree);
        wordsTwo.add(wordFour);
        Row rowTwo = new Row(wordsTwo);

        Word wordFive = new Word("Hawk", WordType.PREFIX);
        Word wordSix = new Word("Fish", WordType.PREPOSITION);
        List<Word> wordsThree = new ArrayList<Word>();
        wordsThree.add(wordFive);
        wordsThree.add(wordSix);
        Row rowThree = new Row(wordsThree);

        List<Row> rows = new ArrayList<Row>();
        rows.add(rowOne);
        rows.add(rowTwo);
        rows.add(rowThree);

        Poem poem = new Poem(rows);
        assertEquals("Dog Cat\nMouse Tiger\nHawk Fish", poem.getValue());
        assertTrue(poem.disconnectEdgeWord(wordTwo));
        assertEquals("Dog\nMouse Tiger\nHawk Fish", poem.getValue());
    }

    @Test
     public void testDisconnectEdgeWordInRowTwo() throws Exception {
        Word wordOne = new Word("Dog", WordType.NOUN);
        Word wordTwo = new Word("Cat", WordType.ADVERB);
        List<Word> wordsOne = new ArrayList<Word>();
        wordsOne.add(wordOne);
        wordsOne.add(wordTwo);
        Row rowOne = new Row(wordsOne);

        Word wordThree = new Word("Mouse", WordType.ANY);
        Word wordFour = new Word("Tiger", WordType.POSTFIX);
        List<Word> wordsTwo = new ArrayList<Word>();
        wordsTwo.add(wordThree);
        wordsTwo.add(wordFour);
        Row rowTwo = new Row(wordsTwo);

        Word wordFive = new Word("Hawk", WordType.PREFIX);
        Word wordSix = new Word("Fish", WordType.PREPOSITION);
        List<Word> wordsThree = new ArrayList<Word>();
        wordsThree.add(wordFive);
        wordsThree.add(wordSix);
        Row rowThree = new Row(wordsThree);

        List<Row> rows = new ArrayList<Row>();
        rows.add(rowOne);
        rows.add(rowTwo);
        rows.add(rowThree);

        Poem poem = new Poem(rows);
        assertEquals("Dog Cat\nMouse Tiger\nHawk Fish", poem.getValue());
        assertTrue(poem.disconnectEdgeWord(wordThree));
        assertEquals("Dog Cat\nTiger\nHawk Fish", poem.getValue());
    }

    @Test
    public void testDisconnectEdgeWordInRowThree() throws Exception {
        Word wordOne = new Word("Dog", WordType.NOUN);
        Word wordTwo = new Word("Cat", WordType.ADVERB);
        List<Word> wordsOne = new ArrayList<Word>();
        wordsOne.add(wordOne);
        wordsOne.add(wordTwo);
        Row rowOne = new Row(wordsOne);

        Word wordThree = new Word("Mouse", WordType.ANY);
        Word wordFour = new Word("Tiger", WordType.POSTFIX);
        List<Word> wordsTwo = new ArrayList<Word>();
        wordsTwo.add(wordThree);
        wordsTwo.add(wordFour);
        Row rowTwo = new Row(wordsTwo);

        Word wordFive = new Word("Hawk", WordType.PREFIX);
        Word wordSix = new Word("Seagull", WordType.INTERJECTION);
        Word wordSeven = new Word("Fish", WordType.PREPOSITION);
        List<Word> wordsThree = new ArrayList<Word>();
        wordsThree.add(wordFive);
        wordsThree.add(wordSix);
        wordsThree.add(wordSeven);
        Row rowThree = new Row(wordsThree);

        List<Row> rows = new ArrayList<Row>();
        rows.add(rowOne);
        rows.add(rowTwo);
        rows.add(rowThree);

        Poem poem = new Poem(rows);
        assertEquals("Dog Cat\nMouse Tiger\nHawk Seagull Fish", poem.getValue());
        try {
            assertTrue(poem.disconnectEdgeWord(wordSix));
        } catch(Exception e) {
            assertEquals(e.getMessage(), "Invalid disconnect");
        }
        assertEquals("Dog Cat\nMouse Tiger\nHawk Seagull Fish", poem.getValue());
    }
}