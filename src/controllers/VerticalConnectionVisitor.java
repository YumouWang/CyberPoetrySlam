package controllers;

import models.*;
import views.MainView;
import views.PoemView;
import views.RowView;
import views.WordView;

import java.awt.*;

/**
 * A realization of the AbstractWordViewVisitor interface for connecting two words
 *
 * Created by Nathan on 10/15/2014.
 */
public class VerticalConnectionVisitor implements AbstractWordViewVisitor {

    MainView mainView;
    Area protectedArea;

    /**
     * Constructor
     * @param mainView The MainView to update once a connection has been made
     * @param gameState The GameState to perform connections on
     */
    public VerticalConnectionVisitor(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.protectedArea = gameState.getProtectedArea();
    }

    @Override
    public void visit(WordView wordViewOne, WordView wordViewTwo) {
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

        // Create an appropriate view object
        PoemView resultPoemView = new PoemView(poemOne, wordViewOne.getPosition(), mainView);

        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(wordViewOne);
        mainView.removeProtectedAbstractWordView(wordViewTwo);
        mainView.addProtectedAbstractWordView(resultPoemView);
        // Select the resulting poem
        resultPoemView.setBackground(Color.LIGHT_GRAY);
        // Move the poem to the appropriate position
        // This also updates the positions of all the words in the poem
//        resultPoemView.moveTo(wordViewOne.getPosition());
    }

    @Override
    public void visit(WordView wordViewOne, RowView rowViewTwo) {
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

        // Create an appropriate view object
        PoemView resultPoemView = new PoemView(poemOne, wordViewOne.getPosition(), mainView);

        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(wordViewOne);
        mainView.removeProtectedAbstractWordView(rowViewTwo);
        mainView.addProtectedAbstractWordView(resultPoemView);
        // Select the resulting poem
        resultPoemView.setBackground(Color.LIGHT_GRAY);
        // Move the poem to the appropriate position
        // This also updates the positions of all the words in the poem
//        resultPoemView.moveTo(wordViewOne.getPosition());
    }

    @Override
    public void visit(WordView wordViewOne, PoemView poemViewTwo) {
        Word wordOne = wordViewOne.getWord();
        Poem poemTwo = poemViewTwo.getWord();

        // Convert the first word into a row so we can connect it to the poem
        Row rowOne = new Row(wordOne);

        // Connect the two entities
        poemTwo.connectToTop(rowOne);
        // Update the area to reflect the updated words
        protectedArea.removeAbstractWord(wordOne);

        // Create a row view object so it can be used as a component in the poem view
        RowView rowViewOne = new RowView(rowOne, wordViewOne.getPosition(), mainView);
        // Update the poem view object to reflect the entities
        poemViewTwo.addRowToTop(rowViewOne);

        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(wordViewOne);
        // Select the resulting poem
        poemViewTwo.setBackground(Color.LIGHT_GRAY);
        // Move the poem to the appropriate position
        // This also updates the positions of all the words in the poem
//        poemViewTwo.moveTo(wordViewOne.getPosition());
    }

    @Override
    public void visit(RowView rowViewOne, WordView wordViewTwo) {
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

        // Create an appropriate view object
        PoemView resultPoemView = new PoemView(poemOne, rowViewOne.getPosition(), mainView);

        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(rowViewOne);
        mainView.removeProtectedAbstractWordView(wordViewTwo);
        mainView.addProtectedAbstractWordView(resultPoemView);
        // Select the resulting poem
        resultPoemView.setBackground(Color.LIGHT_GRAY);
        // Move the poem to the appropriate position
        // This also updates the positions of all the words in the poem
//        resultPoemView.moveTo(rowViewOne.getPosition());
    }

    @Override
    public void visit(RowView rowViewOne, RowView rowViewTwo) {
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

        // Create an appropriate view object
        PoemView resultPoemView = new PoemView(poemOne, rowViewOne.getPosition(), mainView);

        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(rowViewOne);
        mainView.removeProtectedAbstractWordView(rowViewTwo);
        mainView.addProtectedAbstractWordView(resultPoemView);
        // Select the resulting poem
        resultPoemView.setBackground(Color.LIGHT_GRAY);
        // Move the poem to the appropriate position
        // This also updates the positions of all the words in the poem
//        resultPoemView.moveTo(rowViewOne.getPosition());
    }

    @Override
    public void visit(RowView rowViewOne, PoemView poemViewTwo) {
        Row rowOne = rowViewOne.getWord();
        Poem poemTwo = poemViewTwo.getWord();

        // Connect the two entities
        poemTwo.connectToTop(rowOne);
        // Update the area to reflect the updated words
        protectedArea.removeAbstractWord(rowOne);

        // Update the view object to reflect the entities
        poemViewTwo.addRowToTop(rowViewOne);

        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(rowViewOne);
        // Select the resulting poem
        poemViewTwo.setBackground(Color.LIGHT_GRAY);
        // Move the poem to the appropriate position
        // This also updates the positions of all the words in the poem
//        poemViewTwo.moveTo(rowViewOne.getPosition());
    }

    @Override
    public void visit(PoemView poemViewOne, WordView wordViewTwo) {
        Poem poemOne = poemViewOne.getWord();
        Word wordTwo = wordViewTwo.getWord();

        // Convert the second word into a row so we can connect vertically
        Row rowTwo = new Row(wordTwo);

        // Connect the two entities
        poemOne.connect(rowTwo);
        // Update the area to reflect the updated words
        protectedArea.removeAbstractWord(wordTwo);

        // Create a row view object so it can be used as a component in the poem view
        RowView rowViewTwo = new RowView(rowTwo, wordViewTwo.getPosition(), mainView);
        // Update the view object to reflect the entities
        poemViewOne.addRow(rowViewTwo);

        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(wordViewTwo);
        // Select the resulting poem
        poemViewOne.setBackground(Color.LIGHT_GRAY);
        // Move the poem to the appropriate position
        // This also updates the positions of all the words in the poem
//        poemViewOne.moveTo(poemViewOne.getPosition());
    }

    @Override
    public void visit(PoemView poemViewOne, RowView rowViewTwo) {
        Poem poemOne = poemViewOne.getWord();
        Row rowTwo = rowViewTwo.getWord();

        // Connect the two entities
        poemOne.connect(rowTwo);
        // Update the area to reflect the updated words
        protectedArea.removeAbstractWord(rowTwo);

        // Update the view object to reflect the entities
        poemViewOne.addRow(rowViewTwo);

        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(rowViewTwo);
        // Select the resulting poem
        poemViewOne.setBackground(Color.LIGHT_GRAY);
        // Move the poem to the appropriate position
        // This also updates the positions of all the words in the poem
//        poemViewOne.moveTo(poemViewOne.getPosition());
    }

    @Override
    public void visit(PoemView poemViewOne, PoemView poemViewTwo) {
        Poem poemOne = poemViewOne.getWord();
        Poem poemTwo = poemViewTwo.getWord();

        // Connect the two entities
        poemOne.connect(poemTwo);
        // Update the area to reflect the updated words
        protectedArea.removeAbstractWord(poemTwo);

        // Update the view object to reflect the entities
        poemViewOne.addPoem(poemViewTwo);

        // Update the main view object to reflect the change
        mainView.removeProtectedAbstractWordView(poemViewTwo);
        // Select the resulting poem
        poemViewOne.setBackground(Color.LIGHT_GRAY);
        // Move the poem to the appropriate position
        // This also updates the positions of all the words in the poem
//        poemViewOne.moveTo(poemViewOne.getPosition());
    }
}
