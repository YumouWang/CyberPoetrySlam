package controllers;

import models.*;
import views.*;

import java.awt.*;

/**
 * A realization of the AbstractWordViewVisitor interface for connecting two words
 *
 * @author Nathan
 * @version 10/15/2014
 */
public class VerticalConnectionVisitor implements AbstractWordViewVisitor {
/**
 * The MainView and GameState of the game
 * The protected Area for vertical connect
 */
    MainView mainView;
    Area protectedArea;
    GameState gameState;

    /**
     * Constructor
     *
     * @param mainView  The MainView to update once a connection has been made
     * @param gameState The GameState to perform connections on
     */
    public VerticalConnectionVisitor(MainView mainView, GameState gameState) {
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

        // Convert the first word into a poem so we can connect vertically
        Row rowOne = new Row(wordOne);
        Poem poemOne = new Poem(rowOne);
        // Convert the second word into a row so it can be added to the poem
        Row rowTwo = new Row(wordTwo);

        // Connect the two entities
        poemOne.connect(rowTwo);
        // Update the area to reflect the updated words
        protectedArea.removeAbstractWord(wordOne);
        protectedArea.removeAbstractWord(wordTwo);
        protectedArea.addAbstractWord(poemOne);

        int positionDiff = wordViewTwo.getPosition().getX() - wordViewOne.getPosition().getX();

        // Create an appropriate view object
        PoemView resultPoemView = new PoemView(poemOne, wordViewOne.getPosition(), mainView);

        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(wordViewOne);
        mainView.removeProtectedAbstractWordView(wordViewTwo);
        mainView.addProtectedAbstractWordView(resultPoemView);
        // Select the resulting poem
        resultPoemView.setBackground(Color.LIGHT_GRAY);

        resultPoemView.shiftRow(resultPoemView.getRowViews().get(resultPoemView.getRowViews().size() - 1),
                positionDiff);
        // Move the poem to the appropriate position
        // This also updates the positions of all the words in the poem
        resultPoemView.moveTo(wordViewOne.getPosition());
        return true;
    }

    @Override
    public boolean visit(WordView wordViewOne, RowView rowViewTwo) {
        if (connectionCausesOverlap(wordViewOne, rowViewTwo)) {
            return false;
        }

        Word wordOne = wordViewOne.getWord();
        Row rowTwo = rowViewTwo.getWord();

        // Convert the first word into a poem so we can connect vertically
        Row rowOne = new Row(wordOne);
        Poem poemOne = new Poem(rowOne);

        // Connect the two entities
        poemOne.connect(rowTwo);
        // Update the area to reflect the updated words
        protectedArea.removeAbstractWord(wordOne);
        protectedArea.removeAbstractWord(rowTwo);
        protectedArea.addAbstractWord(poemOne);

        int positionDiff = rowViewTwo.getPosition().getX() - wordViewOne.getPosition().getX();

        // Create an appropriate view object
        PoemView resultPoemView = new PoemView(poemOne, wordViewOne.getPosition(), mainView);

        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(wordViewOne);
        mainView.removeProtectedAbstractWordView(rowViewTwo);
        mainView.addProtectedAbstractWordView(resultPoemView);
        // Select the resulting poem
        resultPoemView.setBackground(Color.LIGHT_GRAY);

        resultPoemView.shiftRow(resultPoemView.getRowViews().get(resultPoemView.getRowViews().size() - 1),
                positionDiff);
        // Move the poem to the appropriate position
        // This also updates the positions of all the words in the poem
        resultPoemView.moveTo(wordViewOne.getPosition());
        return true;
    }

    @Override
    public boolean visit(WordView wordViewOne, PoemView poemViewTwo) {
        if (connectionCausesOverlap(wordViewOne, poemViewTwo)) {
            return false;
        }

        Word wordOne = wordViewOne.getWord();
        Poem poemTwo = poemViewTwo.getWord();

        // Convert the first word into a row so we can connect it to the poem
        Row rowOne = new Row(wordOne);

        // Connect the two entities
        poemTwo.connectToTop(rowOne);
        // Update the area to reflect the updated words
        protectedArea.removeAbstractWord(wordOne);

        int positionDiff = wordViewOne.getPosition().getX() - poemViewTwo.getPosition().getX();

        // Create a row view object so it can be used as a component in the poem view
        RowView rowViewOne = new RowView(rowOne, wordViewOne.getPosition(), mainView);
        // Update the poem view object to reflect the entities
        poemViewTwo.addRowToTop(rowViewOne);

        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(wordViewOne);
        // Select the resulting poem
        poemViewTwo.setBackground(Color.LIGHT_GRAY);

        poemViewTwo.shiftRow(rowViewOne, positionDiff);
        // Move the poem to the appropriate position
        // This also updates the positions of all the words in the poem
        poemViewTwo.moveTo(new Position(wordViewOne.getPosition().getX() - positionDiff, wordViewOne.getPosition().getY()));
        return true;
    }

