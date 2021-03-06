package models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PoemTest {

    @Test
    public void testConstructorList() throws Exception {
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
    public void testConstructorRow() throws Exception {
        List<Word> words = new ArrayList<Word>();
        Row row = new Row(words);
        Poem poem = new Poem(row);
        assertNotNull(poem);
        assertNotEquals(0, poem.id);
        assertEquals(1, poem.rows.size());
        assertTrue(poem.rows.contains(row));
    }

    @Test
    public void testConstructorNull() throws Exception {
        Row row = null;
        Poem poem1 = new Poem(row);
        List<Row> rows = null;
        Poem poem2 = new Poem(rows);
        assertNotNull(poem1);
        assertNotNull(poem2);
        assertEquals(0, poem1.rows.size());
        assertEquals(0, poem2.rows.size());
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
    public void testConnectRowToTop() throws Exception {
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
        poem.connectToTop(rowTwo);
        assertEquals("Mouse Tiger\nDog Cat", poem.getValue());
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
        assertTrue(poem.disconnect(rowTwo));
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
        assertTrue(poem.disconnect(rowOne));
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
        assertFalse(poem.disconnect(rowTwo));
        assertEquals("Dog Cat\nMouse Tiger\nHawk Fish", poem.getValue());
    }

    @Test
    public void testSplitPoemAtPoem() throws Exception {
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
        Row rowFour = new Row(wordSeven);

        Word wordEight = new Word("Leg", WordType.PREFIX);
        Row rowFive = new Row(wordEight);

        List<Row> rows = new ArrayList<Row>();
        rows.add(rowOne);
        rows.add(rowTwo);
        rows.add(rowThree);
        rows.add(rowFour);
        rows.add(rowFive);

        Poem poem = new Poem(rows);
        assertEquals("Dog Cat\nMouse Tiger\nHawk Fish\nEye\nLeg", poem.getValue());
        assertFalse(poem.disconnect(rowThree));
        AbstractWord result = poem.splitPoemAt(rowThree);
        assertEquals("Dog Cat\nMouse Tiger\nHawk Fish", poem.getValue());
        assertEquals("Eye\nLeg", result.getValue());
    }

    @Test
    public void testSplitPoemAtWord() throws Exception {
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
        Row rowFour = new Row(wordSeven);

        Word wordEight = new Word("Leg", WordType.PREFIX);
        Row rowFive = new Row(wordEight);

        List<Row> rows = new ArrayList<Row>();
        rows.add(rowOne);
        rows.add(rowTwo);
        rows.add(rowThree);
        rows.add(rowFour);
        rows.add(rowFive);

        Poem poem = new Poem(rows);
        assertEquals("Dog Cat\nMouse Tiger\nHawk Fish\nEye\nLeg", poem.getValue());
        assertFalse(poem.disconnect(rowThree));
        Poem result = poem.splitPoemAt(rowFour);
        assertEquals("Dog Cat\nMouse Tiger\nHawk Fish\nEye", poem.getValue());
        assertEquals("Leg", result.getValue());
    }

    @Test
    public void testSplitPoemAtRow() throws Exception {
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
        assertFalse(poem.disconnect(rowTwo));
        Poem result = poem.splitPoemAt(rowTwo);
        assertEquals("Dog Cat\nMouse Tiger", poem.getValue());
        assertEquals("Hawk Fish", result.getValue());
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
        assertFalse(poem.disconnectEdgeWord(wordSix));
        assertEquals("Dog Cat\nMouse Tiger\nHawk Seagull Fish", poem.getValue());
    }

    @Test
    public void testRevalidateLastRow() throws Exception {
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
        List<Word> wordsThree = new ArrayList<Word>();
        wordsThree.add(wordFive);
        Row rowThree = new Row(wordsThree);

        List<Row> rows = new ArrayList<Row>();
        rows.add(rowOne);
        rows.add(rowTwo);
        rows.add(rowThree);

        Poem poem = new Poem(rows);
        assertEquals("Dog Cat\nMouse Tiger\nHawk", poem.getValue());
        assertTrue(poem.disconnectEdgeWord(wordFive));
        assertNull(poem.revalidate());
        assertEquals("Dog Cat\nMouse Tiger", poem.getValue());
    }

    @Test
    public void testRevalidateMiddleRow() throws Exception {
        Word wordOne = new Word("Dog", WordType.NOUN);
        Word wordTwo = new Word("Cat", WordType.ADVERB);
        List<Word> wordsOne = new ArrayList<Word>();
        wordsOne.add(wordOne);
        wordsOne.add(wordTwo);
        Row rowOne = new Row(wordsOne);

        Word wordThree = new Word("Mouse", WordType.ANY);
        Row rowTwo = new Row(wordThree);

        Word wordFour = new Word("Hawk", WordType.PREFIX);
        Row rowThree = new Row(wordFour);

        List<Row> rows = new ArrayList<Row>();
        rows.add(rowOne);
        rows.add(rowTwo);
        rows.add(rowThree);

        Poem poem = new Poem(rows);
        assertEquals("Dog Cat\nMouse\nHawk", poem.getValue());
        assertTrue(poem.disconnectEdgeWord(wordThree));
        Poem result = poem.revalidate();
        assertNotNull(result);
        assertEquals("Dog Cat", poem.getValue());
        assertEquals("Hawk", result.getValue());
    }

    @Test
    public void testContains() throws Exception {
        Word wordOne = new Word("MyWord", WordType.ANY);
        Word wordTwo = new Word("MyOtherWord", WordType.ADJECTIVE);
        Row rowOne = new Row(wordOne);
        Poem poemOne = new Poem(rowOne);
        assertTrue(poemOne.contains(wordOne));
        assertTrue(poemOne.contains(rowOne));
        assertTrue(poemOne.contains(poemOne));
        assertFalse(poemOne.contains(wordTwo));
    }
}