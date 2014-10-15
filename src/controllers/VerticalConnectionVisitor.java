package controllers;

import models.GameState;
import views.MainView;
import views.PoemView;
import views.RowView;
import views.WordView;

/**
 * A realization of the AbstractWordViewVisitor interface for handling connections between two words
 *
 * Created by Nathan on 10/15/2014.
 */
public class VerticalConnectionVisitor implements AbstractWordViewVisitor {

    MainView mainView;
    GameState gameState;

    /**
     * Constructor
     * @param mainView The MainView to update once a connection has been made
     * @param gameState The GameState to perform connections on
     */
    public VerticalConnectionVisitor(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.gameState = gameState;
    }

    @Override
    public void visit(WordView wordViewOne, WordView wordViewTwo) {

    }

    @Override
    public void visit(WordView wordViewOne, RowView rowViewTwo) {

    }

    @Override
    public void visit(WordView wordViewOne, PoemView poemViewTwo) {

    }

    @Override
    public void visit(RowView rowViewOne, WordView wordViewTwo) {

    }

    @Override
    public void visit(RowView rowViewOne, RowView rowViewTwo) {

    }

    @Override
    public void visit(RowView rowViewOne, PoemView poemViewTwo) {

    }

    @Override
    public void visit(PoemView poemViewOne, WordView wordViewTwo) {

    }

    @Override
    public void visit(PoemView poemViewOne, RowView rowViewTwo) {

    }

    @Override
    public void visit(PoemView poemViewOne, PoemView poemViewTwo) {

    }
}
