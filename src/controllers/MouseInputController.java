package controllers;

import models.GameState;
import models.Position;
import views.AbstractWordView;
import views.AdjacencyType;
import views.ConnectionBox;
import views.MainView;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

/**
 * A controller for handling mouse input. Delegates to other controllers.
 * Unlike other controllers, this controller exists for the duration of the program
 * to handle mouse events and maintain state between mouse events.
 *
 * Created by Nathan on 10/4/2014.
 */
public class MouseInputController extends MouseAdapter {

    AbstractWordView selectedWord;
    Position mouseDownPosition;
    MainView mainView;
    GameState gameState;

    /**
     * Constructor
     * @param mainView The view to update when handling mouse events
     * @param gameState The gameState to enact changes on
     */
    public MouseInputController(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.gameState = gameState;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseDownPosition = new Position(e.getX(), e.getY());
        if(selectedWord != null) {
            selectedWord.setBackground(Color.LIGHT_GRAY);
        }
        selectedWord = null;

        Collection<AbstractWordView> words;
        // If it's in the protected area, select a word from the protectedArea
        if(mainView.isInProtectedArea(mouseDownPosition)) {
            words = mainView.getProtectedAreaWords();
        } else {
            // Otherwise, get a word from the unprotectedArea
            words = mainView.getUnprotectedAreaWords();
        }
        // Select the word from the selection
        for (AbstractWordView word : words) {
            if (word.isClicked(mouseDownPosition)) {
                selectedWord = word;
                selectedWord.setBackground(Color.LIGHT_GRAY.brighter());
                break;
            }
        }

        if(selectedWord != null && e.isControlDown()) {
            // If control is selected, disconnect the word
            selectedWord.setBackground(Color.LIGHT_GRAY);
            DisconnectController controller = new DisconnectController(mainView, gameState);
            AbstractWordView selectedElement = selectedWord.getSelectedElement(new ConnectionBox(mouseDownPosition, 0, 0));
            if(controller.disconnect(selectedElement, selectedWord)) {
                selectedWord = selectedElement;
            }
            selectedWord.setBackground(Color.LIGHT_GRAY.brighter());
        }
        mainView.refresh();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(selectedWord != null) {
            MoveWordController moveController = new MoveWordController(mainView, gameState);
            moveController.moveWord(selectedWord, mouseDownPosition, new Position(e.getX(), e.getY()));
        }
        mouseDownPosition = new Position(e.getX(), e.getY());
        mainView.refresh();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(selectedWord != null && mainView.isInProtectedArea(new Position(e.getX(), e.getY()))) {
            Collection<AbstractWordView> words = mainView.getProtectedAreaWords();
            AbstractWordView connectTarget = null;
            for (AbstractWordView word : words) {
                if (!word.equals(selectedWord)) {
                    AdjacencyType adjacencyType = selectedWord.isAdjacentTo(word);
                    if (adjacencyType != AdjacencyType.NOT_ADJACENT) {
                        connectTarget = word;
                        break;
                    }
                }
            }
            if (connectTarget != null) {
                ConnectController controller = new ConnectController(mainView, gameState);
                controller.connect(selectedWord, connectTarget);
            }
        }
        mainView.refresh();
    }
}