    @Override
    public boolean visit(RowView rowViewOne, WordView wordViewTwo) {
        if (connectionCausesOverlap(rowViewOne, wordViewTwo)) {
            return false;
        }
        Row rowOne = rowViewOne.getWord();
        Word wordTwo = wordViewTwo.getWord();

        // Convert the first row into a poem so we can connect vertically
        Poem poemOne = new Poem(rowOne);
        // Convert the second word into a row so it can be added to the poem
        Row rowTwo = new Row(wordTwo);

        // Connect the two entities
        poemOne.connect(rowTwo);
        // Update the area to reflect the updated words
        protectedArea.removeAbstractWord(rowOne);
        protectedArea.removeAbstractWord(wordTwo);
        protectedArea.addAbstractWord(poemOne);

        int positionDiff = wordViewTwo.getPosition().getX() - rowViewOne.getPosition().getX();

        // Create an appropriate view object
        PoemView resultPoemView = new PoemView(poemOne, rowViewOne.getPosition(), mainView);

        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(rowViewOne);
        mainView.removeProtectedAbstractWordView(wordViewTwo);
        mainView.addProtectedAbstractWordView(resultPoemView);
        // Select the resulting poem
        resultPoemView.setBackground(Color.LIGHT_GRAY);
        resultPoemView.shiftRow(resultPoemView.getRowViews().get(resultPoemView.getRowViews().size() - 1),
                positionDiff);
        // Move the poem to the appropriate position
        // This also updates the positions of all the words in the poem
        resultPoemView.moveTo(rowViewOne.getPosition());
        return true;
    }

    @Override
    public boolean visit(RowView rowViewOne, RowView rowViewTwo) {
        if (connectionCausesOverlap(rowViewOne, rowViewTwo)) {
            return false;
        }
        Row rowOne = rowViewOne.getWord();
        Row rowTwo = rowViewTwo.getWord();

        // Convert the first row into a poem so we can connect vertically
        Poem poemOne = new Poem(rowOne);

        // Connect the two entities
        poemOne.connect(rowTwo);
        // Update the area to reflect the updated words
        protectedArea.removeAbstractWord(rowOne);
        protectedArea.removeAbstractWord(rowTwo);
        protectedArea.addAbstractWord(poemOne);

        int positionDiff = rowViewTwo.getPosition().getX() - rowViewOne.getPosition().getX();

        // Create an appropriate view object
        PoemView resultPoemView = new PoemView(poemOne, rowViewOne.getPosition(), mainView);

        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(rowViewOne);
        mainView.removeProtectedAbstractWordView(rowViewTwo);
        mainView.addProtectedAbstractWordView(resultPoemView);
        // Select the resulting poem
        resultPoemView.setBackground(Color.LIGHT_GRAY);
        resultPoemView.shiftRow(resultPoemView.getRowViews().get(resultPoemView.getRowViews().size() - 1),
                positionDiff);
        // Move the poem to the appropriate position
        // This also updates the positions of all the words in the poem
        resultPoemView.moveTo(rowViewOne.getPosition());
        return true;
    }

    @Override
    public boolean visit(RowView rowViewOne, PoemView poemViewTwo) {
        if (connectionCausesOverlap(rowViewOne, poemViewTwo)) {
            return false;
        }
        Row rowOne = rowViewOne.getWord();
        Poem poemTwo = poemViewTwo.getWord();

        // Connect the two entities
        poemTwo.connectToTop(rowOne);
        // Update the area to reflect the updated words
        protectedArea.removeAbstractWord(rowOne);

        int positionDiff = rowViewOne.getPosition().getX() - poemViewTwo.getPosition().getX();

        // Update the view object to reflect the entities
        poemViewTwo.addRowToTop(rowViewOne);

        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(rowViewOne);
        // Select the resulting poem
        poemViewTwo.setBackground(Color.LIGHT_GRAY);

        poemViewTwo.shiftRow(rowViewOne, positionDiff);
        // Move the poem to the appropriate position
        // This also updates the positions of all the words in the poem
        poemViewTwo.moveTo(new Position(rowViewOne.getPosition().getX() - positionDiff, rowViewOne.getPosition().getY()));
        return true;
    }

