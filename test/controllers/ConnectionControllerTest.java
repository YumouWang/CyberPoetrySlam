package controllers;

import models.*;
import org.junit.Before;
import org.junit.Test;
import views.AbstractWordView;
import views.MainView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ConnectionControllerTest {

    GameState gameState;
    MainView mainView;

    @Before
    public void initialize() {
        gameState = new GameState();
        gameState.getProtectedArea().getAbstractWordCollection().clear();
        mainView = new MainView(gameState);
    }

    @Test
    public void testConnectHorizontalWordWord() throws Exception {
        assertTrue(mainView.getWords().isEmpty());
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        gameState.getProtectedArea().addAbstractWord(wordOne);
        gameState.getProtectedArea().addAbstractWord(wordTwo);
        AbstractWordView wordViewOne = new AbstractWordView(wordOne, new Position(0, 0));
        AbstractWordView wordViewTwo = new AbstractWordView(wordTwo, new Position(10, 0));
        wordViewOne.setSize(5, 10);
        mainView.addAbstractWordView(wordViewOne);
        mainView.addAbstractWordView(wordViewTwo);
        // Check that the current state is properly set up
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(wordOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(wordTwo));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());

        ConnectionController controller = new ConnectionController(mainView, gameState);
        controller.connect(wordViewTwo, wordViewOne);
        // Check that gameState appropriately represents the result
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(wordOne));
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(wordTwo));
        assertEquals(1, gameState.getProtectedArea().getAbstractWordCollection().size());
        for(AbstractWord word: gameState.getProtectedArea().getAbstractWordCollection()) {
            assertEquals("Mule Horse", word.getValue());
        }
        // Check that mainView appropriately represents the result
        assertFalse(mainView.getWords().contains(wordViewOne));
        assertFalse(mainView.getWords().contains(wordViewTwo));
        assertEquals(1, mainView.getWords().size());
        for(AbstractWordView word: mainView.getWords()) {
            assertEquals("Mule Horse", word.getWord().getValue());
        }
    }

    @Test
    public void testConnectHorizontalWordRow() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWords = new ArrayList<Word>();
        rowWords.add(wordTwo);
        rowWords.add(wordThree);
        Row rowOne = new Row(rowWords);
        gameState.getProtectedArea().addAbstractWord(wordOne);
        gameState.getProtectedArea().addAbstractWord(rowOne);
        AbstractWordView wordViewOne = new AbstractWordView(wordOne, new Position(0, 0));
        AbstractWordView rowViewTwo = new AbstractWordView(rowOne, new Position(10, 0));
        wordViewOne.setSize(5, 10);
        mainView.addAbstractWordView(wordViewOne);
        mainView.addAbstractWordView(rowViewTwo);
        // Check that the current state is properly set up
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(wordOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(rowOne));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());

        ConnectionController controller = new ConnectionController(mainView, gameState);
        controller.connect(wordViewOne, rowViewTwo);
        // Check that gameState appropriately represents the result
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(wordOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(rowOne));
        assertEquals(1, gameState.getProtectedArea().getAbstractWordCollection().size());
        for(AbstractWord word: gameState.getProtectedArea().getAbstractWordCollection()) {
            assertEquals("Mule Horse Donkey", word.getValue());
        }
        // Check that mainView appropriately represents the result
        assertFalse(mainView.getWords().contains(wordViewOne));
        assertFalse(mainView.getWords().contains(rowViewTwo));
        assertEquals(1, mainView.getWords().size());
        for(AbstractWordView word: mainView.getWords()) {
            assertEquals("Mule Horse Donkey", word.getWord().getValue());
        }
    }

    @Test
    public void testConnectHorizontalWordPoem() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWords = new ArrayList<Word>();
        rowWords.add(wordTwo);
        rowWords.add(wordThree);
        Row rowOne = new Row(rowWords);
        List<Row> poemRows = new ArrayList<Row>();
        poemRows.add(rowOne);
        Poem poemOne = new Poem(poemRows);
        gameState.getProtectedArea().addAbstractWord(wordOne);
        gameState.getProtectedArea().addAbstractWord(poemOne);
        AbstractWordView viewOne = new AbstractWordView(wordOne, new Position(0, 0));
        AbstractWordView viewTwo = new AbstractWordView(poemOne, new Position(10, 0));
        viewOne.setSize(5, 10);
        mainView.addAbstractWordView(viewOne);
        mainView.addAbstractWordView(viewTwo);
        // Check that the current state is properly set up
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(wordOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemOne));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());

        ConnectionController controller = new ConnectionController(mainView, gameState);
        // Invalid connect, should have no effect on gameState
        controller.connect(viewOne, viewTwo);

        // Check that gameState appropriately represents the result
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(wordOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemOne));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());
        for(AbstractWord word: gameState.getProtectedArea().getAbstractWordCollection()) {
            assertTrue(word.getValue().equals("Mule") || word.getValue().equals("Horse Donkey"));
        }
        // Check that mainView appropriately represents the result
        assertTrue(mainView.getWords().contains(viewOne));
        assertTrue(mainView.getWords().contains(viewTwo));
        assertEquals(2, mainView.getWords().size());
        for(AbstractWordView word: mainView.getWords()) {
            assertTrue(word.getWord().getValue().equals("Mule") || word.getWord().getValue().equals("Horse Donkey"));
        }
    }

    @Test
     public void testConnectHorizontalRowWord() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWords = new ArrayList<Word>();
        rowWords.add(wordOne);
        rowWords.add(wordTwo);
        Row rowOne = new Row(rowWords);
        gameState.getProtectedArea().addAbstractWord(rowOne);
        gameState.getProtectedArea().addAbstractWord(wordThree);
        AbstractWordView viewOne = new AbstractWordView(rowOne, new Position(0, 0));
        AbstractWordView viewTwo = new AbstractWordView(wordThree, new Position(10, 0));
        viewOne.setSize(5, 10);
        mainView.addAbstractWordView(viewOne);
        mainView.addAbstractWordView(viewTwo);
        // Check that the current state is properly set up
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(rowOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(wordThree));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());

        ConnectionController controller = new ConnectionController(mainView, gameState);
        controller.connect(viewOne, viewTwo);
        // Check that gameState appropriately represents the result
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(rowOne));
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(wordThree));
        assertEquals(1, gameState.getProtectedArea().getAbstractWordCollection().size());
        for(AbstractWord word: gameState.getProtectedArea().getAbstractWordCollection()) {
            assertEquals("Mule Horse Donkey", word.getValue());
        }
        // Check that mainView appropriately represents the result
        assertFalse(mainView.getWords().contains(viewOne));
        assertFalse(mainView.getWords().contains(viewTwo));
        assertEquals(1, mainView.getWords().size());
        for(AbstractWordView word: mainView.getWords()) {
            assertEquals("Mule Horse Donkey", word.getWord().getValue());
        }
    }

    @Test
    public void testConnectHorizontalRowRow() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWordsOne = new ArrayList<Word>();
        rowWordsOne.add(wordOne);
        rowWordsOne.add(wordTwo);
        Row rowOne = new Row(rowWordsOne);
        List<Word> rowWordsTwo = new ArrayList<Word>();
        rowWordsTwo.add(wordThree);
        Row rowTwo = new Row(rowWordsTwo);
        gameState.getProtectedArea().addAbstractWord(rowOne);
        gameState.getProtectedArea().addAbstractWord(rowTwo);
        AbstractWordView viewOne = new AbstractWordView(rowOne, new Position(0, 0));
        AbstractWordView viewTwo = new AbstractWordView(rowTwo, new Position(10, 0));
        viewOne.setSize(5, 10);
        mainView.addAbstractWordView(viewOne);
        mainView.addAbstractWordView(viewTwo);
        // Check that the current state is properly set up
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(rowOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(rowTwo));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());

        ConnectionController controller = new ConnectionController(mainView, gameState);
        controller.connect(viewOne, viewTwo);
        // Check that gameState appropriately represents the result
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(rowOne));
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(rowTwo));
        assertEquals(1, gameState.getProtectedArea().getAbstractWordCollection().size());
        for(AbstractWord word: gameState.getProtectedArea().getAbstractWordCollection()) {
            assertEquals("Mule Horse Donkey", word.getValue());
        }
        // Check that mainView appropriately represents the result
        assertFalse(mainView.getWords().contains(viewOne));
        assertFalse(mainView.getWords().contains(viewTwo));
        assertEquals(1, mainView.getWords().size());
        for(AbstractWordView word: mainView.getWords()) {
            assertEquals("Mule Horse Donkey", word.getWord().getValue());
        }
    }

    @Test
    public void testConnectHorizontalRowPoem() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWordsOne = new ArrayList<Word>();
        rowWordsOne.add(wordOne);
        rowWordsOne.add(wordTwo);
        Row rowOne = new Row(rowWordsOne);
        List<Word> rowWordsTwo = new ArrayList<Word>();
        rowWordsTwo.add(wordThree);
        Row rowTwo = new Row(rowWordsTwo);
        List<Row> poemRowsOne = new ArrayList<Row>();
        poemRowsOne.add(rowTwo);
        Poem poemOne = new Poem(poemRowsOne);
        gameState.getProtectedArea().addAbstractWord(rowOne);
        gameState.getProtectedArea().addAbstractWord(poemOne);
        AbstractWordView viewOne = new AbstractWordView(rowOne, new Position(0, 0));
        AbstractWordView viewTwo = new AbstractWordView(poemOne, new Position(10, 0));
        viewOne.setSize(5, 10);
        mainView.addAbstractWordView(viewOne);
        mainView.addAbstractWordView(viewTwo);
        // Check that the current state is properly set up
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(rowOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemOne));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());

        ConnectionController controller = new ConnectionController(mainView, gameState);
        controller.connect(viewOne, viewTwo);
        // Check that gameState appropriately represents the result
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(rowOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemOne));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());
        for(AbstractWord word: gameState.getProtectedArea().getAbstractWordCollection()) {
            assertTrue(word.getValue().equals("Mule Horse") || word.getValue().equals("Donkey"));
        }
        // Check that mainView appropriately represents the result
        assertTrue(mainView.getWords().contains(viewOne));
        assertTrue(mainView.getWords().contains(viewTwo));
        assertEquals(2, mainView.getWords().size());
        for(AbstractWordView word: mainView.getWords()) {
            assertTrue(word.getWord().getValue().equals("Mule Horse") || word.getWord().getValue().equals("Donkey"));
        }
    }

    @Test
    public void testConnectHorizontalPoemWord() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWordsOne = new ArrayList<Word>();
        rowWordsOne.add(wordOne);
        rowWordsOne.add(wordTwo);
        Row rowOne = new Row(rowWordsOne);
        List<Row> poemRowsOne = new ArrayList<Row>();
        poemRowsOne.add(rowOne);
        Poem poemOne = new Poem(poemRowsOne);
        gameState.getProtectedArea().addAbstractWord(poemOne);
        gameState.getProtectedArea().addAbstractWord(wordThree);
        AbstractWordView viewOne = new AbstractWordView(poemOne, new Position(0, 0));
        AbstractWordView viewTwo = new AbstractWordView(wordThree, new Position(10, 0));
        viewOne.setSize(5, 10);
        mainView.addAbstractWordView(viewOne);
        mainView.addAbstractWordView(viewTwo);
        // Check that the current state is properly set up
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(wordThree));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());

        ConnectionController controller = new ConnectionController(mainView, gameState);
        controller.connect(viewOne, viewTwo);
        // Check that gameState appropriately represents the result
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(wordThree));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());
        for(AbstractWord word: gameState.getProtectedArea().getAbstractWordCollection()) {
            assertTrue(word.getValue().equals("Mule Horse") || word.getValue().equals("Donkey"));
        }
        // Check that mainView appropriately represents the result
        assertTrue(mainView.getWords().contains(viewOne));
        assertTrue(mainView.getWords().contains(viewTwo));
        assertEquals(2, mainView.getWords().size());
        for(AbstractWordView word: mainView.getWords()) {
            assertTrue(word.getWord().getValue().equals("Mule Horse") || word.getWord().getValue().equals("Donkey"));
        }
    }

    @Test
    public void testConnectHorizontalPoemRow() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWordsOne = new ArrayList<Word>();
        rowWordsOne.add(wordOne);
        rowWordsOne.add(wordTwo);
        Row rowOne = new Row(rowWordsOne);
        List<Row> poemRowsOne = new ArrayList<Row>();
        poemRowsOne.add(rowOne);
        Poem poemOne = new Poem(poemRowsOne);
        List<Word> rowWordsTwo = new ArrayList<Word>();
        rowWordsTwo.add(wordThree);
        Row rowTwo = new Row(rowWordsTwo);
        gameState.getProtectedArea().addAbstractWord(poemOne);
        gameState.getProtectedArea().addAbstractWord(rowTwo);
        AbstractWordView viewOne = new AbstractWordView(poemOne, new Position(0, 0));
        AbstractWordView viewTwo = new AbstractWordView(rowTwo, new Position(10, 0));
        viewOne.setSize(5, 10);
        mainView.addAbstractWordView(viewOne);
        mainView.addAbstractWordView(viewTwo);
        // Check that the current state is properly set up
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(rowTwo));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());

        ConnectionController controller = new ConnectionController(mainView, gameState);
        controller.connect(viewOne, viewTwo);
        // Check that gameState appropriately represents the result
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(rowTwo));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());
        for(AbstractWord word: gameState.getProtectedArea().getAbstractWordCollection()) {
            assertTrue(word.getValue().equals("Mule Horse") || word.getValue().equals("Donkey"));
        }
        // Check that mainView appropriately represents the result
        assertTrue(mainView.getWords().contains(viewOne));
        assertTrue(mainView.getWords().contains(viewTwo));
        assertEquals(2, mainView.getWords().size());
        for(AbstractWordView word: mainView.getWords()) {
            assertTrue(word.getWord().getValue().equals("Mule Horse") || word.getWord().getValue().equals("Donkey"));
        }
    }

    @Test
    public void testConnectHorizontalPoemPoem() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWordsOne = new ArrayList<Word>();
        rowWordsOne.add(wordOne);
        rowWordsOne.add(wordTwo);
        Row rowOne = new Row(rowWordsOne);
        List<Row> poemRowsOne = new ArrayList<Row>();
        poemRowsOne.add(rowOne);
        Poem poemOne = new Poem(poemRowsOne);
        List<Word> rowWordsTwo = new ArrayList<Word>();
        rowWordsTwo.add(wordThree);
        Row rowTwo = new Row(rowWordsTwo);
        List<Row> poemRowsTwo = new ArrayList<Row>();
        poemRowsTwo.add(rowTwo);
        Poem poemTwo = new Poem(poemRowsTwo);
        gameState.getProtectedArea().addAbstractWord(poemOne);
        gameState.getProtectedArea().addAbstractWord(poemTwo);
        AbstractWordView viewOne = new AbstractWordView(poemOne, new Position(0, 0));
        AbstractWordView viewTwo = new AbstractWordView(poemTwo, new Position(10, 0));
        viewOne.setSize(5, 10);
        mainView.addAbstractWordView(viewOne);
        mainView.addAbstractWordView(viewTwo);
        // Check that the current state is properly set up
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemTwo));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());

        ConnectionController controller = new ConnectionController(mainView, gameState);
        controller.connect(viewOne, viewTwo);
        // Check that gameState appropriately represents the result
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemTwo));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());
        for(AbstractWord word: gameState.getProtectedArea().getAbstractWordCollection()) {
            assertTrue(word.getValue().equals("Mule Horse") || word.getValue().equals("Donkey"));
        }
        // Check that mainView appropriately represents the result
        assertTrue(mainView.getWords().contains(viewOne));
        assertTrue(mainView.getWords().contains(viewTwo));
        assertEquals(2, mainView.getWords().size());
        for(AbstractWordView word: mainView.getWords()) {
            assertTrue(word.getWord().getValue().equals("Mule Horse") || word.getWord().getValue().equals("Donkey"));
        }
    }

    @Test
    public void testConnectVerticalWordWord() throws Exception {
        assertTrue(mainView.getWords().isEmpty());
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        gameState.getProtectedArea().addAbstractWord(wordOne);
        gameState.getProtectedArea().addAbstractWord(wordTwo);
        AbstractWordView wordViewOne = new AbstractWordView(wordOne, new Position(0, 0));
        AbstractWordView wordViewTwo = new AbstractWordView(wordTwo, new Position(0, 10));
        wordViewOne.setSize(10, 5);
        mainView.addAbstractWordView(wordViewOne);
        mainView.addAbstractWordView(wordViewTwo);
        // Check that the current state is properly set up
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(wordOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(wordTwo));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());

        ConnectionController controller = new ConnectionController(mainView, gameState);
        controller.connect(wordViewTwo, wordViewOne);
        // Check that gameState appropriately represents the result
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(wordOne));
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(wordTwo));
        assertEquals(1, gameState.getProtectedArea().getAbstractWordCollection().size());
        for(AbstractWord word: gameState.getProtectedArea().getAbstractWordCollection()) {
            assertEquals("Mule\nHorse", word.getValue());
        }
        // Check that mainView appropriately represents the result
        assertFalse(mainView.getWords().contains(wordViewOne));
        assertFalse(mainView.getWords().contains(wordViewTwo));
        assertEquals(1, mainView.getWords().size());
        for(AbstractWordView word: mainView.getWords()) {
            assertEquals("Mule\nHorse", word.getWord().getValue());
        }
    }

    @Test
    public void testConnectVerticalWordRow() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWords = new ArrayList<Word>();
        rowWords.add(wordTwo);
        rowWords.add(wordThree);
        Row rowOne = new Row(rowWords);
        gameState.getProtectedArea().addAbstractWord(wordOne);
        gameState.getProtectedArea().addAbstractWord(rowOne);
        AbstractWordView wordViewOne = new AbstractWordView(wordOne, new Position(0, 0));
        AbstractWordView rowViewTwo = new AbstractWordView(rowOne, new Position(0, 10));
        wordViewOne.setSize(10, 5);
        mainView.addAbstractWordView(wordViewOne);
        mainView.addAbstractWordView(rowViewTwo);
        // Check that the current state is properly set up
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(wordOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(rowOne));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());

        ConnectionController controller = new ConnectionController(mainView, gameState);
        controller.connect(wordViewOne, rowViewTwo);
        // Check that gameState appropriately represents the result
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(wordOne));
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(rowOne));
        assertEquals(1, gameState.getProtectedArea().getAbstractWordCollection().size());
        for(AbstractWord word: gameState.getProtectedArea().getAbstractWordCollection()) {
            assertEquals("Mule\nHorse Donkey", word.getValue());
        }
        // Check that mainView appropriately represents the result
        assertFalse(mainView.getWords().contains(wordViewOne));
        assertFalse(mainView.getWords().contains(rowViewTwo));
        assertEquals(1, mainView.getWords().size());
        for(AbstractWordView word: mainView.getWords()) {
            assertEquals("Mule\nHorse Donkey", word.getWord().getValue());
        }
    }

    @Test
    public void testConnectVerticalWordPoem() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWords = new ArrayList<Word>();
        rowWords.add(wordTwo);
        rowWords.add(wordThree);
        Row rowOne = new Row(rowWords);
        List<Row> poemRows = new ArrayList<Row>();
        poemRows.add(rowOne);
        Poem poemOne = new Poem(poemRows);
        gameState.getProtectedArea().addAbstractWord(wordOne);
        gameState.getProtectedArea().addAbstractWord(poemOne);
        AbstractWordView viewOne = new AbstractWordView(wordOne, new Position(0, 0));
        AbstractWordView viewTwo = new AbstractWordView(poemOne, new Position(0, 10));
        viewOne.setSize(10, 5);
        mainView.addAbstractWordView(viewOne);
        mainView.addAbstractWordView(viewTwo);
        // Check that the current state is properly set up
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(wordOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemOne));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());

        ConnectionController controller = new ConnectionController(mainView, gameState);
        // Invalid connect, should have no effect on gameState
        controller.connect(viewOne, viewTwo);

        // Check that gameState appropriately represents the result
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(wordOne));
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(poemOne));
        assertEquals(1, gameState.getProtectedArea().getAbstractWordCollection().size());
        for(AbstractWord word: gameState.getProtectedArea().getAbstractWordCollection()) {
            assertEquals("Mule\nHorse Donkey", word.getValue());
        }
        // Check that mainView appropriately represents the result
        assertFalse(mainView.getWords().contains(viewOne));
        assertFalse(mainView.getWords().contains(viewTwo));
        assertEquals(1, mainView.getWords().size());
        for(AbstractWordView word: mainView.getWords()) {
            assertEquals("Mule\nHorse Donkey", word.getWord().getValue());
        }
    }

    @Test
    public void testConnectVerticalRowWord() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWords = new ArrayList<Word>();
        rowWords.add(wordOne);
        rowWords.add(wordTwo);
        Row rowOne = new Row(rowWords);
        gameState.getProtectedArea().addAbstractWord(rowOne);
        gameState.getProtectedArea().addAbstractWord(wordThree);
        AbstractWordView viewOne = new AbstractWordView(rowOne, new Position(0, 0));
        AbstractWordView viewTwo = new AbstractWordView(wordThree, new Position(0, 10));
        viewOne.setSize(10, 5);
        mainView.addAbstractWordView(viewOne);
        mainView.addAbstractWordView(viewTwo);
        // Check that the current state is properly set up
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(rowOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(wordThree));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());

        ConnectionController controller = new ConnectionController(mainView, gameState);
        controller.connect(viewOne, viewTwo);
        // Check that gameState appropriately represents the result
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(rowOne));
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(wordThree));
        assertEquals(1, gameState.getProtectedArea().getAbstractWordCollection().size());
        for(AbstractWord word: gameState.getProtectedArea().getAbstractWordCollection()) {
            assertEquals("Mule Horse\nDonkey", word.getValue());
        }
        // Check that mainView appropriately represents the result
        assertFalse(mainView.getWords().contains(viewOne));
        assertFalse(mainView.getWords().contains(viewTwo));
        assertEquals(1, mainView.getWords().size());
        for(AbstractWordView word: mainView.getWords()) {
            assertEquals("Mule Horse\nDonkey", word.getWord().getValue());
        }
    }

    @Test
    public void testConnectVerticalRowRow() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWordsOne = new ArrayList<Word>();
        rowWordsOne.add(wordOne);
        rowWordsOne.add(wordTwo);
        Row rowOne = new Row(rowWordsOne);
        List<Word> rowWordsTwo = new ArrayList<Word>();
        rowWordsTwo.add(wordThree);
        Row rowTwo = new Row(rowWordsTwo);
        gameState.getProtectedArea().addAbstractWord(rowOne);
        gameState.getProtectedArea().addAbstractWord(rowTwo);
        AbstractWordView viewOne = new AbstractWordView(rowOne, new Position(0, 0));
        AbstractWordView viewTwo = new AbstractWordView(rowTwo, new Position(0, 10));
        viewOne.setSize(10, 5);
        mainView.addAbstractWordView(viewOne);
        mainView.addAbstractWordView(viewTwo);
        // Check that the current state is properly set up
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(rowOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(rowTwo));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());

        ConnectionController controller = new ConnectionController(mainView, gameState);
        controller.connect(viewOne, viewTwo);
        // Check that gameState appropriately represents the result
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(rowOne));
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(rowTwo));
        assertEquals(1, gameState.getProtectedArea().getAbstractWordCollection().size());
        for(AbstractWord word: gameState.getProtectedArea().getAbstractWordCollection()) {
            assertEquals("Mule Horse\nDonkey", word.getValue());
        }
        // Check that mainView appropriately represents the result
        assertFalse(mainView.getWords().contains(viewOne));
        assertFalse(mainView.getWords().contains(viewTwo));
        assertEquals(1, mainView.getWords().size());
        for(AbstractWordView word: mainView.getWords()) {
            assertEquals("Mule Horse\nDonkey", word.getWord().getValue());
        }
    }

    @Test
    public void testConnectVerticalRowPoem() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWordsOne = new ArrayList<Word>();
        rowWordsOne.add(wordOne);
        rowWordsOne.add(wordTwo);
        Row rowOne = new Row(rowWordsOne);
        List<Word> rowWordsTwo = new ArrayList<Word>();
        rowWordsTwo.add(wordThree);
        Row rowTwo = new Row(rowWordsTwo);
        List<Row> poemRowsOne = new ArrayList<Row>();
        poemRowsOne.add(rowTwo);
        Poem poemOne = new Poem(poemRowsOne);
        gameState.getProtectedArea().addAbstractWord(rowOne);
        gameState.getProtectedArea().addAbstractWord(poemOne);
        AbstractWordView viewOne = new AbstractWordView(rowOne, new Position(0, 0));
        AbstractWordView viewTwo = new AbstractWordView(poemOne, new Position(0, 10));
        viewOne.setSize(10, 5);
        mainView.addAbstractWordView(viewOne);
        mainView.addAbstractWordView(viewTwo);
        // Check that the current state is properly set up
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(rowOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemOne));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());

        ConnectionController controller = new ConnectionController(mainView, gameState);
        controller.connect(viewOne, viewTwo);
        // Check that gameState appropriately represents the result
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(rowOne));
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(poemOne));
        assertEquals(1, gameState.getProtectedArea().getAbstractWordCollection().size());
        for(AbstractWord word: gameState.getProtectedArea().getAbstractWordCollection()) {
            assertEquals("Mule Horse\nDonkey", word.getValue());
        }
        // Check that mainView appropriately represents the result
        assertFalse(mainView.getWords().contains(viewOne));
        assertFalse(mainView.getWords().contains(viewTwo));
        assertEquals(1, mainView.getWords().size());
        for(AbstractWordView word: mainView.getWords()) {
            assertEquals("Mule Horse\nDonkey", word.getWord().getValue());
        }
    }

    @Test
    public void testConnectVerticalPoemWord() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWordsOne = new ArrayList<Word>();
        rowWordsOne.add(wordOne);
        rowWordsOne.add(wordTwo);
        Row rowOne = new Row(rowWordsOne);
        List<Row> poemRowsOne = new ArrayList<Row>();
        poemRowsOne.add(rowOne);
        Poem poemOne = new Poem(poemRowsOne);
        gameState.getProtectedArea().addAbstractWord(poemOne);
        gameState.getProtectedArea().addAbstractWord(wordThree);
        AbstractWordView viewOne = new AbstractWordView(poemOne, new Position(0, 0));
        AbstractWordView viewTwo = new AbstractWordView(wordThree, new Position(0, 10));
        viewOne.setSize(10, 5);
        mainView.addAbstractWordView(viewOne);
        mainView.addAbstractWordView(viewTwo);
        // Check that the current state is properly set up
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(wordThree));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());

        ConnectionController controller = new ConnectionController(mainView, gameState);
        controller.connect(viewOne, viewTwo);
        // Check that gameState appropriately represents the result
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemOne));
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(wordThree));
        assertEquals(1, gameState.getProtectedArea().getAbstractWordCollection().size());
        for(AbstractWord word: gameState.getProtectedArea().getAbstractWordCollection()) {
            assertEquals("Mule Horse\nDonkey", word.getValue());
        }
        // Check that mainView appropriately represents the result
        assertFalse(mainView.getWords().contains(viewOne));
        assertFalse(mainView.getWords().contains(viewTwo));
        assertEquals(1, mainView.getWords().size());
        for(AbstractWordView word: mainView.getWords()) {
            assertEquals("Mule Horse\nDonkey", word.getWord().getValue());
        }
    }

    @Test
    public void testConnectVerticalPoemRow() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWordsOne = new ArrayList<Word>();
        rowWordsOne.add(wordOne);
        rowWordsOne.add(wordTwo);
        Row rowOne = new Row(rowWordsOne);
        List<Row> poemRowsOne = new ArrayList<Row>();
        poemRowsOne.add(rowOne);
        Poem poemOne = new Poem(poemRowsOne);
        List<Word> rowWordsTwo = new ArrayList<Word>();
        rowWordsTwo.add(wordThree);
        Row rowTwo = new Row(rowWordsTwo);
        gameState.getProtectedArea().addAbstractWord(poemOne);
        gameState.getProtectedArea().addAbstractWord(rowTwo);
        AbstractWordView viewOne = new AbstractWordView(poemOne, new Position(0, 0));
        AbstractWordView viewTwo = new AbstractWordView(rowTwo, new Position(0, 10));
        viewOne.setSize(10, 5);
        mainView.addAbstractWordView(viewOne);
        mainView.addAbstractWordView(viewTwo);
        // Check that the current state is properly set up
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(rowTwo));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());

        ConnectionController controller = new ConnectionController(mainView, gameState);
        controller.connect(viewOne, viewTwo);
        // Check that gameState appropriately represents the result
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemOne));
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(rowTwo));
        assertEquals(1, gameState.getProtectedArea().getAbstractWordCollection().size());
        for(AbstractWord word: gameState.getProtectedArea().getAbstractWordCollection()) {
            assertEquals("Mule Horse\nDonkey", word.getValue());
        }
        // Check that mainView appropriately represents the result
        assertFalse(mainView.getWords().contains(viewOne));
        assertFalse(mainView.getWords().contains(viewTwo));
        assertEquals(1, mainView.getWords().size());
        for(AbstractWordView word: mainView.getWords()) {
            assertEquals("Mule Horse\nDonkey", word.getWord().getValue());
        }
    }

    @Test
    public void testConnectVerticalPoemPoem() throws Exception {
        Word wordOne = new Word("Mule", WordType.NOUN);
        Word wordTwo = new Word("Horse", WordType.NOUN);
        Word wordThree = new Word("Donkey", WordType.NOUN);
        List<Word> rowWordsOne = new ArrayList<Word>();
        rowWordsOne.add(wordOne);
        rowWordsOne.add(wordTwo);
        Row rowOne = new Row(rowWordsOne);
        List<Row> poemRowsOne = new ArrayList<Row>();
        poemRowsOne.add(rowOne);
        Poem poemOne = new Poem(poemRowsOne);
        List<Word> rowWordsTwo = new ArrayList<Word>();
        rowWordsTwo.add(wordThree);
        Row rowTwo = new Row(rowWordsTwo);
        List<Row> poemRowsTwo = new ArrayList<Row>();
        poemRowsTwo.add(rowTwo);
        Poem poemTwo = new Poem(poemRowsTwo);
        gameState.getProtectedArea().addAbstractWord(poemOne);
        gameState.getProtectedArea().addAbstractWord(poemTwo);
        AbstractWordView viewOne = new AbstractWordView(poemOne, new Position(0, 0));
        AbstractWordView viewTwo = new AbstractWordView(poemTwo, new Position(0, 10));
        viewOne.setSize(10, 5);
        mainView.addAbstractWordView(viewOne);
        mainView.addAbstractWordView(viewTwo);
        // Check that the current state is properly set up
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemOne));
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemTwo));
        assertEquals(2, gameState.getProtectedArea().getAbstractWordCollection().size());

        ConnectionController controller = new ConnectionController(mainView, gameState);
        controller.connect(viewOne, viewTwo);
        // Check that gameState appropriately represents the result
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(poemOne));
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(poemTwo));
        assertEquals(1, gameState.getProtectedArea().getAbstractWordCollection().size());
        for(AbstractWord word: gameState.getProtectedArea().getAbstractWordCollection()) {
            assertEquals("Mule Horse\nDonkey", word.getValue());
        }
        // Check that mainView appropriately represents the result
        assertFalse(mainView.getWords().contains(viewOne));
        assertFalse(mainView.getWords().contains(viewTwo));
        assertEquals(1, mainView.getWords().size());
        for(AbstractWordView word: mainView.getWords()) {
            assertEquals("Mule Horse\nDonkey", word.getWord().getValue());
        }
    }
}
