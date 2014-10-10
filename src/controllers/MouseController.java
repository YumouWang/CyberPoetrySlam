package controllers;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;

import models.Area;
import models.GameState;
import models.Position;
import views.AbstractWordView;
import views.AdjacencyType;
import views.MainView;
import views.MainGUI;

public class MouseController implements MouseListener, MouseMotionListener {

    AbstractWordView selectedWord;
    Position mouseDownPosition;
    MainGUI view;
    GameState gameState;
    Area unprotectedArea;
    Area protectedArea;

    public MouseController(MainGUI view, GameState gameState) {
        this.view = view;
        this.gameState = gameState;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseDownPosition = new Position(e.getX(), e.getY());
        selectedWord = null;
        Collection<AbstractWordView> words = view.getProtectedAreaWords();
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
            Collection<AbstractWordView> words = view.getProtectedAreaWords();
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
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
    public void removeProtectedWords(AbstractWordView word) {
        view.getProtectedAreaWords().remove(word);
    }
    
    public void removeUnprotectedWords(AbstractWordView word) {
        view.getUnprotectedAreaWords().remove(word);
    }
    
    public boolean isInProtectArea(Position position){
    	
    	return false;
    }
}
