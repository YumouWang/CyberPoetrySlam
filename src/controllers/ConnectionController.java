package controllers;

import models.AbstractWord;
import models.GameState;
import views.AbstractWordView;
import views.AdjacencyType;
import views.MainView;

/**
 * A controller for handling connecting words
 *
 * Created by Nathan on 10/4/2014.
 */
public class ConnectionController {

    MainView display;
    GameState gameState;

    /**
     * Constructor
     * @param display The MainView to update once a connection has been made
     * @param gameState The GameState to perform connections on
     */
    public ConnectionController(MainView display, GameState gameState) {
        this.display = display;
        this.gameState = gameState;
    }

    /**
     * Connects two words. Handles deciding how they should be connected based on their positions.
     * @param wordOne The first word to connect
     * @param wordTwo The second word to connect
     */
    public void connect(AbstractWordView wordOne, AbstractWordView wordTwo) {
        AdjacencyType adjacencyType = wordOne.isAdjacentTo(wordTwo);
        AbstractWordView newWord = null;
        switch(adjacencyType) {
            case ABOVE:
                newWord = connectVertical(wordOne, wordTwo);
                break;
            case BELOW:
                newWord = connectVertical(wordTwo, wordOne);
                break;
            case LEFT:
                newWord = connectHorizontal(wordOne, wordTwo);
                break;
            case RIGHT:
                newWord = connectHorizontal(wordTwo, wordOne);
                break;
        }
        if(newWord == null) {
            System.out.println("Tried to connect two words, something went wrong. No changes made.");
        } else {
            display.removeAbstractWordView(wordOne);
            display.removeAbstractWordView(wordTwo);
            display.addAbstractWordView(newWord);
        }
    }

    /**
     * Private helper for ConnectionController. Connects wordViewOne to wordViewTwo horizontally
     * @param wordViewOne The word to connect to the left
     * @param wordViewTwo The word to connect to the right
     * @return Returns the AbstractWordView of the connected word
     */
    private AbstractWordView connectHorizontal(AbstractWordView wordViewOne, AbstractWordView wordViewTwo) {
        AbstractWord wordOne = wordViewOne.getWord();
        AbstractWord wordTwo = wordViewTwo.getWord();

        AbstractWord result = gameState.getProtectedArea().connectHorizontal(wordOne, wordTwo);
        AbstractWordView resultView = null;

        if(result != null)
            resultView = new AbstractWordView(result, wordViewOne.getPosition());

        return resultView;
    }

    /**
     * Private helper for ConnectionController. Connects wordViewOne to wordViewTwo vertically
     * @param wordViewOne The word to connect to the top
     * @param wordViewTwo The word to connect to the bottom
     * @return Returns the AbstractWordView of the connected word
     */
    private AbstractWordView connectVertical(AbstractWordView wordViewOne, AbstractWordView wordViewTwo) {
        AbstractWord wordOne = wordViewOne.getWord();
        AbstractWord wordTwo = wordViewTwo.getWord();

        AbstractWord result = gameState.getProtectedArea().connectVertical(wordOne, wordTwo);
        AbstractWordView resultView = null;

        if(result != null)
            resultView = new AbstractWordView(result, wordViewOne.getPosition());

        return resultView;
    }
}
