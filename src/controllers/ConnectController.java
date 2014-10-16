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
        AbstractWordView newWord = null;
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

//    /**
//     * Private helper for ConnectController. Connects wordViewOne to wordViewTwo vertically
//     * @param wordViewOne The word to connect to the top
//     * @param wordViewTwo The word to connect to the bottom
//     * @return Returns the AbstractWordView of the connected word
//     */
//    private PoemView connectVertical(AbstractWordView wordViewOne, AbstractWordView wordViewTwo) {
//        AbstractWord wordOne = wordViewOne.getWord();
//        AbstractWord wordTwo = wordViewTwo.getWord();
//
//        Poem resultPoem = gameState.getProtectedArea().connectVertical(wordOne, wordTwo);
//        PoemView resultPoemView = null;
//
//        if(resultPoem != null) {
//            resultPoemView = (PoemView) mainView.getAbstractWordById(resultPoem.getId());
//            // If a view for the poem does not already exist, create it
//            // This handles cases: Row-Word, Row-Row, Word-Word, Word-Row
//            if(resultPoemView == null) {
//                resultPoemView = new PoemView(resultPoem, wordViewOne.getPosition(), mainView);
//            } else {
//                // Otherwise, add the appropriate rowView to the poem
//                // Cases: Poem-Word, Poem-Row, Poem-Poem
//                //        Row-Poem, Word-Poem
//                // Case: wordViewOne is a poem, so the resulting poem is the same view
//                if(wordOne.getId() == resultPoem.getId()) {
//                    // The first element was a poem
//                    if(wordTwo instanceof Word) {
//                        // The first element was a poem and the second element was a word
//                        long wordId = wordTwo.getId();
//                        Row secondRow = null;
//                        for(Row row : resultPoem.getRows()) {
//                            for(Word word : row.getWords()) {
//                                if(word.getId() == wordId) {
//                                    secondRow = row;
//                                }
//                            }
//                        }
//                        resultPoemView.addRow(new RowView(secondRow, wordViewTwo.getPosition(), mainView));
//                    } else if(wordTwo instanceof Row) {
//                        // The first element was a poem and the second element was a row
//                        RowView secondRow = (RowView) wordViewTwo;
//                        resultPoemView.addRow(secondRow);
//                    } else {
//                        // The first element was a poem and the second element was a poem
//                        PoemView secondPoem = (PoemView) wordViewTwo;
//                        for(RowView rowView : secondPoem.getRowViews()) {
//                            resultPoemView.addRow(rowView);
//                        }
//                    }
//                    // Move all the words to the appropriate location
//                    resultPoemView.moveTo(resultPoemView.getPosition());
//                } else {
//                    // The second element was a poem
//                    if(wordOne instanceof Word) {
//                        // The first element was a word and the second element was a poem
//                        long wordId = wordOne.getId();
//                        Row firstRow = null;
//                        for(Row row : resultPoem.getRows()) {
//                            for(Word word : row.getWords()) {
//                                if(word.getId() == wordId) {
//                                    firstRow = row;
//                                }
//                            }
//                        }
//                        resultPoemView.addRowToTop(new RowView(firstRow, wordViewOne.getPosition(), mainView));
//                    } else {
//                        // The first element was a row and the second element was a poem
//                        RowView firstRow = (RowView) wordViewOne;
//                        resultPoemView.addRowToTop(firstRow);
//                    }
//                    // Move all the words to the appropriate location
//                    resultPoemView.moveTo(wordViewOne.getPosition());
//                }
//            }
//        }
//
//        return resultPoemView;
//    }

}
