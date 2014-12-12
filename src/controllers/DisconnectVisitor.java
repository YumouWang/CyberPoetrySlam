package controllers;

import models.*;
import views.AbstractWordView;
import views.MainView;
import views.PoemView;
import views.RowView;
import views.WordView;

import java.util.ArrayList;
import java.util.List;

/**
 * A realization of the AbstractWordViewVisitor interface for disconnecting two words
 *
 * @author Nathan
 * @version 10/15/2014
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
            rowToDisconnectFrom.disconnect(word);
            // Update the area to reflect the updated words
            protectedArea.addAbstractWord(word);
            // If the poem is just one row or just one word, convert it to a row or word
            reduceRowToWordIfPossible(rowToDisconnectFrom);
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
            rowViewToDisconnectFrom.removeWordView(wordView);
            // Update the main view object to reflect the change
            mainView.addProtectedAbstractWordView(wordView);
            // If the rowView is now just one wordView, convert it to a wordView
            reduceRowViewToWordViewIfPossible(rowViewToDisconnectFrom);
        }
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
        Poem poemToDisconnectFrom = poemViewToDisconnectFrom.getWord();
        Word word = wordView.getWord();
        boolean successful = true;
        Poem splitResult = null;

        // Disconnect the two entities
        if(poemToDisconnectFrom.disconnectEdgeWord(word)) {
            // Update the area to reflect the updated words
            protectedArea.addAbstractWord(word);
            // Revalidate checks for empty rows and removes an empty row at the start
            // or end of the poem or splits the poem at an empty row if there is one
            // (It ensures that there are no empty rows in the poem)
            splitResult = poemToDisconnectFrom.revalidate();

            // If the poem is now just one row, convert it to a row
            reducePoemToWordIfPossible(poemToDisconnectFrom);

            // If we split the poem, add the result to the protectedArea
            if(splitResult != null) {
                // Reduce the splitResult to the smallest type it can be
                reducePoemToWordIfPossible(splitResult);
                // Add the result to the protected area
                protectedArea.addAbstractWord(splitResult);
            }
        } else {
            // To add functionality where we can remove a word from the middle of a poem, put it here
            // Use the poem.splitPoemAt() and the row.splitRowAt() functions
            // This would be complicated and the desired behavior is undefined, so this is intentionally not implemented
            System.out.println("Error disconnecting " + word.getValue() + " from " + poemToDisconnectFrom.getValue());
            successful = false;
        }

        // Synchronize the view with the entities
        if(poemViewToDisconnectFrom.removeEdgeWordView(wordView)) {
            // Update the main view object to reflect the change
            mainView.addProtectedAbstractWordView(wordView);

            // Get the empty row, if there was one
            RowView emptyRow = poemViewToDisconnectFrom.getEmptyRowView();
            // If we removed the last word from a row the we have to do special stuff
            // Otherwise, we're already done
            if(emptyRow != null) {
                // If we split the poem, create two new poems based on the poemToDisconnectFrom and the splitResult
                // If we did not split the poem, then the emptyRow was either the first or last row of the poem
                if(splitResult == null) {
                    // Now the rowView is at the end of poemViewToDisconnectFrom so we can
                    // disconnect the rowView so the view reflects the entity change
                    poemViewToDisconnectFrom.removeRowView(emptyRow);
                    // Do NOT add the empty row to the mainView
                    // Move the poem so all the words are in the appropriate location
                    poemViewToDisconnectFrom.moveTo(poemViewToDisconnectFrom.getRowViews().get(0).getPosition());
                    // If the poemView is now just one rowView, convert it to a rowView
                    reducePoemViewToWordViewIfPossible(poemViewToDisconnectFrom);
                } else {
                    // Otherwise, we removed the last word from a row in the middle of the poem,
                    // So we have to split the poem
                    // Remove the old rowViews from the poemView
                    List<RowView> rowViewList = poemViewToDisconnectFrom.getRowViews();
                    // Get the last RowView in the PoemView
                    RowView lastRowViewInPoem = rowViewList.get(rowViewList.size() - 1);
                    // Keep track of the first one in the new poem so we know its position
                    RowView firstRowViewInSplitResult = null;
                    // Keep track of all the rowViews in the splitPoemView so we can remove them from mainView after we make the view
                    List<RowView> resultRowViewList = new ArrayList<RowView>();
                    // Remove rowViews from the end of the list until we get to the emptyRow we just split on
                    while(!emptyRow.equals(lastRowViewInPoem)) {
                        // Remove the row from the poem and add it to the pool of words in the mainView
                        poemViewToDisconnectFrom.removeRowView(lastRowViewInPoem);
                        mainView.addProtectedAbstractWordView(lastRowViewInPoem);

                        // Get the last one in the list
                        resultRowViewList.add(lastRowViewInPoem);
                        firstRowViewInSplitResult = lastRowViewInPoem;
                        lastRowViewInPoem = rowViewList.get(rowViewList.size() - 1);
                    }
                    // lastRowViewInPoem is currently the empty row, so remove it
                    poemViewToDisconnectFrom.removeRowView(emptyRow);
                    reducePoemViewToWordViewIfPossible(poemViewToDisconnectFrom);
                    // The poemView now only has the RowViews it should
                    // And firstRowViewInSplitResult is the first RowView in the split poem view

                    // Create a new PoemView for the second poem
                    // Use the position of the first row in the poem when creating it1
                    // All rows should be available in the mainView so it should be able to construct using those rowViews
                    PoemView splitPoemView = new PoemView(splitResult, firstRowViewInSplitResult.getPosition(), mainView);
                    mainView.addProtectedAbstractWordView(splitPoemView);
                    // Remove the wordViews used in making the splitPoemView
                    for(RowView view : resultRowViewList) {
                        mainView.removeProtectedAbstractWordView(view);
                    }
                    // if the splitPoemView is just one rowView, reduce it
                    reducePoemViewToWordViewIfPossible(splitPoemView);
                }
            }
        } else {
            // To add functionality where we can remove a wordView from the middle of a poem, put it here
            // Use the poem.splitPoemAt() and the row.splitRowAt() functions
            // This would be complicated and the desired behavior is undefined, so this is intentionally not implemented
            System.out.println("Error disconnecting view for " + word.getValue() + " from view for "
                    + poemToDisconnectFrom.getValue());
            successful = false;
        }

        return successful;
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
            poemToDisconnectFrom.disconnect(row);
            // Update the area to reflect the updated words
            protectedArea.addAbstractWord(row);
            // If the row is just one word, convert it to a word
            reduceRowToWordIfPossible(row);
            // If the poem is just one row or just one word, convert it to a row or word
            reducePoemToWordIfPossible(poemToDisconnectFrom);
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
            // Keep track of all the rowViews in the splitPoemView so we can remove them from mainView after we make the view
            List<RowView> resultRowViewList = new ArrayList<RowView>();
            while(!rowView.equals(lastRowViewInPoem)) {
                // Remove the row from the poem and add it to the pool of words in the mainView
                poemViewToDisconnectFrom.removeRowView(lastRowViewInPoem);
                mainView.addProtectedAbstractWordView(lastRowViewInPoem);
                // Get the last one in the list
                resultRowViewList.add(lastRowViewInPoem);
                firstRowViewInSplitResult = lastRowViewInPoem;
                lastRowViewInPoem = rowViewList.get(rowViewList.size() - 1);
            }
            // The poemView now only has the RowViews it should
            // And firstRowViewInSplitResult is the first RowView in the split poem view

            // Create a new PoemView for the second poem
            // Use the position of the first row in the poem when creating it
            // All rows should be available in the mainView so it should be able to construct using those rowViews
            PoemView splitPoemView = new PoemView(splitResult, firstRowViewInSplitResult.getPosition(), mainView);
            mainView.addProtectedAbstractWordView(splitPoemView);
            // Remove the wordViews used in making the splitPoemView
            for(RowView view : resultRowViewList) {
                mainView.removeProtectedAbstractWordView(view);
            }
            // if the splitPoemView is just one rowView, reduce it
            reducePoemViewToWordViewIfPossible(splitPoemView);

            // Now the rowView is at the end of poemViewToDisconnectFrom so we can
            // disconnect the rowView so the view reflects the entity change
            poemViewToDisconnectFrom.removeRowView(rowView);
            // Update the main view object to reflect the change
            mainView.addProtectedAbstractWordView(rowView);
            // If the rowView is just one wordView, convert it to a wordView
            reduceRowViewToWordViewIfPossible(rowView);
            // If the poemView is now just one rowView, convert it to a rowView
            reducePoemViewToWordViewIfPossible(poemViewToDisconnectFrom);
        }
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
