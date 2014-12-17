package controllers;

import models.*;
import views.*;

import java.awt.*;
import java.util.Collection;
import java.util.List;


/**
 * A realization of the AbstractWordViewVisitor interface for connecting two words
 *
 * @author Nathan
 * @version 10/15/2014
 */
public class HorizontalConnectionVisitor implements AbstractWordViewVisitor {

    MainView mainView;
    GameState gameState;
    Area protectedArea;

    /**
     * Constructor
     *
     * @param mainView  The MainView to update once a connection has been made
     * @param gameState The GameState to perform connections on
     */
    public HorizontalConnectionVisitor(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.protectedArea = gameState.getProtectedArea();
        this.gameState = gameState;
    }

    @Override
    public boolean visit(WordView wordViewOne, WordView wordViewTwo) {
        if (connectionCausesOverlap(wordViewOne, wordViewTwo)) {
            return false;
        }
        Word wordOne = wordViewOne.getWord();
        Word wordTwo = wordViewTwo.getWord();

        // Connect the two word entities
        Row result = wordOne.connect(wordTwo);
        // Update the area to reflect the updated words
        protectedArea.removeAbstractWord(wordOne);
        protectedArea.removeAbstractWord(wordTwo);
        protectedArea.addAbstractWord(result);

        // Create an appropriate view object
        RowView resultRowView = new RowView(result, wordViewOne.getPosition(), mainView);
        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(wordViewOne);
        mainView.removeProtectedAbstractWordView(wordViewTwo);
        mainView.addProtectedAbstractWordView(resultRowView);
        // Select the resulting row
        resultRowView.setBackground(Color.LIGHT_GRAY);
        return true;
    }

    @Override
    public boolean visit(WordView wordViewOne, RowView rowViewTwo) {
        if (connectionCausesOverlap(wordViewOne, rowViewTwo)) {
            return false;
        }

        Word wordOne = wordViewOne.getWord();
        Row rowTwo = rowViewTwo.getWord();

        // Connect the row and the word
        rowTwo.connectToFront(wordOne);
        // Update the area to reflect the updated words
        // The row already exists in the area
        protectedArea.removeAbstractWord(wordOne);

        // Update the rowViewObject
        rowViewTwo.addWordToFront(wordViewOne);
        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(wordViewOne);
        // Select the resulting row
        rowViewTwo.setBackground(Color.LIGHT_GRAY);
        // Move the row to the appropriate position
        // This also updates the positions of all the words in the row
        rowViewTwo.moveTo(wordViewOne.getPosition());
        return true;
    }

    @Override
    public boolean visit(WordView wordViewOne, PoemView poemViewTwo) {
        List<RowView> rowViews = poemViewTwo.getRowViews();
        boolean successful = false;
        for (RowView rowView : rowViews) {
            AdjacencyType adjacencyType = rowView.isAdjacentTo(wordViewOne);
            if (adjacencyType == AdjacencyType.RIGHT) {
                successful = visit(wordViewOne, rowView);
                poemViewTwo.shiftRow(rowView, -wordViewOne.getWidth());
                break;
            }
        }
        poemViewTwo.moveTo(poemViewTwo.getPosition());
        return successful;
    }

    @Override
    public boolean visit(RowView rowViewOne, WordView wordViewTwo) {
        if (connectionCausesOverlap(rowViewOne, wordViewTwo)) {
            return false;
        }

        Row rowOne = rowViewOne.getWord();
        Word wordTwo = wordViewTwo.getWord();

        // Connect the row and the word
        rowOne.connect(wordTwo);
        // Update the area to reflect the updated words
        // The row already exists in the area
        protectedArea.removeAbstractWord(wordTwo);

        // Update the rowViewObject
        rowViewOne.addWord(wordViewTwo);
        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(wordViewTwo);
        // Select the resulting row
        rowViewOne.setBackground(Color.LIGHT_GRAY);
        // Move the row to the appropriate position
        // This also updates the positions of all the words in the row
        rowViewOne.moveTo(rowViewOne.getPosition());
        return true;
    }

    @Override
    public boolean visit(RowView rowViewOne, RowView rowViewTwo) {
        if (connectionCausesOverlap(rowViewOne, rowViewTwo)) {
            return false;
        }

        Row rowOne = rowViewOne.getWord();
        Row rowTwo = rowViewTwo.getWord();

        // Connect the row and the word
        rowOne.connect(rowTwo);
        // Update the area to reflect the updated words
        // The row already exists in the area
        protectedArea.removeAbstractWord(rowTwo);

        // Update the rowViewObject
        rowViewOne.addRow(rowViewTwo);
        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(rowViewTwo);
        // Select the resulting row
        rowViewOne.setBackground(Color.LIGHT_GRAY);
        // Move the row to the appropriate position
        // This also updates the positions of all the words in the row
        rowViewOne.moveTo(rowViewOne.getPosition());
        return true;
    }

    @Override
    public boolean visit(RowView rowViewOne, PoemView poemViewTwo) {
        return false;
    }

    @Override
    public boolean visit(PoemView poemViewOne, WordView wordViewTwo) {
        List<RowView> rowViews = poemViewOne.getRowViews();
        boolean successful = false;
        for (RowView rowView : rowViews) {
            AdjacencyType adjacencyType = rowView.isAdjacentTo(wordViewTwo);
            if (adjacencyType == AdjacencyType.RIGHT) {
                successful = visit(wordViewTwo, rowView);
                break;
            } else if (adjacencyType == AdjacencyType.LEFT) {
                successful = visit(rowView, wordViewTwo);
                break;
            }
        }
        poemViewOne.moveTo(poemViewOne.getPosition());
        return successful;
    }

    @Override
    public boolean visit(PoemView poemViewOne, RowView rowViewTwo) {
        List<RowView> rowViews = poemViewOne.getRowViews();
        boolean successful = false;
        for (RowView rowView : rowViews) {
            AdjacencyType adjacencyType = rowView.isAdjacentTo(rowViewTwo);
            if (adjacencyType == AdjacencyType.RIGHT) {
                successful = visit(rowViewTwo, rowView);
                break;
            } else if (adjacencyType == AdjacencyType.LEFT) {
                successful = visit(rowView, rowViewTwo);
                break;
            }
        }
        poemViewOne.moveTo(poemViewOne.getPosition());
        return successful;
    }

    @Override
    public boolean visit(PoemView poemViewOne, PoemView poemViewTwo) {
        // Cannot horizontally connect two poems, so do nothing
        return false;
    }

    /**
     * Attempts to move the second word to right after the first word (where it will be after connection)
     * and returns whether the connection would cause an overlap
     *
     * @param one The word on top of the connection
     * @param two The word below the connection
     * @return Returns whether the connection would cause an overlap
     */
    protected boolean connectionCausesOverlap(AbstractWordView one, AbstractWordView two) {
        // Check if the connection will cause an overlap
        Position targetPosition = new Position(one.getPosition().getX() + one.getWidth() + 1, one.getPosition().getY());
        Position originalPosition = two.getPosition();
        two.moveTo(targetPosition);
        // Check if it's overlapping with other words
        Collection<AbstractWordView> words = mainView.getProtectedAreaWords();
        boolean isOverlapping = false;
        for (AbstractWordView word : words) {
            if (!word.equals(two) && !(word.equals(two) || word.contains(two)) && !(word.equals(one) || word.contains(one))) {
                if (word.isOverlapping(two)) {
                    isOverlapping = true;
                    break;
                }
            }
        }
        if (isOverlapping) {
            two.moveTo(originalPosition);
            return true;
        }
        return false;
    }
}
