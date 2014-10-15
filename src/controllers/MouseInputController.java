package controllers;

import models.GameState;
import models.Position;
import views.AbstractWordView;
import views.AdjacencyType;
import views.ConnectionBox;
import views.MainView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;

/**
 * A controller for handling mouse input. Delegates to other controllers.
 * Unlike other controllers, this controller exists for the duration of the program
 * to handle mouse events and maintain state between mouse events.
 *
 * Created by Nathan on 10/4/2014.
 */
public class MouseInputController implements MouseListener, MouseMotionListener {

    AbstractWordView selectedWord;
    Collection<AbstractWordView> selectedWords;
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
//            selectedWord.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        selectedWord = null;
        Collection<AbstractWordView> words = mainView.getWords();
        for(AbstractWordView word: words) {
            if(word.isClicked(mouseDownPosition)) {
                selectedWord = word;
                selectedWord.setBackground(Color.LIGHT_GRAY.brighter());
//                selectedWord.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.CYAN), BorderFactory.createLineBorder(Color.BLACK)));
                break;
            }
        }
        if(selectedWord == null) {
            mainView.getSelectionBox().startNewSelection(mouseDownPosition);
        } else {
            if(e.isControlDown()) {
                selectedWord.setBackground(Color.LIGHT_GRAY);
                DisconnectController controller = new DisconnectController(mainView, gameState);
                AbstractWordView selectedElement = selectedWord.getSelectedElement(new ConnectionBox(mouseDownPosition, 0, 0));
                if(controller.disconnect(selectedElement, selectedWord)) {
                    selectedWord = selectedElement;
                }
                selectedWord.setBackground(Color.LIGHT_GRAY.brighter());
            }
        }
        mainView.refresh();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(selectedWord != null) {
            Position positionDiff = new Position(e.getX() - mouseDownPosition.getX(), e.getY() - mouseDownPosition.getY());
            selectedWord.moveTo(new Position(selectedWord.getPosition().getX() + positionDiff.getX(), selectedWord.getPosition().getY() + positionDiff.getY()));
            boolean isOverlapping = false;
            boolean isAdjacent = false;
            Collection<AbstractWordView> words = mainView.getWords();
            for (AbstractWordView word : words) {
                if(!word.equals(selectedWord)) {
                    if (word.isOverlapping(selectedWord)) {
                        isOverlapping = true;
                    }
                    AdjacencyType adjacencyType = selectedWord.isAdjacentTo(word);
                    if(adjacencyType != AdjacencyType.NOT_ADJACENT) {
                        isAdjacent = true;
                        word.setBackground(Color.GREEN);
                    } else {
                        word.setBackground(Color.LIGHT_GRAY);
                    }
                }
            }
            if(isOverlapping) {
                selectedWord.setBackground(Color.RED);
            } else if (isAdjacent) {
                selectedWord.setBackground(Color.GREEN);
            } else {
                selectedWord.setBackground(Color.LIGHT_GRAY.brighter());
            }
        }
        mouseDownPosition = new Position(e.getX(), e.getY());
        if(selectedWord == null) {
            mainView.getSelectionBox().moveSelection(mouseDownPosition);
            // Highlight selected items, un-highlight unselected items
            selectedWords = mainView.getSelectionBox().getSelectedItems(mainView.getWords());
            for(AbstractWordView view : mainView.getWords()) {
                if(selectedWords.contains(view)) {
                    view.setBackground(Color.LIGHT_GRAY.brighter());
                } else {
                    view.setBackground(Color.LIGHT_GRAY);
                }
            }
        }
        mainView.refresh();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(selectedWord != null) {
            Collection<AbstractWordView> words = mainView.getWords();
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
        } else {
            selectedWords = mainView.getSelectionBox().getSelectedItems(mainView.getWords());
            for(AbstractWordView view : mainView.getWords()) {
                if(selectedWords.contains(view)) {
                    view.setBackground(Color.LIGHT_GRAY.brighter());
                } else {
                    view.setBackground(Color.LIGHT_GRAY);
                }
            }
            mainView.getSelectionBox().clearBox();
        }
        mainView.refresh();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
