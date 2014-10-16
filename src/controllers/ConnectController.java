package controllers;

import models.GameState;
import views.AbstractWordView;
import views.AdjacencyType;
import views.MainView;

/**
 * A controller for handling connecting words
 *
 * Created by Nathan on 10/4/2014.
 */
public class ConnectController {

    MainView mainView;
    GameState gameState;

    /**
     * Constructor
     * @param mainView The MainView to update once a connection has been made
     * @param gameState The GameState to perform connections on
     */
    public ConnectController(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.gameState = gameState;
    }

    /**
     * Connects two words. Handles deciding how they should be connected based on their positions.
     * @param wordOne The first word to connect
     * @param wordTwo The second word to connect
     */
    public void connect(AbstractWordView wordOne, AbstractWordView wordTwo) {
        AdjacencyType adjacencyType = wordOne.isAdjacentTo(wordTwo);
        switch(adjacencyType) {
            case ABOVE:
                connectVertical(wordOne, wordTwo);
                break;
            case BELOW:
                connectVertical(wordTwo, wordOne);
                break;
            case LEFT:
                connectHorizontal(wordOne, wordTwo);
                break;
            case RIGHT:
                connectHorizontal(wordTwo, wordOne);
                break;
        }
    }

    /**
     * Private helper for ConnectController. Connects wordViewOne to wordViewTwo horizontally
     * @param wordViewOne The word to connect to the left
     * @param wordViewTwo The word to connect to the right
     */
    private void connectHorizontal(AbstractWordView wordViewOne, AbstractWordView wordViewTwo) {
        HorizontalConnectionVisitor connector = new HorizontalConnectionVisitor(mainView, gameState);
        wordViewOne.acceptVisitor(connector, wordViewTwo);
    }

    /**
     * Private helper for ConnectController. Connects wordViewOne to wordViewTwo vertically
     * @param wordViewOne The word to connect to the top
     * @param wordViewTwo The word to connect to the bottom
     */
    private void connectVertical(AbstractWordView wordViewOne, AbstractWordView wordViewTwo) {
        VerticalConnectionVisitor connector = new VerticalConnectionVisitor(mainView, gameState);
        wordViewOne.acceptVisitor(connector, wordViewTwo);
    }
}
