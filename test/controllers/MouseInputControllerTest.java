package controllers;

import models.GameState;
import models.Position;
import models.Word;
import models.WordType;
import org.junit.Before;
import org.junit.Test;
import views.AbstractWordView;
import views.MainView;
import views.WordView;

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
        wordViewOne = new WordView(new Word("Elephant", WordType.ADVERB), new Position(10, 10));
        wordViewTwo = new WordView(new Word("Buffalo", WordType.POSTFIX), new Position(50, 50));
        mainView.addAbstractWordView(wordViewOne);
        mainView.addAbstractWordView(wordViewTwo);
        controller = new MouseInputController(mainView, gameState);
    }

    @Test
    public void testMousePressedNothingSelected() throws Exception {
        MouseEvent event = new MouseEvent(mainView, 0, 0, 0, 9, 9, 1, false);
        assertNull(controller.selectedWord);
        assertNull(controller.mouseDownPosition);

        controller.mousePressed(event);
        assertNull(controller.selectedWord);
        assertEquals(9, controller.mouseDownPosition.getX());
        assertEquals(9, controller.mouseDownPosition.getY());
    }

    @Test
    public void testMousePressedSelectSomething() throws Exception {
        MouseEvent event = new MouseEvent(mainView, 0, 0, 0, 12, 12, 1, false);
        assertNull(controller.selectedWord);
        assertNull(controller.mouseDownPosition);

        controller.mousePressed(event);
        assertEquals(wordViewOne, controller.selectedWord);
        assertEquals(12, controller.mouseDownPosition.getX());
        assertEquals(12, controller.mouseDownPosition.getY());
    }

    @Test
    public void testMouseDraggedNothingSelected() throws Exception {
        MouseEvent event = new MouseEvent(mainView, 0, 0, 0, 9, 9, 1, false);
        assertNull(controller.selectedWord);
        assertNull(controller.mouseDownPosition);

        controller.mouseDragged(event);
        assertNull(controller.selectedWord);
        assertEquals(9, controller.mouseDownPosition.getX());
        assertEquals(9, controller.mouseDownPosition.getY());
    }

    @Test
    public void testMouseDraggedSomethingSelected() throws Exception {
        MouseEvent event = new MouseEvent(mainView, 0, 0, 0, 15, 15, 1, false);
        controller.selectedWord = wordViewOne;
        controller.mouseDownPosition = new Position(11, 11);

        controller.mouseDragged(event);
        assertEquals(wordViewOne, controller.selectedWord);
        assertEquals(15, controller.mouseDownPosition.getX());
        assertEquals(15, controller.mouseDownPosition.getY());
        assertEquals(14, wordViewOne.getPosition().getX());
        assertEquals(14, wordViewOne.getPosition().getY());
    }

    @Test
    public void testMouseReleasedNothingSelected() throws Exception {
        MouseEvent event = new MouseEvent(mainView, 0, 0, 0, 9, 9, 1, false);
        assertNull(controller.selectedWord);
        assertNull(controller.mouseDownPosition);

        controller.mouseReleased(event);
        assertNull(controller.selectedWord);
        assertNull(controller.mouseDownPosition);
    }

    @Test
    public void testMouseReleasedSomethingSelected() throws Exception {
        MouseEvent event = new MouseEvent(mainView, 0, 0, 0, 13, 13, 1, false);
        controller.selectedWord = wordViewOne;
        controller.mouseDownPosition = new Position(11, 11);

        controller.mouseReleased(event);
        assertEquals(wordViewOne, controller.selectedWord);
        assertEquals(11, controller.mouseDownPosition.getX());
        assertEquals(11, controller.mouseDownPosition.getY());
        assertEquals(10, wordViewOne.getPosition().getX());
        assertEquals(10, wordViewOne.getPosition().getY());
    }
}