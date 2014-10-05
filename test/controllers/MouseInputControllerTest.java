package controllers;

import models.GameState;
import models.Position;
import models.Word;
import models.WordType;
import org.junit.Before;
import org.junit.Test;
import views.AbstractWordView;
import views.MainView;

import java.awt.event.MouseEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MouseInputControllerTest {

    GameState gameState;
    MainView mainView;
    MouseInputController controller;
    AbstractWordView wordViewOne;
    AbstractWordView wordViewTwo;

    @Before
    public void initialize() {
        gameState = new GameState();
        gameState.getProtectedArea().getAbstractWordCollection().clear();
        mainView = new MainView(gameState);
        wordViewOne = new AbstractWordView(new Word("Elephant", WordType.ADVERB), new Position(10, 10));
        wordViewTwo = new AbstractWordView(new Word("Buffalo", WordType.POSTFIX), new Position(50, 50));
        mainView.addAbstractWordView(wordViewOne);
        mainView.addAbstractWordView(wordViewTwo);
        controller = new MouseInputController(mainView, gameState);
    }

    @Test
    public void testMousePressed() throws Exception {
        MouseEvent event = new MouseEvent(mainView, 0, 0, 0, 9, 9, 1, false);
        assertNull(controller.selectedWord);
        assertNull(controller.mouseDownPosition);

        controller.mousePressed(event);
        assertNull(controller.selectedWord);
        assertEquals(9, controller.mouseDownPosition.getX());
        assertEquals(9, controller.mouseDownPosition.getY());
    }

    @Test
    public void testMousePressed2() throws Exception {
        MouseEvent event = new MouseEvent(mainView, 0, 0, 0, 12, 12, 1, false);
        assertNull(controller.selectedWord);
        assertNull(controller.mouseDownPosition);

        controller.mousePressed(event);
        assertEquals(controller.selectedWord, wordViewOne);
        assertEquals(12, controller.mouseDownPosition.getX());
        assertEquals(12, controller.mouseDownPosition.getY());
    }

    @Test
    public void testMouseDragged() throws Exception {

    }

    @Test
    public void testMouseReleased() throws Exception {

    }
}