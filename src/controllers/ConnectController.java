package controllers;

import models.GameState;
import views.AbstractWordView;
import views.AdjacencyType;
import views.MainView;

/**
 * A controller for handling connecting words
 *
 * @author Nathan
 * @version 10/4/2014
 */
public class ConnectController {
/**
 * The MainView of the game
 */
    MainView mainView;
    /**
     * The GameState of the game
     */
    GameState gameState;

    /**
     * Constructor
     *
     * @param mainView  The MainView to update once a connection has been made
     * @param gameState The GameState to perform connections on
     */
    public ConnectController(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.gameState = gameState;
    }

    /**
     * Connects two words. Handles deciding how they should be connected based on their positions.
     *
     * @param wordOne The first word to connect
     * @param wordTwo The second word to connect
     * @return Returns whether the connect was successful
     */
    public boolean connect(AbstractWordView wordOne, AbstractWordView wordTwo) {
        AdjacencyType adjacencyType = wordOne.isAdjacentTo(wordTwo);
        AdjacencyType adjacencyTypeTwo = wordTwo.isAdjacentTo(wordOne);
        boolean successful = false;
        switch (adjacencyType) {
            case ABOVE:
                if (adjacencyTypeTwo == AdjacencyType.BELOW) {
                    successful = connectVertical(wordOne, wordTwo);
                }
                break;
            case BELOW:
                if (adjacencyTypeTwo == AdjacencyType.ABOVE) {
                    successful = connectVertical(wordTwo, wordOne);
                }
                break;
            case LEFT:
                successful = connectHorizontal(wordOne, wordTwo);
                break;
            case RIGHT:
                successful = connectHorizontal(wordTwo, wordOne);
                break;
            default:
                break;
        }
        return successful;
    }

    /**
     * Private helper for ConnectController. Connects wordViewOne to wordViewTwo horizontally
     *
     * @param wordViewOne The word to connect to the left
     * @param wordViewTwo The word to connect to the right
     * @return Returns whether the connect was successful
     */
    boolean connectHorizontal(AbstractWordView wordViewOne, AbstractWordView wordViewTwo) {
        HorizontalConnectionVisitor connector = new HorizontalConnectionVisitor(mainView, gameState);
        return wordViewOne.acceptVisitor(connector, wordViewTwo);
    }

    /**
     * Private helper for ConnectController. Connects wordViewOne to wordViewTwo vertically
     *
     * @param wordViewOne The word to connect to the top
     * @param wordViewTwo The word to connect to the bottom
     * @return Returns whether the connect was successful
     */
    boolean connectVertical(AbstractWordView wordViewOne, AbstractWordView wordViewTwo) {
        VerticalConnectionVisitor connector = new VerticalConnectionVisitor(mainView, gameState);
        return wordViewOne.acceptVisitor(connector, wordViewTwo);
    }
}
