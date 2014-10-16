package controllers;

import models.Area;
import models.GameState;
import models.Row;
import models.Word;
import views.MainView;
import views.PoemView;
import views.RowView;
import views.WordView;

import java.awt.*;

/**
 * A realization of the AbstractWordViewVisitor interface for handling horizontal connections between two words
 *
 * Created by Nathan on 10/15/2014.
 */
public class HorizontalConnectionVisitor implements AbstractWordViewVisitor {

    MainView mainView;
    Area protectedArea;

    /**
     * Constructor
     * @param mainView The MainView to update once a connection has been made
     * @param gameState The GameState to perform connections on
     */
    public HorizontalConnectionVisitor(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.protectedArea = gameState.getProtectedArea();
    }

    @Override
    public void visit(WordView wordViewOne, WordView wordViewTwo) {
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
        mainView.removeAbstractWordView(wordViewOne);
        mainView.removeAbstractWordView(wordViewTwo);
        mainView.addAbstractWordView(resultRowView);
        // Select the resulting row
        resultRowView.setBackground(Color.LIGHT_GRAY);
    }

    @Override
    public void visit(WordView wordViewOne, RowView rowViewTwo) {
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
        mainView.removeAbstractWordView(wordViewOne);
        // Select the resulting row
        rowViewTwo.setBackground(Color.LIGHT_GRAY);
        // Move the row to the appropriate position
        // This also updates the positions of all the words in the row
        rowViewTwo.moveTo(wordViewOne.getPosition());
    }

    @Override
    public void visit(WordView wordViewOne, PoemView poemViewTwo) {
        // Cannot horizontally connect a word and a poem, so do nothing
    }

    @Override
    public void visit(RowView rowViewOne, WordView wordViewTwo) {
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
        mainView.removeAbstractWordView(wordViewTwo);
        // Select the resulting row
        rowViewOne.setBackground(Color.LIGHT_GRAY);
        // Move the row to the appropriate position
        // This also updates the positions of all the words in the row
        rowViewOne.moveTo(rowViewOne.getPosition());
    }

    @Override
    public void visit(RowView rowViewOne, RowView rowViewTwo) {
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
        mainView.removeAbstractWordView(rowViewTwo);
        // Select the resulting row
        rowViewOne.setBackground(Color.LIGHT_GRAY);
        // Move the row to the appropriate position
        // This also updates the positions of all the words in the row
        rowViewOne.moveTo(rowViewOne.getPosition());
    }

    @Override
    public void visit(RowView rowViewOne, PoemView poemViewTwo) {
        // Cannot horizontally connect a row and a poem, so do nothing
    }

    @Override
    public void visit(PoemView poemViewOne, WordView wordViewTwo) {
        // Cannot horizontally connect a poem and a word, so do nothing
    }

    @Override
    public void visit(PoemView poemViewOne, RowView rowViewTwo) {
        // Cannot horizontally connect a poem and a row, so do nothing
    }

    @Override
    public void visit(PoemView poemViewOne, PoemView poemViewTwo) {
        // Cannot horizontally connect two poems, so do nothing
    }
}
