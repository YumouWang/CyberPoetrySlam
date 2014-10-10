package controllers;

import models.*;
import views.*;

/**
 * A controller for handling connecting words
 *
 * Created by Nathan on 10/4/2014.
 */
public class ConnectionController {

    MainView mainView;
    GameState gameState;

    /**
     * Constructor
     * @param display The MainView to update once a connection has been made
     * @param gameState The GameState to perform connections on
     */
    public ConnectionController(MainView display, GameState gameState) {
        this.mainView = display;
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
            mainView.removeAbstractWordView(wordOne);
            mainView.removeAbstractWordView(wordTwo);
            mainView.addAbstractWordView(newWord);
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

        Row resultRow = gameState.getProtectedArea().connectHorizontal(wordOne, wordTwo);
        RowView resultRowView = null;

        if(resultRow != null) {
            resultRowView = (RowView)mainView.getAbstractWordById(resultRow.getId());
            // If a view for the row does not already exist, create it
            if(resultRowView == null) {
                resultRowView = new RowView(resultRow, wordViewOne.getPosition(), mainView);
            } else {
                // Otherwise, add the appropriate wordView to the row
                //Cases: Word- Row, Row-word, Row-row
                // Case: wordViewOne is the view for the resulting row
                if(wordOne.getId() == resultRow.getId()) {
                    // The first element was a row
                    if(wordTwo instanceof Word) {
                        // The first element was a row and the second element was a word
                        WordView word = (WordView)wordViewTwo;
                        resultRowView.addWord(word);
                    } else if(wordTwo instanceof Row) {
                        RowView secondRow = (RowView) wordViewTwo;
                        for(WordView wordView : secondRow.getWordViews()) {
                            resultRowView.addWord(wordView);
                        }
                    } else {
                        System.out.println("In connectHorizontal the second word was not a row or word!");
                        System.exit(1);
                    }
                    // Move all the words to the appropriate location
                    resultRowView.moveTo(resultRowView.getPosition());
                } else if(wordTwo.getId() == resultRow.getId()) {
                    // The first element was a word and the second element was a row
                    WordView word = (WordView)wordViewOne;
                    resultRowView.addWordToFront(word);
                    // Move all the words to the appropriate location
                    resultRowView.moveTo(word.getPosition());
                } else {
                    // Should never hit this
                    System.out.println("Something went wrong in connectHorizontal");
                    System.exit(1);
                }
            }
        }

        return resultRowView;
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

        Poem result = gameState.getProtectedArea().connectVertical(wordOne, wordTwo);
        AbstractWordView resultView = null;

        if(result != null)
            resultView = new WordView(result, wordViewOne.getPosition());

        return resultView;
    }
}
