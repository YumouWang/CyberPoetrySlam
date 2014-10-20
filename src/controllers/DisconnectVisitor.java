package controllers;

import models.*;
import views.*;

import java.util.List;

/**
 * A realization of the AbstractWordViewVisitor interface for disconnecting two words
 *
 * Created by Nathan on 10/15/2014.
 */
public class DisconnectVisitor implements AbstractWordViewVisitor {

    MainView mainView;
    Area protectedArea;

    /**
     * Constructor
     * @param mainView The MainView to update once a connection has been made
     * @param gameState The GameState to perform connections on
     */
    public DisconnectVisitor(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.protectedArea = gameState.getProtectedArea();
    }

    @Override
    public boolean visit(WordView wordViewToDisconnectFrom, WordView wordView) {
        // Cannot disconnect a word from another word, so do nothing
        return false;
    }

    @Override
    public boolean visit(WordView wordViewToDisconnectFrom, RowView rowView) {
        // Cannot disconnect a row from a word, so do nothing
        return false;
    }

    @Override
    public boolean visit(WordView wordViewToDisconnectFrom, PoemView poemView) {
        // Cannot disconnect a poem from a word, so do nothing
        return false;
    }

    @Override
    public boolean visit(RowView rowViewToDisconnectFrom, WordView wordView) {
        Row rowToDisconnectFrom = rowViewToDisconnectFrom.getWord();
        Word word = wordView.getWord();
        boolean successful = true;
        Row splitResult = null;

        // Disconnect the two entities
        if(rowToDisconnectFrom.disconnect(word)) {
            // Update the area to reflect the updated words
            protectedArea.addAbstractWord(word);
            // If the row is now just one word, convert it to a word
            reduceRowToWordIfPossible(rowToDisconnectFrom);
        } else {
            // Handles removing a word from the middle of a row
            // This was not in the user stories, but it's the same logic as
            // removing a row from the middle of a poem, so it was easy to add

            // Split the row so the word we're removing is at the end of the first row
            splitResult = rowToDisconnectFrom.splitRowAt(word);
            // Update the area to reflect the updated words
            protectedArea.addAbstractWord(splitResult);
            // If the row is just one word, convert it to a word
            reduceRowToWordIfPossible(splitResult);

            // Now the row is at the end of rowToDisconnectFrom so we can disconnect it
            if(rowToDisconnectFrom.disconnect(word)) {
                // Update the area to reflect the updated words
                protectedArea.addAbstractWord(word);
                // If the poem is just one row or just one word, convert it to a row or word
                reduceRowToWordIfPossible(rowToDisconnectFrom);
            } else {
                System.out.println("Error disconnecting " + word.getValue() + " from " + rowToDisconnectFrom.getValue());
                successful = false;
            }
        }

        // Disconnect the wordView so the view reflects the entity change
        if(rowViewToDisconnectFrom.removeWordView(wordView)) {
            // Update the main view object to reflect the change
            mainView.addProtectedAbstractWordView(wordView);
            // If the row is now just one word, convert it to a word
            reduceRowViewToWordViewIfPossible(rowViewToDisconnectFrom);
        } else {
            // Remove the old wordViews from the rowView
            List<WordView> wordViewList = rowViewToDisconnectFrom.getWordViews();
            // Remove wordViews from the end of the list until we get to the wordView we just split on
            WordView lastWordViewInPoem = wordViewList.get(wordViewList.size()-1);
            // Keep track of the first one in the new row so we know its position
            WordView firstWordViewInSplitResult = null;
            while(!wordView.equals(lastWordViewInPoem)) {
                // Remove the wordView from the rowView and add it to the pool of words in the mainView
                rowViewToDisconnectFrom.removeWordView(lastWordViewInPoem);
                mainView.addProtectedAbstractWordView(lastWordViewInPoem);
                // Get the last one in the list
                firstWordViewInSplitResult = lastWordViewInPoem;
                lastWordViewInPoem = wordViewList.get(wordViewList.size()-1);
            }
            // The rowView now only has the WordViews it should
            // And firstWordViewInSplitResult is the first WordView in the split row view

            // Create a new PoemView for the second poem
            // Use the position of the first row in the poem when creating it
            // All rows should be available in the mainView so it should be able to construct using those rowViews
            RowView splitRowView = new RowView(splitResult, firstWordViewInSplitResult.getPosition(), mainView);
            mainView.addProtectedAbstractWordView(splitRowView);
            // if the splitPoemView is just one rowView, reduce it
            reduceRowViewToWordViewIfPossible(splitRowView);

            // Now the wordView is at the end of rowViewToDisconnectFrom so we can
            // disconnect the wordView so the view reflects the entity change
            if(rowViewToDisconnectFrom.removeWordView(wordView)) {
                // Update the main view object to reflect the change
                mainView.addProtectedAbstractWordView(wordView);
                // If the rowView is now just one wordView, convert it to a wordView
                reduceRowViewToWordViewIfPossible(rowViewToDisconnectFrom);
            } else {
                System.out.println("Error disconnecting view for " + word.getValue() + " from view for " + rowToDisconnectFrom.getValue());
                successful = false;
            }
        }
        // Move the row to the appropriate position
        // This also updates the positions of all the words in the row
        rowViewToDisconnectFrom.moveTo(rowViewToDisconnectFrom.getWordViews().get(0).getPosition());
        return successful;
    }

