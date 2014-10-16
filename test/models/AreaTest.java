package models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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

//    @Test
//    public void testConnectHorizontal1() throws Exception {
//        Word wordOne = new Word("Mule", WordType.NOUN);
//        Word wordTwo = new Word("Horse", WordType.NOUN);
//        Collection<AbstractWord> words = new HashSet<AbstractWord>();
//        words.add(wordOne);
//        words.add(wordTwo);
//        Area area = new Area(words);
//        assertTrue(area.abstractWordCollection.contains(wordOne));
//        assertTrue(area.abstractWordCollection.contains(wordTwo));
//        AbstractWord result = area.connectHorizontal(wordOne, wordTwo);
//        assertFalse(area.abstractWordCollection.contains(wordOne));
//        assertFalse(area.abstractWordCollection.contains(wordTwo));
//        assertTrue(area.abstractWordCollection.contains(result));
//        assertEquals("Mule Horse", result.getValue());
//    }
//
//    @Test
//    public void testConnectHorizontal2() throws Exception {
//        Word wordOne = new Word("Mule", WordType.NOUN);
//        Word wordTwo = new Word("Horse", WordType.NOUN);
//        Collection<AbstractWord> words = new HashSet<AbstractWord>();
//        words.add(wordOne);
//        Area area = new Area(words);
//        assertTrue(area.abstractWordCollection.contains(wordOne));
//        assertFalse(area.abstractWordCollection.contains(wordTwo));
//        AbstractWord result = area.connectHorizontal(wordOne, wordTwo);
//        assertTrue(area.abstractWordCollection.contains(wordOne));
//        assertFalse(area.abstractWordCollection.contains(wordTwo));
//        assertNull(result);
//    }
//
//    @Test
//    public void testConnectHorizontal3() throws Exception {
//        Word wordOne = new Word("Mule", WordType.NOUN);
//        Word wordTwo = new Word("Horse", WordType.NOUN);
//        Collection<AbstractWord> words = new HashSet<AbstractWord>();
//        words.add(wordTwo);
//        Area area = new Area(words);
//        assertTrue(area.abstractWordCollection.contains(wordTwo));
//        assertFalse(area.abstractWordCollection.contains(wordOne));
//        AbstractWord result = area.connectHorizontal(wordOne, wordTwo);
//        assertTrue(area.abstractWordCollection.contains(wordTwo));
//        assertFalse(area.abstractWordCollection.contains(wordOne));
//        assertNull(result);
//    }
//
//    @Test
//    public void testConnectHorizontal4() throws Exception {
//        Word wordOne = new Word("Mule", WordType.NOUN);
//        Word wordTwo = new Word("Horse", WordType.NOUN);
//        Word wordThree = new Word("Donkey", WordType.NOUN);
//        List<Word> rowWords = new ArrayList<Word>();
//        rowWords.add(wordTwo);
//        rowWords.add(wordThree);
//        Row rowOne = new Row(rowWords);
//        Collection<AbstractWord> words = new HashSet<AbstractWord>();
//        words.add(wordOne);
//        words.add(rowOne);
//        Area area = new Area(words);
//        assertTrue(area.abstractWordCollection.contains(wordOne));
//        assertTrue(area.abstractWordCollection.contains(rowOne));
//        AbstractWord result = area.connectHorizontal(wordOne, rowOne);
//        assertFalse(area.abstractWordCollection.contains(wordOne));
//        assertTrue(area.abstractWordCollection.contains(rowOne));
//        assertEquals(rowOne, result);
//        assertEquals("Mule Horse Donkey", result.getValue());
//    }
//
//    @Test
//    public void testConnectHorizontal5() throws Exception {
//        Word wordOne = new Word("Mule", WordType.NOUN);
//        Word wordTwo = new Word("Horse", WordType.NOUN);
//        Word wordThree = new Word("Donkey", WordType.NOUN);
//        List<Word> rowWords = new ArrayList<Word>();
//        rowWords.add(wordTwo);
//        rowWords.add(wordThree);
//        Row rowOne = new Row(rowWords);
//        Collection<AbstractWord> words = new HashSet<AbstractWord>();
//        words.add(wordOne);
//        words.add(rowOne);
//        Area area = new Area(words);
//        assertTrue(area.abstractWordCollection.contains(wordOne));
//        assertTrue(area.abstractWordCollection.contains(rowOne));
//        AbstractWord result = area.connectHorizontal(rowOne, wordOne);
//        assertFalse(area.abstractWordCollection.contains(wordOne));
//        assertTrue(area.abstractWordCollection.contains(rowOne));
//        assertEquals(rowOne, result);
//        assertEquals("Horse Donkey Mule", result.getValue());
//    }
//
//    @Test
//    public void testConnectHorizontal6() throws Exception {
//        Word wordOne = new Word("Mule", WordType.NOUN);
//        Word wordTwo = new Word("Horse", WordType.NOUN);
//        Word wordThree = new Word("Donkey", WordType.NOUN);
//        List<Word> rowWordsOne = new ArrayList<Word>();
//        rowWordsOne.add(wordOne);
//        Row rowOne = new Row(rowWordsOne);
//        List<Word> rowWordsTwo = new ArrayList<Word>();
//        rowWordsTwo.add(wordTwo);
//        rowWordsTwo.add(wordThree);
//        Row rowTwo = new Row(rowWordsTwo);
//        Collection<AbstractWord> words = new HashSet<AbstractWord>();
//        words.add(rowOne);
//        words.add(rowTwo);
//        Area area = new Area(words);
//        assertTrue(area.abstractWordCollection.contains(rowOne));
//        assertTrue(area.abstractWordCollection.contains(rowTwo));
//        AbstractWord result = area.connectHorizontal(rowOne, rowTwo);
//        assertTrue(area.abstractWordCollection.contains(rowOne));
//        assertFalse(area.abstractWordCollection.contains(rowTwo));
//        assertEquals(rowOne, result);
//        assertEquals("Mule Horse Donkey", result.getValue());
//    }
//
//    @Test
//    public void testConnectHorizontal7() throws Exception {
//        Word wordOne = new Word("Mule", WordType.NOUN);
//        Word wordTwo = new Word("Horse", WordType.NOUN);
//        Word wordThree = new Word("Donkey", WordType.NOUN);
//        List<Word> rowWords = new ArrayList<Word>();
//        rowWords.add(wordTwo);
//        rowWords.add(wordThree);
//        Row rowOne = new Row(rowWords);
//        List<Row> poemWords = new ArrayList<Row>();
//        poemWords.add(rowOne);
//        Poem poemOne = new Poem(poemWords);
//        Collection<AbstractWord> words = new HashSet<AbstractWord>();
//        words.add(wordOne);
//        words.add(poemOne);
//        Area area = new Area(words);
//        assertTrue(area.abstractWordCollection.contains(wordOne));
//        assertTrue(area.abstractWordCollection.contains(poemOne));
//        AbstractWord result = area.connectHorizontal(poemOne, wordOne);
//        assertTrue(area.abstractWordCollection.contains(wordOne));
//        assertTrue(area.abstractWordCollection.contains(poemOne));
//        assertNull(result);
//    }
//
//    @Test
//    public void testConnectHorizontal8() throws Exception {
//        Word wordOne = new Word("Mule", WordType.NOUN);
//        Word wordTwo = new Word("Horse", WordType.NOUN);
//        Word wordThree = new Word("Donkey", WordType.NOUN);
//        List<Word> rowWords = new ArrayList<Word>();
//        rowWords.add(wordTwo);
//        rowWords.add(wordThree);
//        Row rowOne = new Row(rowWords);
//        List<Row> poemWords = new ArrayList<Row>();
//        poemWords.add(rowOne);
//        Poem poemOne = new Poem(poemWords);
//        Collection<AbstractWord> words = new HashSet<AbstractWord>();
//        words.add(wordOne);
//        words.add(poemOne);
//        Area area = new Area(words);
//        assertTrue(area.abstractWordCollection.contains(wordOne));
//        assertTrue(area.abstractWordCollection.contains(poemOne));
//        AbstractWord result = area.connectHorizontal(wordOne, poemOne);
//        assertTrue(area.abstractWordCollection.contains(wordOne));
//        assertTrue(area.abstractWordCollection.contains(poemOne));
//        assertNull(result);
//    }
//
//    @Test
//    public void testConnectHorizontal9() throws Exception {
//        Word wordOne = new Word("Mule", WordType.NOUN);
//        Word wordTwo = new Word("Horse", WordType.NOUN);
//        Word wordThree = new Word("Donkey", WordType.NOUN);
//        List<Word> rowWordsOne = new ArrayList<Word>();
//        rowWordsOne.add(wordOne);
//        Row rowOne = new Row(rowWordsOne);
//        List<Word> rowWordsTwo = new ArrayList<Word>();
//        rowWordsTwo.add(wordTwo);
//        rowWordsTwo.add(wordThree);
//        Row rowTwo = new Row(rowWordsTwo);
//        List<Row> poemWords = new ArrayList<Row>();
//        poemWords.add(rowTwo);
//        Poem poemOne = new Poem(poemWords);
//        Collection<AbstractWord> words = new HashSet<AbstractWord>();
//        words.add(rowOne);
//        words.add(poemOne);
//        Area area = new Area(words);
//        assertTrue(area.abstractWordCollection.contains(rowOne));
//        assertTrue(area.abstractWordCollection.contains(poemOne));
//        AbstractWord result = area.connectHorizontal(rowOne, poemOne);
//        assertTrue(area.abstractWordCollection.contains(rowOne));
//        assertTrue(area.abstractWordCollection.contains(poemOne));
//        assertNull(result);
//    }
//
//    @Test
//    public void testConnectHorizontal10() throws Exception {
//        Word wordOne = new Word("Mule", WordType.NOUN);
//        Word wordTwo = new Word("Horse", WordType.NOUN);
//        Word wordThree = new Word("Donkey", WordType.NOUN);
//        List<Word> rowWordsOne = new ArrayList<Word>();
//        rowWordsOne.add(wordOne);
//        Row rowOne = new Row(rowWordsOne);
//        List<Word> rowWordsTwo = new ArrayList<Word>();
//        rowWordsTwo.add(wordTwo);
//        rowWordsTwo.add(wordThree);
//        Row rowTwo = new Row(rowWordsTwo);
//        List<Row> poemWords = new ArrayList<Row>();
//        poemWords.add(rowTwo);
//        Poem poemOne = new Poem(poemWords);
//        Collection<AbstractWord> words = new HashSet<AbstractWord>();
//        words.add(rowOne);
//        words.add(poemOne);
//        Area area = new Area(words);
//        assertTrue(area.abstractWordCollection.contains(rowOne));
//        assertTrue(area.abstractWordCollection.contains(poemOne));
//        AbstractWord result = area.connectHorizontal(poemOne, rowOne);
//        assertTrue(area.abstractWordCollection.contains(rowOne));
//        assertTrue(area.abstractWordCollection.contains(poemOne));
//        assertNull(result);
//    }
//
//    @Test
//    public void testConnectHorizontal11() throws Exception {
//        Word wordOne = new Word("Mule", WordType.NOUN);
//        Word wordTwo = new Word("Horse", WordType.NOUN);
//        Word wordThree = new Word("Donkey", WordType.NOUN);
//        List<Word> rowWordsOne = new ArrayList<Word>();
//        rowWordsOne.add(wordOne);
//        Row rowOne = new Row(rowWordsOne);
//        List<Row> poemWordsOne = new ArrayList<Row>();
//        poemWordsOne.add(rowOne);
//        Poem poemOne = new Poem(poemWordsOne);
//        List<Word> rowWordsTwo = new ArrayList<Word>();
//        rowWordsTwo.add(wordTwo);
//        rowWordsTwo.add(wordThree);
//        Row rowTwo = new Row(rowWordsTwo);
//        List<Row> poemWordsTwo = new ArrayList<Row>();
//        poemWordsTwo.add(rowTwo);
//        Poem poemTwo = new Poem(poemWordsTwo);
//        Collection<AbstractWord> words = new HashSet<AbstractWord>();
//        words.add(poemOne);
//        words.add(poemTwo);
//        Area area = new Area(words);
//        assertTrue(area.abstractWordCollection.contains(poemOne));
//        assertTrue(area.abstractWordCollection.contains(poemTwo));
//        AbstractWord result = area.connectHorizontal(poemOne, poemTwo);
//        assertTrue(area.abstractWordCollection.contains(poemOne));
//        assertTrue(area.abstractWordCollection.contains(poemTwo));
//        assertNull(result);
//    }


    @Test
    public void testConnectVertical1() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Collection<AbstractWord> words = new HashSet<AbstractWord>();
        words.add(wordOne);
        words.add(wordTwo);
        Area area = new Area(words);
        assertTrue(area.abstractWordCollection.contains(wordOne));
        assertTrue(area.abstractWordCollection.contains(wordTwo));
        AbstractWord result = area.connectVertical(wordOne, wordTwo);
        assertFalse(area.abstractWordCollection.contains(wordOne));
        assertFalse(area.abstractWordCollection.contains(wordTwo));
        assertTrue(area.abstractWordCollection.contains(result));
        assertEquals("Mule\nHorse", result.getValue());
    }

    @Test
    public void testConnectVertical2() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Collection<AbstractWord> words = new HashSet<AbstractWord>();
        words.add(wordOne);
        Area area = new Area(words);
        assertTrue(area.abstractWordCollection.contains(wordOne));
        assertFalse(area.abstractWordCollection.contains(wordTwo));
        AbstractWord result = area.connectVertical(wordOne, wordTwo);
        assertTrue(area.abstractWordCollection.contains(wordOne));
        assertFalse(area.abstractWordCollection.contains(wordTwo));
        assertNull(result);
    }

    @Test
    public void testConnectVertical3() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Collection<AbstractWord> words = new HashSet<AbstractWord>();
        words.add(wordTwo);
        Area area = new Area(words);
        assertTrue(area.abstractWordCollection.contains(wordTwo));
        assertFalse(area.abstractWordCollection.contains(wordOne));
        AbstractWord result = area.connectVertical(wordOne, wordTwo);
        assertFalse(area.abstractWordCollection.contains(wordOne));
        assertTrue(area.abstractWordCollection.contains(wordTwo));
        assertNull(result);
    }

    @Test
    public void testConnectVertical4() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWords = new ArrayList<Word>();
        rowWords.add(wordTwo);
        rowWords.add(wordThree);
        Row rowOne = new Row(rowWords);
        Collection<AbstractWord> words = new HashSet<AbstractWord>();
        words.add(wordOne);
        words.add(rowOne);
        Area area = new Area(words);
        assertTrue(area.abstractWordCollection.contains(wordOne));
        assertTrue(area.abstractWordCollection.contains(rowOne));
        AbstractWord result = area.connectVertical(wordOne, rowOne);
        assertFalse(area.abstractWordCollection.contains(wordOne));
        assertFalse(area.abstractWordCollection.contains(rowOne));
        assertTrue(area.abstractWordCollection.contains(result));
        assertEquals("Mule\nHorse Donkey", result.getValue());
    }

    @Test
    public void testConnectVertical5() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWords = new ArrayList<Word>();
        rowWords.add(wordTwo);
        rowWords.add(wordThree);
        Row rowOne = new Row(rowWords);
        Collection<AbstractWord> words = new HashSet<AbstractWord>();
        words.add(wordOne);
        words.add(rowOne);
        Area area = new Area(words);
        assertTrue(area.abstractWordCollection.contains(wordOne));
        assertTrue(area.abstractWordCollection.contains(rowOne));
        AbstractWord result = area.connectVertical(rowOne, wordOne);
        assertFalse(area.abstractWordCollection.contains(wordOne));
        assertFalse(area.abstractWordCollection.contains(rowOne));
        assertTrue(area.abstractWordCollection.contains(result));
        assertEquals("Horse Donkey\nMule", result.getValue());
    }

    @Test
    public void testConnectVertical6() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWordsOne = new ArrayList<Word>();
        rowWordsOne.add(wordOne);
        Row rowOne = new Row(rowWordsOne);
        List<Word> rowWordsTwo = new ArrayList<Word>();
        rowWordsTwo.add(wordTwo);
        rowWordsTwo.add(wordThree);
        Row rowTwo = new Row(rowWordsTwo);
        Collection<AbstractWord> words = new HashSet<AbstractWord>();
        words.add(rowOne);
        words.add(rowTwo);
        Area area = new Area(words);
        assertTrue(area.abstractWordCollection.contains(rowOne));
        assertTrue(area.abstractWordCollection.contains(rowTwo));
        AbstractWord result = area.connectVertical(rowOne, rowTwo);
        assertFalse(area.abstractWordCollection.contains(rowOne));
        assertFalse(area.abstractWordCollection.contains(rowTwo));
        assertTrue(area.abstractWordCollection.contains(result));
        assertEquals("Mule\nHorse Donkey", result.getValue());
    }

    @Test
    public void testConnectVertical7() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWords = new ArrayList<Word>();
        rowWords.add(wordTwo);
        rowWords.add(wordThree);
        Row rowOne = new Row(rowWords);
        List<Row> poemWords = new ArrayList<Row>();
        poemWords.add(rowOne);
        Poem poemOne = new Poem(poemWords);
        Collection<AbstractWord> words = new HashSet<AbstractWord>();
        words.add(wordOne);
        words.add(poemOne);
        Area area = new Area(words);
        assertTrue(area.abstractWordCollection.contains(wordOne));
        assertTrue(area.abstractWordCollection.contains(poemOne));
        AbstractWord result = area.connectVertical(poemOne, wordOne);
        assertFalse(area.abstractWordCollection.contains(wordOne));
        assertTrue(area.abstractWordCollection.contains(poemOne));
        assertEquals(poemOne, result);
        assertEquals("Horse Donkey\nMule", result.getValue());
    }

    @Test
    public void testConnectVertical8() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWords = new ArrayList<Word>();
        rowWords.add(wordTwo);
        rowWords.add(wordThree);
        Row rowOne = new Row(rowWords);
        List<Row> poemWords = new ArrayList<Row>();
        poemWords.add(rowOne);
        Poem poemOne = new Poem(poemWords);
        Collection<AbstractWord> words = new HashSet<AbstractWord>();
        words.add(wordOne);
        words.add(poemOne);
        Area area = new Area(words);
        assertTrue(area.abstractWordCollection.contains(wordOne));
        assertTrue(area.abstractWordCollection.contains(poemOne));
        AbstractWord result = area.connectVertical(wordOne, poemOne);
        assertFalse(area.abstractWordCollection.contains(wordOne));
        assertTrue(area.abstractWordCollection.contains(result));
        assertEquals("Mule\nHorse Donkey", result.getValue());
    }

    @Test
    public void testConnectVertical9() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWordsOne = new ArrayList<Word>();
        rowWordsOne.add(wordOne);
        Row rowOne = new Row(rowWordsOne);
        List<Word> rowWordsTwo = new ArrayList<Word>();
        rowWordsTwo.add(wordTwo);
        rowWordsTwo.add(wordThree);
        Row rowTwo = new Row(rowWordsTwo);
        List<Row> poemWords = new ArrayList<Row>();
        poemWords.add(rowTwo);
        Poem poemOne = new Poem(poemWords);
        Collection<AbstractWord> words = new HashSet<AbstractWord>();
        words.add(rowOne);
        words.add(poemOne);
        Area area = new Area(words);
        assertTrue(area.abstractWordCollection.contains(rowOne));
        assertTrue(area.abstractWordCollection.contains(poemOne));
        AbstractWord result = area.connectVertical(rowOne, poemOne);
        assertFalse(area.abstractWordCollection.contains(rowOne));
        assertTrue(area.abstractWordCollection.contains(result));
        assertEquals("Mule\nHorse Donkey", result.getValue());
    }

    @Test
    public void testConnectVertical10() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWordsOne = new ArrayList<Word>();
        rowWordsOne.add(wordOne);
        Row rowOne = new Row(rowWordsOne);
        List<Word> rowWordsTwo = new ArrayList<Word>();
        rowWordsTwo.add(wordTwo);
        rowWordsTwo.add(wordThree);
        Row rowTwo = new Row(rowWordsTwo);
        List<Row> poemWords = new ArrayList<Row>();
        poemWords.add(rowTwo);
        Poem poemOne = new Poem(poemWords);
        Collection<AbstractWord> words = new HashSet<AbstractWord>();
        words.add(rowOne);
        words.add(poemOne);
        Area area = new Area(words);
        assertTrue(area.abstractWordCollection.contains(rowOne));
        assertTrue(area.abstractWordCollection.contains(poemOne));
        AbstractWord result = area.connectVertical(poemOne, rowOne);
        assertFalse(area.abstractWordCollection.contains(rowOne));
        assertTrue(area.abstractWordCollection.contains(poemOne));
        assertEquals(poemOne, result);
        assertEquals("Horse Donkey\nMule", result.getValue());
    }

    @Test
    public void testConnectVertical11() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWordsOne = new ArrayList<Word>();
        rowWordsOne.add(wordOne);
        Row rowOne = new Row(rowWordsOne);
        List<Row> poemWordsOne = new ArrayList<Row>();
        poemWordsOne.add(rowOne);
        Poem poemOne = new Poem(poemWordsOne);
        List<Word> rowWordsTwo = new ArrayList<Word>();
        rowWordsTwo.add(wordTwo);
        rowWordsTwo.add(wordThree);
        Row rowTwo = new Row(rowWordsTwo);
        List<Row> poemWordsTwo = new ArrayList<Row>();
        poemWordsTwo.add(rowTwo);
        Poem poemTwo = new Poem(poemWordsTwo);
        Collection<AbstractWord> words = new HashSet<AbstractWord>();
        words.add(poemOne);
        words.add(poemTwo);
        Area area = new Area(words);
        assertTrue(area.abstractWordCollection.contains(poemOne));
        assertTrue(area.abstractWordCollection.contains(poemTwo));
        AbstractWord result = area.connectVertical(poemOne, poemTwo);
        assertFalse(area.abstractWordCollection.contains(poemTwo));
        assertTrue(area.abstractWordCollection.contains(poemOne));
        assertEquals(poemOne, result);
        assertEquals("Mule\nHorse Donkey", result.getValue());
    }
}