package controllers;

import models.*;
import org.junit.Before;
import org.junit.Test;
import views.MainView;
import views.PoemView;
import views.RowView;
import views.WordView;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ButtonControllerTest {
    GameState gameState;
    MainView mainView;
    Word wordOne;
    WordView wordViewOne;
    Word wordTwo;
    WordView wordViewTwo;
    Row rowOne;
    RowView rowViewOne;
    Row rowTwo;
    RowView rowViewTwo;
    Poem poemOne;
    PoemView poemViewOne;
    ButtonController buttonController;

    @Before
    public void initialize() {
        gameState = new GameState(null);
        gameState.getProtectedArea().getAbstractWordCollection().clear();
        gameState.getUnprotectedArea().getAbstractWordCollection().clear();
        mainView = new MainView(gameState, null);
        // create two words
        wordOne = new Word("wordOne", WordType.NOUN);
        wordViewOne = new WordView(wordOne, new Position(50, 50));
        wordTwo = new Word("wordTwo", WordType.NOUN);
        wordViewTwo = new WordView(wordTwo, new Position(100, 100));
        mainView.addProtectedAbstractWordView(wordViewOne);
        gameState.getProtectedArea().addAbstractWord(wordOne);
        mainView.addProtectedAbstractWordView(wordViewTwo);
        gameState.getProtectedArea().addAbstractWord(wordTwo);
        // create two rows
        rowOne = new Row(wordOne);
        rowViewOne = new RowView(rowOne, wordViewOne.getPosition(), mainView);
        rowTwo = new Row(wordTwo);
        rowViewTwo = new RowView(rowTwo, wordViewTwo.getPosition(), mainView);
        rowOne.connect(rowTwo);
        mainView.addProtectedAbstractWordView(rowViewOne);
        gameState.getProtectedArea().addAbstractWord(rowOne);
        mainView.addProtectedAbstractWordView(rowViewTwo);
        gameState.getProtectedArea().addAbstractWord(rowTwo);
        // create a poem
        poemOne = new Poem(rowOne);
        poemOne.connect(rowTwo);
        poemViewOne = new PoemView(poemOne, wordViewOne.getPosition(), mainView);
        mainView.addProtectedAbstractWordView(poemViewOne);
        gameState.getProtectedArea().addAbstractWord(poemOne);
        buttonController = new ButtonController(mainView, gameState);
    }

    @Test
    public void testButton() {
        mainView.getPublishButton();
    }

    @Test
    public void testPublishPoem() {
        assertFalse(mainView.getPublishButton().isEnabled());
        assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
                .contains(wordOne));
        assertTrue(mainView.getProtectedAreaWords().contains(wordViewOne));
        assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
                .contains(wordOne));
        assertFalse(mainView.getUnprotectedAreaWords().contains(wordViewOne));

        assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
                .contains(wordTwo));
        assertTrue(mainView.getProtectedAreaWords().contains(wordViewTwo));
        assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
                .contains(wordTwo));
        assertFalse(mainView.getUnprotectedAreaWords().contains(wordViewTwo));

        assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
                .contains(rowOne));
        assertTrue(mainView.getProtectedAreaWords().contains(rowViewOne));
        assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
                .contains(rowOne));
        assertFalse(mainView.getUnprotectedAreaWords().contains(rowViewOne));

        assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
                .contains(rowTwo));
        assertTrue(mainView.getProtectedAreaWords().contains(rowViewTwo));
        assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
                .contains(rowTwo));
        assertFalse(mainView.getUnprotectedAreaWords().contains(rowViewTwo));

        assertTrue(gameState.getProtectedArea().getAbstractWordCollection()
                .contains(poemOne));
        assertTrue(mainView.getProtectedAreaWords().contains(poemViewOne));
        assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection()
                .contains(poemOne));
        assertFalse(mainView.getUnprotectedAreaWords().contains(poemViewOne));

        //test publish a single row
        buttonController.publishPoem(rowViewOne, "testWall.txt");
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection()
                .contains(rowOne));
        assertFalse(mainView.getProtectedAreaWords().contains(rowViewOne));
        assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection()
                .contains(wordOne));
        assertTrue(mainView.getUnprotectedAreaWords().contains(wordViewOne));

        //test publish a poem
        buttonController.publishPoem(poemViewOne, "testWall.txt");
        assertFalse(gameState.getProtectedArea().getAbstractWordCollection()
                .contains(poemOne));
        assertFalse(mainView.getProtectedAreaWords().contains(poemViewOne));
        assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection()
                .contains(wordOne));
        assertTrue(mainView.getUnprotectedAreaWords().contains(wordViewOne));
        assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection()
                .contains(wordTwo));
        assertTrue(mainView.getUnprotectedAreaWords().contains(wordViewTwo));
        assertFalse(mainView.getPublishButton().isEnabled());

        File wall = new File("testWall.txt");
        wall.delete();
    }
}