    @Override
    public boolean visit(RowView rowViewToDisconnectFrom, RowView rowView) {
        // Cannot disconnect a row from a row, so do nothing
        return false;
    }

    @Override
    public boolean visit(RowView rowViewToDisconnectFrom, PoemView poemView) {
        // Cannot disconnect a poem from a row, so do nothing
        return false;
    }

    @Override
    public boolean visit(PoemView poemViewToDisconnectFrom, WordView wordView) {
        System.out.println("Disconnect " + wordView.getWord().getValue() + " from " + poemViewToDisconnectFrom.getWord().getValue());
//        Poem poemToDisconnectFrom = poemViewToDisconnectFrom.getWord();
//        Word word = wordView.getWord();
//        boolean successful = true;
//        AbstractWord splitResult = null;
//
//        // Disconnect the two entities
//        if(poemToDisconnectFrom.disconnectEdgeWord(word)) {
//
//            // Update the area to reflect the updated words
//            protectedArea.addAbstractWord(word);
//
//            // Check if we removed the last word in a row
//            splitResult = poemToDisconnectFrom.revalidate();
//            // If we did, we might have also split the poem. If so,
//            // Add the result to the protectedArea
//            if(splitResult != null) {
//                protectedArea.addAbstractWord(splitResult);
//            }
//
//            // If the poem is now just one row, convert it to a row
//            reducePoemToWordIfPossible(poemToDisconnectFrom);
//        } else {
//            // To add functionality where we can remove a word from the middle of a poem, put it here
//            // Use the poem.splitPoemAt() and the row.splitRowAt() functions
//            System.out.println("Error disconnecting " + word.getValue() + " from " + poemToDisconnectFrom.getValue());
//            successful = false;
//        }
//
//        // Disconnect the two entities
//        if(poemViewToDisconnectFrom.removeEdgeWordView(wordView)) {
//
//            // Update the main view object to reflect the change
//            mainView.addProtectedAbstractWordView(wordView);
//
//            // If we split the poem, create two new poems based on the poemToDisconnectFrom and the splitResult
//            if(splitResult != null) {
//                System.out.println("We split the poem. This isn't implemented yet");
////                mainView.addProtectedAbstractWordView(splitResult);
//            } else if(poemViewToDisconnectFrom.getRowViews().size() == 1) {
//                // If the poem is now just one row, convert it to a row
//                RowView rowViewFromPoem = poemViewToDisconnectFrom.getRowViews().get(0);
//                // And then make the appropriate change in the protected area
//                mainView.removeProtectedAbstractWordView(poemViewToDisconnectFrom);
//                mainView.addProtectedAbstractWordView(rowViewFromPoem);
//            }
//        } else {
//            // To add functionality where we can remove a wordView from the middle of a poem, put it here
//            // Use the poem.splitPoemAt() and the row.splitRowAt() functions
//            System.out.println("Error disconnecting " + word.getValue() + " from " + poemToDisconnectFrom.getValue());
//            successful = false;
//        }

        return false;
    }