    @Override
    public boolean visit(PoemView poemViewOne, WordView wordViewTwo) {
        if (connectionCausesOverlap(poemViewOne, wordViewTwo)) {
            return false;
        }

        Poem poemOne = poemViewOne.getWord();
        Word wordTwo = wordViewTwo.getWord();

        // Convert the second word into a row so we can connect vertically
        Row rowTwo = new Row(wordTwo);

        // Connect the two entities
        poemOne.connect(rowTwo);
        // Update the area to reflect the updated words
        protectedArea.removeAbstractWord(wordTwo);

        int positionDiff = wordViewTwo.getPosition().getX() - poemViewOne.getPosition().getX();

        // Create a row view object so it can be used as a component in the poem view
        RowView rowViewTwo = new RowView(rowTwo, wordViewTwo.getPosition(), mainView);
        // Update the view object to reflect the entities
        poemViewOne.addRow(rowViewTwo);

        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(wordViewTwo);
        // Select the resulting poem
        poemViewOne.setBackground(Color.LIGHT_GRAY);

        poemViewOne.shiftRow(rowViewTwo, positionDiff);
        // Move the poem to the appropriate position
        // This also updates the positions of all the words in the poem
        poemViewOne.moveTo(poemViewOne.getPosition());
        return true;
    }

    @Override
    public boolean visit(PoemView poemViewOne, RowView rowViewTwo) {
        if (connectionCausesOverlap(poemViewOne, rowViewTwo)) {
            return false;
        }
        Poem poemOne = poemViewOne.getWord();
        Row rowTwo = rowViewTwo.getWord();

        // Connect the two entities
        poemOne.connect(rowTwo);
        // Update the area to reflect the updated words
        protectedArea.removeAbstractWord(rowTwo);

        int positionDiff = rowViewTwo.getPosition().getX() - poemViewOne.getPosition().getX();
        // Update the view object to reflect the entities
        poemViewOne.addRow(rowViewTwo);

        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(rowViewTwo);
        // Select the resulting poem
        poemViewOne.setBackground(Color.LIGHT_GRAY);

        poemViewOne.shiftRow(rowViewTwo, positionDiff);
        // Move the poem to the appropriate position
        // This also updates the positions of all the words in the poem
        poemViewOne.moveTo(poemViewOne.getPosition());
        return true;
    }

    @Override
    public boolean visit(PoemView poemViewOne, PoemView poemViewTwo) {
        if (connectionCausesOverlap(poemViewOne, poemViewTwo)) {
            return false;
        }
        Poem poemOne = poemViewOne.getWord();
        Poem poemTwo = poemViewTwo.getWord();

        // Connect the two entities
        poemOne.connect(poemTwo);
        // Update the area to reflect the updated words
        protectedArea.removeAbstractWord(poemTwo);

        int positionDiff = poemViewTwo.getPosition().getX() - poemViewOne.getPosition().getX();
        // Update the view object to reflect the entities
        poemViewOne.addPoem(poemViewTwo);

        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(poemViewTwo);
        // Select the resulting poem
        poemViewOne.setBackground(Color.LIGHT_GRAY);

        for (RowView rowView : poemViewTwo.getRowViews()) {
            poemViewOne.shiftRow(rowView, positionDiff);
        }
        // Move the poem to the appropriate position
        // This also updates the positions of all the words in the poem
        poemViewOne.moveTo(poemViewOne.getPosition());
        return true;
    }

    /**
     * Attempts to move the second word to right under the first word (where it will be after connection)
     * and returns whether the connection would cause an overlap
     *
     * @param one The word on top of the connection
     * @param two The word below the connection
     * @return Returns whether the connection would cause an overlap
     */
    protected boolean connectionCausesOverlap(AbstractWordView one, AbstractWordView two) {
        // Check if the connection will cause an overlap
        Position targetPosition = new Position(two.getPosition().getX(), one.getPosition().getY() + one.getHeight() + 1);
        MoveWordController moveWordController = new MoveWordController(mainView, gameState);
        moveWordController.moveWord(two, two.getPosition(), targetPosition);
        return !two.getPosition().equals(targetPosition);
    }
}
