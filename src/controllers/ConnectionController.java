package controllers;

import models.*;
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

    public ConnectionController(MainView display, GameState gameState) {
        this.display = display;
        this.gameState = gameState;
    }

    public void connect(AbstractWordView wordOne, AbstractWordView wordTwo) throws Exception {
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

    private AbstractWordView connectHorizontal(AbstractWordView wordViewOne, AbstractWordView wordViewTwo) throws Exception {
        AbstractWord wordOne = wordViewOne.getWord();
        AbstractWord wordTwo = wordViewTwo.getWord();

        AbstractWord result = gameState.getProtectedArea().connectHorizontal(wordOne, wordTwo);
        AbstractWordView resultView = null;

        if(result != null)
            resultView = new AbstractWordView(result, wordViewOne.getPosition());

        return resultView;
    }

    private AbstractWordView connectVertical(AbstractWordView wordViewOne, AbstractWordView wordViewTwo) throws Exception {
        AbstractWord wordOne = wordViewOne.getWord();
        AbstractWord wordTwo = wordViewTwo.getWord();

        AbstractWord result = gameState.getProtectedArea().connectVertical(wordOne, wordTwo);
        AbstractWordView resultView = null;

        if(result != null)
            resultView = new AbstractWordView(result, wordViewOne.getPosition());

        return resultView;
    }
}