    @Override
    public boolean visit(PoemView poemViewToDisconnectFrom, RowView rowView) {
        Poem poemToDisconnectFrom = poemViewToDisconnectFrom.getWord();
        Row row = rowView.getWord();
        boolean successful = true;
        Poem splitResult = null;

        // Disconnect the two entities
        if(poemToDisconnectFrom.disconnect(row)) {
            // Handles the case where we can disconnect a row from either
            // the top or bottom of a poem, but not middle

            // Update the area to reflect the updated words
            protectedArea.addAbstractWord(row);
            // If the row is just one word, convert it to a word
            reduceRowToWordIfPossible(row);
            // If the poem is just one row or just one word, convert it to a row or word
            reducePoemToWordIfPossible(poemToDisconnectFrom);
        } else {
            // Handles removing a row from the middle of a poem
            // Split the poem so the row we're removing is at the end of the first poem
            splitResult = poemToDisconnectFrom.splitPoemAt(row);
            // Update the area to reflect the updated words
            protectedArea.addAbstractWord(splitResult);
            // If the poem is just one row or just one word, convert it to a row or word
            reducePoemToWordIfPossible(splitResult);

            // Now the row is at the end of poemToDisconnectFrom so we can disconnect it
            if(poemToDisconnectFrom.disconnect(row)) {
                // Update the area to reflect the updated words
                protectedArea.addAbstractWord(row);
                // If the row is just one word, convert it to a word
                reduceRowToWordIfPossible(row);
                // If the poem is just one row or just one word, convert it to a row or word
                reducePoemToWordIfPossible(poemToDisconnectFrom);
            } else {
                System.out.println("Error disconnecting " + row.getValue() + " from " + poemToDisconnectFrom.getValue());
                successful = false;
            }
        }

        // Disconnect the wordView so the view reflects the entity change
        if(poemViewToDisconnectFrom.removeRowView(rowView)) {
            // Update the main view object to reflect the change
            mainView.addProtectedAbstractWordView(rowView);
            // If the rowView is just one wordView, convert it to a wordView
            reduceRowViewToWordViewIfPossible(rowView);
            // If the poemView is now just one rowView, convert it to a rowView
            reducePoemViewToWordViewIfPossible(poemViewToDisconnectFrom);
        } else {
            // Remove the old rowViews from the poemView
            List<RowView> rowViewList = poemViewToDisconnectFrom.getRowViews();
            // Remove rowViews from the end of the list until we get to the rowView we just split on
            RowView lastRowViewInPoem = rowViewList.get(rowViewList.size()-1);
            // Keep track of the first one in the new poem so we know its position
            RowView firstRowViewInSplitResult = null;
            while(!rowView.equals(lastRowViewInPoem)) {
                // Remove the row from the poem and add it to the pool of words in the mainView
                poemViewToDisconnectFrom.removeRowView(lastRowViewInPoem);
                mainView.addProtectedAbstractWordView(lastRowViewInPoem);
                // Get the last one in the list
                firstRowViewInSplitResult = lastRowViewInPoem;
                lastRowViewInPoem = rowViewList.get(rowViewList.size()-1);
            }
            // The poemView now only has the RowViews it should
            // And firstRowViewInSplitResult is the first RowView in the split poem view

            // Create a new PoemView for the second poem
            // Use the position of the first row in the poem when creating it
            // All rows should be available in the mainView so it should be able to construct using those rowViews
            PoemView splitPoemView = new PoemView(splitResult, firstRowViewInSplitResult.getPosition(), mainView);
            mainView.addProtectedAbstractWordView(splitPoemView);
            // if the splitPoemView is just one rowView, reduce it
            reducePoemViewToWordViewIfPossible(splitPoemView);

            // Now the rowView is at the end of poemViewToDisconnectFrom so we can
            // disconnect the rowView so the view reflects the entity change
            if(poemViewToDisconnectFrom.removeRowView(rowView)) {
                // Update the main view object to reflect the change
                mainView.addProtectedAbstractWordView(rowView);
                // If the rowView is just one wordView, convert it to a wordView
                reduceRowViewToWordViewIfPossible(rowView);
                // If the poemView is now just one rowView, convert it to a rowView
                reducePoemViewToWordViewIfPossible(poemViewToDisconnectFrom);
            } else {
                System.out.println("Error disconnecting view for " + row.getValue() + " from view for " + poemToDisconnectFrom.getValue());
                successful = false;
            }
        }
        // Move the row to the appropriate position
        // This also updates the positions of all the words in the row
        poemViewToDisconnectFrom.moveTo(poemViewToDisconnectFrom.getRowViews().get(0).getPosition());
        return successful;
    }

