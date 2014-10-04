package controllers;

import models.AbstractWord;
import models.Row;
import models.Word;
import views.AbstractWordView;
import views.AdjacencyType;
import views.MainView;

/**
 * A controller for handling connecting words
 *
 * Created by Nathan on 10/4/2014.
 */
public class ConnectionController {

    public void connect(AbstractWordView wordOne, AbstractWordView wordTwo) throws Exception {
        MainView display = MainView.getInstance();

        AdjacencyType adjacencyType = wordOne.isAdjacentTo(wordTwo);
        AbstractWordView newWord = null;
        switch(adjacencyType) {
            case ABOVE:
                newWord = connectVertical(wordOne, wordTwo);
                break;
            case BELOW:
                newWord = connectVertical(wordOne, wordTwo);
                break;
            case LEFT:
                newWord = connectHorizontal(wordOne, wordTwo);
                break;
            case RIGHT:
                newWord = connectHorizontal(wordTwo, wordOne);
                break;
        }
        if(newWord == null) {
            throw new Exception("Tried to connect two words, got a null result");
        }
        display.removeAbstractWordView(wordOne);
        display.removeAbstractWordView(wordTwo);
        display.addAbstractWordView(newWord);
    }

    private AbstractWordView connectHorizontal(AbstractWordView wordViewOne, AbstractWordView wordViewTwo) {
        AbstractWord wordOne = wordViewOne.getWord();
        AbstractWord wordTwo = wordViewTwo.getWord();

        AbstractWord result = null;

        // Identify the types of the words so we can call the appropriate connect method
        // Method currently does not handle connecting poems
        if(wordOne instanceof Word) {
            Word leftWord = (Word) wordOne;
            if(wordTwo instanceof Word) {
                result = leftWord.connect((Word) wordTwo);
            } else if(wordTwo instanceof Row) {
                result = leftWord.connect((Row) wordTwo);
            }
        } else if(wordOne instanceof Row) {
            Row leftRow = (Row) wordOne;
            if(wordTwo instanceof Word) {
                leftRow.connect((Word) wordTwo);
            } else if(wordTwo instanceof Row) {
                leftRow.connect((Row) wordTwo);
            }
            result = leftRow;
        }

        AbstractWordView resultView = null;
        if(result != null) {
            resultView = new AbstractWordView(result, wordViewOne.getPosition());
        }
        return resultView;
    }

    private AbstractWordView connectVertical(AbstractWordView wordOne, AbstractWordView wordTwo) {
        return null;
    }
}
