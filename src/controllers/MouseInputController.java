package controllers;

import models.Position;
import views.AbstractWordView;
import views.AdjacencyType;
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

    static MouseInputController instance;

    AbstractWordView selectedWord;
    Position mouseDownPosition;

    public static MouseInputController getInstance() {
        if(instance == null) {
            instance = new MouseInputController();
        }
        return instance;
    }

    private MouseInputController() {}

    @Override
    public void mousePressed(MouseEvent e) {
        mouseDownPosition = new Position(e.getX(), e.getY());
        selectedWord = null;
        Collection<AbstractWordView> words = MainView.getInstance().getWords();
        for(AbstractWordView word: words) {
            if(word.isClicked(mouseDownPosition)) {
                selectedWord = word;
                break;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(selectedWord != null) {
            Position positionDiff = new Position(e.getX() - mouseDownPosition.getX(), e.getY() - mouseDownPosition.getY());
            selectedWord.moveTo(new Position(selectedWord.getPosition().getX() + positionDiff.getX(), selectedWord.getPosition().getY() + positionDiff.getY()));
            boolean isOverlapping = false;
            boolean isAdjacent = false;
            Collection<AbstractWordView> words = MainView.getInstance().getWords();
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
                selectedWord.setBackground(Color.LIGHT_GRAY);
            }
        }
        mouseDownPosition = new Position(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(selectedWord != null) {
            Collection<AbstractWordView> words = MainView.getInstance().getWords();
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
                ConnectionController controller = new ConnectionController();
                try {
                    controller.connect(selectedWord, connectTarget);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    System.exit(1);
                }
                MainView.getInstance().refresh();
            }
        }
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