    @Override
    public boolean visit(PoemView poemViewToDisconnectFrom, PoemView poemView) {
        // Cannot disconnect a poem from a poem, so do nothing
        return false;
    }

    /**
     * Helper method for reducing poems that only have one row to just a row
     * @param poem The poem to try to reduce
     */
    void reducePoemToWordIfPossible(Poem poem) {
        // If the poem is now just one row, convert it to a row
        if(poem.getRows().size() == 1) {
            Row rowFromPoem = poem.getRows().get(0);
            // And then make the appropriate change in the protected area
            protectedArea.removeAbstractWord(poem);
            protectedArea.addAbstractWord(rowFromPoem);

            // If the row is just one word, convert it to a word
            reduceRowToWordIfPossible(rowFromPoem);
        }
    }

    /**
     * Helper method for reducing rows that only have one word to just a word
     * @param row The row to try to reduce
     */
    void reduceRowToWordIfPossible(Row row) {
        // If the row is just one word, convert it to a word
        if(row.getWords().size() == 1) {
            Word wordFromRowOne = row.getWords().get(0);
            // And then make the appropriate change in the protected area
            protectedArea.removeAbstractWord(row);
            protectedArea.addAbstractWord(wordFromRowOne);
        }
    }

    /**
     * Helper method for reducing PoemViews that only have one RowView to just a RowView
     * @param poemView The PoemView to try to reduce
     */
    void reducePoemViewToWordViewIfPossible(PoemView poemView) {
        // If the poemView is now just one rowView, convert it to a rowView
        if(poemView.getRowViews().size() == 1) {
            RowView rowViewFromPoem = poemView.getRowViews().get(0);
            // And then make the appropriate change in the mainView
            mainView.removeProtectedAbstractWordView(poemView);
            mainView.addProtectedAbstractWordView(rowViewFromPoem);

            // If the rowView is just one wordView, convert it to a wordView
            reduceRowViewToWordViewIfPossible(rowViewFromPoem);
        }
    }

    /**
     * Helper method for reducing RowViews that only have one WordView to just a WordView
     * @param rowView The RowView to try to reduce
     */
    void reduceRowViewToWordViewIfPossible(RowView rowView) {
        // If the rowView is just one wordView, convert it to a wordView
        if(rowView.getWordViews().size() == 1) {
            WordView wordViewFromRowOne = rowView.getWordViews().get(0);
            // And then make the appropriate change in the mainView
            mainView.removeProtectedAbstractWordView(rowView);
            mainView.addProtectedAbstractWordView(wordViewFromRowOne);
        }
    }
}
