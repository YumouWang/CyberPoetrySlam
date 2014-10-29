package controllers;

import models.GameState;
import models.Position;
import views.AbstractWordView;
import views.AdjacencyType;
import views.MainView;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

import common.Constants;

/**
 * A controller for handling mouse input. Delegates to other controllers.
 * Unlike other controllers, this controller exists for the duration of the program
 * to handle mouse events and maintain state between mouse events.
 *
 * Created by Nathan on 10/4/2014.
 */
public class MouseInputController extends MouseAdapter {

    AbstractWordView selectedWord;
    AbstractWordView selectedWordToDisconnect;
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
        Position mousePosition = new Position(e.getX(), e.getY());
        mousePressedHandler(mousePosition);
        mainView.refresh();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Position mousePosition = new Position(e.getX(), e.getY());
        mouseDraggedHandler(mousePosition);
        mainView.refresh();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Position mousePosition = new Position(e.getX(), e.getY());
        mouseReleasedHandler(mousePosition);
        mainView.refresh();
    }

    void mousePressedHandler(Position position) {
        mouseDownPosition = position;
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

        if(selectedWord == null) {
            mainView.getSelectionBox().startNewSelection(mouseDownPosition);
        }
        if(selectedWord != null && selectedWord.contains(selectedWordToDisconnect)
                && selectedWordToDisconnect.isClicked(mouseDownPosition)) {
            // Disconnect the word
            DisconnectController controller = new DisconnectController(mainView, gameState);
            if(controller.disconnect(selectedWordToDisconnect, selectedWord)) {
                selectedWord.setBackground(Color.LIGHT_GRAY);
                selectedWord = selectedWordToDisconnect;
                selectedWord.setBackground(Color.LIGHT_GRAY.brighter());
            }
        }
        selectedWordToDisconnect = null;
    }

    void mouseDraggedHandler(Position mousePosition) {
        if(selectedWord != null) {
            MoveWordController moveController = new MoveWordController(mainView, gameState);
            moveController.moveWord(selectedWord, mouseDownPosition, mousePosition);
        }
        mouseDownPosition = mousePosition;

        if(selectedWord == null) {
            mainView.getSelectionBox().moveSelection(mouseDownPosition);
            // Highlight selected item, un-highlight unselected items
            if(selectedWordToDisconnect != null) {
                for(AbstractWordView view : mainView.getProtectedAreaWords()) {
                    view.setBackground(Color.LIGHT_GRAY);
                }
            }
            selectedWordToDisconnect = mainView.getSelectionBox().getSelectedItem(mainView.getProtectedAreaWords());

            if(selectedWordToDisconnect != null) {
                selectedWordToDisconnect.setBackground(Color.LIGHT_GRAY.brighter());
            }
        }
    }

    void mouseReleasedHandler(Position mousePosition) {
        if(selectedWord != null && mainView.isInProtectedArea(mousePosition)) {
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
        if(selectedWord == null) {
            selectedWordToDisconnect = mainView.getSelectionBox().getSelectedItem(mainView.getProtectedAreaWords());
            if(selectedWordToDisconnect != null) {
                selectedWordToDisconnect.setBackground(Color.LIGHT_GRAY.brighter());
            }

            mainView.getSelectionBox().clearBox();
        }
    }
}
